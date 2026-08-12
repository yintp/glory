# 跨主题高频面试 Q&A

> **一句话定位**：面试前冲刺用，41 题速答串联各主题，附连环套问思维导图。
> **面试热度**：⭐⭐⭐⭐⭐
> **返回**：[ES 知识图谱](../README.md)

---

## 使用说明

- 全部 41 题按主题分类，每题 3-5 句要点速答，末尾 **关联** 链接指向对应主题文档的详细推导。
- 连环追问题在题号后标注 🔗，配合文末「连环套问思维导图」把握面试官的追问路径。
- 建议先盖住答案自答，再对照要点查漏，最后跳转关联文档补全原理。
- 版本基线 Elasticsearch 8.x，7.x 仅作差异对比。
- 答案只给「要点 + 关键数字 + 为什么」，不展开推导——推导在关联文档里。

**各篇题目数与关联文档**：

| 篇章 | 题目数 | 关联文档 |
|------|--------|---------|
| 一、架构与部署篇 | 6 题（Q1-Q6） | [架构与部署拓扑](./01-architecture/architecture-and-topology.md) |
| 二、索引与映射篇 | 6 题（Q7-Q12） | [索引与映射](./02-index-mapping/index-and-mapping.md) |
| 三、倒排索引与分词篇 | 5 题（Q13-Q17） | [倒排索引与分词](./03-inverted-index/inverted-index-and-analysis.md) |
| 四、读写流程与 Translog 篇 | 5 题（Q18-Q22） | [读写流程与 Translog](./04-read-write-translog/read-write-and-translog.md) |
| 五、查询 DSL 与打分篇 | 6 题（Q23-Q28） | [查询 DSL 与打分](./05-query-dsl-scoring/query-dsl-and-scoring.md) |
| 六、聚合篇 | 4 题（Q29-Q32） | [聚合与 Pipeline](./06-aggregation/aggregation-and-pipeline.md) |
| 七、分片路由与 Reindex 篇 | 5 题（Q33-Q37） | [分片路由与 Reindex](./07-shard-routing/shard-routing-and-reindex.md) |
| 八、高可用与调优篇 | 4 题（Q38-Q41） | [高可用与调优](./08-ha-tuning/ha-and-tuning.md) |
| 合计 | **41 题** | 8 份主题文档 |

---

## 一、架构与部署篇（6 题）

### Q1: ES 有哪些节点角色？各自职责？🔗

**答**：五大角色——①**Master**（维护 ClusterState 元数据、选举主节点、分配 Shard，生产推荐 3 个专用 `node.roles: [master]`）；②**Data**（存储 Shard 执行读写与聚合，按分层可细分为 `data_hot`/`data_warm`/`data_cold`）；③**Coordinating**（`node.roles: []` 空列表，接收客户端请求、Scatter-Gather 路由、结果归并）；④**Ingest**（写入前 Pipeline 预处理，Grok/Script/Enrich）；⑤**Machine Learning**（X-Pack ML 任务，需 Platinum 许可）。8.x 用 `node.roles` 列表替代 7.x 的布尔开关，默认 `[data, master, ingest]` 三位一体，生产必须角色分离避免 Master 被读写拖累。

**关联**：→ [架构与部署拓扑](./01-architecture/architecture-and-topology.md)

### Q2: Master 怎么选出来的？Zen2 是什么？🔗

**答**：ES 用 **Zen2** 自研发现协议（7.x 起替代旧 Zen，8.x 继续沿用）选举 Master——Master 候选节点（`node.roles` 含 `master`）检测到无 Master 心跳（`cluster.election.duration` 默认 10s）后发起选举，向 **Voting Configuration**（有投票权节点集合）中所有节点请求投票，获得**多数派**（`floor(N/2)+1`）投票则当选。Zen2 是 Raft-like 变种，不是标准 Raft，但借鉴了 Raft 的多数派思想。关键设计是 Voting Configuration 动态维护——节点加入时 Master 提议加入，多数派同意后生效，无需人工配 `minimum_master_nodes`（7.x 之前的旧方案）。

**关联**：→ [架构与部署拓扑](./01-architecture/architecture-and-topology.md)

### Q3: 为什么不用 ZooKeeper？🔗

**答**：核心论点是**自研更轻量，无外部依赖**。ES 的 ClusterState 是低频写的元数据（Index 创建、节点上下线），ZK 的 CP 强一致 + ZAB 协议是过度设计——为了 99.999% 强一致付出 ZAB 协议、Leader 选举、跨节点同步的复杂度代价，还要额外运维一个 ZK 集群。ES 团队的取舍是"Zen2 自研 Raft 变种 + 内嵌发现协议"换"极简架构 + 无外部依赖"——元数据中心与数据节点同进程，部署只需一套 JVM。对比 RocketMQ NameServer 选 AP（路由可短暂不一致），ES Master 必须选 CP（ClusterState 分裂会导致数据分裂）。

**关联**：→ [架构与部署拓扑](./01-architecture/architecture-and-topology.md)

### Q4: 脑裂怎么避免？Voting Configuration 是什么？🔗

**答**：**Voting Configuration 多数派天然防脑裂**。Voting Configuration 是一个动态的"有投票权节点集合"，存储在 ClusterState 中，选举 Master 需获得该集合中多数派（`floor(N/2)+1`）投票。3 节点集群网络分区成 1+2，只有 2 节点分区能获得多数派（≥2）选举 Master，1 节点分区无法获得多数派不会选举，只能等待分区恢复。7.x 之前用 `discovery.zen.minimum_master_nodes`（人工配置）防脑裂，配置不当（如配成 1）易脑裂；7.x 起 Zen2 自动维护 Voting Configuration，无需人工配置。关键原理：只要 Voting Configuration 成员数是奇数（3/5/7），就能容忍 `floor(N/2)` 个节点故障。

**关联**：→ [架构与部署拓扑](./01-architecture/architecture-and-topology.md)

### Q5: 协调节点做什么？Scatter-Gather 是什么？🔗

**答**：Coordinating 节点（`node.roles: []` 空列表）**接收客户端请求、按分片路由 Scatter-Gather、归并结果**。查询请求时协调节点查 ClusterState 路由表确定目标 Shard 列表，**并行分发（Scatter）** 到各 Shard 所在 Data 节点，收集（Gather）各 Shard 部分结果后归并（排序、聚合、分页）。写入请求时按 `hash(routing) % num_primary_shards` 定位目标 Shard 直接转发。普通查询各 Shard 返回 `from+size` 条归并，所以 `from+size` 默认上限 10000（深度分页用 search_after）。生产高负载场景建议独立部署 Coordinating 节点，避免读写争抢 Master 角色资源。

**关联**：→ [架构与部署拓扑](./01-architecture/architecture-and-topology.md)

### Q6: Index/Shard/Replica 的关系？分片数能改吗？🔗

**答**：**Index 是逻辑命名空间，Shard 是并行单位，Replica 是副本**。Index 类似 MySQL 的 database，Shard 是 Index 的物理分片（每个是一个 Lucene Index），Primary Shard 接收写入首选，Replica Shard 是 Primary 的副本用于读负载均衡和故障转移。**主分片数创建后不可变**（因为路由公式 `hash(routing) % num_primary_shards` 含分片数，改了路由失效），要改只能 Reindex 重建；**副本数可动态调整**（`PUT _settings` 改 `number_of_replicas`，不影响路由）。生产规划：单 Shard 30-50GB，主分片数按 `总数据量 / 50GB` 估算，副本数 1-2（高可用 1 副本，高读吞吐 2 副本）。

**关联**：→ [架构与部署拓扑](./01-architecture/architecture-and-topology.md)

---

## 二、索引与映射篇（6 题）

### Q7: ES 有哪些字段类型？keyword 和 text 区别？🔗

