# Elasticsearch 面试知识体系设计文档

> **创建日期**：2026-08-12
> **模块路径**：`middleware/es/`
> **定位**：面向 Java 后端高级/资深面试（5 年+）的 Elasticsearch 知识体系，与 `middleware/mysql`、`middleware/redis`、`middleware/rocketmq` 模块完全对齐

---

## 一、模块整体结构

### 目录组织

```
middleware/es/
├── README.md                                    # 入口索引（知识图谱 mindmap + 导航表 + 学习路径 + 模块关联）
├── 01-architecture/
│   └── architecture-and-topology.md             # 架构与部署拓扑
├── 02-index-mapping/
│   └── index-and-mapping.md                     # 索引与映射
├── 03-inverted-index/
│   └── inverted-index-and-analysis.md          # 倒排索引与分词
├── 04-read-write-translog/
│   └── read-write-and-translog.md              # 读写流程与 Translog
├── 05-query-dsl-scoring/
│   └── query-dsl-and-scoring.md                # 查询 DSL 与打分
├── 06-aggregation/
│   └── aggregation-and-pipeline.md             # 聚合与 Pipeline
├── 07-shard-routing/
│   └── shard-routing-and-reindex.md            # 分片路由与 Reindex
├── 08-ha-tuning/
│   └── ha-and-tuning.md                         # 高可用与调优
└── 08-interview-qa.md                           # 跨主题高频面试 Q&A
```

### 文件命名约定

- 主题文件采用 `kebab-case`，与 MySQL 的 `index-and-optimization.md`、Redis 的 `data-structure-and-encoding.md`、RocketMQ 的 `architecture-and-topology.md` 风格一致
- 文件名即主题全称（如 `inverted-index-and-analysis.md`），不缩写
- `08-interview-qa.md` 沿用「与第 8 个主题同号」的约定（Q&A 在根目录，`08-ha-tuning/` 是子目录，与 redis/rocketmq 完全一致）

### 与 MySQL / Redis / RocketMQ 的结构对齐

| 维度 | MySQL | Redis | RocketMQ | **ES** |
|------|-------|-------|----------|--------|
| 主题目录数 | 7 | 7 | 7 | **8** |
| Q&A 文件 | 1 份（08-interview-qa.md） | 1 份（08-interview-qa.md） | 1 份（08-interview-qa.md） | **1 份（08-interview-qa.md）** |
| 入口 README | 含 mindmap + 导航表 + 学习路径 + 模块关联 | 完全对齐 | 完全对齐 | **完全对齐** |
| 每份主题文档 | 五段式 + 顶部 `> 返回` 链接 | 完全对齐 | 完全对齐 | **完全对齐** |
| 版本基线 | MySQL 8.0 | Redis 7.x | RocketMQ 5.x | **ES 8.x** |

> 注：ES 知识点较多（倒排索引、分词映射、读写 translog、查询打分、聚合、分片路由各自独立成篇），主题目录数为 8，比 mysql/redis/rocketmq 的 7 多一个，Q&A 文件与第 8 个主题 `08-ha-tuning` 共用 08 前缀，这与 redis/rocketmq 的 Q&A 与 `07-ops` 共用 07 前缀的约定一致——Q&A 永远与最后一个主题目录同号。

### 与上层 README 的衔接

`middleware/README.md` 第 6 行 `- es` 将更新为：

```
- [es](./es) — Elasticsearch 面试知识体系（10 份文档，面向 5 年+ 资深面试）
```

与 mysql/redis/rocketmq 行格式完全一致。

---

## 二、知识图谱 mindmap

这是 `README.md` 中的核心导航图，采用 mermaid mindmap（与 MySQL、Redis、RocketMQ 的 `mindmap` 语法完全一致），覆盖 8 主题 + 面试冲刺：

```mermaid
mindmap
  root((Elasticsearch))
    架构与部署
      节点角色
        Master 节点
        Data 节点
        Coordinating 节点
        Ingest 节点
        Machine Learning 节点
      Cluster 与 Discovery
        Zen2 发现与选举
        Master 选举 Raft-like
        Voting Configuration
      Index/Shard/Replica
        主分片与副本
        分片数规划
        副本故障转移
      网络模型
        Transport 层 TCP
        HTTP 层 RestController
        Netty 4 线程模型
    索引与映射
      Index Settings
        number_of_shards/replicas
        refresh_interval
        analysis 配置
      Mapping 字段类型
        keyword/text
        long/scaled_float
        dense_vector 8.x
        object/nested/flattened
      Dynamic Mapping
        动态推断与日期检测
        dynamic strict
      Dynamic Template
        按 field 名匹配类型
      Runtime Field 8.x
        运行时计算字段
      别名与模板
        Index Alias
        Index Template
        ILM 生命周期
    倒排索引与分词
      倒排结构
        Term Dictionary
        Term Index FST
        Posting List
      存储格式
        _source 原始 JSON
        doc_values 列存
        _field_data 堆内存
      Posting List 压缩
        Frame of Reference
        Roaring Bitmap
      Analyzer 分词链
        Character Filter
        Tokenizer
        Token Filter
      Normalizer
        keyword 归一化
      分词与索引选型
        索引时分词 vs 查询时分词
    读写流程与 Translog
      写流程
        primary→replica
        写一致性 quorum
        版本控制与乐观并发
      Translog
        刷盘策略 index.translog
        fsync 崩溃一致性
      refresh
        1s 可见性
        index buffer→segment
      flush
        translog 清空
        segment 持久化
      bulk 批量
        批量写优化
      Near Real-Time 模型
        写后 1s 可见
    查询 DSL 与打分
      Query DSL 结构
        query/bool/filter
        term/terms/range
        match/multi_match
      Bool 查询
        must/should/filter/must_not
        查询 vs 过滤上下文
      Function Score
        script_score
        field_value_factor
      BM25 打分
        TF/IDF→BM25
        可调参数 k1/b
      Rescoring
        重打分窗口
      分页与游标
        from/size 限制
        search_after
        PIT Point-in-Time 8.x
    聚合
      Bucket 聚合
        terms/date_histogram
        nested aggregation
      Metric 聚合
        avg/sum/max/min
        cardinality hyperloglog++
        percentile t-digest
      Pipeline 聚合
        移动平均
        导数
      聚合内存
        bwc/breadth 调优
      ES|QL 8.x
        管道查询语言
    分片路由与 Reindex
      分片路由
        hash(routing)%num_primary
        routing key 选型
      跨集群复制 CCR
        Leader/Follower 索引
      Reindex
        重建索引
        Update/Delete By Query
      分片数规划
        单分片大小
        over-sharding 风险
      Hot-Warm-Cold 架构
        节点角色分层
        ILM 迁移
    高可用与调优
      副本与故障恢复
        主分片选举
        副本同步
      分片再平衡
        rebalance 策略
        cluster.routing.*
      监控
        cat API
        _cluster/health
        _nodes/stats
      调优
        JVM heap 50%规则
        circuit breaker
        index store preload
        file cache 8.x
      版本升级
        7.x→8.x 兼容
        安全默认开启
    面试冲刺
      Q&A 速答
        40+ 高频题
      连环套问思维导图
        6 条追问链
```

