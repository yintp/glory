# Elasticsearch 面试知识体系实施计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 在 `middleware/es/` 下构建 10 份文档的 Elasticsearch 面试知识体系，深度对标 `middleware/mysql`、`middleware/redis`、`middleware/rocketmq` 模块，覆盖 ES 8.x。

**Architecture:** 纯文档项目，无代码无测试。按 spec 的分阶段交付节奏，每个 Task 完成一份文档并自检（结构校验、链接校验、体量校验）后提交。文档遵循 ES 专用五段式：概念定义 → 原理与流程 → 高频追问 → 实战关联（Java 后端视角）→ 系统设计案例。

**Tech Stack:** Markdown + Mermaid 图表，中文撰写。

## Global Constraints

- 语言：全部中文（遵循 AGENTS.md 约定）
- 模块路径：`middleware/es/`（目录骨架在各 Task 中创建）
- 文档结构：ES 专用五段式（概念定义/原理与流程/高频追问/实战关联/系统设计案例）
- 单份主题文档体量：600-900 行（ES 知识点密集，与 MySQL/Redis/RocketMQ 对齐）
- Q&A 文档体量：500-700 行
- README 体量：240-320 行（含 mindmap + 导航表 + 学习路径 + 模块关联 + 交叉引用，8 主题比 7 主题略多）
- 深度：原理级 + 架构级 + 实战级（对标 mysql/redis/rocketmq）
- 版本基线：Elasticsearch 8.x（覆盖 Runtime Field、dense_vector KNN、ES|QL、file cache、PIT、安全默认开启等特性，7.x 仅作差异对比）
- 每份主题文档头部固定三行：`> **一句话定位**` / `> **面试热度**：⭐⭐⭐⭐⭐` / `> **返回**：[ES 知识图谱](../README.md)`
- README 自动更新规则：每完成一份主题文档，回填 `middleware/es/README.md` 导航表进度标记；完成任何模块内容变更同步更新 `middleware/README.md`
- 提交规范：`docs(es): <描述>`，参照现有 `docs(mysql):` / `docs(redis):` / `docs(rocketmq):` 风格
- 参考样本：`middleware/redis/01-data-structure/data-structure-and-encoding.md`（主题文档五段式）、`middleware/rocketmq/08-interview-qa.md`（Q&A）、`middleware/redis/README.md`（入口）、`middleware/mysql/01-index/index-and-optimization.md`（索引底层深度样本）
- 交叉引用原则：ES 章只讲"ES 场景下的实现与选择"，原理推导链回对应模块（ops/linux、middleware/mysql、middleware/redis、middleware/rocketmq、java-core、framework），不重复展开
- 源码引用约定：ES 源码用 Java 包路径格式标注（如 `org.elasticsearch.index.engine.InternalEngine`、`org.apache.lucene.search.similarities.BM25Similarity`），与 RocketMQ 的 `store.CommitLog` 格式风格一致
- 进度标记：导航表初始用 `⬜`，完成后回填为 `✅`

## File Structure

```
middleware/es/
├── README.md                                    # Task 1 创建（入口）
├── 01-architecture/
│   └── architecture-and-topology.md             # Task 2（架构与部署拓扑）
├── 02-index-mapping/
│   └── index-and-mapping.md                     # Task 3（索引与映射）
├── 03-inverted-index/
│   └── inverted-index-and-analysis.md          # Task 4（倒排索引与分词）
├── 04-read-write-translog/
│   └── read-write-and-translog.md              # Task 5（读写流程与 Translog）
├── 05-query-dsl-scoring/
│   └── query-dsl-and-scoring.md                # Task 6（查询 DSL 与打分）
├── 06-aggregation/
│   └── aggregation-and-pipeline.md             # Task 7（聚合与 Pipeline）
├── 07-shard-routing/
│   └── shard-routing-and-reindex.md            # Task 8（分片路由与 Reindex）
├── 08-ha-tuning/
│   └── ha-and-tuning.md                         # Task 9（高可用与调优）
└── 08-interview-qa.md                           # Task 10（面试 Q&A 速答，含回填）
```

每份主题文档职责：覆盖该专题的底层机制 + 实战关联（Java 后端视角）+ 系统设计案例，独立可读。Q&A 篇不套五段式，采用速答列表 + 连环套问思维导图。

---

## Task 1: 创建 middleware/es/README.md 入口

**Files:**
- Create: `middleware/es/README.md`
- Create: `middleware/es/01-architecture/`、`02-index-mapping/`、`03-inverted-index/`、`04-read-write-translog/`、`05-query-dsl-scoring/`、`06-aggregation/`、`07-shard-routing/`、`08-ha-tuning/`（8 个子目录，用 mkdir -p 创建）
- Modify: `middleware/README.md`（把 `es` 行从纯文本改为链接）

**Interfaces:**
- Produces: `middleware/es/README.md`，作为后续所有主题文档的导航入口；导航表中的链接路径是后续 Task 的产出契约

- [ ] **Step 1: 创建目录骨架**

Run:
```bash
mkdir -p middleware/es/01-architecture middleware/es/02-index-mapping middleware/es/03-inverted-index middleware/es/04-read-write-translog middleware/es/05-query-dsl-scoring middleware/es/06-aggregation middleware/es/07-shard-routing middleware/es/08-ha-tuning
```

- [ ] **Step 2: 编写 middleware/es/README.md**

按 spec 第二节"模块整体结构"与第三节"知识图谱 mindmap"编写，内容要点：

**一、模块简介**：
- 定位：面向 Java 后端高级/资深面试的 Elasticsearch 知识体系，深度对标 `middleware/mysql`、`middleware/redis`、`middleware/rocketmq`、`ops/linux`
- 适用对象：Java 后端面试（社招高级/资深，5 年+），兼顾架构与系统设计方向
- 组织方式：8 个主题目录 + 1 个 Q&A 文件，每份主题文档遵循「概念定义 → 原理与流程 → 高频追问 → 实战关联 → 系统设计案例」五段式结构
- 导航约定：每份文档顶部含 `> 返回 [ES 知识图谱](../README.md)` 链接，本文档为统一入口
- 版本基线：Elasticsearch 8.x（覆盖 Runtime Field、dense_vector KNN、ES|QL、file cache、PIT、安全默认开启等特性，7.x 仅作差异对比）

**二、知识图谱（Mermaid mindmap）**：根节点 `Elasticsearch`，9 大分支（完整内容见 spec 第二节 mindmap）：

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

**三、导航表**（9 行，与 spec 第三节完全一致）：

| 分层 | 文档 | 核心考点 |
|------|------|---------|
| 架构与部署 | [架构与部署拓扑](./01-architecture/architecture-and-topology.md) ⬜ | 节点角色/Master 选举 Raft-like/Zen2/Index-Shard-Replica/Netty 线程模型 |
| 索引与映射 | [索引与映射](./02-index-mapping/index-and-mapping.md) ⬜ | Index Settings/Mapping 字段类型/Dynamic Mapping/Dynamic Template/Runtime Field/别名模板 ILM |
| 倒排索引与分词 | [倒排索引与分词](./03-inverted-index/inverted-index-and-analysis.md) ⬜ | FST 倒排结构/doc_values 列存/Roaring Bitmap/Analyzer 分词链/Normalizer/索引与分词选型 |
| 读写流程与 Translog | [读写流程与 Translog](./04-read-write-translog/read-write-and-translog.md) ⬜ | 写流程 primary→replica/translog 刷盘/refresh 1s 可见/flush/版本乐观并发/bulk 批量 |
| 查询 DSL 与打分 | [查询 DSL 与打分](./05-query-dsl-scoring/query-dsl-and-scoring.md) ⬜ | Query DSL/Bool must·should·filter·must_not/Function Score/BM25 打分可调参数/Rescoring/search_after·PIT |
| 聚合 | [聚合与 Pipeline](./06-aggregation/aggregation-and-pipeline.md) ⬜ | Bucket/Metric/Pipeline/Cardinality hyperloglog++/Percentile t-digest/聚合内存调优/ES\|QL 8.x |
| 分片路由与 Reindex | [分片路由与 Reindex](./07-shard-routing/shard-routing-and-reindex.md) ⬜ | routing 路由公式/routing key 选型/CCR 跨集群复制/reindex·Update By Query/分片数规划/Hot-Warm-Cold |
| 高可用与调优 | [高可用与调优](./08-ha-tuning/ha-and-tuning.md) ⬜ | 副本故障恢复/分片再平衡/cat API·_cluster/health/JVM heap·circuit breaker/file cache 8.x/版本升级 7.x→8.x |
| 面试冲刺 | [Q&A 速答](./08-interview-qa.md) ⬜ | 40+ 题速答 + 连环套问思维导图 |

> 共 **10 份**文档：入口 README（本文档）+ 上表 8 份主题文档 + 1 份 Q&A 文档。

**四、推荐学习路径**：
- 路线一：系统学习（1-2 周）：01 架构 → 02 索引与映射 → 03 倒排索引与分词 → 04 读写流程与 Translog → 05 查询 DSL 与打分 → 06 聚合 → 07 分片路由与 Reindex → 08 高可用与调优 → 08 Q&A
- 路线二：面试冲刺（3-5 天）：01 架构 + 02 索引与映射 → 03 倒排索引 + 05 查询打分 → 04 读写 + 06 聚合 → 07 分片路由 + 08 调优 → 08 Q&A
- 起手三连问：倒排索引原理 → 写入流程与近实时可见 → 查询与打分