**答**：核心分四类——①**字符串**：keyword（不分词精确匹配）、text（分词全文检索）、wildcard（8.x 通配符）；②**数值**：long/integer/short/byte、double/float/scaled_float（scaled_float 用 long 存放大 100 倍的整数，精度最佳）；③**时序**：date（毫秒时间戳）、ip；④**对象**：object（扁平化）、nested（独立文档）、flattened（8.x 整对象一个 keyword）；⑤**向量**：dense_vector（8.x KNN 检索）。**keyword vs text**：keyword 不分词整个值作为一个 token 建倒排，适合 term 精确匹配、聚合、排序（标签、品牌、状态码）；text 分词后每个 token 各建一条倒排，适合 match 全文检索（标题、正文），但不能直接聚合（要走 `.keyword` 子字段）。生产惯例：标题/正文用 text + ik 分词 + keyword 子字段兼顾两者。

**关联**：→ [索引与映射](./02-index-mapping/index-and-mapping.md)

### Q8: Dynamic Mapping 是什么？有什么坑？🔗

**答**：Dynamic Mapping 是 ES 区别于 MySQL 的核心特性——写入未知字段时**自动推断 JSON 类型并生成 Mapping**，无需预先 DDL。三大坑：①**字段爆炸**——日志场景每条日志字段名不同，Dynamic Mapping 为每个字段建 Mapping，集群元数据膨胀、Master OOM，必加 `index.mapping.total_fields.limit`（默认 1000）；②**类型冲突**——第一条 `price` 是整数推断为 long，第二条带小数写入失败，只能 Reindex 重建；③**日期误判**——字符串 `2026-08-12` 被推断为 date，但若是订单号则类型错配。`dynamic` 三种模式：`true`（默认自动推断）、`runtime`（8.x 未知字段转 Runtime Field 不索引）、`strict`（拒绝未知字段）。生产推荐核心业务 `dynamic: strict`，日志用 Dynamic Template 把字符串默认设为 keyword。

**关联**：→ [索引与映射](./02-index-mapping/index-and-mapping.md)

### Q9: nested 和 object 区别？🔗

**答**：**object 扁平化**——数组内对象的字段被打散成数组，对象间关联关系丢失。如 `[{name: red, qty: 1}, {name: blue, qty: 2}]` 被存成 `name: [red, blue]` + `qty: [1, 2]`，查询 `name=red AND qty=2` 误命中（red 的 qty 实际是 1）。**nested 独立文档**——每个对象作为独立 Lucene 文档索引，保持对象边界，查询精确。代价：N 个对象 = N 个文档，写入和存储开销大，查询要用 `nested` 查询语法。选型：数组内对象需保持关联（如商品 SKU、订单明细）用 nested；单层对象不需关联用 object；动态字段多的对象省字段数用 flattened（8.x）。生产事故高频根源就是把本该用 nested 的对象数组设为 object 导致查询误命中。

**关联**：→ [索引与映射](./02-index-mapping/index-and-mapping.md)

### Q10: Runtime Field 是什么？8.x 新特性？🔗

**答**：8.0 引入的**运行时计算字段**——不在索引时计算和存储，查询时按 Painless 脚本实时计算。优势：①新增字段无需 Reindex（改 Mapping 即生效）；②不占磁盘（仅定义在 Mapping）。劣势：查询慢（每条文档都要跑脚本），数据量大时延迟显著。适用场景：①探索期字段不稳定，先 Runtime Field 试，稳定后转 indexed；②修复历史数据类型错误（如 price 原本是 string），临时用 Runtime Field 转换供查询，同时 Reindex 修类型；③低频查询字段不值得占磁盘。生产原则：Runtime Field 是过渡态，高频字段最终要转 indexed（Reindex 或新建索引）。

**关联**：→ [索引与映射](./02-index-mapping/index-and-mapping.md)

### Q11: 别名有什么用？Index Template 是什么？🔗

**答**：**别名**是指向一个或多个 Index 的"软链接"，核心价值：①**零停机切换**——Reindex 重建索引时新旧 Index 共享别名，切别名指向即完成迁移，客户端无感；②**多索引查询**——日志按天滚动（`logs-2026.08.11`、`logs-2026.08.12`）共享别名 `logs`，查别名即查所有天数据并归并；③**写入路由**——`is_write_index: true` 指定别名关联的多个 Index 中哪个接收写入。**Index Template**是新 Index 创建时的"模板"——按 `index_patterns` 匹配 Index 名，匹配则自动应用预定义的 Settings、Mappings、Aliases。日志按天滚动场景必备：新一天的 Index 自动套用模板，无需人工配置。8.x 引入 Composable Template（`_component_template` 可复用片段 + `_index_template` 引用组合）。

**关联**：→ [索引与映射](./02-index-mapping/index-and-mapping.md)

### Q12: ILM 索引生命周期是什么？Hot-Warm-Cold 怎么实现？🔗

**答**：**ILM（Index Lifecycle Management）** 是 ES 管理"索引生命周期"的自动化机制——按索引年龄或大小触发阶段流转，自动 Rollover、Shrink、Force-merge、Delete。四阶段：**Hot**（新索引、写入+高频查询、data_hot SSD 节点）→ **Warm**（age 1d、shrink 缩分片、forcemerge 合并段、data_warm HDD 节点）→ **Cold**（age 7d、转 searchable snapshot 仅元数据本地、data_cold 节点）→ **Delete**（age 90d、删除索引）。关键动作：**Rollover**（按 max_age/max_size/max_docs 滚动新建索引）、**Shrink**（缩分片数）、**Force-merge**（合并段到指定数）、**Searchable Snapshot**（数据迁 S3 仅元数据本地）。日志场景标配：按天滚动 + Hot 1d→Warm 7d→Cold 30d→Delete 90d，自动省存储和运维。

**关联**：→ [索引与映射](./02-index-mapping/index-and-mapping.md)

---

## 三、倒排索引与分词篇（5 题）

### Q13: 倒排索引是什么？和 B+Tree 有什么区别？🔗

**答**：倒排索引是"从词找文档"的数据结构，由三部分组成：**Term Dictionary**（词典，按 term 有序）、**Term Index**（词典索引，用 FST 前缀压缩加速定位）、**Posting List**（倒排列表，每个 term 对应的 doc_id 列表 + 词频 + 位置）。查询时先在 FST 上找块，再块内二分定位 term，最后取 Posting List 拿命中文档。**与 B+Tree 区别**：B+Tree 擅长"给定 key 找行"（等值/范围扫描），倒排擅长"给定词找所有包含它的文档"——多词组合查询变 Posting List 集合运算，O(文档数) 内完成。ES 定位全文检索 + 聚合分析，倒排是天然匹配；MySQL 定位 OLTP 事务型精确查询，B+Tree 更合适。MySQL 5.6 起的 InnoDB Fulltext 也是倒排，但功能弱（无分词链、无 BM25、无聚合），生产全文检索仍首选 ES。

**关联**：→ [倒排索引与分词](./03-inverted-index/inverted-index-and-analysis.md)

### Q14: FST 是什么？Term Index 怎么加速？🔗

**答**：**FST（Finite State Transducer，有限状态转换器）** 是把字符串集合压缩为 DAG（有向无环图）的数据结构——公共前缀共享边、公共后缀共享终点。百万级 term 经 FST 压缩后堆内存占用从 GB 级降为 MB 级。FST 常驻 JVM 堆作为 **Term Index**，加速 Term Dictionary 的块定位。查询流程：给定查询词，先在 FST 上沿状态机匹配——走通到某个终点，该终点记录"该 term 在 Term Dictionary 的块号"；按块号从磁盘读入该块 Term Dictionary（按字典序排好的 term 数组），块内二分查找精确定位；拿到其 Posting List 指针，读 Posting List 取命中文档。对比 Redis 的 dict（哈希表，每 key 独立存），FST 在大量公共前缀场景下内存效率高一个数量级。

**关联**：→ [倒排索引与分词](./03-inverted-index/inverted-index-and-analysis.md)

### Q15: Posting List 怎么压缩？Roaring Bitmap 是什么？🔗