### 设计要点

1. **根节点**：`root((Elasticsearch))`，与 MySQL 的 `root((MySQL))`、Redis 的 `root((Redis))`、RocketMQ 的 `root((RocketMQ))` 对齐
2. **一级节点**：9 个（8 主题 + 面试冲刺），与导航表一一对应
3. **二级节点**：每个主题的核心子领域（如"架构与部署"下 4 个子领域）
4. **三级节点**：关键考点/关键词（如"Master 选举 Raft-like"、"dense_vector 8.x"），用于面试检索
5. **深度对标 MySQL/Redis/RocketMQ**：同为三级 mindmap，末尾为"面试冲刺 → Q&A 速答 → 40+ 高频题 + 连环套问"

### 与 MySQL / Redis / RocketMQ mindmap 的结构对照

| 一级节点 | MySQL | Redis | RocketMQ | **ES** |
|---------|-------|-------|----------|--------|
| 1 | 索引原理 | 数据结构与对象 | 架构与部署 | **架构与部署** |
| 2 | 事务与 MVCC | 持久化机制 | 存储与刷盘 | **索引与映射** |
| 3 | 锁机制 | 内存管理与淘汰 | 消息模型 | **倒排索引与分词** |
| 4 | 查询优化 | 事件与并发模型 | 高可用与副本 | **读写流程与 Translog** |
| 5 | 存储引擎 | 复制与集群 | 高级特性 | **查询 DSL 与打分** |
| 6 | 日志体系 | 缓存实战与分布式锁 | 实战与最佳实践 | **聚合** |
| 7 | 架构与高可用 | 高可用与运维 | 运维与排障 | **分片路由与 Reindex** |
| 8 | 面试冲刺 | 面试冲刺 | 面试冲刺 | **高可用与调优** |
| 9 | — | — | — | **面试冲刺** |

> 注：ES 主题顺序遵循自身的知识递进——先架构（整体认识）→ 索引映射（数据建模）→ 倒排索引（底层存储）→ 读写流程（写入可见性）→ 查询打分（检索核心）→ 聚合（分析能力）→ 分片路由（分布式能力）→ 高可用调优（运维落地）。

---

## 三、导航表

| 分层 | 文档 | 核心考点 |
|------|------|---------|
| 架构与部署 | [架构与部署拓扑](./01-architecture/architecture-and-topology.md) ✅ | 节点角色/Master 选举 Raft-like/Zen2/Index-Shard-Replica/Netty 线程模型 |
| 索引与映射 | [索引与映射](./02-index-mapping/index-and-mapping.md) ✅ | Index Settings/Mapping 字段类型/Dynamic Mapping/Dynamic Template/Runtime Field/别名模板 ILM |
| 倒排索引与分词 | [倒排索引与分词](./03-inverted-index/inverted-index-and-analysis.md) ✅ | FST 倒排结构/doc_values 列存/Roaring Bitmap/Analyzer 分词链/Normalizer/索引与分词选型 |
| 读写流程与 Translog | [读写流程与 Translog](./04-read-write-translog/read-write-and-translog.md) ✅ | 写流程 primary→replica/translog 刷盘/refresh 1s 可见/flush/版本乐观并发/bulk 批量 |
| 查询 DSL 与打分 | [查询 DSL 与打分](./05-query-dsl-scoring/query-dsl-and-scoring.md) ✅ | Query DSL/Bool must·should·filter·must_not/Function Score/BM25 打分可调参数/Rescoring/search_after·PIT |
| 聚合 | [聚合与 Pipeline](./06-aggregation/aggregation-and-pipeline.md) ✅ | Bucket/Metric/Pipeline/Cardinality hyperloglog++/Percentile t-digest/聚合内存调优/ES\|QL 8.x |
| 分片路由与 Reindex | [分片路由与 Reindex](./07-shard-routing/shard-routing-and-reindex.md) ✅ | routing 路由公式/routing key 选型/CCR 跨集群复制/reindex·Update By Query/分片数规划/Hot-Warm-Cold |
| 高可用与调优 | [高可用与调优](./08-ha-tuning/ha-and-tuning.md) ✅ | 副本故障恢复/分片再平衡/cat API·_cluster/health/JVM heap·circuit breaker/file cache 8.x/版本升级 7.x→8.x |
| 面试冲刺 | [Q&A 速答](./08-interview-qa.md) ✅ | 40+ 题速答 + 连环套问思维导图 |

> 共 **10 份**文档：入口 README（本文档）+ 上表 8 份主题文档 + 1 份 Q&A 文档。

### 设计要点

1. **表头/列名/格式**：与 MySQL、Redis、RocketMQ 导航表完全一致（分层 | 文档 | 核心考点）
2. **文档链接**：相对路径，指向各主题目录下的 `.md`
3. **核心考点列**：每个文档 5-7 个关键词，用 `/` 分隔，对应 mindmap 的三级节点
4. **文档计数说明**：底部标注"10 份"，比 mysql/redis/rocketmq 的 9 份多一份（8 主题 + QA）

---

## 四、推荐学习路径

### 路线一：系统学习（适合有 1-2 周准备期）

按 Elasticsearch 知识层次自顶向下，先建立整体架构认识，再深入索引建模、倒排存储、读写流程，最后到查询、聚合、分片与调优：

```
01 架构与部署 → 02 索引与映射 → 03 倒排索引与分词 → 04 读写流程与 Translog → 05 查询 DSL 与打分 → 06 聚合 → 07 分片路由与 Reindex → 08 高可用与调优 → 08 Q&A
```

**特点**：先见森林后见树木，符合「架构总览 → 数据建模 → 底层存储 → 写入可见性 → 检索核心 → 分析能力 → 分布式能力 → 运维调优」的认知递进。架构是入口，索引映射决定数据组织，倒排索引决定检索效率，读写流程决定一致性语义，查询打分是检索核心，聚合是分析能力，分片路由决定扩展性，调优是工程落地。

### 路线二：面试冲刺（高频优先，适合 3-5 天突击）

按面试热度排序，先啃必考点，再补体系：

1. 01 架构与部署 → 02 簸引与映射（架构 + 建模）
2. 03 倒排索引与分词 → 05 查询 DSL 与打分（底层 + 检索）
3. 04 读写流程与 Translog → 06 聚合（写入 + 分析）
4. 07 分片路由 → 08 高可用与调优 → 08 Q&A（分布式 + 运维 + 40+ 题）

**特点**：投入产出比最高，覆盖 80% 高频考点。ES 面试起手三连问是「倒排索引原理 → 写入流程与近实时可见 → 查询与打分」，先把这三块拿下再补聚合与分片调优。

> 两套路线殊途同归，最终都应回到 [Q&A 速答](./08-interview-qa.md) 做闭环检验。

---

## 五、与 java-core / framework 模块的关联