**五、与 java-core / framework 模块的关联**（12 条，见 spec 第五节）：
- 01 架构 / Netty 线程模型 → `java-core/lambda`：ES Transport 层 Netty 4 与 Stream 异步编程的对照
- 01 架构 / 节点角色与线程 → `java-core/jvm`：Master/Data 节点角色与 JVM 线程模型的对照
- 03 倒排索引 / doc_values → `java-core/jvm`：doc_values 列存与堆外 DirectByteBuffer 的对照
- 04 读写 / translog fsync → `java-core/jvm`：translog 刷盘与 JVM GC 停顿对可见性延迟的影响
- 05 查询 / Spring Data ES → `framework/spring-framework`：`@Document`/`ElasticsearchRepository` 注解驱动配置
- 05 查询 / 客户端连接 → `framework/spring-framework`：RestClient 连接池与 Spring 集成
- 05 查询 / 序列化 → `framework/jackson`：查询结果 JSON 反序列化与 Jackson 自定义序列化
- 07 分片 / routing key → `framework/spring-framework`：routing 与 Spring 多数据源路由的对照
- 08 调优 / JVM heap → `java-core/jvm`：ES JVM 堆 50% 规则与 JVM GC 调优、堆外内存预算
- 08 调优 / circuit breaker → `java-core/jvm`：ES 熔断器与 JVM 内存溢出保护的对照
- 08 调优 / 序列化 → `framework/jackson`：bulk JSON 序列化与 Jackson 配置
- 08 调优 / 参数校验 → `framework/valid`：索引字段校验与 Hibernate Validator 的互补

**延伸阅读**：
- `java-core/jvm` —— 对照理解 ES JVM heap 50% 规则、堆外内存（Lucene segment mmap）、GC 停顿对近实时可见性的影响
- `framework/spring-framework` —— Spring Data Elasticsearch 的 `@Document` 注解驱动、RestClient 连接池
- `framework/jackson` —— bulk/查询结果的 JSON 序列化器与 Jackson 自定义配置

**六、与 ops 模块的交叉引用**（10 条，见 spec 第六节）：
- 02 索引与映射 → `ops/linux/05-fs/filesystem-and-vfs.md`：segment 文件组织与文件系统、fsync 崩溃一致性
- 03 倒排索引 → `ops/linux/03-memory/memory-management.md`：doc_values 列存与 mmap、堆外内存与 os cache 的权衡
- 04 读写与 translog → `ops/linux/05-fs/filesystem-and-vfs.md`：translog fsync 与文件系统崩溃一致性
- 04 读写与 translog → `ops/linux/03-memory/memory-management.md`：ES 堆外内存（Lucene mmap）、JVM heap 与 os cache 的分配
- 04 读写与 translog → `ops/linux/04-io/io-model-and-epoll.md`：Netty 4 线程模型与 epoll、IO 多路复用
- 08 高可用 → `ops/linux/06-network/network-kernel.md`：节点间 TCP 长连接、集群发现与网络分区
- 08 调优 → `ops/linux/02-process/process-and-thread.md`：ES JVM 进程模型 vs Linux 进程线程
- 08 调优 → `ops/linux/03-memory/memory-management.md`：JVM heap 50% 与 os file cache 的内存权衡
- 08 调优 → `ops/docker/`：ES 容器化部署、`vm.max_map_count` 内核参数
- 08 调优 → `ops/k8s/`：ES on K8s、Elastic Operator、PV 与 StatefulSet

**与 middleware 内其他模块的交叉引用**（10 条）：
- 02 索引与映射 → `middleware/mysql/01-index/index-and-optimization.md`：ES Mapping vs MySQL 表结构、B+Tree vs 倒排索引
- 03 倒排索引 → `middleware/mysql/01-index/index-and-optimization.md`：倒排索引 vs B+Tree 正向索引的本质差异
- 03 倒排索引 → `middleware/redis/01-data-structure/data-structure-and-encoding.md`：FST vs Redis SDS/dict 内存结构对照
- 04 读写与 translog → `middleware/mysql/06-log/log-system.md`：translog WAL vs MySQL Redo Log WAL 思想一致
- 04 读写与 translog → `middleware/redis/02-persistence/persistence-mechanism.md`：translog 刷盘 vs Redis AOF appendfsync 策略对照
- 05 查询打分 → `middleware/mysql/04-query/query-optimization.md`：ES 全文检索 vs MySQL LIKE/全文索引的本质差异
- 07 分片路由 → `middleware/redis/05-replication/replication-and-cluster.md`：ES 分片 vs Redis Cluster 16384 槽位
- 07 分片路由 → `middleware/mysql/07-architecture/ha-and-sharding.md`：ES 分片 vs MySQL 分库分表对照
- 08 高可用 → `middleware/rocketmq/04-ha/ha-and-replication.md`：ES 副本恢复 vs RocketMQ 主从复制
- 08 高可用 → `middleware/redis/05-replication/replication-and-cluster.md`：ES 副本 vs Redis 主从复制

> 处理原则：ES 章只讲"ES 场景下的实现与选择"，原理推导链回对应模块，不重复展开。

- [ ] **Step 3: 更新 middleware/README.md**

把 `middleware/README.md` 第 6 行 `- es` 改为：
```
- [es](./es) — Elasticsearch 面试知识体系（10 份文档，面向 5 年+ 资深面试）
```

- [ ] **Step 4: 体量与结构校验**

Run: `wc -l middleware/es/README.md`，Expected: 240-320 行。
Run: `grep -c '^|' middleware/es/README.md`，Expected: ≥ 11（导航表 9 行 + 表头分隔）。
Run: `grep 'mindmap' middleware/es/README.md`，Expected: 含 mermaid mindmap。
Run: `grep '学习路径\|路线一\|路线二' middleware/es/README.md`，Expected: 含两条学习路径。

- [ ] **Step 5: 提交**

```bash
git add middleware/es/README.md middleware/es/01-architecture middleware/es/02-index-mapping middleware/es/03-inverted-index middleware/es/04-read-write-translog middleware/es/05-query-dsl-scoring middleware/es/06-aggregation middleware/es/07-shard-routing middleware/es/08-ha-tuning middleware/README.md
git commit -m "docs(es): 创建 ES 模块骨架与 README 入口"
```

---

## Task 2: 01-architecture/architecture-and-topology.md 架构与部署拓扑

**Files:**
- Create: `middleware/es/01-architecture/architecture-and-topology.md`
- Modify: `middleware/es/README.md`（导航表第一行 `⬜` → `✅`）

**Interfaces:**
- Consumes: `middleware/es/README.md` 导航表链接路径
- Produces: 架构基础概念（节点角色、Cluster/Discovery、Master 选举、Index/Shard/Replica、Netty 线程模型），后续 Task 3-9 的内容均引用本文档定义的术语

- [ ] **Step 1: 编写 architecture-and-topology.md**

按 spec 第七章文档 1 大纲展开五段式内容，600-900 行。头部三行：
```
# 架构与部署拓扑

> **一句话定位**：ES 架构是面试起手题，"讲讲 ES 节点角色与 Master 选举"几乎每场必问，能讲到 Zen2 与 Voting Configuration 才算合格。
> **面试热度**：⭐⭐⭐⭐⭐
> **返回**：[ES 知识图谱](../README.md)
```

**一、概念定义**（约 150 行）：
- 1.1 ES 节点角色（Master/Data/Coordinating/Ingest/Machine Learning，职责划分与协作关系，8.x 角色分离配置 `node.roles`，mermaid 架构图 `flowchart TD` 展示节点角色交互）
- 1.2 Cluster 与 Discovery（Zen2 发现与选举、为什么不用 ZooKeeper——ES 自研 Zen2 更轻量、Master 选举 Raft-like、Voting Configuration 多数派，对比表：ES Zen2 vs ZK 的 CP/AP、有状态/无状态、复杂度）
- 1.3 Index/Shard/Replica 模型（Index 逻辑命名空间、Shard 并行单位、主分片与副本、为什么分片——水平扩展与故障转移，对比表：ES 分片 vs MySQL 分库分表 vs Redis 16384 槽位）
- 1.4 网络模型（Transport 层 TCP 节点间通信、HTTP 层 RestController 客户端接口、Netty 4 线程模型，与 RocketMQ 1+N+M Reactor 的对照）

**二、原理与流程**（约 250 行）：
- 2.1 Master 选举流程（Zen2 的 `DiscoveryNode`、`NodeJoinController`、Voting Configuration 多数派、避免脑裂、`cluster.election.duration`，mermaid sequenceDiagram 展示选举流程）
- 2.2 集群状态发布（`ClusterState` 的 `master`→`node` 推送、二阶段提交 Publish/Commit、`ClusterStatePublisher`，mermaid sequenceDiagram 展示状态发布流程）
- 2.3 分片分配（`Allocator` 的 `ShardAllocator`、感知磁盘水位 `cluster.routing.allocation.disk.watermark`、副本分配 `Decider`，mermaid flowchart 展示分片分配决策链）
- 2.4 协调节点路由（Coordinating 节点接收请求、按分片路由 Scatter-Gather、结果归并 merge，mermaid sequenceDiagram 展示 Scatter-Gather 流程）
- 2.5 Netty 4 线程模型（Transport 层 `Netty4Transport`、HTTP 层 `Netty4HttpServerTransport`、`HttpRequestHandler` 业务线程池，与 Redis Reactor、RocketMQ 1+N+M 的对比表）
- 2.6 源码路径（`org.elasticsearch.discovery.zen2.ZenDiscovery`、`org.elasticsearch.cluster.service.ClusterService`、`org.elasticsearch.http.netty4.Netty4HttpServerTransport`）