**答**：两层压缩：①**Frame of Reference（FOR，变长增量编码）**——Posting List 按 doc_id 升序后做增量编码（差值），再分块（256 一组）选最小位宽变长存储，压缩比 3-5 倍；②**Roaring Bitmap（咆哮位图）**——把整个 doc_id 空间分为高 16 位桶（2^16 = 65536 个桶），每个桶内根据数量选容器：桶内 doc 数 ≤ 4096 用**有序数组**（省内存，二分查找），> 4096 用**位图**（2^16 bit = 8KB，O(1) 查找），桶内只有一个值用**单值容器**。多 term 查询（如 `match: "分布式 锁"` → term `分布式` AND term `锁`）要对两条 Posting List 做集合运算，Roaring Bitmap 把运算分桶——只有两 bitmap 都有的桶才参与运算，同桶内按容器类型选高效算法（位图 AND 用位运算、数组 AND 用归并排序），整体复杂度远低于线性扫描。

**关联**：→ [倒排索引与分词](./03-inverted-index/inverted-index-and-analysis.md)

### Q16: doc_values 是什么？和 _field_data 区别？🔗

**答**：**doc_values 是磁盘列存**，每字段独立一列按 doc_id 排序存值，用于排序、聚合、脚本取字段值。通过 mmap 映射到 OS page cache，**不占 JVM 堆**。**_field_data 是堆内列存**，doc_values 不可用时兜底（如 text 字段聚合），但吃堆易 OOM，ES 5.x 起默认关闭。区别：doc_values 磁盘 + mmap 不吃堆，_field_data 堆内吃堆引发 GC。text 字段默认不开 doc_values（分词后每个 token 一条倒排链，再建 doc_values 成本翻倍），要聚合推荐用 `.keyword` 子字段而非开 _field_data。ES 的"JVM heap 50% 规则"正是为留出另一半物理内存给 mmap file cache，让 doc_values 和 segment 的读取走 OS cache。

**关联**：→ [倒排索引与分词](./03-inverted-index/inverted-index-and-analysis.md)

### Q17: Analyzer 分几步？ik 分词器是什么？索引时和查询时分词不一致会怎样？🔗

**答**：Analyzer 是三段式分词链：①**Character Filter**（分词前改原始文本，html_strip 去标签、mapping 字符替换）；②**Tokenizer**（切 token 决定粒度，standard/ik/whitespace，只能选一个）；③**Token Filter**（对 token 后处理，lowercase/stop/synonym/word_delimiter/stemmer，可串联多个）。**ik 是开源中文分词插件**，两种模式：`ik_smart` 粗粒度（`中文分词` → `[中文分词]` 整体一个 token，精度高），`ik_max_word` 细粒度（`中文分词` → `[中文, 分词, 中文分词]` 多 token，召回全）。生产标配：索引时 `analyzer: ik_max_word`（召回全），查询时 `search_analyzer: ik_smart`（精度高）。**分词不一致会"搜不到"**——索引用 A 分词器入倒排，查询用 B 分词器产出 token 不在倒排里就 miss。排查用 `_analyze` API 比较两侧分词结果。注意：改 `analyzer` 要重建索引（旧数据是旧分词器入的倒排），改 `search_analyzer` 不用重建。

**关联**：→ [倒排索引与分词](./03-inverted-index/inverted-index-and-analysis.md)

---

## 四、读写流程与 Translog 篇（5 题）

### Q18: 写后为什么 1s 才能搜到？refresh 是什么？🔗

**答**：因为 ES 是**近实时**系统，写请求只把数据写进 index buffer + translog，index buffer 是 JVM 堆内的内存结构，**查询读不到**。要等一次 **refresh**（默认每 1 秒触发）把 index buffer 物化为一个新 segment，segment 加入 `SearcherManager` 后查询才能读到。所以"写后 1 秒可见"的本质是 `refresh_interval` 默认 1s。refresh 不 fsync segment（segment 写到内存目录或 page cache，断电仍可能丢，真正持久化要等 flush）。要立即可见可发 `?refresh=true` 强制刷新，但高频用会破坏吞吐（每次 refresh 生成一个 segment，segment 数膨胀，查询要合并更多 segment）。调优：高吞吐场景调大到 `30s` 减 segment 数量；批量导入设 `-1` 禁用自动 refresh，导完恢复。

**关联**：→ [读写流程与 Translog](./04-read-write-translog/read-write-and-translog.md)

### Q19: translog 是什么？和 MySQL Redo Log 有什么关系？🔗

**答**：**Translog（Transaction Log）** 是 ES 的**事务日志**，采用 **WAL（Write-Ahead Log，预写日志）** 思想——每次写请求在修改 index buffer 前，先把操作记录追加到 translog，fsync 到磁盘后才返回成功。崩溃恢复时 ES 按 translog 回放未 flush 的操作，重建 index buffer 到 refresh 前状态。**与 MySQL Redo Log 的关系**：两者都是 WAL——先写日志再改数据，fsync 保证不丢。差异：①日志类型——ES 是操作日志（index/update/delete 序列化），MySQL 是物理日志（页的物理变更字节）；②可见性——ES refresh 后近实时可见（1s），MySQL 事务提交后立即可见；③用途——ES translog 仅崩溃恢复（复制走 primary→replica 同步写），MySQL Redo Log 既崩溃恢复又主从复制（binlog 才是复制日志）。与 Redis AOF 也同构，但 ES 多了 refresh 这一层"近实时可见"机制。

**关联**：→ [读写流程与 Translog](./04-read-write-translog/read-write-and-translog.md)

### Q20: refresh 和 flush 区别？🔗

**答**：简记**refresh 管可搜，flush 管持久**。①**refresh**——index buffer → 新 segment（内存/page cache），让写入可搜，不 fsync segment，不清 translog，频率默认 1s，代价低；②**flush**——segment fsync 落盘 + 清空 translog + 写新 commit point，让 segment 真正持久化，频率默认 translog 达 512MB（`flush_threshold_size`），代价高（fsync 所有未落盘 segment）。写后 1 秒可搜靠 refresh，崩溃不丢靠 flush（+ translog）。flush 是重操作，不应高频触发，生产依赖默认 translog 阈值自动 flush 即可。translog 阈值调优：写入量大场景调大到 `1gb`/`2gb` 减少 flush 频率，内存紧张调小到 `256mb` 但 flush 更频繁。

**关联**：→ [读写流程与 Translog](./04-read-write-translog/read-write-and-translog.md)

### Q21: ES 怎么保证写不丢？translog 怎么刷盘？🔗

**答**：三层保障：①**translog fsync**——`index.translog.durability` 控制：`request`（默认）每条写请求 fsync，0 丢失但 fsync 阻塞（SSD QPS 约 10 万/节点）；`async` 后台定时 fsync（`sync_interval` 默认 30s），最多丢 30 秒但无 fsync 阻塞吞吐高；②**副本数**——primary 写完后同步给 replica，即使 primary 节点宕机，副本仍有数据；③**wait_for_active_shards**——可设 `quorum` 或 `all`，确保多数/全部副本有数据才返回成功。对照 Redis AOF：`request` ≈ `always`（但 ES 多线程不阻塞其他写），`async` ≈ `everysec`（但 ES 默认 30s 比 Redis 1s 丢失窗口大）。生产默认 `request`，高吞吐日志场景可 `async` + `sync_interval=5s`，金融级强一致保持 `request` 并增加副本数。

**关联**：→ [读写流程与 Translog](./04-read-write-translog/read-write-and-translog.md)

### Q22: 怎么做乐观锁？bulk 怎么用？🔗

**答**：**乐观锁**用 `if_seq_no` + `if_primary_term`。流程：①读文档拿到当前 `_seq_no` 和 `_primary_term`；②写回时带 `?if_seq_no=N&if_primary_term=M`，ES 检查当前版本匹配才写入并递增 `_seq_no`；③版本冲突返回 409 Conflict，客户端重读最新版本后重试（CAS 模式）。老式 `?version=N` 已不推荐，外部版本 `?version=N&version_type=external` 适合 MySQL 同步 ES 场景（用 binlog position 作版本）。**bulk** 用 `POST /_bulk`，NDJSON 格式（每两行一组：action 行 + data 行），支持 index/create/update/delete 四种操作。批量大小推荐 **5-15MB**——过小网络开销占比大，过大单请求耗时长且失败重试成本高。客户端用多线程并行 bulk（线程数 ≈ 分片数 × 2）榨干集群吞吐，或用 `BulkProcessor` 自动攒批与重试。bulk 是"部分成功"语义，需遍历响应处理失败 item。