本模块虽为中间件文档，但与仓库内 Java 模块存在直接关联，便于在面试中结合源码与实战作答：

| ES 知识点 | 关联 Java 模块 | 关联要点 |
|-----------|---------------|---------|
| 01 架构 / Netty 线程模型 | `java-core/lambda` | ES Transport 层 Netty 4 与 Stream 异步编程的对照 |
| 01 架构 / 节点角色与线程 | `java-core/jvm` | Master/Data 节点角色与 JVM 线程模型的对照 |
| 03 倒排索引 / doc_values | `java-core/jvm` | doc_values 列存与堆外 DirectByteBuffer 的对照 |
| 04 读写 / translog fsync | `java-core/jvm` | translog 刷盘与 JVM GC 停顿对可见性延迟的影响 |
| 05 查询 / Spring Data ES | `framework/spring-framework` | `@Document`/`ElasticsearchRepository` 注解驱动配置 |
| 05 查询 / 客户端连接 | `framework/spring-framework` | RestClient 连接池与 Spring 集成 |
| 05 查询 / 序列化 | `framework/jackson` | 查询结果 JSON 反序列化与 Jackson 自定义序列化 |
| 07 分片 / routing key | `framework/spring-framework` | routing 与 Spring 多数据源路由的对照 |
| 08 调优 / JVM heap | `java-core/jvm` | ES JVM 堆 50% 规则与 JVM GC 调优、堆外内存预算 |
| 08 调优 / circuit breaker | `java-core/jvm` | ES 熔断器与 JVM 内存溢出保护的对照 |
| 08 调优 / 序列化 | `framework/jackson` | bulk JSON 序列化与 Jackson 配置 |
| 08 调优 / 参数校验 | `framework/valid` | 索引字段校验与 Hibernate Validator 的互补 |

**延伸阅读**：

- `java-core/jvm` —— 对照理解 ES JVM heap 50% 规则、堆外内存（Lucene segment mmap）、GC 停顿对近实时可见性的影响
- `framework/spring-framework` —— Spring Data Elasticsearch 的 `@Document` 注解驱动、RestClient 连接池
- `framework/jackson` —— bulk/查询结果的 JSON 序列化器与 Jackson 自定义配置

> 建议在阅读读写流程、查询打分与调优文档时，对照 `java-core`/`framework` 模块源码，加深「面试八股 → 工程实战」双向映射。

---

## 六、与 ops / middleware 内其他模块的交叉引用

本模块部分原理推导链与 `ops` 运维文档及其他中间件文档存在对照关系，ES 章只讲"ES 场景下的实现与选择"，原理推导回对应模块：

### 与 ops 模块的交叉引用

| ES 文档 | 跳转目标 | 对照要点 |
|---------|---------|---------|
| 02 索引与映射 | `ops/linux/05-fs/filesystem-and-vfs.md` | segment 文件组织与文件系统、fsync 崩溃一致性 |
| 03 倒排索引 | `ops/linux/03-memory/memory-management.md` | doc_values 列存与 mmap、堆外内存与 os cache 的权衡 |
| 04 读写与 translog | `ops/linux/05-fs/filesystem-and-vfs.md` | translog fsync 与文件系统崩溃一致性 |
| 04 读写与 translog | `ops/linux/03-memory/memory-management.md` | ES 堆外内存（Lucene mmap）、JVM heap 与 os cache 的分配 |
| 04 读写与 translog | `ops/linux/04-io/io-model-and-epoll.md` | Netty 4 线程模型与 epoll、IO 多路复用 |
| 08 高可用 | `ops/linux/06-network/network-kernel.md` | 节点间 TCP 长连接、集群发现与网络分区 |
| 08 调优 | `ops/linux/02-process/process-and-thread.md` | ES JVM 进程模型 vs Linux 进程线程 |
| 08 调优 | `ops/linux/03-memory/memory-management.md` | JVM heap 50% 与 os file cache 的内存权衡 |
| 08 调优 | `ops/docker/` | ES 容器化部署、`vm.max_map_count` 内核参数 |
| 08 调优 | `ops/k8s/` | ES on K8s、Elastic Operator、PV 与 StatefulSet |

### 与 middleware 内其他模块的交叉引用

| ES 文档 | 跳转目标 | 对照要点 |
|---------|---------|---------|
| 02 索引与映射 | `middleware/mysql/01-index/index-and-optimization.md` | ES Mapping vs MySQL 表结构、B+Tree vs 倒排索引 |
| 03 倒排索引 | `middleware/mysql/01-index/index-and-optimization.md` | 倒排索引 vs B+Tree 正向索引的本质差异 |
| 03 倒排索引 | `middleware/redis/01-data-structure/data-structure-and-encoding.md` | FST vs Redis SDS/dict 内存结构对照 |
| 04 读写与 translog | `middleware/mysql/06-log/log-system.md` | translog WAL vs MySQL Redo Log WAL 思想一致 |
| 04 读写与 translog | `middleware/redis/02-persistence/persistence-mechanism.md` | translog 刷盘 vs Redis AOF appendfsync 策略对照 |
| 05 查询打分 | `middleware/mysql/04-query/query-optimization.md` | ES 全文检索 vs MySQL LIKE/全文索引的本质差异 |
| 07 分片路由 | `middleware/redis/05-replication/replication-and-cluster.md` | ES 分片 vs Redis Cluster 16384 槽位 |
| 07 分片路由 | `middleware/mysql/07-architecture/ha-and-sharding.md` | ES 分片 vs MySQL 分库分表对照 |
| 08 高可用 | `middleware/rocketmq/04-ha/ha-and-replication.md` | ES 副本恢复 vs RocketMQ 主从复制 |
| 08 高可用 | `middleware/redis/05-replication/replication-and-cluster.md` | ES 副本 vs Redis 主从复制 |

> 处理原则：ES 章只讲"ES 场景下的实现与选择"，原理推导链回对应模块，不重复展开。

---

## 七、每份主题文档的五段式内容大纲

### 文档 1：`01-architecture/architecture-and-topology.md`

> **一句话定位**：ES 架构是面试起手题，"讲讲 ES 节点角色与 Master 选举"几乎每场必问，能讲到 Zen2 与 Voting Configuration 才算合格。
> **面试热度**：⭐⭐⭐⭐⭐

**一、概念定义**
- 1.1 ES 节点角色（Master/Data/Coordinating/Ingest/Machine Learning，职责划分与协作关系，8.x 角色分离配置 `node.roles`）
- 1.2 Cluster 与 Discovery（Zen2 发现与选举、为什么不用 ZooKeeper——ES 自研 Zen2 更轻量、Master 选举 Raft-like、Voting Configuration 多数派）
- 1.3 Index/Shard/Replica 模型（Index 逻辑命名空间、Shard 并行单位、主分片与副本、为什么分片——水平扩展与故障转移）
- 1.4 网络模型（Transport 层 TCP 节点间通信、HTTP 层 RestController 客户端接口、Netty 4 线程模型）

