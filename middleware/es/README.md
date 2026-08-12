# es — Elasticsearch 面试知识体系

## 一、模块简介

本模块按 Elasticsearch 知识层次组织 **9 份**主题/汇总文档，覆盖从架构与部署、索引与映射、倒排索引与分词、读写流程与 Translog、查询 DSL 与打分、聚合、分片路由与 Reindex、高可用与调优到面试冲刺的完整面试知识图谱，并把每个专题都落到 Java 后端工程实战。

- **定位**：面向 Java 后端高级/资深面试的 Elasticsearch 知识体系，深度对标 `middleware/mysql`、`middleware/redis`、`middleware/rocketmq`、`ops/linux`
- **适用对象**：Java 后端面试（社招高级/资深，5 年+），兼顾架构与系统设计方向
- **组织方式**：8 个主题目录 + 1 个 Q&A 文件，每份主题文档遵循「概念定义 → 原理与流程 → 高频追问 → 实战关联 → 系统设计案例」五段式结构
- **导航约定**：每份文档顶部含 `> 返回 [ES 知识图谱](../README.md)` 链接，本文档为统一入口
- **版本基线**：Elasticsearch 8.x（覆盖 Runtime Field、dense_vector KNN、ES|QL、file cache、PIT、安全默认开启等特性，7.x 仅作差异对比）

---

## 二、知识图谱

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
| 分片路由与 Reindex | [分片路由与 Reindex](./07-shard-routing/shard-routing-and-reindex.md) ⬜ | routing 路由公式/routing key 选型/CCR 跨集群复制/reindex·Update By Query/分片数规划/Hot-Warm-Cold |
| 高可用与调优 | [高可用与调优](./08-ha-tuning/ha-and-tuning.md) ⬜ | 副本故障恢复/分片再平衡/cat API·_cluster/health/JVM heap·circuit breaker/file cache 8.x/版本升级 7.x→8.x |
| 面试冲刺 | [Q&A 速答](./08-interview-qa.md) ⬜ | 40+ 题速答 + 连环套问思维导图 |

> 共 **10 份**文档：入口 README（本文档）+ 上表 8 份主题文档 + 1 份 Q&A 文档。

---

## 四、推荐学习路径

### 路线一：系统学习（适合有 1-2 周准备期）

按 Elasticsearch 知识层次自底向上，先建立架构与索引模型底层，再向上到读写流程、查询打分、聚合、分片路由、高可用：

```
01 架构 → 02 索引与映射 → 03 倒排索引与分词 → 04 读写流程与 Translog → 05 查询 DSL 与打分 → 06 聚合 → 07 分片路由与 Reindex → 08 高可用与调优 → 08 Q&A
```

**特点**：先见森林后见树木，符合「架构 → 索引 → 倒排 → 读写 → 查询 → 聚合 → 分片 → 高可用」的认知递进，适合建立完整体系。底层到上层路径清晰：架构是骨架，索引与映射决定数据组织，倒排索引决定检索能力，读写流程决定近实时性与可靠性，查询与聚合决定业务能力，分片与高可用决定扩展性与容灾。

### 路线二：面试冲刺（高频优先，适合 3-5 天突击）

按面试热度排序，先啃必考点，再补体系：

1. 01 架构 + 02 索引与映射
2. 03 倒排索引 + 05 查询打分
3. 04 读写 + 06 聚合
4. 07 分片路由 + 08 调优
5. 08 Q&A（40+ 题，含连环套问思维导图）

**特点**：投入产出比最高，覆盖 80% 高频考点。Elasticsearch 面试起手三连问是「倒排索引原理 → 写入流程与近实时可见 → 查询与打分」，先把这三块拿下再补分片路由与高可用。

> 两套路线殊途同归，最终都应回到 [Q&A 速答](./08-interview-qa.md) 做闭环检验。

---

## 五、与 java-core / framework 模块的关联

本模块虽为中间件文档，但与仓库内 Java 模块存在直接关联，便于在面试中结合源码与实战作答：

| ES 知识点 | 关联 Java 模块 | 关联要点 |
|----------|---------------|---------|
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

> 建议在阅读架构、读写流程与调优文档时，对照 `java-core`/`framework` 模块源码，加深「面试八股 → 工程实战」双向映射。

**延伸阅读**：
- `java-core/jvm` —— 对照理解 ES JVM heap 50% 规则、堆外内存（Lucene segment mmap）、GC 停顿对近实时可见性的影响
- `framework/spring-framework` —— Spring Data Elasticsearch 的 `@Document` 注解驱动、RestClient 连接池
- `framework/jackson` —— bulk/查询结果的 JSON 序列化器与 Jackson 自定义配置

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