**关联**：→ [读写流程与 Translog](./04-read-write-translog/read-write-and-translog.md)

---

## 五、查询 DSL 与打分篇（6 题）

### Q23: Bool 查询四子句区别？must/should/filter/must_not？🔗

**答**：四子句对照——①**must**：文档必须匹配（AND），**算分**参与排序，不可缓存（算分依赖查询词）；②**should**：文档至少匹配 N 个（N 由 `minimum_should_match` 控制，默认 1），**算分**，不可缓存；③**filter**：文档必须匹配（AND），**不算分**只判断匹配，**可缓存**（bitset 位图，重复查询命中极快）；④**must_not**：文档必须不匹配（NOT），**不算分**，**可缓存**。关键细节：`must` vs `filter` 两者都要求"必须匹配"，但 `must` 算分（适合相关性查询），`filter` 不算分且可缓存（适合精确过滤）。同样的 `term: {brand: "apple"}` 放 `must` 里会算分但品牌是 keyword TF=1 对所有匹配文档一样无区分度，放 `filter` 里不算分更快——所以精确匹配字段应放 `filter`。当 `bool` 里有 `must` 或 `filter` 时，`should` 的 `minimum_should_match` 默认为 0（即 should 变"加分项"而非"必须匹配"）。

**关联**：→ [查询 DSL 与打分](./05-query-dsl-scoring/query-dsl-and-scoring.md)

### Q24: 查询上下文和过滤上下文区别？🔗

**答**：①**查询上下文**（`bool` 的 `must`/`should` 里、或顶层 `match` 等叶子查询里）——ES 会计算文档与查询的相关性得分（`_score`），按 `_score` 排序，适合全文检索场景（找最相关的文档）；②**过滤上下文**（`bool` 的 `filter`/`must_not` 里、或 `constant_score` 里）——ES **不算分**，只判断文档是否匹配（是/否），适合精确过滤场景（如品牌、价格范围、时间范围），filter 结果可缓存（bitset 缓存），重复查询极快。**为什么 filter 不算分还能更快？** ①算分要查 Posting List 的词频（TF）和文档长度（dl）等字段，开销不小；filter 只需取 doc_id 判断存在性，无算分开销；②filter 的结果（命中文档的 doc_id 集合）会被 ES 缓存为 **bitset**（位图），下次相同 filter 查询直接命中缓存，跳过倒排查询；③`must` 每次都要重新算分（因 `_score` 依赖查询词，查询词变则得分变），无法跨查询缓存。实战建议：能用 filter 就别用 must。

**关联**：→ [查询 DSL 与打分](./05-query-dsl-scoring/query-dsl-and-scoring.md)

### Q25: term 和 match 区别？match_phrase 是什么？🔗

**答**：①**term** 不分词原样匹配，适合 `keyword` 字段精确匹配（如 `term: {brand: "apple"}`）；②**match** 分词后 OR 匹配（默认），适合 `text` 字段全文检索（如 `match: {title: "智能手机"}`）——分词后每个 token 查倒排，多词 OR，要 AND 用 `"operator": "and"`；③**match_phrase** 分词后按顺序且位置相邻匹配，适合短语精确匹配（如 `match_phrase: {title: "苹果手机"}` 要求"苹果"和"手机"按此顺序且位置相邻，`slop` 默认 0 控制位置差）。**高频陷阱：term 用在 text 字段查不到**——`term: {title: "iPhone 15"}` 因 title 是 text 索引时被分词为 `["iphone", "15"]`，倒排里只有 `iphone` 和 `15`，而 term 不分词原样查 "iPhone 15"（带空格），倒排里没有，查不到。正确：match 查 text，term 查 keyword。

**关联**：→ [查询 DSL 与打分](./05-query-dsl-scoring/query-dsl-and-scoring.md)

### Q26: BM25 怎么打分？k1 和 b 调什么？🔗

**答**：ES 默认打分算法是 **BM25（Okapi BM25）**，5.0 起替代 TF/IDF。BM25 公式（简化）：`score = IDF(q) × [f×(k1+1)] / [f + k1×(1 - b + b×dl/avgdl)]`。其中 `IDF(q)` 是词的逆文档频率（词越稀有 IDF 越大），`f` 是词频，`dl` 是文档长度，`avgdl` 是平均文档长度。BM25 相比 TF/IDF 两个改进：①**TF 饱和**——TF 项是 `f(k1+1)/(f+k1)`，当词频趋于无穷时该项趋于 `k1+1`（饱和值），不再线性增长（"出现 10 次"和"100 次"得分差异远小于 10 倍）；②**文档长度归一化**——引入 `1 - b + b×(dl/avgdl)` 项，长文档的 TF 项被压低避免虚高。**k1 和 b 调什么**：`k1` 控制 TF 饱和度（默认 1.2，短文本调小如 1.0，长文本调大如 2.0），`b` 控制文档长度归一化强度（默认 0.75，`b=0` 不归一化，`b=1` 全归一化）。调参靠业务评测集，无通用最优值。

**关联**：→ [查询 DSL 与打分](./05-query-dsl-scoring/query-dsl-and-scoring.md)

### Q27: Function Score 怎么用？field_value_factor 是什么？🔗

**答**：**Function Score** 是 ES 的"自定义打分"查询——在基础查询的 `_score` 上叠加业务函数得分，实现"相关性打分 + 业务加权"混合排序。五种打分函数：①`script_score`（Painless 脚本计算，复杂业务公式）；②`field_value_factor`（用文档某字段的值做因子乘以/加到 `_score`，如销量加权 `field: sales, factor: 1.2, modifier: log`）；③`weight`（简单权重乘以 `_score`）；④`random_score`（生成随机分，种子可复现，A/B 测试分流）；⑤`decay_functions`（衰减函数 `gauss`/`exp`/`linear`，按字段值离中心点距离衰减，时间衰减/距离衰减）。`score_mode` 控制**多 functions 之间**如何合并（sum/multiply/avg/max/first），`boost_mode` 控制**基础 _score 与函数得分**如何合并（sum/multiply/avg/max/min/replace）。**field_value_factor** 的 `modifier` 常用 `log`（对数压扁，避免极端值主导）和 `none`（直接用原值）。电商搜索"标题匹配 + 销量加权 + 新品加权"是典型 Function Score 场景。

**关联**：→ [查询 DSL 与打分](./05-query-dsl-scoring/query-dsl-and-scoring.md)

### Q28: 深度分页怎么办？search_after 和 PIT 是什么？🔗

**答**：三种分页方式——①**from + size**：协调节点取各分片 `from+size` 条归并，`from` 越大开销越大（`from=9990` 各分片返回 10000 条归并），默认上限 `index.max_result_window=10000`，适合浅分页（from < 1000）；②**search_after**：用上一页最后一条的排序值作游标，各分片取 > 游标值的 size 条，无需 from 开销，适合深度分页且不要求数据一致性（如导出历史数据），要求排序字段唯一且有序；③**PIT + search_after**（8.x）：先创建 PIT（Point-in-Time）锁定数据快照，再 `search_after` 翻页，保证翻页期间数据变化不影响结果一致性，适合深度分页且要求一致性（如生产报表、合规审计）。PIT 有生命周期（默认 5 分钟，可续期）。生产推荐：浅分页用 from+size，深度分页用 search_after，强一致深度分页用 PIT + search_after。

**关联**：→ [查询 DSL 与打分](./05-query-dsl-scoring/query-dsl-and-scoring.md)

---

## 六、聚合篇（4 题）

### Q29: 聚合有哪几类？Bucket/Metric/Pipeline？🔗

**答**：三大类——①**Bucket（桶聚合）**：按维度分组（分桶），每桶含 `key` + `doc_count`，典型 `terms`/`date_histogram`/`nested`/`filter`，对应 SQL `GROUP BY`；②**Metric（指标聚合）**：计算单个指标值，分基础（`avg`/`sum`/`max`/`min`/`stats` 精确计算）和近似（`cardinality`/`percentile` 用算法近似以精度换内存），对应 SQL `COUNT`/`SUM`/`AVG`；③**Pipeline（管道聚合）**：不直接处理文档，基于其他聚合（通常 Bucket）的输出结果做二次计算，典型 `moving_avg`/`derivative`/`cumulative_sum`/`max_bucket`/`bucket_script`，对应 SQL 窗口函数（`OVER`）。三类嵌套关系：Bucket → 子 Bucket → Metric 是树形嵌套（顶层 Bucket 分组后每个桶内可再嵌套），Pipeline 不在树形嵌套里而是基于已有 Bucket 的桶序列做二次计算（如对 `date_histogram` 的桶做 `derivative` 导数）。