**二、原理与流程**
- 2.1 Master 选举流程（Zen2 的 `DiscoveryNode`、`NodeJoinController`、Voting Configuration 多数派、避免脑裂、`cluster.election.duration`）
- 2.2 集群状态发布（`ClusterState` 的 `master`→`node` 推送、二阶段提交 Publish/Commit、`ClusterStatePublisher`）
- 2.3 分片分配（`Allocator` 的 `ShardAllocator`、感知磁盘水位 `cluster.routing.allocation.disk.watermark`、副本分配 `Decider`）
- 2.4 协调节点路由（Coordinating 节点接收请求、按分片路由 Scatter-Gather、结果归并 merge）
- 2.5 Netty 4 线程模型（Transport 层 `Netty4Transport`、HTTP 层 `Netty4HttpServerTransport`、`HttpRequestHandler` 业务线程池）
- 2.6 源码路径（`org.elasticsearch.discovery.zen2.ZenDiscovery`、`org.elasticsearch.cluster.service.ClusterService`、`org.elasticsearch.http.netty4.Netty4HttpServerTransport`）

**三、高频追问**
- ES 有哪些节点角色？（Master/Data/Coordinating/Ingest/ML）
- Master 怎么选出来的？（Zen2 多数派，Raft-like）
- 为什么不用 ZooKeeper？（自研更轻量，无需外部依赖）
- 脑裂怎么避免？（Voting Configuration 多数派，min_master_nodes 弃用）
- 协调节点做什么？（接收请求，路由分片，归并结果）
- Data 节点能当 Master 吗？（默认能，生产建议角色分离）

**四、实战关联**
- Java 场景：RestHighLevelClient 连接 Coordinating 节点、Spring Data ES 配置
- 生产部署（3 Master 专用 + N Data、角色分离 `node.roles`）
- 与 MySQL 高可用对比（MHA/MGR vs Zen2，主从思想一致但 ES 是分片级选举）

**五、系统设计案例**
- 设计一个支撑亿级文档的搜索集群（3 Master + 10 Data、按数据量规划分片数、Hot-Warm 分层）
- 设计一个多机房 ES 部署方案（Cross-Cluster Replication、机房间步与延迟权衡）

---

### 文档 2：`02-index-mapping/index-and-mapping.md`

> **一句话定位**：索引与映射是 ES 数据建模的核心，"Mapping 怎么设计、Dynamic Mapping 有什么坑"是中高级面试必问。
> **面试热度**：⭐⭐⭐⭐⭐

**一、概念定义**
- 1.1 Index vs MySQL Table（ES Index 是逻辑命名空间，底层由多个 shard 的 Lucene Index 组成，与传统表结构对照）
- 1.2 Mapping 字段类型（keyword 不分词精确匹配、text 分词全文检索、数值类型 long/scaled_float、dense_vector 8.x 向量、object/nested/flattened 三种对象类型差异）
- 1.3 Dynamic Mapping（动态推断类型——字符串→text+keyword、数字→long、日期→date，风险——字段爆炸、类型冲突）
- 1.4 Runtime Field 8.x（运行时计算字段，不索引、查询时计算，与 schema-on-read 的对照）

**二、原理与流程**
- 2.1 Index Settings 详解（`number_of_shards`/`number_of_replicas`/`refresh_interval`/`analysis` 配置，为什么分片数不可改）
- 2.2 Mapping 结构（`properties` 定义字段、`type` 指定类型、`analyzer` 指定分词器、`index` 控制是否索引）
- 2.3 Dynamic Mapping 推断规则（JSON 类型→ES 类型映射表、`date_detection` 日期检测的误判风险、`dynamic_templates` 按字段名匹配类型）
- 2.4 Dynamic Template（按 `match_matcher`/`match_pattern`/`mapping` 定义模板，规避动态推断风险）
- 2.5 Runtime Field 8.x（`runtime` 段定义、`fields` 内 runtime 字段、Painless 脚本计算、与 indexed 字段的权衡）
- 2.6 Index Alias 与 Index Template（别名零停机切换、模板自动化新索引 Settings/Mapping、`index_patterns` 匹配）
- 2.7 ILM 索引生命周期（Hot/Warm/Cold/Delete 阶段、`rollover` 滚动、`shrink` 缩分片、`forcemerge` 合并段）
- 2.8 源码路径（`org.elasticsearch.index.mapper.DocumentMapper`、`org.elasticsearch.index.IndexSettings`、`org.elasticsearch.cluster.metadata.MetadataIndexTemplateService`）

**三、高频追问**
- ES 有哪些字段类型？（keyword/text/数值/dense_vector/object/nested）
- keyword 和 text 区别？（精确匹配 vs 分词全文检索）
- Dynamic Mapping 有什么坑？（字段爆炸、类型冲突）
- nested 和 object 区别？（nested 独立对象数组、object 扁平化）
- Runtime Field 是什么？（8.x 运行时计算字段，不索引）
- 分片数能改吗？（不能，只能 reindex）
- 别名有什么用？（零停机切换、多索引查询）

**四、实战关联**
- Java 场景：Spring Data ES `@Document`/`@Field` 注解定义 Mapping
- 字段类型选型（标签 keyword、标题 text+ik 分词、价格 scaled_float、向量 dense_vector）
- 与 MySQL 表结构设计对比（Schema-on-write vs Schema-on-read、DDL 对照）

**五、系统设计案例**
- 设计一个电商商品搜索的索引方案（标题 text+ik 分词、品牌 keyword、价格 scaled_float、标签 nested、SKU 子文档）
- 设计一个日志索引的 ILM 方案（Hot 1d→Warm 7d→Cold 30d→Delete 90d，按天滚动索引）

---

### 文档 3：`03-inverted-index/inverted-index-and-analysis.md`

> **一句话定位**：倒排索引是 ES 的灵魂，"讲讲倒排索引结构、Analyzer 分词链"是面试起手题，能讲到 FST 与 Roaring Bitmap 才算合格。
> **面试热度**：⭐⭐⭐⭐⭐

**一、概念定义**
- 1.1 倒排索引 vs 正向索引（MySQL B+Tree 按主键找行，ES 倒排按词找文档列表，本质差异——全文检索 vs 精确查询）
- 1.2 倒排结构三部分（Term Dictionary 词典、Term Index 词典索引 FST、Posting List 倒排列表）
- 1.3 存储格式（_source 原始 JSON、doc_values 列存用于排序聚合、_field_data 堆内存兜底）
- 1.4 Analyzer 分词链（Character Filter→Tokenizer→Token Filter 三段式，索引时与查询时各跑一遍）