**三、高频追问**（约 120 行，6-8 题）：
- ES 有哪些节点角色？（Master/Data/Coordinating/Ingest/ML）
- Master 怎么选出来的？（Zen2 多数派，Raft-like）
- 为什么不用 ZooKeeper？（自研更轻量，无需外部依赖）
- 脑裂怎么避免？（Voting Configuration 多数派，min_master_nodes 弃用）
- 协调节点做什么？（接收请求，路由分片，归并结果）
- Data 节点能当 Master 吗？（默认能，生产建议角色分离）
- Index/Shard/Replica 关系？（Index 逻辑命名空间，Shard 并行单位，Replica 副本）
- Netty 4 线程模型？（Transport + HTTP 双层，业务线程池隔离）

每题 2-3 句要点速答。

**四、实战关联**（约 100 行）：
- Java 场景：RestHighLevelClient 连接 Coordinating 节点、Spring Data ES 配置
- 生产部署（3 Master 专用 + N Data、角色分离 `node.roles`、JVM heap 31GB）
- 与 MySQL 高可用对比（MHA/MGR vs Zen2，主从思想一致但 ES 是分片级选举）
- 与 `java-core/lambda` 的对照（Netty 4 与 Stream 异步编程）
- 与 `java-core/jvm` 的对照（节点角色与 JVM 线程模型）

**五、系统设计案例**（约 100 行）：
- 设计一个支撑亿级文档的搜索集群（3 Master + 10 Data、按数据量规划分片数、Hot-Warm 分层，含容量估算与部署拓扑图）
- 设计一个多机房 ES 部署方案（Cross-Cluster Replication、机房间步与延迟权衡，mermaid 部署拓扑图）

- [ ] **Step 2: 体量与结构校验**

Run: `wc -l middleware/es/01-architecture/architecture-and-topology.md`，Expected: 600-900 行。
Run: `grep -c '^## ' middleware/es/01-architecture/architecture-and-topology.md`，Expected: 5（五段式）。
Run: `grep '一句话定位\|面试热度\|返回.*ES 知识图谱' middleware/es/01-architecture/architecture-and-topology.md`，Expected: 头部三行齐全。
Run: `grep -c 'mermaid' middleware/es/01-architecture/architecture-and-topology.md`，Expected: ≥ 3（架构图+选举时序图+Scatter-Gather 时序图）。

- [ ] **Step 3: 回填 README 进度标记**

把 `middleware/es/README.md` 导航表第一行末尾的 `⬜` 改为 `✅`。

- [ ] **Step 4: 提交**

```bash
git add middleware/es/01-architecture/architecture-and-topology.md middleware/es/README.md
git commit -m "docs(es): 新增架构与部署拓扑"
```

---

## Task 3: 02-index-mapping/index-and-mapping.md 索引与映射

**Files:**
- Create: `middleware/es/02-index-mapping/index-and-mapping.md`
- Modify: `middleware/es/README.md`（导航表第二行 `⬜` → `✅`）

**Interfaces:**
- Consumes: Task 2 的 Index/Shard/Replica 概念
- Produces: 索引与映射基础概念（Index Settings、Mapping 字段类型、Dynamic Mapping、Dynamic Template、Runtime Field、别名与模板 ILM），Task 4（倒排索引）引用 Mapping 的 analyzer 配置，Task 5（读写流程）引用 Index Settings，Task 6（查询）引用字段类型

- [ ] **Step 1: 编写 index-and-mapping.md**

按 spec 第七章文档 2 大纲展开五段式内容，600-900 行。头部三行：
```
# 索引与映射

> **一句话定位**：索引与映射是 ES 数据建模的核心，"Mapping 怎么设计、Dynamic Mapping 有什么坑"是中高级面试必问。
> **面试热度**：⭐⭐⭐⭐⭐
> **返回**：[ES 知识图谱](../README.md)
```

**一、概念定义**（约 150 行）：
- 1.1 Index vs MySQL Table（ES Index 是逻辑命名空间，底层由多个 shard 的 Lucene Index 组成，与传统表结构对照，对比表：ES Index vs MySQL Table 的逻辑/物理边界）
- 1.2 Mapping 字段类型（keyword 不分词精确匹配、text 分词全文检索、数值类型 long/scaled_float、dense_vector 8.x 向量、object/nested/flattened 三种对象类型差异，对比表：各字段类型的用途/索引方式/聚合支持）
- 1.3 Dynamic Mapping（动态推断类型——字符串→text+keyword、数字→long、日期→date，风险——字段爆炸、类型冲突，`dynamic` 三种模式 `true`/`runtime`/`strict`）
- 1.4 Runtime Field 8.x（运行时计算字段，不索引、查询时计算，与 schema-on-read 的对照，对比表：Runtime Field vs indexed Field 的查询性能/存储/灵活性）

**二、原理与流程**（约 280 行）：
- 2.1 Index Settings 详解（`number_of_shards`/`number_of_replicas`/`refresh_interval`/`analysis` 配置，为什么分片数不可改——路由公式含 num_primary_shards，mermaid flowchart 展示 Settings 层级）
- 2.2 Mapping 结构（`properties` 定义字段、`type` 指定类型、`analyzer` 指定分词器、`index` 控制是否索引、`doc_values` 控制列存，代码片段展示 Mapping JSON 结构）
- 2.3 Dynamic Mapping 推断规则（JSON 类型→ES 类型映射表、`date_detection` 日期检测的误判风险、`dynamic_date_formats` 自定义、`numeric_detection` 数字检测）
- 2.4 Dynamic Template（按 `match_matcher`/`match_pattern`/`mapping` 定义模板，规避动态推断风险，代码片段展示 Dynamic Template JSON 示例）
- 2.5 Runtime Field 8.x（`runtime` 段定义、`fields` 内 runtime 字段、Painless 脚本计算、与 indexed 字段的权衡，代码片段展示 Runtime Field 定义与查询）
- 2.6 Index Alias 与 Index Template（别名零停机切换、模板自动化新索引 Settings/Mapping、`index_patterns` 匹配，mermaid flowchart 展示别名切换流程）
- 2.7 ILM 索引生命周期（Hot/Warm/Cold/Delete 阶段、`rollover` 滚动、`shrink` 缩分片、`forcemerge` 合并段，mermaid flowchart 展示 ILM 阶段流转）
- 2.8 源码路径（`org.elasticsearch.index.mapper.DocumentMapper`、`org.elasticsearch.index.IndexSettings`、`org.elasticsearch.cluster.metadata.MetadataIndexTemplateService`）

**三、高频追问**（约 100 行，6-8 题）：
- ES 有哪些字段类型？（keyword/text/数值/dense_vector/object/nested）
- keyword 和 text 区别？（精确匹配 vs 分词全文检索）
- Dynamic Mapping 有什么坑？（字段爆炸、类型冲突）
- nested 和 object 区别？（nested 独立对象数组、object 扁平化）
- Runtime Field 是什么？（8.x 运行时计算字段，不索引）
- 分片数能改吗？（不能，只能 reindex）
- 别名有什么用？（零停机切换、多索引查询）
- ILM 是什么？（索引生命周期管理，Hot/Warm/Cold/Delete）

每题 2-3 句要点速答。

**四、实战关联**（约 80 行）：
- Java 场景：Spring Data ES `@Document`/`@Field` 注解定义 Mapping，代码片段展示注解配置
- 字段类型选型（标签 keyword、标题 text+ik 分词、价格 scaled_float、向量 dense_vector、子文档 nested）
- 与 MySQL 表结构设计对比（Schema-on-write vs Schema-on-read、DDL 对照）
- 与 `framework/spring-framework` 的对照（`@Document` 注解驱动）

**五、系统设计案例**（约 90 行）：
- 设计一个电商商品搜索的索引方案（标题 text+ik 分词、品牌 keyword、价格 scaled_float、标签 nested、SKU 子文档，含完整 Mapping JSON）
- 设计一个日志索引的 ILM 方案（Hot 1d→Warm 7d→Cold 30d→Delete 90d，按天滚动索引，mermaid 流程图）

- [ ] **Step 2: 体量与结构校验**

Run: `wc -l middleware/es/02-index-mapping/index-and-mapping.md`，Expected: 600-900 行。
Run: `grep -c '^## ' middleware/es/02-index-mapping/index-and-mapping.md`，Expected: 5。
Run: `grep '一句话定位\|面试热度\|返回.*ES 知识图谱' middleware/es/02-index-mapping/index-and-mapping.md`，Expected: 头部三行齐全。
Run: `grep -c 'mermaid' middleware/es/02-index-mapping/index-and-mapping.md`，Expected: ≥ 2（别名切换流程图+ILM 阶段流转图）。

- [ ] **Step 3: 回填 README 进度标记**

把 `middleware/es/README.md` 导航表第二行末尾的 `⬜` 改为 `✅`。

- [ ] **Step 4: 提交**

```bash
git add middleware/es/02-index-mapping/index-and-mapping.md middleware/es/README.md
git commit -m "docs(es): 新增索引与映射"
```

---

## Task 4: 03-inverted-index/inverted-index-and-analysis.md 倒排索引与分词

**Files:**
- Create: `middleware/es/03-inverted-index/inverted-index-and-analysis.md`
- Modify: `middleware/es/README.md`（导航表第三行 `⬜` → `✅`）

**Interfaces:**
- Consumes: Task 3 的 Mapping 字段类型与 analyzer 配置
- Produces: 倒排索引基础概念（FST、Term Dictionary、Posting List、doc_values、Analyzer 分词链），Task 5（读写流程）引用 segment 与倒排结构，Task 6（查询）引用 Analyzer 与查询分词

- [ ] **Step 1: 编写 inverted-index-and-analysis.md**