**关联**：→ [聚合与 Pipeline](./06-aggregation/aggregation-and-pipeline.md)

### Q30: Cardinality 怎么去重？hyperloglog++ 是什么？precision_threshold？🔗

**答**：`cardinality` 聚合用 **HyperLogLog++（HLL++）** 算法近似去重计数——不存储所有不同值，用概率算法估算不同值数量。**HLL++ 原理**：①对每个值 hash 得 64 位哈希值；②根据哈希值前 `p` 位分桶（`2^p` 个寄存器），后位中前导零数 +1 作为该桶估计值；③各桶保留最大估计值；④用调和平均数合并各桶估计值得到全局基数估计。HLL++ 是 HLL 的改进版（小基数精确、大基数偏差校正）。**`precision_threshold`** 控制精度与内存：默认 3000（误差约 13%，内存 3KB），调到 40000（误差约 1%，内存 40KB）。误差公式约 `40000 / precision_threshold`。`precision_threshold` 是"阈值"——当不同值数 ≤ 阈值时精确（完全存储），超过阈值才用 HLL++ 近似。典型场景是 UV（独立访客数）去重——精确去重要存储所有不同 user_id（百万级）内存爆炸，HLL++ 只用几 KB 内存估算百万级 UV。对照 MySQL `COUNT(DISTINCT)` 精确但百万级内存大且慢。

**关联**：→ [聚合与 Pipeline](./06-aggregation/aggregation-and-pipeline.md)

### Q31: Percentile 怎么算？t-digest 是什么？global_ordinals？🔗

**答**：`percentile` 聚合用 **t-digest** 算法近似分位数（P50/P95/P99），避免排序所有值再取分位的高内存开销。**t-digest 原理**：①把所有值按大小聚类为一组"质心"（centroid），每质心记录均值 + 权重；②聚类策略是**中间疏两端密**（中间用大质心代表多值，两端用小质心保证精度——因为分位数在两端 P1/P99 比中间 P50 更敏感）；③求分位数时按质心权重累计找到分位位置，用质心均值近似。`compression` 控制精度与内存（默认 100，越大越精确内存越多）。**global_ordinals** 是 `keyword` 字段聚合的核心优化——用整数 ordinal 替代字符串 term 作桶 key（`HashMap<Integer, bucket>` 比 `HashMap<String, bucket>` 紧凑），聚合完再 ordinal → term 还原。global_ordinals 默认懒加载（首次聚合时构建映射表），对频繁聚合字段可设 `"eager_global_ordinals": true` 预加载（segment 生成时就构建，聚合时直接用），代价是 refresh 时多一步构建开销。

**关联**：→ [聚合与 Pipeline](./06-aggregation/aggregation-and-pipeline.md)

### Q32: ES|QL 是什么？8.x 新特性？🔗

**答**：**ES|QL** 是 8.x 引入的全新查询语言——基于管道（pipe）组合的声明式查询语言，语法类似 `FROM logs | WHERE level == "ERROR" | STATS count = COUNT(*) BY service`。与 Query DSL 的区别：①**表达力**——ES|QL 专注表格化数据查询与聚合（类似 SQL），Query DSL 表达力更强（支持复杂 Bool 组合、Function Score、Rescoring 等）；②**性能**——ES|QL 基于全新的计算引擎（不依赖 Lucene 的 Query/Aggregation 类），某些场景更快；③**易用性**——ES|QL 语法对熟悉 SQL 的开发者更友好，无需学习 JSON 嵌套 DSL。ES|QL 适合"简单查询 + 聚合"场景（如日志分析、监控报表），复杂相关性检索仍用 Query DSL。8.x 还引入 dense_vector/KNN 原生 ANN 检索能力（HNSW 算法）、Runtime Field 默认可用、file cache 文件缓存加速查询等新特性。

**关联**：→ [聚合与 Pipeline](./06-aggregation/aggregation-and-pipeline.md)

---

## 七、分片路由与 Reindex 篇（5 题）

### Q33: 分片怎么路由？hash(routing)%num_primary？🔗

**答**：ES 用 **`hash(routing) % num_primary_shards`** 公式路由——`routing` 是路由键，默认取文档 `_id`，写入/查询时也可传 `?routing=user_id` 自定义；`hash` 是 ES 内部的 Murmur3Hash（非 Java 的 `Object.hashCode`，分布更均匀）；`num_primary_shards` 是建索引时定的主分片数，**之后不可改**。路由是确定性的——同一 routing 永远路由到同一分片，所以写入和查询必须用相同 routing（自定义 routing 写入就必须带相同 routing 查询，否则查错分片）。副本不参与路由只做备份/读分流。与 Redis Cluster 对照：Redis 用 `CRC16(key) % 16384` 槽位（固定 16384 槽位），ES 用 `hash(routing) % num_primary_shards`（分片数建索引时定）；Redis 扩容靠槽位迁移（数据不动槽位归属变），ES 扩容靠分片搬迁但分片数不变，改分片数只能 reindex。

**关联**：→ [分片路由与 Reindex](./07-shard-routing/shard-routing-and-reindex.md)

### Q34: 自定义 routing 有什么用和风险？🔗

**答**：**收益**：同一用户的文档路由到同一分片，查询时也带 `?routing=user_id`，只需查一个分片而非 Scatter-Gather 所有分片——降低查询延迟与资源开销。典型场景是"用户维度的订单/日志查询"（同用户文档聚一起，查时只查一个分片）。**风险——数据倾斜**：如果某个用户（如大客户）的文档量远超均值，该用户所在分片会远大于其他分片（"热点分片"），导致该分片写入/查询慢、节点负载不均。缓解手段：①给 routing 加随机后缀（`routing=u_42_0` 到 `u_42_9` 共 10 个分片，分散大客户数据）；②用 `routing` + `size` 控制单用户文档量；③监控分片大小不均（`_cat/shards` 看分片 `store` 大小），倾斜超阈值告警。**注意**：GET/UPDATE/DELETE 单文档时若忘了带 routing，ES 会广播到所有分片找文档（性能差且可能找不到）。

**关联**：→ [分片路由与 Reindex](./07-shard-routing/shard-routing-and-reindex.md)

### Q35: reindex 怎么用？Update By Query 是什么？🔗

**答**：**reindex** 用 `POST /_reindex` API 重建索引——新建一个分片数/mapping/settings 不同的索引，把旧索引数据搬到新索引，用于分片数变更、mapping 变更、版本升级等场景。关键参数：`source.index`（源索引）、`dest.index`（目标索引）、`dest.op_type: create`（_id 冲突则失败不覆盖，生产常用防意外覆盖）、`slices: auto`（按源索引分片数自动并行加速）、`size`（每批文档数 1000-5000）。reindex 是异步任务（返回 `task_id`，用 `GET /_tasks/<task_id>` 查进度）。**零停机 reindex 流程**：①新建新索引；②用 alias 指向旧索引（业务通过别名读写）；③reindex 旧数据到新索引（`op_type: create` 避免覆盖增量写入）；④切换 alias 从旧指向新；⑤删除旧索引。**Update By Query** 是"原地更新"（不换索引），用 `POST /_update_by_query` 对匹配查询的文档批量脚本更新，常用于字段值变更、mapping 小改（如加字段、改 `dynamic`）让新 mapping 生效。mapping 大改（如改字段类型）必须 reindex 重建。

**关联**：→ [分片路由与 Reindex](./07-shard-routing/shard-routing-and-reindex.md)

### Q36: CCR 跨集群复制是什么？🔗