**二、原理与流程**
- 2.1 Term Dictionary 与 Term Index（词典按 term 排序、Term Index 用 FST 前缀压缩加速定位、FST 为什么省内存——前缀共享）
- 2.2 Posting List 结构（每个 term 对应文档列表、按 doc_id 排序、Frame of Reference 增量压缩、Roaring Bitmap 集合运算）
- 2.3 _source 与 doc_values（_source 存原始 JSON 用于返回、doc_values 列存用于排序聚合、为什么不用 _field_data——堆内存压力）
- 2.4 Posting List 压缩（Frame of Reference 变长增量编码、Roaring Bitmap 分桶高低位、AND/OR 集合运算加速）
- 2.5 Analyzer 分词链详解（Character Filter——HTML strip/mapping、Tokenizer——standard/ik/whitespace、Token Filter——lowercase/stop/synonym/word_delimiter）
- 2.6 Normalizer（keyword 字段的归一化，如小写转换，与 text Analyzer 的区别——不分词只归一化）
- 2.7 索引时分词 vs 查询时分词（`analyzer` 索引时、`search_analyzer` 查询时、`search_analyzer` 不一致导致的"搜不到"问题）
- 2.8 源码路径（`org.apache.lucene.index.Terms`、`org.apache.lucene.codecs.lucene94.Lucene94PostingsFormat`、`org.elasticsearch.index.analysis.AnalysisService`）

**三、高频追问**
- 倒排索引是什么？（Term Dictionary + Term Index FST + Posting List）
- FST 是什么？（前缀压缩的有限状态转换器，省内存加速定位）
- Posting List 怎么压缩？（Frame of Reference 增量编码 + Roaring Bitmap）
- doc_values 是什么？（列存，用于排序聚合，避免 _field_data 堆内存）
- Analyzer 分几步？（Character Filter → Tokenizer → Token Filter）
- ik 分词器是什么？（中文分词，ik_smart 粗粒度/ik_max_word 细粒度）
- 索引时和查询时分词不一致会怎样？（搜不到，需 search_analyzer 对齐）

**四、实战关联**
- Java 场景：自定义 Analyzer 插件开发、Synonym 同义词配置
- 分词器选型（中文用 ik、英文用 standard、自定义 Token Filter）
- 与 MySQL 全文索引对比（InnoDB Fulltext vs ES 倒排，倒排更强大但维护成本高）

**五、系统设计案例**
- 设计一个中英文混合的搜索分词方案（Character Filter 处理 HTML、Tokenizer 用 ik、Token Filter 加同义词与停用词）
- 设计一个千万级标签的倒排索引（keyword 标签的 Posting List 压缩、Roaring Bitmap 集合运算加速）

---

### 文档 4：`04-read-write-translog/read-write-and-translog.md`

> **一句话定位**：读写流程是 ES 近实时性的根基，"写后为什么 1s 才能搜到、translog 是什么"是中高级面试分水岭。
> **面试热度**：⭐⭐⭐⭐⭐

**一、概念定义**
- 1.1 ES 近实时模型（写后不立即可搜，refresh 1s 后才可见，与 MySQL 事务提交即可见的本质差异）
- 1.2 写流程三阶段（写 index buffer + translog→refresh 生成 segment→flush 持久化 segment 并清空 translog）
- 1.3 Translog（事务日志，WAL 思想，崩溃恢复用，与 MySQL Redo Log 一致）
- 1.4 版本控制与乐观并发（`_version`/`seq_no`/`primary_term`，`if_seq_no`+`if_primary_term` 乐观锁）

**二、原理与流程**
- 2.1 写流程 primary→replica（协调节点路由→primary 写 buffer+translog→并行写 replica→primary 返回，`wait_for_active_shards` 一致性）
- 2.2 Translog 刷盘策略（`index.translog.durability`：`request` 每次请求 fsync/`async` 定时 fsync，`sync_interval` 间隔，与 Redis AOF appendfsync 对照）
- 2.3 refresh 流程（index buffer→segment 的 `DocumentsWriter` 生成新 segment、内存可见、默认 1s、`?refresh=true` 强制刷新）
- 2.4 flush 流程（segment 持久化到磁盘、translog 清空、`fsync` segment 文件、`index.translog.flush_threshold_size` 触发阈值）
- 2.5 segment 不可变性（Lucene segment 写后不可改，删除是标记 tombstone、更新是标记旧版本、merge 合并清理）
- 2.6 版本控制（`_version` 递增、`seq_no`+`primary_term` 替代老版本、`if_seq_no`+`if_primary_term` 乐观锁、外部版本 `version_type=external`）
- 2.7 bulk 批量写（`_bulk` API 批量、JSON Action/Metadata 格式、批量大小推荐 5-15MB、并行 bulk 提升吞吐）
- 2.8 写一致性（`wait_for_active_shards`：`1`/`quorum`/`all`，quorum=`(replica+1)/2`）
- 2.9 源码路径（`org.elasticsearch.index.engine.InternalEngine`、`org.elasticsearch.index.translog.Translog`、`org.elasticsearch.index.engine.DocumentWriter`）

**三、高频追问**
- 写后为什么 1s 才能搜到？（index buffer 需 refresh 生成 segment 才可搜）
- translog 是什么？（事务日志，WAL，崩溃恢复用）
- refresh 和 flush 区别？（refresh 生成内存 segment 可搜，flush 持久化磁盘清空 translog）
- translog 怎么刷盘？（`request` 每次请求 fsync/`async` 定时 fsync）
- ES 怎么保证写不丢？（translog fsync + 副本数）
- 怎么做乐观锁？（`if_seq_no`+`if_primary_term`）
- bulk 怎么用？（`_bulk` API，批量 5-15MB）

**四、实战关联**
- Java 场景：RestHighLevelClient `BulkRequest`、`UpdateRequest` `ifSeqNo`+`ifPrimaryTerm`
- 写性能调优（批量大小、`refresh_interval` 调大、translog `async`）
- 与 MySQL Redo Log 对比（WAL 思想一致，但 ES refresh 近实时 vs MySQL 事务提交即持久）
- 与 Redis AOF 对比（translog 刷盘 vs AOF appendfsync 策略对照）

**五、系统设计案例**
- 设计一个高吞吐写入方案（bulk 批量 15MB + `refresh_interval` 30s + translog async + 副本 1）
- 设计一个乐观并发更新方案（`if_seq_no`+`if_primary_term` + 重试，避免并发更新覆盖）

---

### 文档 5：`05-query-dsl-scoring/query-dsl-and-scoring.md`

> **一句话定位**：查询与打分是 ES 检索的核心，"Bool 查询怎么组合、BM25 怎么打分"是中高级面试必问。
> **面试热度**：⭐⭐⭐⭐⭐

**一、概念定义**
- 1.1 Query DSL 概述（JSON 结构表达查询，`query`/`bool`/`filter`/`must` 等，与 SQL 表达式对照）
- 1.2 查询上下文 vs 过滤上下文（query 算分参与排序，filter 不算分只判断匹配，filter 可缓存）
- 1.3 Bool 查询四子句（`must` 算分且匹配、`should` 算分至少 N 个匹配、`filter` 不算分只过滤、`must_not` 不匹配）
- 1.4 BM25 打分（TF/IDF 的演进，BM25 引入饱和与文档长度归一化，可调参数 `k1`/`b`）