按 spec 第七章文档 3 大纲展开五段式内容，600-900 行。头部三行：
```
# 倒排索引与分词

> **一句话定位**：倒排索引是 ES 的灵魂，"讲讲倒排索引结构、Analyzer 分词链"是面试起手题，能讲到 FST 与 Roaring Bitmap 才算合格。
> **面试热度**：⭐⭐⭐⭐⭐
> **返回**：[ES 知识图谱](../README.md)
```

**一、概念定义**（约 150 行）：
- 1.1 倒排索引 vs 正向索引（MySQL B+Tree 按主键找行，ES 倒排按词找文档列表，本质差异——全文检索 vs 精确查询，对比表：倒排 vs B+Tree 的查询方式/适用场景/复杂度）
- 1.2 倒排结构三部分（Term Dictionary 词典、Term Index 词典索引 FST、Posting List 倒排列表，图解三部分关系）
- 1.3 存储格式（_source 原始 JSON 用于返回、doc_values 列存用于排序聚合、_field_data 堆内存兜底，对比表：_source/doc_values/_field_data 的用途/存储位置/性能）
- 1.4 Analyzer 分词链（Character Filter→Tokenizer→Token Filter 三段式，索引时与查询时各跑一遍，mermaid flowchart 展示分词链流程）

**二、原理与流程**（约 280 行）：
- 2.1 Term Dictionary 与 Term Index（词典按 term 排序、Term Index 用 FST 前缀压缩加速定位、FST 为什么省内存——前缀共享，图解 FST 结构）
- 2.2 Posting List 结构（每个 term 对应文档列表、按 doc_id 排序、Frame of Reference 增量压缩、Roaring Bitmap 集合运算，图解 Posting List 压缩格式）
- 2.3 _source 与 doc_values（_source 存原始 JSON 用于返回、doc_values 列存用于排序聚合、为什么不用 _field_data——堆内存压力，对比表：_source vs doc_values vs _field_data）
- 2.4 Posting List 压缩（Frame of Reference 变长增量编码、Roaring Bitmap 分桶高低位、AND/OR 集合运算加速，图解 Roaring Bitmap 分桶结构）
- 2.5 Analyzer 分词链详解（Character Filter——HTML strip/mapping、Tokenizer——standard/ik/whitespace、Token Filter——lowercase/stop/synonym/word_delimiter，代码片段展示自定义 Analyzer 配置）
- 2.6 Normalizer（keyword 字段的归一化，如小写转换，与 text Analyzer 的区别——不分词只归一化，对比表：Normalizer vs Analyzer）
- 2.7 索引时分词 vs 查询时分词（`analyzer` 索引时、`search_analyzer` 查询时、`search_analyzer` 不一致导致的"搜不到"问题，代码片段展示 `analyzer`+`search_analyzer` 配置）
- 2.8 源码路径（`org.apache.lucene.index.Terms`、`org.apache.lucene.codecs.lucene94.Lucene94PostingsFormat`、`org.elasticsearch.index.analysis.AnalysisService`）

**三、高频追问**（约 100 行，6-8 题）：
- 倒排索引是什么？（Term Dictionary + Term Index FST + Posting List）
- FST 是什么？（前缀压缩的有限状态转换器，省内存加速定位）
- Posting List 怎么压缩？（Frame of Reference 增量编码 + Roaring Bitmap）
- doc_values 是什么？（列存，用于排序聚合，避免 _field_data 堆内存）
- Analyzer 分几步？（Character Filter → Tokenizer → Token Filter）
- ik 分词器是什么？（中文分词，ik_smart 粗粒度/ik_max_word 细粒度）
- 索引时和查询时分词不一致会怎样？（搜不到，需 search_analyzer 对齐）
- Normalizer 和 Analyzer 区别？（Normalizer 不分词只归一化 keyword）

每题 2-3 句要点速答。

**四、实战关联**（约 80 行）：
- Java 场景：自定义 Analyzer 插件开发、Synonym 同义词配置
- 分词器选型（中文用 ik、英文用 standard、自定义 Token Filter）
- 与 MySQL 全文索引对比（InnoDB Fulltext vs ES 倒排，倒排更强大但维护成本高）
- 与 `middleware/redis/01-data-structure` 的对照（FST vs Redis SDS/dict 内存结构）
- 与 `java-core/jvm` 的对照（doc_values 列存与堆外 DirectByteBuffer）

**五、系统设计案例**（约 90 行）：
- 设计一个中英文混合的搜索分词方案（Character Filter 处理 HTML、Tokenizer 用 ik、Token Filter 加同义词与停用词，含完整 Analyzer JSON 配置）
- 设计一个千万级标签的倒排索引（keyword 标签的 Posting List 压缩、Roaring Bitmap 集合运算加速，含容量估算）

- [ ] **Step 2: 体量与结构校验**

Run: `wc -l middleware/es/03-inverted-index/inverted-index-and-analysis.md`，Expected: 600-900 行。
Run: `grep -c '^## ' middleware/es/03-inverted-index/inverted-index-and-analysis.md`，Expected: 5。
Run: `grep '一句话定位\|面试热度\|返回.*ES 知识图谱' middleware/es/03-inverted-index/inverted-index-and-analysis.md`，Expected: 头部三行齐全。
Run: `grep -c 'mermaid' middleware/es/03-inverted-index/inverted-index-and-analysis.md`，Expected: ≥ 2（分词链流程图+Posting List 结构图）。

- [ ] **Step 3: 回填 README 进度标记**

把 `middleware/es/README.md` 导航表第三行末尾的 `⬜` 改为 `✅`。

- [ ] **Step 4: 提交**

```bash
git add middleware/es/03-inverted-index/inverted-index-and-analysis.md middleware/es/README.md
git commit -m "docs(es): 新增倒排索引与分词"
```

---

## Task 5: 04-read-write-translog/read-write-and-translog.md 读写流程与 Translog

**Files:**
- Create: `middleware/es/04-read-write-translog/read-write-and-translog.md`
- Modify: `middleware/es/README.md`（导航表第四行 `⬜` → `✅`）

**Interfaces:**
- Consumes: Task 2 的分片与协调节点概念、Task 3 的 Index Settings（refresh_interval）、Task 4 的 segment 与倒排结构
- Produces: 读写流程基础概念（写流程 primary→replica、translog、refresh/flush、版本控制、bulk），Task 6（查询）引用 segment 可见性，Task 9（调优）引用 translog 刷盘与 refresh 调优

- [ ] **Step 1: 编写 read-write-and-translog.md**

按 spec 第七章文档 4 大纲展开五段式内容，600-900 行。头部三行：
```
# 读写流程与 Translog

> **一句话定位**：读写流程是 ES 近实时性的根基，"写后为什么 1s 才能搜到、translog 是什么"是中高级面试分水岭。
> **面试热度**：⭐⭐⭐⭐⭐
> **返回**：[ES 知识图谱](../README.md)
```

**一、概念定义**（约 130 行）：
- 1.1 ES 近实时模型（写后不立即可搜，refresh 1s 后才可见，与 MySQL 事务提交即可见的本质差异，对比表：ES 近实时 vs MySQL 实时可见）
- 1.2 写流程三阶段（写 index buffer + translog→refresh 生成 segment→flush 持久化 segment 并清空 translog，mermaid flowchart 展示三阶段）
- 1.3 Translog（事务日志，WAL 思想，崩溃恢复用，与 MySQL Redo Log 一致，对比表：ES translog vs MySQL Redo Log vs Redis AOF）
- 1.4 版本控制与乐观并发（`_version`/`seq_no`/`primary_term`，`if_seq_no`+`if_primary_term` 乐观锁）

**二、原理与流程**（约 280 行）：
- 2.1 写流程 primary→replica（协调节点路由→primary 写 buffer+translog→并行写 replica→primary 返回，`wait_for_active_shards` 一致性，mermaid sequenceDiagram 展示写流程）
- 2.2 Translog 刷盘策略（`index.translog.durability`：`request` 每次请求 fsync/`async` 定时 fsync，`sync_interval` 间隔，与 Redis AOF appendfsync 对照，对比表：request vs async 的数据丢失窗口/性能/适用场景）
- 2.3 refresh 流程（index buffer→segment 的 `DocumentsWriter` 生成新 segment、内存可见、默认 1s、`?refresh=true` 强制刷新，mermaid flowchart 展示 refresh 流程）
- 2.4 flush 流程（segment 持久化到磁盘、translog 清空、`fsync` segment 文件、`index.translog.flush_threshold_size` 触发阈值，mermaid flowchart 展示 flush 流程）
- 2.5 segment 不可变性（Lucene segment 写后不可改，删除是标记 tombstone、更新是标记旧版本、merge 合并清理，mermaid flowchart 展示 segment merge 流程）
- 2.6 版本控制（`_version` 递增、`seq_no`+`primary_term` 替代老版本、`if_seq_no`+`if_primary_term` 乐观锁、外部版本 `version_type=external`，代码片段展示乐观锁请求）
- 2.7 bulk 批量写（`_bulk` API 批量、JSON Action/Metadata 格式、批量大小推荐 5-15MB、并行 bulk 提升吞吐，代码片段展示 bulk 请求格式）
- 2.8 写一致性（`wait_for_active_shards`：`1`/`quorum`/`all`，quorum=`(replica+1)/2`，对比表：三种一致性级别的可用性/可靠性）
- 2.9 源码路径（`org.elasticsearch.index.engine.InternalEngine`、`org.elasticsearch.index.translog.Translog`、`org.elasticsearch.index.engine.DocumentWriter`）