**答**：**CCR（Cross-Cluster Replication）** 是 ES 6.5 引入（Platinum 许可）、8.x 持续增强的**跨集群复制**特性——一个集群的索引（Leader 索引）把变更实时复制到另一个集群的索引（Follower 索引），实现跨集群灾备与就近读取。**核心模型**：Leader 索引（源集群可写）→ Follower 索引（目标集群**只读**），基于 translog 复制（Follower 定期拉取 Leader 的 translog 序列并重放）。**两种跟随模式**：`auto_follow`（自动跟随匹配模式的 Leader 索引，如 `orders-*` 前缀的新索引自动被 Follower）和手动跟随（`POST /<follower_index>/_ccr/follow`）。**一致性**：最终一致——Follower 异步拉取 translog，Leader 写入后 Follower 有延迟（通常秒级到分钟级）。**Follower 切主**：Leader 集群故障时需手动 `POST /<follower_index>/_ccr/unfollow` 解除跟随转为独立可写索引。与 Redis 主从/RocketMQ 主从对照：CCR 是**跨集群**复制（Leader 和 Follower 在两个独立集群），Redis/RocketMQ 主从通常是**同集群**复制；CCR 的 Follower 只读不像 Redis 哨兵自动切主。

**关联**：→ [分片路由与 Reindex](./07-shard-routing/shard-routing-and-reindex.md)

### Q37: 分片数怎么规划？over-sharding 有什么风险？🔗

**答**：ES 官方推荐**单分片大小 30-50GB**——过小（over-sharding）浪费资源，过大（under-sharding）rebalance 慢且查询慢。**分片数规划公式**：`number_of_shards = ceil(预计数据总量 / 50GB)`，如预计 1TB 数据取 20 或 24（偶数便于节点均分）。**过小（< 10GB）**：分片多，每分片是一个 Lucene 索引（开销固定：segment 文件句柄、内存占用），分片过多浪费资源（`over-sharding`），如 100GB 数据建 50 个 2GB 分片，每分片的元数据/segment 开销远大于数据本身。**过大（> 50GB）**：分片大，Lucene segment 大，查询时合并 segment 慢；节点故障时 rebalance（搬迁分片）慢（搬运几十 GB 数据耗时长）。**over-sharding 风险**：①集群状态膨胀——每分片的元数据（routingTable）在 Master 内存，分片数过多 Master OOM；②`cluster.max_shards_per_node` 保护——ES 7.x 起默认每节点最多 1000 个分片，防 over-sharding 把集群拖垮；③调度开销大——分片数多时分配决策、再平衡、recovery 调度开销显著。建索引前估算总分片数 = `number_of_shards × (1 + number_of_replicas)`，确保不超过集群总分片容量。

**关联**：→ [分片路由与 Reindex](./07-shard-routing/shard-routing-and-reindex.md)

---

## 八、高可用与调优篇（4 题）

### Q38: 节点宕机怎么办？副本怎么恢复？🔗

**答**：ES 高可用三层机制叠加：①**副本故障转移**——承载某主分片的节点宕机时，Master 检测心跳超时（`cluster.election.duration` 10s）后把该分片的副本提升为新主（`ReplicaAsPrimary` 动作，提升瞬间完成因副本已是完整 segment + translog），再补一个新副本；②**Master 选举**——Master 宕机后剩余 Master 候选节点触发 Zen2 选举，选出新 Master 推送 ClusterState；③**分片再平衡**——集群新增/缩容节点时 Master 自动触发再平衡。**副本恢复流程**：①Master 的 `NodeFailureDetector` 感知节点失联；②`AllocationService.applyFailedShard` 标记宕机节点上的分片为 UNASSIGNED；③分配器选同步完成的副本提升为新 primary；④分配器为新 primary 分配新 replica（选负载最低的节点）；⑤新 replica 从 primary 复制数据（Phase1 复制现有 segment file chunk，Phase2 replay translog 追平实时写入），完成后状态变 ACTIVE，集群恢复 green。ES 是**分片级**故障转移（每个分片独立提升新主，不影响其他分片），恢复速度快、影响范围可控。

**关联**：→ [高可用与调优](./08-ha-tuning/ha-and-tuning.md)

### Q39: JVM heap 为什么 50%？Circuit Breaker 是什么？🔗

**答**：**JVM heap 50% 规则**——把物理内存的 50% 分给 JVM heap，剩下 50% 留给 os file cache（Lucene segment mmap 用）。原因：Lucene 的 segment 文件靠 mmap 映射到进程地址空间，实际读取走 os page cache，os cache 越大能缓存的 segment 越多查询越快。heap 用在 query/aggregation 中间结果、index buffer（10% heap）、ClusterState 元数据、query cache（10% heap）等。**heap 上限 31GB**——超过 31GB JVM 会禁用 Compressed Oops（压缩指针），对象引用从 4 字节变 8 字节，内存浪费 + GC 变慢。生产部署：物理内存 ≤ 64GB 时 heap 给 50%（如 64GB → 31GB heap + 33GB os cache）；物理内存 ≥ 128GB 时 heap 仍给 31GB，剩下全给 os cache。**Circuit Breaker** 是 ES 防止 OOM 的内存保护机制——在执行查询/聚合前先估算内存占用，超限直接抛 `CircuitBreakingException` 拒绝请求，避免 OOM kill 整个 JVM 进程。层级：`parent_breaker`（总熔断 95% heap）、`fielddata`（text 字段 40% heap）、`request`（单请求 60% heap）、`accounting`（Lucene 间接内存）。与 JVM OOM 对照：CB 是请求级保护（拒一个请求），OOM 是进程级（杀整个 JVM），CB 是 OOM 的前置防线。

**关联**：→ [高可用与调优](./08-ha-tuning/ha-and-tuning.md)

### Q40: 怎么提升写入吞吐？怎么调优查询延迟？file cache 8.x？🔗

**答**：**写入吞吐调优**四条路径：①`index.refresh_interval` 调到 10s/30s（segment 生成少，写吞吐↑，代价是可见性延迟）；②`index.number_of_replicas` 临时设 0（副本同步开销消失，写吞吐↑↑ 2-3 倍，风险是宕机数据丢，**必须写完恢复**）；③`index.translog.durability: async` + `sync_interval=5s`（无 fsync 阻塞，吞吐↑，最多丢 5s）；④bulk 批量 5-15MB + 客户端多线程并行（线程数 ≈ 分片数 × 2）。**查询延迟调优**三条路径：①`index.store.preload: ["tim", "tip", "dvd"]` 索引打开时预加载 segment 扩展名到 page cache（首次查询不冷启动）；②`indices.queries.cache.size`（默认 10% heap）filter 上下文查询结果缓存，重复 filter 查询秒回；③`index.sort.field: create_time` 索引时按字段排序存储，提前剪枝减少扫描。**file cache（8.x 新特性）**：8.0 引入的专用文件缓存——专门缓存 Lucene segment 的热数据（如 `.tim`/`.tip`/`.doc`），区别于 os page cache 的全文件缓存。`index.store.type: hybridcache`（8.16+）让热段数据常驻专用缓存，查询更稳。

**关联**：→ [高可用与调优](./08-ha-tuning/ha-and-tuning.md)

### Q41: 7.x 升 8.x 注意什么？yellow 和 red 区别？🔗

**答**：**7.x 升 8.x** 关键变化集中在**安全默认开启 + 性能优化 + API 现代化**三条主线：①**安全默认开启**——`xpack.security.enabled: true`（默认），自动生成 TLS 证书 + `elastic` 用户密码，节点间通信强制 TLS，跨版本升级必须先处理安全配置；②**API 兼容**——REST API 兼容 7.x 大部分接口，但**类型移除**（`_doc` 替代 `_type`，`type` 参数忽略）、`_search` 默认 `track_total_hits: 10000`；③**file cache 引入**（8.0+ 加速查询）、**Runtime Field 默认可用**（7.x 是实验特性）、**dense_vector/KNN 8.x 增强**（原生 ANN 检索 HNSW 算法）。升级方案推荐：搭建 8.x 新集群 → 配置安全 → CCR 跨集群复制（7.x Leader → 8.x Follower）→ 灰度切流 → 验证数据一致 → 全量切换 → 7.x 集群下线。**yellow 和 red 区别**：`_cluster/health` 三色状态——**green**（全部分片已分配，所有 primary + replica 都 STARTED，读写正常）；**yellow**（所有 primary 已分配，部分 replica 未分配，读写正常但容灾降级单点风险，典型原因节点数 ≤ 副本数/磁盘满/节点临时宕机）；**red**（部分 primary 未分配，受影响分片读写失败数据暂时不可用，典型原因节点宕机超过副本数/磁盘损坏）。