**二、原理与流程**
- 2.1 Query DSL 结构（`query`→`bool`→`must/should/filter/must_not`，叶子查询 term/range/match/multi_match）
- 2.2 term 与 match（`term` 不分词精确匹配 keyword、`match` 对查询词分词后 OR 匹配 text、`match_phrase` 短语匹配）
- 2.3 Bool 查询组合（`must` 参与算分、`should` `minimum_should_match` 控制、`filter` 利用缓存不算分、`must_not` 反向过滤）
- 2.4 Function Score（`script_score` 脚本打分、`field_value_factor` 字段值加权、`weight` 权重、`random_score` 随机、`decay_functions` 衰减函数）
- 2.5 BM25 打分公式（`IDF × (f(k1+1) / f+k1(1-b+b×dl/avgdl))`，`k1` 控制 TF 饱和度默认 1.2、`b` 控制文档长度归一化默认 0.75）
- 2.6 Rescoring（`rescore` 窗口内重打分，先用 query 粗筛再用 `rescore_query` 精排，`window_size` 控制）
- 2.7 分页（`from`+`size` 深度分页性能差——coordinate node 需取 `from+size` 条归并、`search_after` 游标分页、`PIT` Point-in-Time 8.x 保证结果一致性）
- 2.8 协调节点 Scatter-Gather（路由到各分片、各分片局部排序返回 TopN、协调节点归并全局 TopN）
- 2.9 源码路径（`org.elasticsearch.index.query.BoolQueryBuilder`、`org.elasticsearch.index.query.MatchQueryBuilder`、`org.apache.lucene.search.similarities.BM25Similarity`）

**三、高频追问**
- Bool 查询四子句区别？（must 算分、should 至少 N 个、filter 不算分、must_not 反向）
- 查询和过滤上下文区别？（query 算分参与排序，filter 不算分可缓存）
- term 和 match 区别？（term 不分词精确匹配，match 分词后匹配）
- BM25 怎么打分？（TF/IDF 演进，引入饱和与文档长度归一化）
- `k1` 和 `b` 调什么？（k1 TF 饱和度，b 文档长度归一化）
- 深度分页怎么办？（`search_after` 或 `PIT`）
- Function Score 怎么用？（script_score/field_value_factor 等加权）

**四、实战关联**
- Java 场景：RestHighLevelClient `SearchRequest`/`BoolQueryBuilder`/`MatchQueryBuilder`
- 打分调优（`k1`/`b` 调参、`function_score` 业务加权）
- 与 MySQL 查询对比（SQL vs Query DSL，LIKE vs match，B+Tree vs 倒排）

**五、系统设计案例**
- 设计一个电商搜索的查询方案（标题 match + 品牌 filter + 价格 range + 销量 field_value_factor 加权 + BM25 打分）
- 设计一个深度分页方案（`PIT` + `search_after`，避免 from+size 深度分页性能问题）

---

### 文档 6：`06-aggregation/aggregation-and-pipeline.md`

> **一句话定位**：聚合是 ES 分析能力的核心，"Bucket/Metric/Pipeline 三类聚合、Cardinality 怎么去重"是中高级面试必问。
> **面试热度**：⭐⭐⭐⭐

**一、概念定义**
- 1.1 聚合三类（Bucket 桶聚合按维度分组、Metric 指标聚合计算值、Pipeline 管道聚合对其他聚合结果二次计算）
- 1.2 Bucket 聚合（`terms` 按字段值分桶、`date_histogram` 按时间分桶、`nested` 嵌套对象分桶、`filter` 自定义过滤分桶）
- 1.3 Metric 聚合（`avg`/`sum`/`max`/`min` 基础、`cardinality` 去重计数 hyperloglog++、`percentile` 百分位 t-digest、`stats` 多指标合并）
- 1.4 Pipeline 聚合（`moving_avg` 移动平均、`derivative` 导数、`cumulative_sum` 累计和，基于 Bucket 结果二次计算）

**二、原理与流程**
- 2.1 Bucket 聚合原理（`terms` 用 `doc_values` 或 `global_ordinals` 收集、`size` 控制 Bucket 数、`doc_count_error` 误差与 `show_term_doc_count_error`）
- 2.2 `global_ordinals` 优化（keyword 预构建全局序数映射、避免每次聚合遍历 `doc_values`、`eager_global_ordinals` 预加载）
- 2.3 Cardinality hyperloglog++（`cardinality` 用 HLL++ 算法近似去重、`precision_threshold` 控制精度与内存、误差约 `40000/precision`）
- 2.4 Percentile t-digest（`percentile` 用 t-digest 算法近似分位数、`compression` 控制精度与内存、适用于 P50/P95/P99）
- 2.5 子聚合（`aggs` 内嵌套 `aggs`，Bucket→子 Bucket→Metric，树形结构，注意 breadth-first vs depth-first 策略）
- 2.6 聚合内存与调优（`bwc` breadth vs `collect_mode`、`breadth_first` 宽度优先省内存、`depth_first` 深度优先精确、`terminate_after` 限制）
- 2.7 ES|QL 8.x（管道式查询语言 `POST _query`、`FROM`/`WHERE`/`STATS`/`SORT`/`LIMIT`，统一查询、聚合、计算，替代 `_search` + `aggs` 的表达力不足）
- 2.8 源码路径（`org.elasticsearch.search.aggregations.AggregationPhase`、`org.elasticsearch.search.aggregations.bucket.terms.TermsAggregator`、`org.elasticsearch.search.aggregations.metrics.CardinalityAggregator`）

**三、高频追问**
- 聚合有哪几类？（Bucket/Metric/Pipeline）
- Cardinality 怎么去重？（hyperloglog++ 近似算法）
- precision_threshold 是什么？（控制 Cardinality 精度与内存）
- Percentile 怎么算？（t-digest 近似算法）
- `global_ordinals` 是什么？（keyword 全局序数优化聚合）
- 子聚合怎么嵌套？（`aggs` 内嵌 `aggs`，树形）
- ES|QL 是什么？（8.x 管道查询语言，统一查询聚合计算）

**四、实战关联**
- Java 场景：RestHighLevelClient `SearchRequest` `AggregationBuilders.terms`/`cardinality`
- 聚合调优（`eager_global_ordinals` 预加载、`collect_mode` 策略、`terminate_after` 限制）
- 与 MySQL GROUP BY 对比（SQL 聚合 vs ES 聚合，B+Tree 扫描 vs `doc_values` 列存）

**五、系统设计案例**
- 设计一个电商商品的多维聚合方案（品牌 terms + 价格 percentile + 销量 cardinality 去重 + 子聚合 date_histogram 按月统计）
- 设计一个日志分析的 ES|QL 方案（`FROM logs | WHERE level=="ERROR" | STATS count=count(*) BY service | SORT count DESC | LIMIT 10`）

---

### 文档 7：`07-shard-routing/shard-routing-and-reindex.md`