**三、高频追问**（约 100 行，6-8 题）：
- 写后为什么 1s 才能搜到？（index buffer 需 refresh 生成 segment 才可搜）
- translog 是什么？（事务日志，WAL，崩溃恢复用）
- refresh 和 flush 区别？（refresh 生成内存 segment 可搜，flush 持久化磁盘清空 translog）
- translog 怎么刷盘？（`request` 每次请求 fsync/`async` 定时 fsync）
- ES 怎么保证写不丢？（translog fsync + 副本数）
- 怎么做乐观锁？（`if_seq_no`+`if_primary_term`）
- bulk 怎么用？（`_bulk` API，批量 5-15MB）
- segment 为什么不可变？（Lucene 设计，删除标记 tombstone，merge 清理）

每题 2-3 句要点速答。

**四、实战关联**（约 80 行）：
- Java 场景：RestHighLevelClient `BulkRequest`、`UpdateRequest` `ifSeqNo`+`ifPrimaryTerm`，代码片段展示批量写与乐观锁
- 写性能调优（批量大小、`refresh_interval` 调大、translog `async`）
- 与 MySQL Redo Log 对比（WAL 思想一致，但 ES refresh 近实时 vs MySQL 事务提交即持久）
- 与 Redis AOF 对比（translog 刷盘 vs AOF appendfsync 策略对照）
- 与 `java-core/jvm` 的对照（translog fsync 与 JVM GC 停顿对可见性延迟的影响）

**五、系统设计案例**（约 90 行）：
- 设计一个高吞吐写入方案（bulk 批量 15MB + `refresh_interval` 30s + translog async + 副本 1，含吞吐估算）
- 设计一个乐观并发更新方案（`if_seq_no`+`if_primary_term` + 重试，避免并发更新覆盖，含流程图）

- [ ] **Step 2: 体量与结构校验**

Run: `wc -l middleware/es/04-read-write-translog/read-write-and-translog.md`，Expected: 600-900 行。
Run: `grep -c '^## ' middleware/es/04-read-write-translog/read-write-and-translog.md`，Expected: 5。
Run: `grep '一句话定位\|面试热度\|返回.*ES 知识图谱' middleware/es/04-read-write-translog/read-write-and-translog.md`，Expected: 头部三行齐全。
Run: `grep -c 'mermaid' middleware/es/04-read-write-translog/read-write-and-translog.md`，Expected: ≥ 3（写流程时序图+refresh 流程图+flush 流程图）。

- [ ] **Step 3: 回填 README 进度标记**

把 `middleware/es/README.md` 导航表第四行末尾的 `⬜` 改为 `✅`。

- [ ] **Step 4: 提交**

```bash
git add middleware/es/04-read-write-translog/read-write-and-translog.md middleware/es/README.md
git commit -m "docs(es): 新增读写流程与 Translog"
```

---

## Task 6: 05-query-dsl-scoring/query-dsl-and-scoring.md 查询 DSL 与打分

**Files:**
- Create: `middleware/es/05-query-dsl-scoring/query-dsl-and-scoring.md`
- Modify: `middleware/es/README.md`（导航表第五行 `⬜` → `✅`）

**Interfaces:**
- Consumes: Task 4 的 Analyzer 与查询分词、Task 5 的 segment 可见性
- Produces: 查询与打分基础概念（Query DSL、Bool 查询、Function Score、BM25、Rescoring、search_after/PIT），Task 7（聚合）引用查询上下文，Task 9（调优）引用查询缓存与深度分页

- [ ] **Step 1: 编写 query-dsl-and-scoring.md**

按 spec 第七章文档 5 大纲展开五段式内容，600-900 行。头部三行：
```
# 查询 DSL 与打分

> **一句话定位**：查询与打分是 ES 检索的核心，"Bool 查询怎么组合、BM25 怎么打分"是中高级面试必问。
> **面试热度**：⭐⭐⭐⭐⭐
> **返回**：[ES 知识图谱](../README.md)
```

**一、概念定义**（约 130 行）：
- 1.1 Query DSL 概述（JSON 结构表达查询，`query`/`bool`/`filter`/`must` 等，与 SQL 表达式对照，对比表：Query DSL vs SQL 的表达力/组合方式）
- 1.2 查询上下文 vs 过滤上下文（query 算分参与排序，filter 不算分只判断匹配，filter 可缓存，对比表：query vs filter 的算分/缓存/性能）
- 1.3 Bool 查询四子句（`must` 算分且匹配、`should` 算分至少 N 个匹配、`filter` 不算分只过滤、`must_not` 不匹配，对比表：四子句的算分/匹配要求/缓存）
- 1.4 BM25 打分（TF/IDF 的演进，BM25 引入饱和与文档长度归一化，可调参数 `k1`/`b`，对比表：TF/IDF vs BM25 的饱和度/文档长度归一化）

**二、原理与流程**（约 280 行）：
- 2.1 Query DSL 结构（`query`→`bool`→`must/should/filter/must_not`，叶子查询 term/range/match/multi_match，代码片段展示 Bool 查询 JSON）
- 2.2 term 与 match（`term` 不分词精确匹配 keyword、`match` 对查询词分词后 OR 匹配 text、`match_phrase` 短语匹配，对比表：term vs match vs match_phrase 的分词/匹配方式）
- 2.3 Bool 查询组合（`must` 参与算分、`should` `minimum_should_match` 控制、`filter` 利用缓存不算分、`must_not` 反向过滤，代码片段展示复杂 Bool 查询）
- 2.4 Function Score（`script_score` 脚本打分、`field_value_factor` 字段值加权、`weight` 权重、`random_score` 随机、`decay_functions` 衰减函数，代码片段展示 Function Score 配置）
- 2.5 BM25 打分公式（`IDF × (f(k1+1) / f+k1(1-b+b×dl/avgdl))`，`k1` 控制 TF 饱和度默认 1.2、`b` 控制文档长度归一化默认 0.75，图解 BM25 公式各部分含义）
- 2.6 Rescoring（`rescore` 窗口内重打分，先用 query 粗筛再用 `rescore_query` 精排，`window_size` 控制，代码片段展示 Rescoring 配置）
- 2.7 分页（`from`+`size` 深度分页性能差——coordinate node 需取 `from+size` 条归并、`search_after` 游标分页、`PIT` Point-in-Time 8.x 保证结果一致性，对比表：from/size vs search_after vs PIT 的性能/一致性/适用场景）
- 2.8 协调节点 Scatter-Gather（路由到各分片、各分片局部排序返回 TopN、协调节点归并全局 TopN，mermaid sequenceDiagram 展示 Scatter-Gather 流程）
- 2.9 源码路径（`org.elasticsearch.index.query.BoolQueryBuilder`、`org.elasticsearch.index.query.MatchQueryBuilder`、`org.apache.lucene.search.similarities.BM25Similarity`）

**三、高频追问**（约 100 行，6-8 题）：
- Bool 查询四子句区别？（must 算分、should 至少 N 个、filter 不算分、must_not 反向）
- 查询和过滤上下文区别？（query 算分参与排序，filter 不算分可缓存）
- term 和 match 区别？（term 不分词精确匹配，match 分词后匹配）
- BM25 怎么打分？（TF/IDF 演进，引入饱和与文档长度归一化）
- `k1` 和 `b` 调什么？（k1 TF 饱和度，b 文档长度归一化）
- 深度分页怎么办？（`search_after` 或 `PIT`）
- Function Score 怎么用？（script_score/field_value_factor 等加权）
- PIT 是什么？（8.x Point-in-Time，保证分页结果一致性）

每题 2-3 句要点速答。

**四、实战关联**（约 80 行）：
- Java 场景：RestHighLevelClient `SearchRequest`/`BoolQueryBuilder`/`MatchQueryBuilder`，代码片段展示 Java 查询构造
- 打分调优（`k1`/`b` 调参、`function_score` 业务加权）
- 与 MySQL 查询对比（SQL vs Query DSL，LIKE vs match，B+Tree vs 倒排）
- 与 `framework/spring-framework` 的对照（Spring Data ES `ElasticsearchRepository` 查询方法）
- 与 `framework/jackson` 的对照（查询结果 JSON 反序列化）

**五、系统设计案例**（约 90 行）：
- 设计一个电商搜索的查询方案（标题 match + 品牌 filter + 价格 range + 销量 field_value_factor 加权 + BM25 打分，含完整 Query DSL JSON）
- 设计一个深度分页方案（`PIT` + `search_after`，避免 from+size 深度分页性能问题，含流程图）

- [ ] **Step 2: 体量与结构校验**

Run: `wc -l middleware/es/05-query-dsl-scoring/query-dsl-and-scoring.md`，Expected: 600-900 行。
Run: `grep -c '^## ' middleware/es/05-query-dsl-scoring/query-dsl-and-scoring.md`，Expected: 5。
Run: `grep '一句话定位\|面试热度\|返回.*ES 知识图谱' middleware/es/05-query-dsl-scoring/query-dsl-and-scoring.md`，Expected: 头部三行齐全。
Run: `grep -c 'mermaid' middleware/es/05-query-dsl-scoring/query-dsl-and-scoring.md`，Expected: ≥ 1（Scatter-Gather 时序图）。

- [ ] **Step 3: 回填 README 进度标记**

把 `middleware/es/README.md` 导航表第五行末尾的 `⬜` 改为 `✅`。

- [ ] **Step 4: 提交**

```bash
git add middleware/es/05-query-dsl-scoring/query-dsl-and-scoring.md middleware/es/README.md
git commit -m "docs(es): 新增查询 DSL 与打分"
```

---

## Task 7: 06-aggregation/aggregation-and-pipeline.md 聚合与 Pipeline