**关联**：→ [高可用与调优](./08-ha-tuning/ha-and-tuning.md)

---

## 连环套问思维导图

面试官常从一个点切入连环追问，下面 6 条追问链覆盖 80% 高频追问路径，对照检查每条链是否能完整答出。

```mermaid
mindmap
  root((ES 连环套问))
    架构链
      节点角色职责
        Master/Data/Coordinating/Ingest/ML
      Master 选举
        Zen2 Raft-like / cluster.election.duration 10s
      Voting Configuration
        多数派 floor(N/2)+1 / 动态维护
      为什么不用 ZK
        自研更轻 / 无外部依赖 / CP
      脑裂防护
        奇数节点 / 多数派天然防脑裂
    倒排链
      倒排索引结构
        Term Dictionary / Term Index / Posting List
      FST 前缀压缩
        DAG / 公共前缀共享 / 堆内存 MB 级
      Posting List 压缩
        Frame of Reference 增量编码
      Roaring Bitmap
        高 16 位桶 / 数组 vs 位图 / 集合运算
      doc_values 列存
        mmap / 不占堆 / 替代 _field_data
    写流程链
      写 index buffer
        JVM 堆内 / 查询读不到
      refresh 1s
        buffer → segment 内存 / 可搜
      segment 不可变
        tombstone 删除 / merge 合并
      translog WAL
        先写日志再改数据 / fsync 不丢
      flush 持久化
        segment fsync 落盘 / 清 translog
      近实时可见
        refresh 管可搜 / flush 管持久
    查询链
      Query DSL
        JSON 嵌套 / bool 组合
      Bool 四子句
        must 算分 / filter 不算分可缓存
      filter vs query
        bitset 缓存 / 算分开销
      BM25 打分
        TF 饱和 / 文档长度归一化
      k1/b 调参
        k1 TF 饱和度 / b 长度归一化
      Function Score
        field_value_factor / decay / 业务加权
    聚合链
      三类聚合
        Bucket 桶 / Metric 指标 / Pipeline 管道
      Cardinality HLL++
        概率算法 / precision_threshold / UV 去重
      Percentile t-digest
        质心聚类 / 中间疏两端密 / compression
      global_ordinals
        ordinal 整数作桶 key / 懒加载 / eager 预构建
      ES|QL 8.x
        管道组合 / 表格化查询 / 新计算引擎
    分片链
      路由公式
        hash(routing) % num_primary_shards
      分片数不可改
        路由刚性约束 / 改了文档找不到
      reindex 重建
        _reindex API / slices 并行 / 零停机别名切换
      CCR 跨集群复制
        Leader/Follower / translog 复制 / 只读
      Hot-Warm-Cold
        ILM 自动迁移 / searchable snapshot / 分层存储
```

> **使用提示**：面试前盖住答案自答 41 题，对照思维导图检查每条追问链是否答得完整；答不上来的题跳转 **关联** 文档补原理推导。

### 连环套问链详注

下面把思维导图中的 6 条追问链展开为问答路径，标注每一步的"考点 + 易踩坑"，供面试前对照演练。

**链 1：架构链（Q1 → Q2 → Q4 → Q3）**

- **Q1 起手问"节点角色"**：考点是五大角色职责边界，易踩坑是把 Coordinating 说成"无角色节点"——它是 `node.roles: []` 空列表的专用协调节点，不是没角色。
- **Q2 追问"Master 选举"**：考点是 Zen2 Raft-like + Voting Configuration 多数派，易踩坑是把 Zen2 说成"标准 Raft"——它是 ES 自研变种，借鉴 Raft 思想但不是标准 Raft。
- **Q4 追问"脑裂防护"**：考点是 Voting Configuration 多数派天然防脑裂 + 奇数节点，易踩坑是答不出 7.x 之前用 `minimum_master_nodes` 人工配置易脑裂。
- **Q3 反问"为什么不用 ZK"**：考点是自研更轻 + 无外部依赖 + ES Master 必须选 CP（ClusterState 分裂导致数据分裂），易踩坑是只说"ZK 慢"不说 ES 元数据低频写 ZK 是过度设计。

**链 2：倒排链（Q13 → Q14 → Q15 → Q16）**

- **Q13 起手问"倒排索引"**：考点是三部分（Term Dictionary / Term Index / Posting List）+ 与 B+Tree 区别，易踩坑是答不出"为什么 ES 用倒排而不用 B+Tree"——倒排擅长"给定词找所有包含它的文档"。
- **Q14 追问"FST"**：考点是 FST 前缀压缩 + DAG + 常驻堆内存，易踩坑是答成"FST 是哈希表"——FST 是状态机 O(字符串长) 查找，不是 O(1) 哈希。
- **Q15 追问"Posting List 压缩"**：考点是 Frame of Reference 增量编码 + Roaring Bitmap 分桶，易踩坑是把 Roaring Bitmap 说成"普通位图"——它分桶后按数量选容器（数组/位图/单值）。
- **Q16 追问"doc_values"**：考点是磁盘列存 + mmap 不占堆 + 替代 _field_data，易踩坑是答不出 text 字段默认不开 doc_values 的原因（分词后每 token 一条倒排链再建 doc_values 成本翻倍）。

**链 3：写流程链（Q18 → Q20 → Q19 → Q21）**

- **Q18 起手问"写后 1s 可见"**：考点是近实时 + refresh 1s + index buffer 查询读不到，易踩坑是把 refresh 说成"fsync"——refresh 不 fsync segment 只是写入内存目录。
- **Q20 追问"refresh vs flush"**：考点是 refresh 管可搜 / flush 管持久，易踩坑是答成"refresh 也清 translog"——清 translog 是 flush 的职责。
- **Q19 追问"translog"**：考点是 WAL 思想 + 先写日志再改数据 + 与 MySQL Redo Log 同构，易踩坑是答成"translog 用于复制"——ES 复制走 primary→replica 同步写，不走 translog。
- **Q21 追问"写不丢保障"**：考点是 translog fsync + 副本数 + wait_for_active_shards 三层，易踩坑是只答 translog 忽略副本和一致性级别。

**链 4：查询链（Q23 → Q24 → Q26 → Q27）**

- **Q23 起手问"Bool 四子句"**：考点是 must/should/filter/must_not 的算分与缓存差异，易踩坑是把 should 说成"不算分"——should 算分，filter 和 must_not 才不算分。
- **Q24 追问"查询 vs 过滤上下文"**：考点是算分 vs 不算分 + filter bitset 缓存，易踩坑是答不出"filter 为什么更快"——无算分开销 + bitset 缓存。
- **Q26 追问"BM25"**：考点是 TF 饱和 + 文档长度归一化 + k1/b 调参，易踩坑是把 BM25 说成"TF/IDF"——BM25 是 TF/IDF 的改进版，5.0 起默认。
- **Q27 追问"Function Score"**：考点是五种打分函数 + score_mode vs boost_mode，易踩坑是混淆 score_mode（多 functions 之间合并）和 boost_mode（基础分与函数分合并）。

**链 5：聚合链（Q29 → Q30 → Q31 → Q32）**

- **Q29 起手问"聚合三类"**：考点是 Bucket/Metric/Pipeline + 嵌套关系，易踩坑是把 Pipeline 说成"直接处理文档"——Pipeline 基于其他聚合输出做二次计算。
- **Q30 追问"Cardinality HLL++"**：考点是概率算法 + precision_threshold 误差公式（约 40000/precision_threshold），易踩坑是把 HLL++ 说成"精确去重"——它是近似算法。
- **Q31 追问"Percentile t-digest"**：考点是质心聚类 + 中间疏两端密 + global_ordinals 整数作桶 key，易踩坑是答不出 t-digest 为什么 P1/P99 比 P50 更精确——两端用小质心保证精度。
- **Q32 追问"ES|QL"**：考点是 8.x 新查询语言 + 管道组合 + 新计算引擎，易踩坑是把 ES|QL 说成"Query DSL 的别名"——它是全新语言基于管道组合。