> **一句话定位**：分片路由是 ES 分布式能力的核心，"分片怎么路由、为什么分片数不可改、reindex 怎么重建"是资深面试分水岭。
> **面试热度**：⭐⭐⭐⭐

**一、概念定义**
- 1.1 分片路由（`hash(routing) % num_primary_shards`，默认 `routing=_id`，可自定义 `routing` 参数）
- 1.2 分片数不可改（路由公式含 `num_primary_shards`，改了路由结果变，文档找不到，只能 reindex）
- 1.3 CCR 跨集群复制（Leader 索引→Follower 燕引，跨集群灾备与就近读取）
- 1.4 Hot-Warm-Cold 架构（Hot SSD 高写入、Warm HDD 查询、Cold 冷数据归档，ILM 自动迁移）

**二、原理与流程**
- 2.1 路由公式（`hash(routing) % num_primary_shards`、`routing` 默认 `_id`、自定义 `routing=user_id` 让同用户数据同分片、风险——数据倾斜）
- 2.2 分片数规划（单分片大小推荐 30-50GB、过小 over-sharding 浪费资源、过大 rebalance 慢、`number_of_shards` 一旦定不可改）
- 2.3 Reindex 重建索引（`_reindex` API、source/dest、`op_type=create` 避免覆盖、sliced 并行、`size` 批量）
- 2.4 Update By Query（`_update_by_query` 批量更新匹配文档、`scripted` 脚本更新、`version_type=internal` 乐观锁）
- 2.5 Delete By Query（`_delete_by_query` 批量删除、与 `DeleteByQueryRequest` 的 Java 调用）
- 2.6 CCR 跨集群复制（Leader/Follower 索引、`auto_follow` 自动跟随、`_ccr/follow` 手动跟随、底层基于 translog 复制）
- 2.7 Hot-Warm-Cold 架构（节点 `node.roles` 分层、`index.routing.allocation.include._tier` 路由、ILM `migrate` 阶段自动迁移）
- 2.8 源码路径（`org.elasticsearch.cluster.routing.OperationRouting`、`org.elasticsearch.index.reindex.ReindexAction`、`org.elasticsearch.xpack.ccr.CCRService`）

**三、高频追问**
- 分片怎么路由？（`hash(routing) % num_primary_shards`）
- 分片数能改吗？（不能，只能 reindex）
- 自定义 routing 有什么用？（同用户同分片，减少跨分片查询）
- 自定义 routing 有什么风险？（数据倾斜）
- reindex 怎么用？（`_reindex` API，sliced 并行）
- CCR 是什么？（跨集群复制，Leader/Follower）
- Hot-Warm-Cold 怎么实现？（节点角色分层 + ILM 迁移）

**四、实战关联**
- Java 场景：`ReindexRequest`/`UpdateByQueryRequest` 的 Java 调用
- 分片数规划（按数据量估算 `number_of_shards`、单分片 30-50GB）
- 与 MySQL 分库分表对比（ShardingSphere vs ES routing，分片路由思想一致）
- 与 Redis Cluster 对比（16384 槽位 vs ES 分片数，都是 hash 取模）

**五、系统设计案例**
- 设计一个亿级文档的分片方案（单分片 40GB、20 分片、副本 1、按时间滚动索引 + ILM）
- 设计一个零停机的 Mapping 变更方案（新索引 + alias + reindex + 切换别名）

---

### 文档 8：`08-ha-tuning/ha-and-tuning.md`

> **一句话定位**：高可用与调优是资深面试的加分项，"节点宕机怎么恢复、JVM heap 为什么 50%"区分是否有生产经验。
> **面试热度**：⭐⭐⭐⭐

**一、概念定义**
- 1.1 ES 高可用（副本故障转移、Master 选举、分片再平衡，与 MySQL MHA/RocketMQ Dledger 对照）
- 1.2 调优核心目标（索引吞吐、查询延迟、聚合内存、JVM heap 50% 规则与 os cache）
- 1.3 监控体系（cat API、`_cluster/health`、`_nodes/stats`、Prometheus + Grafana）
- 1.4 常见故障（节点宕机、分片未分配、JVM OOM、脑裂、慢查询）

**二、原理与流程**
- 2.1 副本故障恢复（primary 宕机→replica 提升为 primary、`RerouteActionCode`、`AllocationService` 重新分配、recovery 从 primary 复制到新 replica）
- 2.2 分片再平衡（`cluster.routing.allocation.balance.shard`/`index`/`threshold`、`cluster.routing.allocation.cluster_concurrent_rebalance` 并发数、`DiskWatermark` 磁盘水位触发）
- 2.3 监控 cat API（`_cat/indices` 索引状态、`_cat/shards` 分片分布、`_cat/health` 集群健康、`_cat/allocation` 节点磁盘）
- 2.4 监控 _cluster/health（`status` green/yellow/red、`active_shards`/`relocating_shards`/`unassigned_shards`、`delayed_unassigned_shards` 延迟分配）
- 2.5 监控 _nodes/stats（`jvm.mem`/`gc`/`thread_pool`/`indices`/`fs`/`transport`、按节点维度定位瓶颈）
- 2.6 JVM heap 50% 规则（Lucene segment mmap 用 os cache、heap 给 Lucene inverted index 缓存与 query/aggregation 中间结果、50% 平衡 heap 与 os cache）
- 2.7 Circuit Breaker（`parent_breaker` 总熔断、`fielddata`/`request`/`accounting` 子熔断、`indices.breaker.total.limit` 默认 95% heap、避免 OOM kill）
- 2.8 调优索引吞吐（`refresh_interval` 调大、`number_of_replicas` 0 临时、`translog.durability=async`、bulk 批量）
- 2.9 调优查询延迟（file cache 8.x、`index.store.preload` 预加载、`indices.queries.cache.size` 查询缓存）
- 2.10 版本升级 7.x→8.x（安全默认开启 TLS、`Security` 自动配置、API 兼容、`elasticdump` 数据迁移）
- 2.11 源码路径（`org.elasticsearch.cluster.routing.allocation.AllocationService`、`org.elasticsearch.indices.breaker.CircuitBreakerService`、`org.elasticsearch.monitor.jvm.JvmStats`）

**三、高频追问**
- 节点宕机怎么办？（replica 提升为 primary，重新分配）
- 分片未分配怎么排查？（`_cat/shards` + `_cluster/allocation/explain`）
- JVM heap 为什么 50%？（给 os cache 留空间，Lucene segment mmap）
- Circuit Breaker 是什么？（熔断器，避免 OOM kill）
- 怎么提升写入吞吐？（`refresh_interval` 调大 + bulk 批量 + 副本 0 临时）
- 7.x 升 8.x 注意什么？（安全默认开启、API 兼容）
- yellow 和 red 区别？（yellow 副本未分配，red 主分片未分配）

**四、实战关联**
- Java 场景：RestHighLevelClient `ClusterHealthRequest`/`NodesStatsRequest`
- 生产部署（3 Master 专用 + N Data、JVM heap 31GB、SSD + file cache）
- 与 `ops/linux` 监控对照（JVM heap vs os cache、进程线程模型）
- 与 `ops/docker`/`ops/k8s` 部署对照（`vm.max_map_count`、StatefulSet + PV）