**Files:**
- Create: `middleware/es/06-aggregation/aggregation-and-pipeline.md`
- Modify: `middleware/es/README.md`（导航表第六行 `⬜` → `✅`）

**Interfaces:**
- Consumes: Task 4 的 doc_values 列存、Task 6 的查询上下文
- Produces: 聚合基础概念（Bucket/Metric/Pipeline、Cardinality、Percentile、global_ordinals、ES|QL），Task 9（调优）引用聚合内存调优

- [ ] **Step 1: 编写 aggregation-and-pipeline.md**

按 spec 第七章文档 6 大纲展开五段式内容，600-900 行。头部三行：
```
# 聚合与 Pipeline

> **一句话定位**：聚合是 ES 分析能力的核心，"Bucket/Metric/Pipeline 三类聚合、Cardinality 怎么去重"是中高级面试必问。
> **面试热度**：⭐⭐⭐⭐
> **返回**：[ES 知识图谱](../README.md)
```

**一、概念定义**（约 130 行）：
- 1.1 聚合三类（Bucket 桶聚合按维度分组、Metric 指标聚合计算值、Pipeline 管道聚合对其他聚合结果二次计算，对比表：三类聚合的输入/输出/用途）
- 1.2 Bucket 聚合（`terms` 按字段值分桶、`date_histogram` 按时间分桶、`nested` 嵌套对象分桶、`filter` 自定义过滤分桶，对比表：四种 Bucket 聚合的分桶方式/适用场景）
- 1.3 Metric 聚合（`avg`/`sum`/`max`/`min` 基础、`cardinality` 去重计数 hyperloglog++、`percentile` 百分位 t-digest、`stats` 多指标合并，对比表：各 Metric 聚合的算法/精度/内存）
- 1.4 Pipeline 聚合（`moving_avg` 移动平均、`derivative` 导数、`cumulative_sum` 累计和，基于 Bucket 结果二次计算）

**二、原理与流程**（约 280 行）：
- 2.1 Bucket 聚合原理（`terms` 用 `doc_values` 或 `global_ordinals` 收集、`size` 控制 Bucket 数、`doc_count_error` 误差与 `show_term_doc_count_error`，mermaid flowchart 展示 terms 聚合流程）
- 2.2 `global_ordinals` 优化（keyword 预构建全局序数映射、避免每次聚合遍历 `doc_values`、`eager_global_ordinals` 预加载，图解 global_ordinals 结构）
- 2.3 Cardinality hyperloglog++（`cardinality` 用 HLL++ 算法近似去重、`precision_threshold` 控制精度与内存、误差约 `40000/precision`，图解 HLL++ 算法原理）
- 2.4 Percentile t-digest（`percentile` 用 t-digest 算法近似分位数、`compression` 控制精度与内存、适用于 P50/P95/P99，图解 t-digest 算法原理）
- 2.5 子聚合（`aggs` 内嵌套 `aggs`，Bucket→子 Bucket→Metric，树形结构，注意 breadth-first vs depth-first 策略，mermaid flowchart 展示子聚合树形结构）
- 2.6 聚合内存与调优（`bwc` breadth vs `collect_mode`、`breadth_first` 宽度优先省内存、`depth_first` 深度优先精确、`terminate_after` 限制，对比表：breadth_first vs depth_first 的内存/精确度/适用场景）
- 2.7 ES|QL 8.x（管道式查询语言 `POST _query`、`FROM`/`WHERE`/`STATS`/`SORT`/`LIMIT`，统一查询、聚合、计算，替代 `_search` + `aggs` 的表达力不足，代码片段展示 ES|QL 查询示例）
- 2.8 源码路径（`org.elasticsearch.search.aggregations.AggregationPhase`、`org.elasticsearch.search.aggregations.bucket.terms.TermsAggregator`、`org.elasticsearch.search.aggregations.metrics.CardinalityAggregator`）

**三、高频追问**（约 100 行，6-8 题）：
- 聚合有哪几类？（Bucket/Metric/Pipeline）
- Cardinality 怎么去重？（hyperloglog++ 近似算法）
- precision_threshold 是什么？（控制 Cardinality 精度与内存）
- Percentile 怎么算？（t-digest 近似算法）
- `global_ordinals` 是什么？（keyword 全局序数优化聚合）
- 子聚合怎么嵌套？（`aggs` 内嵌 `aggs`，树形）
- ES|QL 是什么？（8.x 管道查询语言，统一查询聚合计算）
- terms 聚合的 doc_count_error 怎么来的？（分片级返回 TopN 归并时的误差）

每题 2-3 句要点速答。

**四、实战关联**（约 80 行）：
- Java 场景：RestHighLevelClient `SearchRequest` `AggregationBuilders.terms`/`cardinality`，代码片段展示 Java 聚合构造
- 聚合调优（`eager_global_ordinals` 预加载、`collect_mode` 策略、`terminate_after` 限制）
- 与 MySQL GROUP BY 对比（SQL 聚合 vs ES 聚合，B+Tree 扫描 vs doc_values 列存）
- 与 `framework/jackson` 的对照（聚合结果 JSON 反序列化）

**五、系统设计案例**（约 90 行）：
- 设计一个电商商品的多维聚合方案（品牌 terms + 价格 percentile + 销量 cardinality 去重 + 子聚合 date_histogram 按月统计，含完整 Aggregation DSL JSON）
- 设计一个日志分析的 ES|QL 方案（`FROM logs | WHERE level=="ERROR" | STATS count=count(*) BY service | SORT count DESC | LIMIT 10`，含完整 ES|QL 语句）

- [ ] **Step 2: 体量与结构校验**

Run: `wc -l middleware/es/06-aggregation/aggregation-and-pipeline.md`，Expected: 600-900 行。
Run: `grep -c '^## ' middleware/es/06-aggregation/aggregation-and-pipeline.md`，Expected: 5。
Run: `grep '一句话定位\|面试热度\|返回.*ES 知识图谱' middleware/es/06-aggregation/aggregation-and-pipeline.md`，Expected: 头部三行齐全。
Run: `grep -c 'mermaid' middleware/es/06-aggregation/aggregation-and-pipeline.md`，Expected: ≥ 2（terms 聚合流程图+子聚合树形结构图）。

- [ ] **Step 3: 回填 README 进度标记**

把 `middleware/es/README.md` 导航表第六行末尾的 `⬜` 改为 `✅`。

- [ ] **Step 4: 提交**

```bash
git add middleware/es/06-aggregation/aggregation-and-pipeline.md middleware/es/README.md
git commit -m "docs(es): 新增聚合与 Pipeline"
```

---

## Task 8: 07-shard-routing/shard-routing-and-reindex.md 分片路由与 Reindex

**Files:**
- Create: `middleware/es/07-shard-routing/shard-routing-and-reindex.md`
- Modify: `middleware/es/README.md`（导航表第七行 `⬜` → `✅`）

**Interfaces:**
- Consumes: Task 2 的 Index/Shard/Replica 概念、Task 3 的 Index Settings 与别名
- Produces: 分片路由基础概念（routing 公式、routing key、CCR、reindex、Update/Delete By Query、分片数规划、Hot-Warm-Cold），Task 9（调优）引用分片再平衡与容量规划

- [ ] **Step 1: 编写 shard-routing-and-reindex.md**

按 spec 第七章文档 7 大纲展开五段式内容，600-900 行。头部三行：
```
# 分片路由与 Reindex

> **一句话定位**：分片路由是 ES 分布式能力的核心，"分片怎么路由、为什么分片数不可改、reindex 怎么重建"是资深面试分水岭。
> **面试热度**：⭐⭐⭐⭐
> **返回**：[ES 知识图谱](../README.md)
```

**一、概念定义**（约 130 行）：
- 1.1 分片路由（`hash(routing) % num_primary_shards`，默认 `routing=_id`，可自定义 `routing` 参数，对比表：ES routing vs Redis Cluster 16384 槽位 vs MySQL 分库分表）
- 1.2 分片数不可改（路由公式含 `num_primary_shards`，改了路由结果变，文档找不到，只能 reindex，对比表：分片数 vs 副本数可改性）
- 1.3 CCR 跨集群复制（Leader 索引→Follower 索引，跨集群灾备与就近读取，对比表：CCR vs Redis 主从 vs RocketMQ 主从）
- 1.4 Hot-Warm-Cold 架构（Hot SSD 高写入、Warm HDD 查询、Cold 冷数据归档，ILM 自动迁移，对比表：三层架构的硬件/性能/成本）

**二、原理与流程**（约 280 行）：
- 2.1 路由公式（`hash(routing) % num_primary_shards`、`routing` 默认 `_id`、自定义 `routing=user_id` 让同用户数据同分片、风险——数据倾斜，mermaid flowchart 展示路由流程）
- 2.2 分片数规划（单分片大小推荐 30-50GB、过小 over-sharding 浪费资源、过大 rebalance 慢、`number_of_shards` 一旦定不可改，对比表：不同分片数的存储/查询/rebalance 性能）
- 2.3 Reindex 重建索引（`_reindex` API、source/dest、`op_type=create` 避免覆盖、sliced 并行、`size` 批量，代码片段展示 reindex 请求）
- 2.4 Update By Query（`_update_by_query` 批量更新匹配文档、`scripted` 脚本更新、`version_type=internal` 乐观锁，代码片段展示 Update By Query 请求）
- 2.5 Delete By Query（`_delete_by_query` 批量删除、与 `DeleteByQueryRequest` 的 Java 调用，代码片段展示 Delete By Query 请求）
- 2.6 CCR 跨集群复制（Leader/Follower 索引、`auto_follow` 自动跟随、`_ccr/follow` 手动跟随、底层基于 translog 复制，mermaid sequenceDiagram 展示 CCR 复制流程）
- 2.7 Hot-Warm-Cold 架构（节点 `node.roles` 分层、`index.routing.allocation.include._tier` 路由、ILM `migrate` 阶段自动迁移，mermaid flowchart 展示 Hot-Warm-Cold 分层）
- 2.8 源码路径（`org.elasticsearch.cluster.routing.OperationRouting`、`org.elasticsearch.index.reindex.ReindexAction`、`org.elasticsearch.xpack.ccr.CCRService`）