**链 6：分片链（Q33 → Q37 → Q35 → Q36 → Q12）**

- **Q33 起手问"分片路由"**：考点是 `hash(routing) % num_primary_shards` + Murmur3Hash + 路由确定性，易踩坑是答成"一致性哈希"——ES 用固定取模不是一致性哈希。
- **Q37 追问"分片数规划"**：考点是单分片 30-50GB + over-sharding 风险 + `cluster.max_shards_per_node`，易踩坑是答"分片越多越好"——分片过多集群状态膨胀 Master OOM。
- **Q35 追问"reindex"**：考点是 `_reindex` API + slices 并行 + 零停机别名切换，易踩坑是答成"reindex 就是 update"——reindex 是搬新索引，Update By Query 才是原地更新。
- **Q36 追问"CCR"**：考点是 Leader/Follower + translog 复制 + Follower 只读，易踩坑是把 CCR 说成"同集群主从"——CCR 是跨集群复制。
- **Q12 收尾问"Hot-Warm-Cold"**：考点是 ILM 四阶段 + 节点角色分层 + searchable snapshot，易踩坑是答不出 Cold 层用可搜索快照（数据迁 S3 仅元数据本地）。

> **串联技巧**：面试官追问本质是"由点及面"，回答时主动用"其实这背后还有 X" 把下个考点带出来，化被动为主动。

## 附：高频面试场景速查

| 场景 | 核心题 | 关联文档 |
|------|--------|---------|
| "讲讲 ES 架构" | Q1-Q6 | [架构与部署拓扑](./01-architecture/architecture-and-topology.md) |
| "Mapping 怎么设计" | Q7-Q12 | [索引与映射](./02-index-mapping/index-and-mapping.md) |
| "倒排索引原理" | Q13-Q17 | [倒排索引与分词](./03-inverted-index/inverted-index-and-analysis.md) |
| "写后为什么 1s 才能搜到" | Q18-Q22 | [读写流程与 Translog](./04-read-write-translog/read-write-and-translog.md) |
| "Bool 查询怎么组合" | Q23-Q28 | [查询 DSL 与打分](./05-query-dsl-scoring/query-dsl-and-scoring.md) |
| "聚合怎么用" | Q29-Q32 | [聚合与 Pipeline](./06-aggregation/aggregation-and-pipeline.md) |
| "分片数怎么规划" | Q33-Q37 | [分片路由与 Reindex](./07-shard-routing/shard-routing-and-reindex.md) |
| "节点宕机怎么办" | Q38-Q41 | [高可用与调优](./08-ha-tuning/ha-and-tuning.md) |

---

## 附：面试 30 秒自检表

面试前最后 30 秒过一遍这张表，每行对应一个"必答要点"，能脱口而出才算过关。

| 题号 | 30 秒必答要点 |
|------|--------------|
| Q1 | 五大角色：Master 元数据 / Data 存 Shard / Coordinating 路由归并 / Ingest 预处理 / ML |
| Q2 | Zen2 Raft-like 多数派选举，Voting Configuration 动态维护，10s 心跳超时 |
| Q3 | 自研更轻无外部依赖，ES Master 必须选 CP，ZK 对低频写元数据是过度设计 |
| Q4 | Voting Configuration 多数派天然防脑裂，奇数节点容忍 floor(N/2) 故障 |
| Q5 | Coordinating Scatter-Gather，查 ClusterState 路由表并行分发各 Shard 归并 |
| Q6 | Index 逻辑命名空间，Shard 并行单位不可改，Replica 副本可动态调 |
| Q7 | keyword 不分词精确匹配，text 分词全文检索，text+keyword 子字段兼顾 |
| Q8 | Dynamic Mapping 三坑：字段爆炸/类型冲突/日期误判，生产用 strict |
| Q9 | object 扁平化丢失关联，nested 独立文档保持边界，代价是 N 对象 = N 文档 |
| Q10 | Runtime Field 8.x 查询时 Painless 脚本计算，不占磁盘但查询慢，过渡态 |
| Q11 | 别名零停机切换，Index Template 按 index_patterns 自动套用，is_write_index |
| Q12 | ILM 四阶段 Hot/Warm/Cold/Delete，Rollover+Shrink+forcemerge+searchable snapshot |
| Q13 | 倒排三部分 Term Dictionary/Index/Posting List，从词找文档 vs B+Tree 从 key 找行 |
| Q14 | FST 前缀压缩 DAG，公共前缀共享边，百万 term 堆内存 MB 级 |
| Q15 | FOR 增量编码 + Roaring Bitmap 分桶（≤4096 数组/>4096 位图）集合运算高效 |
| Q16 | doc_values 磁盘列存 mmap 不占堆，_field_data 堆内易 OOM 已默认关闭 |
| Q17 | Analyzer 三段式 CharFilter/Tokenizer/TokenFilter，ik 索引 max_word 查询 smart |
| Q18 | 写后 1s 可见因 refresh 1s 把 buffer 物化为 segment，?refresh=true 强制但破坏吞吐 |
| Q19 | translog 是 WAL 先写日志再改数据，与 MySQL Redo Log/Redis AOF 同构 |
| Q20 | refresh 管可搜（buffer→segment 内存），flush 管持久（segment fsync+清 translog） |
| Q21 | 三层保障：translog fsync（request 0 丢）+ 副本数 + wait_for_active_shards |
| Q22 | 乐观锁 if_seq_no+if_primary_term CAS 模式，bulk NDJSON 5-15MB 多线程并行 |
| Q23 | Bool 四子句：must 算分不可缓存，filter 不算分可缓存 bitset |
| Q24 | 查询上下文算分不缓存，过滤上下文不算分 bitset 缓存，能用 filter 就别用 must |
| Q25 | term 不分词查 keyword，match 分词查 text，match_phrase 顺序+位置相邻 |
| Q26 | BM25 = IDF × TF 饱和项 × 长度归一化，k1 控 TF 饱和(1.2)，b 控长度归一(0.75) |
| Q27 | Function Score 五函数：script/field_value_factor/weight/random/decay |
| Q28 | from+size 浅分页≤10000，search_after 游标深分页，PIT+search_after 强一致 |
| Q29 | 三类聚合：Bucket 分桶/Metric 指标/Pipeline 二次计算，树形嵌套 Bucket→Metric |
| Q30 | Cardinality 用 HLL++ 概率算法，precision_threshold 误差约 40000/阈值 |
| Q31 | Percentile 用 t-digest 质心聚类中间疏两端密，global_ordinals 整数作桶 key |
| Q32 | ES|QL 8.x 管道组合查询语言，新计算引擎，dense_vector/KNN HNSW |
| Q33 | 路由 hash(routing)%num_primary_shards，Murmur3Hash，路由确定性 |
| Q34 | 自定义 routing 同用户同分片查单分片，风险是数据倾斜加随机后缀缓解 |
| Q35 | reindex 搬新索引 slices 并行，零停机别名切换，Update By Query 原地更新 |
| Q36 | CCR 跨集群 Leader/Follower 基于 translog 复制，Follower 只读需手动 unfollow |
| Q37 | 单分片 30-50GB，over-sharding 集群状态膨胀 Master OOM，max_shards_per_node |
| Q38 | 节点宕机副本提升新主补新副本，分片级故障转移，Zen2 选举 + 再平衡 |
| Q39 | heap 50% 留 50% 给 os cache mmap segment，heap 上限 31GB 压缩指针 |
| Q40 | 写调优 refresh 30s+replicas 0+translog async+bulk 5-15MB，file cache 8.x |
| Q41 | 7.x→8.x 安全默认开启 TLS+认证，类型移除，CCR 灰度切流，green/yellow/red |

> **临场技巧**：被问到不熟的题，先答"30 秒必答要点"中的关键词，再展开细节；答不上来就主动引导到相邻题（如被问 Q32 ES|QL，可带出 Q29 三类聚合），把追问链拉到自己熟的段落。