**五、系统设计案例**
- 设计一个 ES 生产集群的监控告警体系（Prometheus + elasticsearch-exporter + 5 大类指标 + 阈值告警）
- 设计一次从 7.x 到 8.x 的零停机升级方案（新集群搭建 + CCR 复制 + 灰度切流 + 别名切换）

---

### 文档 9：`08-interview-qa.md`

> **一句话定位**：面试前冲刺用，40+ 题速答串联各主题，附连环套问思维导图。
> **面试热度**：⭐⭐⭐⭐⭐

**结构**（与 MySQL、Redis、RocketMQ Q&A 完全对齐）：

- **使用说明**：每题 3-5 句要点速答，末尾 **关联** 链接指向对应主题文档
- **各篇题目数与关联文档**：

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

- **连环套问思维导图**：6 条追问链（与 MySQL、Redis、RocketMQ 的 6 条对齐）：
  - 链 1：节点角色 → Master 选举 → Zen2 → Voting Configuration → 为什么不用 ZK
  - 链 2：倒排索引 → Term Dictionary → FST → Posting List → Roaring Bitmap → doc_values
  - 链 3：写流程 → index buffer → refresh → segment → translog → flush → 近实时可见
  - 链 4：Query DSL → Bool 查询 → filter vs query → BM25 → k1/b → Function Score
  - 链 5：聚合 → Bucket/Metric/Pipeline → Cardinality HLL++ → Percentile t-digest → global_ordinals → ES|QL
  - 链 6：分片路由 → routing 公式 → 分片数不可改 → reindex → CCR → Hot-Warm-Cold

---

## 八、文档统一规范

### 文档头部模板

```markdown
# <主题标题>

> **一句话定位**：<1 句话说明该主题在面试中的定位与合格标准>
> **面试热度**：⭐⭐⭐⭐⭐（或 ⭐⭐⭐⭐）
> **返回**：[ES 知识图谱](../README.md)

---
```

### 五段式结构

| 段落 | 标题 | 内容要求 |
|------|------|---------|
| 一 | 概念定义 | 定义、对比表、设计动机、术语澄清 |
| 二 | 原理与流程 | 核心原理推导、mermaid 流程图、源码路径、数据结构图解 |
| 三 | 高频追问 | 6-8 个面试常见追问，每题 2-3 句要点速答 |
| 四 | 实战关联 | Java/Spring 场景落地、与仓库内模块的关联 |
| 五 | 系统设计案例 | 1-2 个完整系统设计题，含方案与权衡 |

### 排版约定

- **源码路径**：用 Java 包路径格式标注（如 `org.elasticsearch.index.engine.InternalEngine`、`org.apache.lucene.search.similarities.BM25Similarity`）
- **对比表**：用 markdown 表格，列名与 MySQL、Redis、RocketMQ 风格一致
- **流程图**：用 `mermaid flowchart TD` 或 `sequenceDiagram`
- **关键数字**：加粗（如 **1s** refresh 间隔、**50%** JVM heap 规则、**30-50GB** 单分片大小）
- **命令关键字**：用反引号（如 `_cat/indices`、`_reindex`、`if_seq_no`）
- **关联链接**：用 `→ [文档名](./xx-xxx/xxx.md)` 格式

### 关联约定

- 每份文档顶部 `> 返回 [ES 知识图谱](../README.md)`
- 文档内部引用其他主题时用相对路径链接（如"刷盘策略详见 [读写流程与 Translog](../04-read-write-translog/read-write-and-translog.md)"）
- Q&A 文档每题末尾 `**关联**：→ [文档名](./xx-xxx/xxx.md)`

### 版本基线标注

- 默认 Elasticsearch 8.x，涉及版本差异时标注（如"7.x 无 Runtime Field"、"8.x 引入 file cache"）
- 与 MySQL 的"MySQL 8.0，5.7 仅作差异对比"、Redis 的"Redis 7.x，5.x/6.x 仅作差异对比"、RocketMQ 的"RocketMQ 5.x，4.x 仅作差异对比"风格对齐

---

## 九、实施顺序

按认知递进与依赖关系，建议分 4 批实施（每批可并行）：

| 批次 | 文档 | 依赖 |
|------|------|------|
| 第 1 批 | README.md + 01 架构与部署 + 02 索引与映射 | 无依赖，可并行 |
| 第 2 批 | 03 倒排索引与分词 + 04 读写流程与 Translog | 引用 01/02 的概念 |
| 第 3 批 | 05 查询 DSL 与打分 + 06 聚合 | 引用 03/04 的概念 |
| 第 4 批 | 07 分片路由与 Reindex + 08 高可用与调优 + 08 Q&A + middleware/README.md 更新 | Q&A 引用所有主题，最后完成 |

> Q&A 文档必须最后写，因为它要串联所有主题；README.md 可先搭骨架，待所有主题完成后回填导航表状态。

---

## 十、设计自检

| 检查项 | 结果 |
|--------|------|
| **占位符扫描**：有无 TBD/TODO/未填内容？ | ✅ 无，所有大纲已展开到三级要点 |
| **内部一致性**：mindmap 一级节点 vs 导航表 vs 文档大纲是否一致？ | ✅ 9 个一级节点（8 主题 + 面试冲刺）一一对应 |
| **mindmap 二级节点 vs 文档大纲**：是否覆盖？ | ✅ 每个二级节点都在对应文档的"原理与流程"中展开 |
| **Q&A 题目数 vs 主题文档**：41 题分配到 8 篇是否覆盖所有主题？ | ✅ 6+6+5+5+6+4+5+4=41 |
| **与 MySQL/Redis/RocketMQ 模块对齐**：结构/格式/深度是否一致？ | ✅ 目录组织/mindmap/导航表/学习路径/模块关联/五段式/Q&A 全部对齐 |
| **与 java-core/framework 关联**：是否每条关联都有对应的仓库模块？ | ✅ 12 条关联均指向实际存在的模块 |
| **与 ops 交叉引用**：跳转目标是否存在或已标注？ | ✅ ops/linux 各文件标注（含 `network-kernel.md` 实际文件名），docker/k8s 标注 |
| **与 middleware 内交叉引用**：mysql/redis/rocketmq 跳转是否合理？ | ✅ 倒排 vs B+Tree、translog vs Redo Log/AOF、分片 vs 槽位/分库分表、副本 vs 主从复制均有对照 |
| **ES 8.x 新特性覆盖**：是否包含 8.x 关键特性？ | ✅ Runtime Field、dense_vector KNN、ES\|QL、file cache、PIT、安全默认开启 |
| **深度达标**：是否有源码路径、数据结构图解、数字推导？ | ✅ 每份文档含 Java/Lucene 源码路径、关键数字加粗、mermaid 图 |
| **范围控制**：是否适合单轮实现？ | ✅ 10 份文档按主题独立，可并行编写 |