**三、高频追问**（约 100 行，6-8 题）：
- 分片怎么路由？（`hash(routing) % num_primary_shards`）
- 分片数能改吗？（不能，只能 reindex）
- 自定义 routing 有什么用？（同用户同分片，减少跨分片查询）
- 自定义 routing 有什么风险？（数据倾斜）
- reindex 怎么用？（`_reindex` API，sliced 并行）
- CCR 是什么？（跨集群复制，Leader/Follower）
- Hot-Warm-Cold 怎么实现？（节点角色分层 + ILM 迁移）
- Update By Query 怎么用？（`_update_by_query` 批量脚本更新）

每题 2-3 句要点速答。

**四、实战关联**（约 80 行）：
- Java 场景：`ReindexRequest`/`UpdateByQueryRequest` 的 Java 调用，代码片段展示 reindex 与 Update By Query
- 分片数规划（按数据量估算 `number_of_shards`、单分片 30-50GB）
- 与 MySQL 分库分表对比（ShardingSphere vs ES routing，分片路由思想一致）
- 与 Redis Cluster 对比（16384 槽位 vs ES 分片数，都是 hash 取模）
- 与 `framework/spring-framework` 的对照（routing 与 Spring 多数据源路由）

**五、系统设计案例**（约 90 行）：
- 设计一个亿级文档的分片方案（单分片 40GB、20 分片、副本 1、按时间滚动索引 + ILM，含容量估算）
- 设计一个零停机的 Mapping 变更方案（新索引 + alias + reindex + 切换别名，mermaid flowchart 展示零停机变更流程）

- [ ] **Step 2: 体量与结构校验**

Run: `wc -l middleware/es/07-shard-routing/shard-routing-and-reindex.md`，Expected: 600-900 行。
Run: `grep -c '^## ' middleware/es/07-shard-routing/shard-routing-and-reindex.md`，Expected: 5。
Run: `grep '一句话定位\|面试热度\|返回.*ES 知识图谱' middleware/es/07-shard-routing/shard-routing-and-reindex.md`，Expected: 头部三行齐全。
Run: `grep -c 'mermaid' middleware/es/07-shard-routing/shard-routing-and-reindex.md`，Expected: ≥ 3（路由流程图+CCR 复制时序图+Hot-Warm-Cold 分层图）。

- [ ] **Step 3: 回填 README 进度标记**

把 `middleware/es/README.md` 导航表第七行末尾的 `⬜` 改为 `✅`。

- [ ] **Step 4: 提交**

```bash
git add middleware/es/07-shard-routing/shard-routing-and-reindex.md middleware/es/README.md
git commit -m "docs(es): 新增分片路由与 Reindex"
```

---

## Task 9: 08-ha-tuning/ha-and-tuning.md 高可用与调优

**Files:**
- Create: `middleware/es/08-ha-tuning/ha-and-tuning.md`
- Modify: `middleware/es/README.md`（导航表第八行 `⬜` → `✅`）

**Interfaces:**
- Consumes: Task 2 的 Master 选举与分片分配、Task 5 的 translog 刷盘、Task 7 的分片数规划
- Produces: 高可用与调优基础概念（副本故障恢复、分片再平衡、监控、JVM heap、circuit breaker、版本升级），Task 10（Q&A）引用调优要点

- [ ] **Step 1: 编写 ha-and-tuning.md**

按 spec 第七章文档 8 大纲展开五段式内容，600-900 行。头部三行：
```
# 高可用与调优

> **一句话定位**：高可用与调优是资深面试的加分项，"节点宕机怎么恢复、JVM heap 为什么 50%"区分是否有生产经验。
> **面试热度**：⭐⭐⭐⭐
> **返回**：[ES 知识图谱](../README.md)
```

**一、概念定义**（约 130 行）：
- 1.1 ES 高可用（副本故障转移、Master 选举、分片再平衡，与 MySQL MHA/RocketMQ Dledger 对照，对比表：ES Zen2 vs MySQL MHA vs RocketMQ Controller）
- 1.2 调优核心目标（索引吞吐、查询延迟、聚合内存、JVM heap 50% 规则与 os cache，对比表：索引调优 vs 查询调优的参数/目标）
- 1.3 监控体系（cat API、`_cluster/health`、`_nodes/stats`、Prometheus + Grafana，对比表：四种监控 API 的维度/用途）
- 1.4 常见故障（节点宕机、分片未分配、JVM OOM、脑裂、慢查询）

**二、原理与流程**（约 300 行）：
- 2.1 副本故障恢复（primary 宕机→replica 提升为 primary、`RerouteActionCode`、`AllocationService` 重新分配、recovery 从 primary 复制到新 replica，mermaid sequenceDiagram 展示故障恢复流程）
- 2.2 分片再平衡（`cluster.routing.allocation.balance.shard`/`index`/`threshold`、`cluster.routing.allocation.cluster_concurrent_rebalance` 并发数、`DiskWatermark` 磁盘水位触发，对比表：再平衡参数与触发条件）
- 2.3 监控 cat API（`_cat/indices` 索引状态、`_cat/shards` 分片分布、`_cat/health` 集群健康、`_cat/allocation` 节点磁盘，代码片段展示常用 cat 命令）
- 2.4 监控 _cluster/health（`status` green/yellow/red、`active_shards`/`relocating_shards`/`unassigned_shards`/`delayed_unassigned_shards` 延迟分配，对比表：green/yellow/red 的含义与触发条件）
- 2.5 监控 _nodes/stats（`jvm.mem`/`gc`/`thread_pool`/`indices`/`fs`/`transport`、按节点维度定位瓶颈，代码片段展示关键监控指标）
- 2.6 JVM heap 50% 规则（Lucene segment mmap 用 os cache、heap 给 Lucene inverted index 缓存与 query/aggregation 中间结果、50% 平衡 heap 与 os cache，图解 heap vs os cache 内存分配）
- 2.7 Circuit Breaker（`parent_breaker` 总熔断、`fielddata`/`request`/`accounting` 子熔断、`indices.breaker.total.limit` 默认 95% heap、避免 OOM kill，对比表：各熔断器的限制/触发/恢复）
- 2.8 调优索引吞吐（`refresh_interval` 调大、`number_of_replicas` 0 临时、`translog.durability=async`、bulk 批量，对比表：各参数的调优效果/风险）
- 2.9 调优查询延迟（file cache 8.x、`index.store.preload` 预加载、`indices.queries.cache.size` 查询缓存，对比表：各参数的调优效果/适用场景）
- 2.10 版本升级 7.x→8.x（安全默认开启 TLS、`Security` 自动配置、API 兼容、`elasticdump` 数据迁移，mermaid flowchart 展示升级流程）
- 2.11 源码路径（`org.elasticsearch.cluster.routing.allocation.AllocationService`、`org.elasticsearch.indices.breaker.CircuitBreakerService`、`org.elasticsearch.monitor.jvm.JvmStats`）

**三、高频追问**（约 100 行，6-8 题）：
- 节点宕机怎么办？（replica 提升为 primary，重新分配）
- 分片未分配怎么排查？（`_cat/shards` + `_cluster/allocation/explain`）
- JVM heap 为什么 50%？（给 os cache 留空间，Lucene segment mmap）
- Circuit Breaker 是什么？（熔断器，避免 OOM kill）
- 怎么提升写入吞吐？（`refresh_interval` 调大 + bulk 批量 + 副本 0 临时）
- 7.x 升 8.x 注意什么？（安全默认开启、API 兼容）
- yellow 和 red 区别？（yellow 副本未分配，red 主分片未分配）
- file cache 8.x 是什么？（8.x 新特性，加速查询的文件缓存）

每题 2-3 句要点速答。

**四、实战关联**（约 80 行）：
- Java 场景：RestHighLevelClient `ClusterHealthRequest`/`NodesStatsRequest`，代码片段展示监控指标获取
- 生产部署（3 Master 专用 + N Data、JVM heap 31GB、SSD + file cache）
- 与 `ops/linux` 监控对照（JVM heap vs os cache、进程线程模型）
- 与 `ops/docker`/`ops/k8s` 部署对照（`vm.max_map_count`、StatefulSet + PV）
- 与 `java-core/jvm` 的对照（JVM heap 50% 规则、circuit breaker 与内存溢出保护）

**五、系统设计案例**（约 90 行）：
- 设计一个 ES 生产集群的监控告警体系（Prometheus + elasticsearch-exporter + 5 大类指标 + 阈值告警，含监控指标清单）
- 设计一次从 7.x 到 8.x 的零停机升级方案（新集群搭建 + CCR 复制 + 灰度切流 + 别名切换，mermaid flowchart 展示升级流程）

- [ ] **Step 2: 体量与结构校验**

Run: `wc -l middleware/es/08-ha-tuning/ha-and-tuning.md`，Expected: 600-900 行。
Run: `grep -c '^## ' middleware/es/08-ha-tuning/ha-and-tuning.md`，Expected: 5。
Run: `grep '一句话定位\|面试热度\|返回.*ES 知识图谱' middleware/es/08-ha-tuning/ha-and-tuning.md`，Expected: 头部三行齐全。
Run: `grep -c 'mermaid' middleware/es/08-ha-tuning/ha-and-tuning.md`，Expected: ≥ 2（故障恢复时序图+升级流程图）。

- [ ] **Step 3: 回填 README 进度标记**

把 `middleware/es/README.md` 导航表第八行末尾的 `⬜` 改为 `✅`。

- [ ] **Step 4: 提交**

```bash
git add middleware/es/08-ha-tuning/ha-and-tuning.md middleware/es/README.md
git commit -m "docs(es): 新增高可用与调优"
```

---

## Task 10: 08-interview-qa.md 面试 Q&A 速答

**Files:**
- Create: `middleware/es/08-interview-qa.md`
- Modify: `middleware/es/README.md`（导航表第九行 `⬜` → `✅`）

**Interfaces:**
- Consumes: 所有 Task 2-9 的主题文档，Q&A 每题末尾关联链接指向对应主题文档
- Produces: 41 题速答 + 连环套问思维导图，作为面试冲刺闭环

- [ ] **Step 1: 编写 08-interview-qa.md**

按 spec 第七章文档 9 大纲展开，500-700 行。头部三行：
```
# 跨主题高频面试 Q&A

> **一句话定位**：面试前冲刺用，41 题速答串联各主题，附连环套问思维导图。
> **面试热度**：⭐⭐⭐⭐⭐
> **返回**：[ES 知识图谱](../README.md)
```

**结构**（与 MySQL、Redis、RocketMQ Q&A 完全对齐）：

**使用说明**：每题 3-5 句要点速答，末尾 **关联** 链接指向对应主题文档。

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

**一、架构与部署篇（6 题）**：
- Q1: ES 有哪些节点角色？各自职责？🔗
- Q2: Master 怎么选出来的？Zen2 是什么？🔗
- Q3: 为什么不用 ZooKeeper？🔗
- Q4: 脑裂怎么避免？Voting Configuration 是什么？🔗
- Q5: 协调节点做什么？Scatter-Gather 是什么？🔗
- Q6: Index/Shard/Replica 的关系？分片数能改吗？🔗

每题 3-5 句要点速答，末尾 `**关联**：→ [架构与部署拓扑](./01-architecture/architecture-and-topology.md)`

**二、索引与映射篇（6 题）**：
- Q7: ES 有哪些字段类型？keyword 和 text 区别？🔗
- Q8: Dynamic Mapping 是什么？有什么坑？🔗
- Q9: nested 和 object 区别？🔗
- Q10: Runtime Field 是什么？8.x 新特性？🔗
- Q11: 别名有什么用？Index Template 是什么？🔗
- Q12: ILM 索引生命周期是什么？Hot-Warm-Cold 怎么实现？🔗

**三、倒排索引与分词篇（5 题）**：
- Q13: 倒排索引是什么？和 B+Tree 有什么区别？🔗
- Q14: FST 是什么？Term Index 怎么加速？🔗
- Q15: Posting List 怎么压缩？Roaring Bitmap 是什么？🔗
- Q16: doc_values 是什么？和 _field_data 区别？🔗
- Q17: Analyzer 分几步？ik 分词器是什么？索引时和查询时分词不一致会怎样？🔗

**四、读写流程与 Translog 篇（5 题）**：
- Q18: 写后为什么 1s 才能搜到？refresh 是什么？🔗
- Q19: translog 是什么？和 MySQL Redo Log 有什么关系？🔗
- Q20: refresh 和 flush 区别？🔗
- Q21: ES 怎么保证写不丢？translog 怎么刷盘？🔗
- Q22: 怎么做乐观锁？bulk 怎么用？🔗

**五、查询 DSL 与打分篇（6 题）**：
- Q23: Bool 查询四子句区别？must/should/filter/must_not？🔗
- Q24: 查询上下文和过滤上下文区别？🔗
- Q25: term 和 match 区别？match_phrase 是什么？🔗
- Q26: BM25 怎么打分？k1 和 b 调什么？🔗
- Q27: Function Score 怎么用？field_value_factor 是什么？🔗
- Q28: 深度分页怎么办？search_after 和 PIT 是什么？🔗

**六、聚合篇（4 题）**：
- Q29: 聚合有哪几类？Bucket/Metric/Pipeline？🔗
- Q30: Cardinality 怎么去重？hyperloglog++ 是什么？precision_threshold？🔗
- Q31: Percentile 怎么算？t-digest 是什么？global_ordinals？🔗
- Q32: ES|QL 是什么？8.x 新特性？🔗

**七、分片路由与 Reindex 篇（5 题）**：
- Q33: 分片怎么路由？hash(routing)%num_primary？🔗
- Q34: 自定义 routing 有什么用和风险？🔗
- Q35: reindex 怎么用？Update By Query 是什么？🔗
- Q36: CCR 跨集群复制是什么？🔗
- Q37: 分片数怎么规划？over-sharding 有什么风险？🔗

**八、高可用与调优篇（4 题）**：
- Q38: 节点宕机怎么办？副本怎么恢复？🔗
- Q39: JVM heap 为什么 50%？Circuit Breaker 是什么？🔗
- Q40: 怎么提升写入吞吐？怎么调优查询延迟？file cache 8.x？🔗
- Q41: 7.x 升 8.x 注意什么？yellow 和 red 区别？🔗

**连环套问思维导图**：6 条追问链（与 MySQL、Redis、RocketMQ 的 6 条对齐），用 mermaid mindmap 或文本缩进展示：
- 链 1：节点角色 → Master 选举 → Zen2 → Voting Configuration → 为什么不用 ZK
- 链 2：倒排索引 → Term Dictionary → FST → Posting List → Roaring Bitmap → doc_values
- 链 3：写流程 → index buffer → refresh → segment → translog → flush → 近实时可见
- 链 4：Query DSL → Bool 查询 → filter vs query → BM25 → k1/b → Function Score
- 链 5：聚合 → Bucket/Metric/Pipeline → Cardinality HLL++ → Percentile t-digest → global_ordinals → ES|QL
- 链 6：分片路由 → routing 公式 → 分片数不可改 → reindex → CCR → Hot-Warm-Cold

- [ ] **Step 2: 体量与结构校验**

Run: `wc -l middleware/es/08-interview-qa.md`，Expected: 500-700 行。
Run: `grep -c '### Q' middleware/es/08-interview-qa.md`，Expected: 41。
Run: `grep -c '关联.*→' middleware/es/08-interview-qa.md`，Expected: 41。
Run: `grep '连环套问\|追问链' middleware/es/08-interview-qa.md`，Expected: 含 6 条追问链。

- [ ] **Step 3: 回填 README 进度标记**

把 `middleware/es/README.md` 导航表第九行（面试冲刺行）末尾的 `⬜` 改为 `✅`。

- [ ] **Step 4: 全局一致性校验**

Run: `grep -c '⬜' middleware/es/README.md`，Expected: 0（所有进度标记回填完毕）。
Run: `grep -c '✅' middleware/es/README.md`，Expected: 9（8 主题 + 1 QA 全部完成）。

- [ ] **Step 5: 提交**

```bash
git add middleware/es/08-interview-qa.md middleware/es/README.md
git commit -m "docs(es): 新增面试 Q&A 速答，ES 模块 10 份文档全部完成"
```

---

## Self-Review

### 1. Spec coverage（规格覆盖）

| Spec 章节 | 对应 Task | 覆盖 |
|-----------|----------|------|
| 一、模块整体结构 | Task 1 | ✅ |
| 二、知识图谱 mindmap | Task 1 | ✅ |
| 三、导航表 | Task 1 | ✅ |
| 四、推荐学习路径 | Task 1 | ✅ |
| 五、与 java-core/framework 关联 | Task 1 | ✅ |
| 六、与 ops/middleware 交叉引用 | Task 1 | ✅ |
| 七、文档 1 架构与部署 | Task 2 | ✅ |
| 七、文档 2 索引与映射 | Task 3 | ✅ |
| 七、文档 3 倒排索引与分词 | Task 4 | ✅ |
| 七、文档 4 读写流程与 Translog | Task 5 | ✅ |
| 七、文档 5 查询 DSL 与打分 | Task 6 | ✅ |
| 七、文档 6 聚合 | Task 7 | ✅ |
| 七、文档 7 分片路由与 Reindex | Task 8 | ✅ |
| 七、文档 8 高可用与调优 | Task 9 | ✅ |
| 七、文档 9 Q&A | Task 10 | ✅ |
| 八、文档统一规范 | Global Constraints | ✅ |
| 九、实施顺序 | Task 1-10 顺序 | ✅ |
| 十、设计自检 | Self-Review | ✅ |

### 2. Placeholder scan（占位符扫描）

- 无 TBD/TODO/未填内容 ✅
- 无"add appropriate error handling"类模糊描述 ✅
- 无"Write tests for the above"无代码步骤 ✅
- 无"Similar to Task N"省略 ✅
- 所有步骤含具体内容（代码片段、校验命令、体量要求） ✅

### 3. Type consistency（类型一致性）

- 文件路径在 Task 1 导航表与 Task 2-10 的 Create 路径一致 ✅
- 源码路径格式统一（`org.elasticsearch.*` / `org.apache.lucene.*`） ✅
- 头部三行模板统一（`> 返回 [ES 知识图谱](../README.md)`） ✅
- Q&A 题目数 6+6+5+5+6+4+5+4=41 与 spec 一致 ✅
- 提交规范统一 `docs(es):` 前缀 ✅
