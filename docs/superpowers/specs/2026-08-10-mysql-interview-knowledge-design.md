# MySQL 面试知识体系 — 设计文档

> **创建日期**：2026-08-10
> **作者**：zihao
> **状态**：已确认，待写实现计划
> **适用对象**：Java 后端工程师面试（社招高级/资深，5 年+）

---

## 一、目标与范围

### 1.1 目标

为 Java 后端工程师面试构建一套**结构化、系统化、有深度**的 MySQL 知识文档体系，作为长期学习与面试冲刺的统一参考。对标 `ops/docker`、`ops/network` 模块的五段式风格，深度达到**原理级 + 架构级 + 实战级**三重标准。

### 1.2 覆盖范围

- **核心原理层**：索引底层结构、事务与 MVCC、锁机制、存储引擎（InnoDB Buffer Pool/Checkpoint/WAL）、日志体系（Undo/Redo/Binlog/两阶段提交）。
- **优化实战层**：Explain 执行计划全字段解读、JOIN Nested Loop、深分页优化、大表 DDL、慢查询排查。
- **架构设计层**：主从复制、读写分离、分库分表、MHA/MGR/Orchestrator 高可用选型、半同步复制。
- **面试场景层**：亿级表优化、热点数据更新、分布式锁、幂等设计、复杂 SQL 调优案例。
- **与 Java 模块联动**：关联 `java-core/jvm`（JDBC 堆外内存、连接池）、`framework/spring-framework`（事务抽象 @Transactional、声明式事务传播行为）、`framework/valid`（参数校验与 DB 约束互补）。

### 1.3 深度标准

采用**面试宝典型五段式**——每个知识点按五段展开：

1. **概念定义**：一句话定位 + 对比表 + 核心概念关系
2. **原理与流程**：底层机制 + mermaid 时序图/流程图 + 关键参数/源码路径
3. **高频追问**：面试官连环追问的典型问题与答案要点（3-5 句要点）
4. **实战关联（Java 后端视角）**：工程场景、SQL 案例、参数调优、与仓库 Java 模块的关联
5. **系统设计案例**：综合大题（亿级表优化、分布式锁、幂等设计等），含 3 分钟标准答法 + 追问链

Q&A 篇不套五段式，采用速答列表 + 连环套问思维导图。

### 1.4 交付方式

一次性全量交付 9 份 Markdown 文档（1 入口 + 7 主题 + 1 Q&A）。

### 1.5 设计决策记录

| 决策点 | 选择 | 理由 |
|--------|------|------|
| 核心定位 | 面试导向 + 原理深度 | 5 年+ 资深面试要求讲到底层机制，不能停留在八股 |
| 组织方式 | 分层目录多文件 | 与 docker/network 模块风格一致，便于检索与增量扩充 |
| 覆盖范围 | 纯 MySQL | 分库分表中间件（ShardingSphere）仅在架构篇标注边界，不展开 |
| 深度级别 | 原理级 + 架构级 | 讲 what/why/how，含 InnoDB 源码路径、时序图、架构决策 |
| 实战比例 | Java 后端视角 | 与仓库 java-core/framework 模块联动 |
| Q&A 汇总 | 独立一篇 | 对标 docker/network 的 interview-qa.md |
| 锁与事务合并 | 分开 | 锁机制独立成篇，因为 Gap/Next-Key/死锁分析内容量大 |
| 存储引擎与日志分开 | 分开 | Buffer Pool 与日志体系各自内容量大，合并会臃肿 |
| 版本基线 | MySQL 8.0 | 覆盖 MPSR、降序索引、隐藏列、Redo Log Archiving 等 8.0 特性，5.7 仅作差异对比 |

---

## 二、目录结构

在 `middleware/mysql/` 下按 MySQL 知识层次组织，共 7 个主题目录 + 1 个 Q&A 文件 + 1 个入口 README。

```
middleware/mysql/
├── README.md                                  # 入口：简介 + 知识图谱(Mermaid) + 导航表 + 学习路径 + Java 模块关联
│
├── 01-index/                                  # 索引原理与优化
│   └── index-and-optimization.md              # B+树/聚簇·二级/回表/覆盖/最左匹配/ICP/MRR/索引失效
│
├── 02-transaction/                            # 事务与 MVCC
│   └── transaction-and-mvcc.md                # ACID/隔离级别/MVCC/ReadView/Undo Chain/RR vs RC 幻读
│
├── 03-lock/                                   # 锁机制
│   └── lock-mechanism.md                      # 行锁/Gap/Next-Key/意向锁/插入意向锁/死锁/RR·RC 锁差异
│
├── 04-query/                                  # 查询优化与执行计划
│   └── query-optimization.md                  # Explain 全字段/JOIN Nested Loop/子查询/深分页/大表 DDL
│
├── 05-storage/                                # 存储引擎底层
│   └── innodb-engine.md                       # Buffer Pool/Change Buffer/AHI/LSN/Checkpoint/WAL/刷盘
│
├── 06-log/                                    # 日志体系
│   └── log-system.md                          # Undo/Redo/Binlog/Relay Log/两阶段提交/Crash Recovery
│
├── 07-architecture/                           # 架构与高可用
│   └── ha-and-sharding.md                     # 主从复制/读写分离/分库分表/MHA/MGR/半同步/高可用选型
│
└── 08-interview-qa.md                         # 40+ 题速答 + 连环套问思维导图
```

共 **9 份**文档：入口 README（本文档体系入口）+ 上表 8 份主题/汇总文档。

---

## 三、统一风格约定

### 3.1 主题文档顶部模板

```markdown
# 标题

> **一句话定位**：xxx
> **面试热度**：⭐⭐⭐⭐⭐
> **返回**：[MySQL 知识图谱](../README.md)

---
```

### 3.2 五段式结构（主题文档）

1. **概念定义**：含对比表、核心概念关系
2. **原理与流程**：含 mermaid 图、源码路径、时序图、调用链
3. **高频追问**：每题 3-5 句要点
4. **实战关联（Java 后端视角）**：关联仓库 Java 模块，示例 SQL/代码用关键点注释
5. **系统设计案例**：3 分钟标准答法 + 追问链

### 3.3 Q&A 篇结构

不套五段式，采用：
- 使用说明
- 按主题分类的 Q&A 列表（每题 3-5 句要点 + 关联链接，连环追问标 🔗）
- 连环套问思维导图（mermaid mindmap）

---

## 四、各文档内容设计

### 4.1 README.md（模块入口）

**五大板块**：

1. **模块简介**：定位（面向 Java 后端高级/资深面试的 MySQL 知识体系）、适用对象、组织方式（7 个主题目录 + 1 个 Q&A 文件）、导航约定（顶部 `> 返回 [MySQL 知识图谱](../README.md)` 链接）。

2. **知识图谱（Mermaid mindmap）**：根节点 `MySQL`，8 大分支：
   - 索引原理：B+树、聚簇/二级索引、回表、覆盖索引、最左匹配、ICP、MRR、索引失效
   - 事务与 MVCC：ACID、隔离级别、MVCC、ReadView、Undo Chain、RR vs RC
   - 锁机制：行锁、Gap、Next-Key、意向锁、插入意向锁、死锁、锁升级
   - 查询优化：Explain、JOIN Nested Loop、子查询、深分页、大表 DDL
   - 存储引擎：Buffer Pool、Change Buffer、AHI、LSN、Checkpoint、WAL
   - 日志体系：Undo Log、Redo Log、Binlog、Relay Log、两阶段提交、Crash Recovery
   - 架构与高可用：主从复制、读写分离、分库分表、MHA、MGR、半同步
   - 面试冲刺：40+ 题速答、连环套问思维导图

3. **导航表**：分层 | 文档 | 核心考点，8 行对应 8 份文档。

4. **两条学习路径**：
   - 路线一（系统学习）：01 索引 → 02 事务 → 03 锁 → 04 查询优化 → 05 存储引擎 → 06 日志 → 07 架构 → 08 Q&A
   - 路线二（面试冲刺）：01 索引 → 03 锁 → 02 事务 → 04 查询优化 → 06 日志 → 05 存储引擎 → 07 架构 → 08 Q&A

5. **与 Java 模块的关联表**：仿 docker 模块第 5 节，列出 MySQL 知识点与 java-core/framework 模块的关联要点。

### 4.2 01-index/index-and-optimization.md（索引原理与优化）

**定位**：⭐⭐⭐⭐⭐，面试最高频考点，"讲讲 MySQL 索引底层结构"是起手题。

1. **概念定义**
   - 索引本质：帮助 MySQL 高效获取数据的**有序数据结构**，InnoDB 的索引即 B+树
   - B+树 vs B树 vs 红黑树 vs Hash 索引对比表（树高、范围查询、磁盘 IO 次数、有序性）
   - 为什么 InnoDB 选 B+树：1）树矮（3-4 层即可存千万行）；2）叶子节点双向链表，范围查询高效；3）非叶子只存键值，单页可存更多键，降低树高
   - 聚簇索引 vs 二级索引对比表（叶子节点存什么、是否回表、一张表能几个、按什么排序）
   - 一张表的索引组织：InnoDB 表即聚簇索引（"索引即数据"），二级索引叶子存主键值

2. **原理与流程**
   - **B+树结构详解**：页（16KB）的结构（File Header/Page Header/User Records/Free Space/Page Directory/File Footer）；单页内记录用单向链表、页间用双向链表；Page Directory 的二分查找；推导 3 层 B+树可存 2000 万行（根 1 页 → 中间 1000 页 → 叶子 1000*1000 页）
   - **聚簇索引**：按主键构建，叶子节点存完整行数据；主键选择策略（自增 ID vs UUID 的页分裂问题）；若无主键 InnoDB 会选唯一非空索引或生成隐藏列 ROW_ID（6 字节）
   - **二级索引（辅助索引）**：按非主键列构建，叶子节点存索引列值 + 主键值；查询需回表（先查二级索引拿主键，再查聚簇索引拿行数据）
   - **覆盖索引**：查询字段全在索引列中，无需回表；`Using index` vs `Using where` vs `Using index condition` 在 Explain 中的含义
   - **最左前缀匹配**：联合索引 (a,b,c) 的匹配规则；`WHERE a=1 AND c=3` 能用到 a（c 用不到，除非 ICP）；`WHERE a>1 AND b=2` 的范围终止原理
   - **索引下推 ICP（Index Condition Pushdown, 5.6+）**：对 `WHERE a=1 AND c LIKE '%x%'`，Server 层下推 c 条件到引擎层，减少回表次数；`Using index condition` 的含义
   - **MRR（Multi-Range Read, 5.6+）**：对二级索引范围查询，先缓存主键再排序后回表，将随机 IO 转为顺序 IO
   - **索引失效场景全表**：函数运算、隐式类型转换、`LIKE '%x'`、`OR` 两边非全索引、`!=`/`<>`（通常）、`NOT IN`、`IS NULL`/`IS NOT NULL`（通常）、字符集不一致、优化器估算成本后选全表扫描
   - **优化器选索引**：基于成本的估算（扫描行数、回表成本、排序成本）；`FORCE INDEX` 的使用场景与副作用

3. **高频追问**
   - 为什么不用红黑树/Hash/跳表做索引？（树高、范围查询、磁盘 IO）
   - 一千万数据的表，B+树大概几层？为什么？（3-4 层）
   - 主键选自增 ID 还是 UUID？为什么？（页分裂、存储、性能）
   - 联合索引 (a,b,c)，`WHERE a=1 AND c=3` 能用几个？（a 用上，c 用 ICP）
   - `WHERE a>1 AND b=2` 联合索引能用上 b 吗？为什么？（范围之后的列失效）
   - `EXPLAIN` 里的 `key_len` 怎么算？有什么用？（判断用了几个索引列）
   - 索引建多了有什么坏处？（写放大、空间、优化器选错）
   - count(*)/count(1)/count(列) 的区别与索引选择？（count(*) 优化选最小索引）

4. **实战关联（Java 后端视角）**
   - MyBatis/JPA 的常见慢查询排查思路（慢日志 + Explain + key_len + rows + Extra）
   - 唯一索引 vs 业务代码校验的权衡（DB 兜底 vs 性能）
   - 软删除 `is_deleted` 加索引导致查询慢的案例与优化（改进设计）
   - 关联 `framework/spring-framework`：`@Transactional` 与索引选择的关系（事务内统计信息可能不准）

5. **系统设计案例**
   - "亿级用户表如何设计索引与分页查询"——3 分钟标准答法（聚簇主键选自增 → 二级索引覆盖 → 深分页用游标/延迟关联 → 考虑分表）
   - "订单表按 status 查询很慢怎么办"——追问链（status 基数低 → 建索引无效 → 联合索引（status, create_time）→ 覆盖索引 → 分表）

### 4.3 02-transaction/transaction-and-mvcc.md（事务与 MVCC）

**定位**：⭐⭐⭐⭐⭐，面试核心，"讲讲 MVCC 原理"必问。

1. **概念定义**
   - ACID 四特性表（Atomicity/Consistency/Isolation/Durability，各自的实现机制：Undo Log/业务约束/锁+MVCC/Redo Log）
   - 并发问题四件套：脏读、不可重复读、幻读、丢失更新
   - 四种隔离级别表（READ UNCOMMITTED/READ COMMITTED/REPEATABLE READ/SERIALIZABLE，分别解决什么问题）
   - MySQL 默认隔离级别：RR（可重复读），且 RR 下通过 Next-Key Lock 解决幻读
   - 范式与反范式（1NF/2NF/3NF/BBCNF，面试偶尔问，简要带过）

2. **原理与流程**
   - **MVCC 多版本并发控制**：
     - 每行隐藏列：`DB_TRX_ID`（6 字节事务 ID）、`DB_ROLL_PTR`（7 字节回滚指针指向 undo log）、`DB_ROW_ID`（6 字节行 ID，无主键时用）
     - Undo Log 版本链：每次更新生成 undo log，通过 `DB_ROLL_PTR` 串联成链表
     - ReadView（读视图）：4 个核心字段（`creator_trx_id`/`m_ids`活跃事务ID列表/`min_trx_id`/`max_trx_id`）
     - 可见性判断算法：访问某行的 undo 链，逐版本判断 trx_id 与 ReadView 的关系（< min_trx_id 可见、>= max_trx_id 不可见、在 m_ids 中不可见、否则可见）
   - **RC vs RR 的 ReadView 生成时机差异**：
     - RC：每次 SELECT 都生成新 ReadView → 每次能看到最新已提交数据（不可重复读）
     - RR：事务第一次 SELECT 生成 ReadView，后续复用 → 可重复读
     - 时序图（mermaid）展示 RC 和 RR 下事务 A、B 的可见性差异
   - **快照读 vs 当前读**：
     - 快照读：普通 SELECT，走 MVCC
     - 当前读：SELECT ... FOR UPDATE / UPDATE / DELETE / INSERT，读最新版本 + 加锁
     - RR 下 `SELECT * FROM t WHERE id=1` 与 `SELECT ... FOR UPDATE` 的差异
   - **幻读的解决**：
     - 快照读通过 MVCC 自然避免幻读
     - 当前读通过 Next-Key Lock（Gap + Record Lock）避免幻读
     - 幻读的特殊场景：先快照读后当前读，或事务中途 commit 后再次快照读

3. **高频追问**
   - MVCC 解决了什么问题？Undo Log 版本链怎么工作？
   - RR 下幻读完全解决了吗？举一个还能幻读的例子
   - RC 和 RR 的 ReadView 生成时机差异？为什么 RC 叫不可重复读？
   - 为什么 MySQL 默认用 RR 而不是 RC？（历史：主从复制依赖 binlog statement 格式，RR 下 statement 可保证从库顺序）
   - 8.0 之后为什么很多公司改用 RC？（binlog row 格式为主、减少锁范围、减少死锁）
   - 长事务为什么危险？（undo 链无法回收，占用表空间、导致历史版本堆积）

4. **实战关联（Java 后端视角）**
   - 关联 `framework/spring-framework`：`@Transactional` 的传播行为（REQUIRED/REQUIRES_NEW/NESTED）与 MySQL 事务的关系
   - Spring 声明式事务失效场景：方法非 public、自调用（AOP 代理不生效）、异常被 catch、`rollbackFor` 未配置
   - 长事务排查：`information_schema.innodb_trx` 查询事务时长、Undo Log 体积
   - 读写分离场景下：主从延迟导致的事务内"读不到刚插入的数据"

5. **系统设计案例**
   - "转账场景的并发安全设计"——3 分钟答法（事务 + 行锁 + 余额校验 + 幂等）
   - "库存扣减超卖怎么办"——追问链（SELECT FOR UPDATE → 乐观锁版本号 → Redis 预扣 → 分段锁）

### 4.4 03-lock/lock-mechanism.md（锁机制）

**定位**：⭐⭐⭐⭐⭐，面试难点，"讲讲 MySQL 的锁"必问。

1. **概念定义**
   - 全局锁 / 表级锁 / 行级锁三层
   - 表级锁：表锁、元数据锁（MDL）、意向锁（IS/IX）
   - 行级锁：Record Lock（记录锁）、Gap Lock（间隙锁）、Next-Key Lock（临键锁）、插入意向锁
   - 共享锁（S）/ 排他锁（X）/ 意向共享（IS）/ 意向排他（IX）矩阵
   - 按思想分：悲观锁（SELECT FOR UPDATE）、乐观锁（版本号/CAS）

2. **原理与流程**
   - **Record/Gap/Next-Key Lock 的加锁规则**（重点中的重点）：
     - 唯一索引等值命中 → 退化为 Record Lock
     - 唯一索引等值未命中 → 退化为 Gap Lock
     - 非唯一索引等值 → Next-Key Lock + 下一个 Gap
     - 范围查询的加锁规则（左开右闭区间）
     - 完整加锁规则表（按索引类型 × 等值/范围 × 命中/未命中）
     - 典型案例 SQL + 加锁区间图（mermaid 展示区间）
   - **意向锁的作用**：表锁与行锁的兼容判断；`IS` 与 `IX` 互相兼容；表 S/X 锁与行 S/X 锁的兼容矩阵
   - **MDL（元数据锁）**：MDL_READ/MDL_WRITE；DDL 与 DML 冲突导致"卡住全表"的原理
   - **插入意向锁**：多个事务插入同一 Gap 不同位置时不互相阻塞；与 Gap Lock 的兼容矩阵
   - **死锁**：产生条件（互斥、持有并等待、不可剥夺、循环等待）；MySQL 死锁检测（`innodb_deadlock_detect`）；`SHOW ENGINE INNODB STATUS` 看死锁日志；`innodb_lock_wait_timeout` 等待超时
   - **RR vs RC 下的锁差异**：RR 下 Gap Lock 防幻读，RC 下无 Gap Lock（除外键约束）；8.0 切 RC 减少锁范围

3. **高频追问**
   - `SELECT ... FOR UPDATE` 锁的是行还是表？（取决于查询条件是否走索引）
   - 唯一索引等值命中加什么锁？未命中呢？
   - 非唯一索引等值加什么锁？为什么多锁一个 Gap？
   - 死锁怎么排查？怎么避免？
   - `innodb_lock_wait_timeout` 和 `innodb_deadlock_detect` 的区别？
   - 为什么 MDL 会导致全表卡住？
   - 乐观锁和悲观锁怎么选？（读多写少乐观、写多冲突多悲观）

4. **实战关联（Java 后端视角）**
   - Spring `@Transactional` + `SELECT FOR UPDATE` 的正确使用姿势
   - 死锁案例：两个业务方法以不同顺序更新同一批行 → 死锁；解法：统一加锁顺序
   - 关联 `framework/spring-framework`：`@Transactional(isolation=...)` 与 MySQL 隔离级别的关系
   - 分布式锁：DB 行锁 vs Redis vs ZooKeeper 的对比与选型

5. **系统设计案例**
   - "秒杀场景的库存扣减如何防超卖"——3 分钟答法（Redis 预扣 + DB 乐观锁兜底 + 唯一索引防重）
   - "两个事务互相死锁怎么排查"——追问链（SHOW ENGINE INNODB STATUS → 加锁顺序分析 → 统一加锁顺序）

### 4.5 04-query/query-optimization.md（查询优化与执行计划）

**定位**：⭐⭐⭐⭐，面试高频，"讲讲慢查询排查"常问。

1. **概念定义**
   - SQL 执行流程：连接器 → 查询缓存（8.0 已移除）→ 分析器 → 优化器 → 执行器 → 存储引擎
   - 优化器的工作：基于成本的执行计划选择（全表扫描 vs 索引、JOIN 顺序、子查询改写）
   - Explain 的 12 个字段全表（id/select_type/table/type/possible_keys/key/key_len/ref/rows/filtered/Extra/partitions）

2. **原理与流程**
   - **Explain 字段详解**：
     - `type` 访问类型级别：system > const > eq_ref > ref > range > index > ALL（面试重点：前 6 个的含义与触发条件）
     - `key_len` 计算规则：单列固定长度（int=4, bigint=8, char(10) utf8mb4=40）+ 变长 + NULL 标志位；用于判断联合索引用了几列
     - `Extra` 关键值：`Using index`（覆盖索引）、`Using where`（Server 后过滤）、`Using index condition`（ICP）、`Using temporary`（临时表）、`Using filesort`（额外排序）、`Using join buffer`（BNL/BKA）
     - `rows` 与 `filtered`：优化器估算的扫描行数与过滤后剩余比例
   - **JOIN 的实现**：
     - Nested Loop Join：驱动表逐行查被驱动表，被驱动表走索引
     - Block Nested Loop（BNL）：被驱动表无索引时，用 join_buffer 缓存驱动表批量匹配
     - Batched Key Access（BKA, 5.6+）：对 NLJ 批量提交主键，配合 MRR 顺序回表
     - 驱动表选择：优化器基于扫描行数选小表做驱动（小表驱动大表）
   - **子查询优化**：
     - Semi Join（半连接）、Materialization（物化）、EXISTS 改写
     - `IN` 子查询的执行方式：物化为临时表 → 转 JOIN
   - **排序优化**：
     - 索引有序时直接取（`Using index`）
     - filesort 的两种算法：单路（sort_buffer 放全行）vs 双路（sort_buffer 放排序字段+指针，排序后回表）；单路占用内存大
     - `sort_buffer_size` 调优；超出则落临时表（磁盘排序）
   - **分页优化**：
     - `LIMIT 1000000, 10` 为什么慢：扫描前 100 万行丢弃
     - 延迟关联：先走覆盖索引拿主键，再 JOIN 回表
     - 游标分页：`WHERE id > last_id ORDER BY id LIMIT 10`（要求有序且无断点）
   - **大表 DDL**：
     - Online DDL 的三个阶段：copy（5.5 之前）、inplace（5.6+ 仍可能阻塞）、instant（8.0+ 部分操作元数据级）
     - 加列/加索引对线上影响；gh-ost / pt-osc 的影子表方案
     - DDL 期间的 MDL 锁阻塞链

3. **高频追问**
   - `type` 的级别有哪些？`ref` 和 `eq_ref` 区别？
   - `key_len` 怎么算？有什么用？
   - `Extra` 里 `Using filesort` 怎么优化？
   - JOIN 时怎么选驱动表？被驱动表没索引会怎样？
   - `LIMIT 1000000, 10` 怎么优化？
   - 大表加索引会锁表吗？怎么办？
   - `SELECT COUNT(*)` 慢怎么办？（近似计数、汇总表、Redis 计数）

4. **实战关联（Java 后端视角）**
   - MyBatis 的 `PageHelper` 深分页慢查询案例与改写
   - 慢查询日志 + pt-query-digest 的排查链路
   - `SELECT *` 的危害：覆盖索引失效、网络传输、序列化成本
   - 关联 `framework/spring-framework`：`@Transactional(readOnly=true)` 对查询优化的意义（优化器可走只读优化）

5. **系统设计案例**
   - "慢查询排查全流程"——3 分钟答法（慢日志定位 → Explain 分析 → 索引/写法/架构三层优化）
   - "大表加字段怎么办"——追问链（instant DDL → gh-ost 影子表 → 分库分表后变更协调）

### 4.6 05-storage/innodb-engine.md（存储引擎底层）

**定位**：⭐⭐⭐⭐，面试中高频，区分度大的题。

1. **概念定义**
   - InnoDB vs MyISAM 对比表（事务、锁粒度、外键、聚簇索引、崩溃恢复、全文索引）
   - InnoDB 内存架构：Buffer Pool / Change Buffer / Adaptive Hash Index / Log Buffer
   - InnoDB 磁盘架构：系统表空间 / 独立表空间 / Undo 表空间 / Redo Log / 临时表空间
   - InnoDB 后台线程：Master Thread / IO Thread / Purge Thread / Page Cleaner Thread

2. **原理与流程**
   - **Buffer Pool**：
     - 作用：缓存热点数据页与索引页，减少磁盘 IO
     - 结构：基于页（16KB）的 LRU 链表，改进版 young/old 两段（young 5/12、old 7/12），新页插到 old 头部，存活超 `innodb_old_blocks_time`（默认 1s）才升 young
     - 改进 LRU 的目的：防全表扫描冲刷热点
     - Flush List / Free List / LRU List 三链表
   - **Change Buffer**：
     - 对非唯一二级索引的写操作先缓存，合并到磁盘由后台 PURGE 执行
     - 只对二级索引页有效（聚簇索引必须即时校验唯一性）
     - 占 Buffer Pool 的 `innodb_change_buffer_max_size`（默认 25%）
   - **Adaptive Hash Index（AHI）**：
     - 自动监控热点查询，对 B+树节点建内存哈希索引
     - 单页等值查询命中时直接定位，跳过 B+树遍历
     - 高并发等值查询显著加速，但写多读少或范围查询无收益
   - **Doublewrite Buffer**：
     - 共享表空间的连续 2MB 区，写页前先顺序写两遍
     - 防止页撕裂（partial page write）：若写数据页时 crash，从 doublewrite 恢复完整副本
   - **LSN（Log Sequence Number）**：
     - 单调递增的日志序列号，记录 Redo Log 的写入位置
     - `flush_lsn`（已刷盘）/`write_lsn`（已写入 OS Page Cache）/`checkpoint_lsn`（可复用 Redo 空间）
   - **Checkpoint 机制**：
     - Sharp Checkpoint（全量）/ Fuzzy Checkpoint（增量，默认）
     - 触发：redo log 写满、Buffer Pool 不足、空闲时、关闭时
     - Checkpoint 推进 LSN，Redo Log 可重用
   - **WAL（Write-Ahead Logging）**：
     - 先写 Redo Log 再写数据页，保证 crash 可恢复
     - 为什么先写日志再写数据：日志顺序写远快于数据随机写
   - **刷盘策略**：
     - `innodb_flush_log_at_trx_commit`：0（每秒刷）/1（每次提交刷，默认）/2（每次提交写 OS Cache，每秒 fsync）
     - `innodb_flush_method`：O_DIRECT（绕过 OS Page Cache）/ fsync（默认）
     - `sync_binlog`：0/1/N，与 Redo 的两阶段提交配合

3. **高频追问**
   - Buffer Pool 的 LRU 为什么改进？怎么改进？
   - Change Buffer 为什么只对二级索引有效？
   - 什么是页撕裂？Doublewrite 怎么解决？
   - WAL 是什么？为什么这么设计？
   - `innodb_flush_log_at_trx_commit=2` 安全吗？（宕机丢 1 秒，崩溃不丢）
   - `sync_binlog=1` 和 `innodb_flush_log_at_trx_commit=1` 怎么配合？
   - LSN 是什么？Checkpoint 推进什么？

4. **实战关联（Java 后端视角）**
   - Buffer Pool 调优：生产环境 `innodb_buffer_pool_size` 一般配物理内存 60%-70%
   - 关联 `java-core/jvm`：JVM 堆外内存（DirectByteBuffer）与 MySQL Buffer Pool 的内存预算协调
   - 性能压测时 `innodb_flush_log_at_trx_commit=2 + sync_binlog=0` 的临时调优与风险
   - JDBC `rewriteBatchedStatements` 与批量写入性能

5. **系统设计案例**
   - "MySQL 宕机会丢数据吗"——3 分钟答法（Redo Log WAL → binlog 两阶段提交 → crash recovery 三步）
   - "高并发写入场景怎么调 InnoDB 参数"——追问链（Buffer Pool → 刷盘策略 → Change Buffer → IO 线程数）

### 4.7 06-log/log-system.md（日志体系）

**定位**：⭐⭐⭐⭐⭐，面试热点，"讲讲 MySQL 的日志"必问。

1. **概念定义**
   - 四大日志总览表：Undo Log / Redo Log / Binlog / Relay Log
   - 各日志的作用、产生层、内容、写入时机、生命周期
   - 物理日志（Redo，页级别的物理修改）vs 逻辑日志（Binlog，SQL/行变更）vs 逻辑-物理混合（Undo）

2. **原理与流程**
   - **Undo Log（回滚日志）**：
     - 作用：事务回滚 + MVCC 版本链
     - 内容：记录修改前的行旧值（逻辑日志，行级）
     - 存放：Undo 表空间，`innodb_undo_tablespaces`、`innodb_undo_log_truncate`（8.0 自动 truncate）
     - 活跃 Undo 与 History 链；事务提交后不再活跃，但 MVCC 可能还要用，由 Purge 线程清理
     - 大事务/长事务导致 Undo 膨胀
   - **Redo Log（重做日志）**：
     - 作用：crash recovery 保证持久性
     - 内容：页级别的物理修改（哪个页哪个偏移改成什么）
     - 结构：`ib_logfile0/1`，固定大小循环写；`innodb_log_file_size`、`innodb_log_files_in_group`（8.0.30 后改为 `innodb_redo_log_capacity` 单一参数）
     - Mini-Transaction（MTR）批量写入 Redo Log
     - `innodb_flush_log_at_trx_commit` 三级刷盘策略
   - **Binlog（归档日志）**：
     - 作用：主从复制 + 数据归档恢复
     - 产生层：Server 层，所有引擎共用
     - 格式：STATEMENT（记 SQL）/ROW（记行变更，默认）/MIXED
     - 写入模式：`sync_binlog` 0/1/N；`binlog_group_commit_sync_delay` 组提交
     - 与 Redo Log 的区别对比表（层、内容、物理/逻辑、写入方式、生命周期）
   - **Relay Log（中继日志）**：
     - 作用：从库接收主库 Binlog 并本地回放
     - 结构：与 Binlog 格式一致
     - IO Thread 写入 → SQL Thread 回放；5.7+ 并行复制（基于组提交 / WRITESET）
   - **两阶段提交（2PC）**：
     - 为什么需要 2PC：保证 Redo Log 与 Binlog 的一致性，避免 crash 后主从不一致
     - 流程时序图（mermaid sequenceDiagram）：Redo Log prepare → 写 Binlog → Redo Log commit
     - Crash Recovery 逻辑：
       - Prepare 阶段 crash：检查 binlog 是否已写入；若未写，回滚；若已写，提交（认为 binlog 已被从库消费）
       - Commit 阶段 crash：直接提交（因 binlog 已完整）
     - `XA` 与两阶段提交的关系
   - **Binlog 与 Redo Log 的一致性**：
     - 组提交（Group Commit）：`binlog_group_commit_sync_delay` + `binlog_group_commit_sync_no_delay_count`
     - `binlog_transaction_dependency_tracking`：COMMIT_ORDER / WRITESET / WRITESET_SESSION（并行复制）

3. **高频追问**
   - Undo Log 和 Redo Log 有什么区别？
   - Binlog 和 Redo Log 有什么区别？为什么需要两个？
   - 两阶段提交是什么？为什么需要？
   - crash recovery 的逻辑是什么？
   - 长事务为什么导致 Undo 膨胀？
   - `sync_binlog=1` 和 `innodb_flush_log_at_trx_commit=1` 必须都配吗？
   - 从库延迟怎么解决？（并行复制、减少大事务、读写分离容忍）

4. **实战关联（Java 后端视角）**
   - Canal 原理：伪装 MySQL 从库，解析 Binlog 推送下游；ROW 格式的必要性
   - 数据恢复：`mysqlbinlog --start-datetime --stop-datetime` 解析恢复
   - 大事务导致 Binlog 单事务过大的排查（`binlog_rows_query_events`）
   - 关联 `framework/spring-framework`：事务传播行为与两阶段提交的边界

5. **系统设计案例**
   - "MySQL 宕机后数据怎么恢复"——3 分钟答法（crash recovery 三步：Redo 重放 → Undo 回滚 → Binlog 补齐）
   - "主从延迟导致业务异常怎么设计"——追问链（半同步 → 并行复制 → 读写分离策略 → 强制走主）

### 4.8 07-architecture/ha-and-sharding.md（架构与高可用）

**定位**：⭐⭐⭐⭐，资深面试区分度题。

1. **概念定义**
   - 主从复制：异步复制、半同步复制、全同步复制
   - 读写分离：主写从读、从库延迟容忍
   - 分库分表：垂直分库、水平分表、垂直分表
   - 高可用方案：MHA、Orchestrator、MGR、MySQL InnoDB Cluster
   - 中间件边界：ShardingSphere、MyCat、Vitess、ProxySQL（仅标注边界，不展开）

2. **原理与流程**
   - **主从复制原理**：
     - 三个线程：主库 Binlog Dump Thread、从库 IO Thread、SQL Thread
     - 流程时序图：主库写 Binlog → Dump Thread 推送 → IO Thread 写 Relay Log → SQL Thread 回放
     - 复制过滤：`replicate_wild_do_table`
     - 延迟来源：单 SQL Thread 串行回放、大事务、网络；5.7+ 并行复制
   - **半同步复制**：
     - `rpl_semi_sync_master_wait_for_slave_count`（至少 N 个从库 ack）
     - `rpl_semi_sync_master_timeout` 超时降级为异步
     - `AFTER_SYNC` vs `AFTER_COMMIT`（5.7+ 默认 AFTER_SYNC，减少幻读风险）
   - **MGR（MySQL Group Replication, 8.0+）**：
     - 基于 Paxos 变种（Mention-based consensus）
     - 单主 vs 多主模式
     - 冲突检测（基于 WRITESET）
   - **分库分表**：
     - 垂直分库：按业务拆库（订单库、用户库）
     - 水平分表：按 hash/range/时间分片
     - 分片键选择：高频查询条件、避免数据倾斜
     - 全局唯一 ID：UUID、Snowflake、号段模式（Leaf）
     - 跨片查询：广播、汇总表、ES 宽表补齐
     - 分布式事务：XA、TCC、本地消息表、Saga
   - **高可用方案选型对比表**：
     - MHA：基于 SSH 的 failover，已老旧
     - Orchestrator：Go 写的拓扑管理，活跃
     - MGR：原生集群，适合金融场景
     - 中间件 + 分库分表：ShardingSphere 等

3. **高频追问**
   - 主从复制原理？延迟怎么解决？
   - 半同步复制是什么？什么时候降级为异步？
   - 分库分表怎么选分片键？跨片查询怎么办？
   - 分布式 ID 方案有哪些？Snowflake 时钟回拨怎么办？
   - 分布式事务怎么选？（强一致 XA vs 最终一致消息表）
   - MGR 和半同步怎么选？
   - 读写分离如何解决主从延迟？（强制走主、缓存、半同步）

4. **实战关联（Java 后端视角）**
   - ShardingSphere-JDBC 与 ShardingSphere-Proxy 的选型
   - Spring Boot 多数据源配置：`@DS` 注解、`AbstractRoutingDataSource`
   - 全局唯一 ID 在订单系统中的实践（Snowflake + 业务前缀）
   - 关联 `framework/spring-framework`：`@Transactional` 与 XA 分布式事务的集成

5. **系统设计案例**
   - "订单系统分库分表方案设计"——3 分钟答法（按 user_id hash 分表 → Snowflake ID → 异步消息保证最终一致）
   - "高可用 MySQL 集群怎么设计"——追问链（一主多从 + 半同步 → MGR/Orchestrator → 跨机房 → 分库分表）

### 4.9 08-interview-qa.md（面试冲刺 Q&A）

**定位**：⭐⭐⭐⭐⭐，冲刺速答与连环套问。

1. **使用说明**：每题 3-5 句要点，🔗 标记可点击跳转到主题文档对应章节，连环追问标 🔄。

2. **按主题分类的 Q&A 列表（共 40+ 题）**：

   - **一、索引篇（8 题）**：Q1-Q8
      - Q1: MySQL 索引底层是什么结构？为什么用 B+树？🔗
      - Q2: 聚簇索引和二级索引的区别？🔗
      - Q3: 什么是回表？怎么避免？🔗
      - Q4: 什么是覆盖索引？🔗
      - Q5: 最左前缀匹配是什么？🔗
      - Q6: 索引下推 ICP 是什么？🔗
      - Q7: 索引失效有哪些场景？🔗
      - Q8: 主键选自增 ID 还是 UUID？为什么？🔗

   - **二、事务与 MVCC 篇（6 题）**：Q9-Q14
      - Q9: ACID 是什么？各自怎么实现？🔗
      - Q10: 并发问题有哪些？分别对应什么隔离级别？🔗
      - Q11: MVCC 原理是什么？ReadView 怎么判断可见性？🔗
      - Q12: RR 下幻读解决了吗？🔗
      - Q13: RC 和 RR 的 ReadView 生成时机差异？🔗
      - Q14: 为什么 MySQL 默认 RR？8.0 后为什么很多公司改 RC？🔗

   - **三、锁机制篇（6 题）**：Q15-Q20
      - Q15: MySQL 有哪些锁？表级、行级、页级？🔗
      - Q16: Record/Gap/Next-Key Lock 分别是什么？🔗
      - Q17: `SELECT FOR UPDATE` 锁的是行还是表？🔗
      - Q18: 唯一索引等值命中加什么锁？未命中呢？🔗
      - Q19: 死锁怎么排查与避免？🔗
      - Q20: 乐观锁和悲观锁怎么选？🔗

   - **四、查询优化篇（6 题）**：Q21-Q26
      - Q21: Explain 各字段含义？`type` 有哪些级别？🔗
      - Q22: `key_len` 怎么算？有什么用？🔗
      - Q23: `Extra` 里 `Using filesort` 怎么优化？🔗
      - Q24: JOIN 时怎么选驱动表？🔗
      - Q25: `LIMIT 1000000, 10` 怎么优化？🔗
      - Q26: 大表加字段/索引会锁表吗？怎么办？🔗

   - **五、存储引擎篇（5 题）**：Q27-Q31
      - Q27: InnoDB 和 MyISAM 区别？🔗
      - Q28: Buffer Pool 的 LRU 为什么改进？🔗
      - Q29: Change Buffer 是什么？为什么只对二级索引有效？🔗
      - Q30: Doublewrite 解决什么问题？🔗
      - Q31: WAL 是什么？为什么这么设计？🔗

   - **六、日志体系篇（5 题）**：Q32-Q36
      - Q32: Undo Log 和 Redo Log 区别？🔗
      - Q33: Binlog 和 Redo Log 区别？为什么需要两个？🔗
      - Q34: 两阶段提交是什么？为什么需要？🔗
      - Q35: crash recovery 怎么保证数据不丢？🔗
      - Q36: 主从复制原理？延迟怎么解决？🔗

   - **七、架构与高可用篇（5 题）**：Q37-Q41
      - Q37: 读写分离如何解决主从延迟？🔗
      - Q38: 半同步复制是什么？什么时候降级？🔗
      - Q39: 分库分表怎么选分片键？跨片查询怎么办？🔗
      - Q40: 分布式 ID 方案有哪些？Snowflake 时钟回拨怎么办？🔗
      - Q41: 分布式事务怎么选？🔗

   - **八、连环套问思维导图**（mermaid mindmap）：
       模拟面试官的追问路径，6 条完整追问链：
       - 索引链：索引底层结构 → B+树 → 聚簇 vs 二级 → 回表 → 覆盖索引 → 最左匹配 → ICP → 索引失效
       - 事务链：ACID → 隔离级别 → MVCC → ReadView → RR vs RC → 幻读 → 为什么默认 RR
       - 锁链：表锁 vs 行锁 → Record/Gap/Next-Key → 加锁规则 → 死锁排查 → 乐观 vs 悲观
       - 日志链：四大日志 → Undo vs Redo → Binlog vs Redo → 两阶段提交 → crash recovery → 主从复制
       - 优化链：慢查询 → Explain → type/key_len/Extra → JOIN 驱动表 → 深分页 → 大表 DDL
       - 架构链：主从复制 → 半同步 → MGR → 读写分离 → 分库分表 → 分布式 ID → 分布式事务

       每条链都是"入口题 → 原理 → 陷阱 → 实战"的递进。

---

## 五、交叉引用与 Java 模块关联

### 5.1 与 ops 模块、middleware 内部的交叉引用

| MySQL 文档 | 跳转目标 | 对照要点 |
|---|---|---|
| 05-storage | `ops/linux/04-io/io-model-and-epoll.md` | IO 线程与 epoll、IO 模型对照 |
| 05-storage | `ops/linux/03-memory/memory-management.md` | Page Cache 与 Buffer Pool 关系 |
| 06-log | `ops/linux/05-fs/filesystem-and-vfs.md` | fsync 与文件系统崩溃一致性 |
| 07-architecture | `middleware/README.md`（redis 待建） | 分布式锁、幂等的 Redis 方案对照 |
| 07-architecture | `middleware/README.md`（kafka 待建） | 本地消息表与 Kafka 互补 |

**处理原则**：MySQL 章只讲"数据库场景下的实现与选择"，原理推导链回对应模块，不重复展开。

### 5.2 与 Java 模块的关联清单

| MySQL 文档 | 关联 Java 模块 | 关联要点 |
|---|---|---|
| 01-index | `framework/spring-framework` | `@Transactional` 与索引选择关系 |
| 01-index | `framework/valid` | 唯一索引与参数校验互补 |
| 02-transaction | `framework/spring-framework` | `@Transactional` 传播行为、失效场景 |
| 02-transaction | `framework/valid` | 业务约束与 DB 约束互补 |
| 03-lock | `framework/spring-framework` | `@Transactional(isolation=...)` 与隔离级别 |
| 03-lock | `java-core/jvm` | JDBC 连接池、死锁异常处理 |
| 04-query | `framework/spring-framework` | `@Transactional(readOnly=true)` 查询优化 |
| 04-query | `java-core/lambda` | 流式处理 vs SQL 排序/分页的权衡 |
| 05-storage | `java-core/jvm` | 堆外内存与 Buffer Pool 内存预算 |
| 05-storage | `java-core/jvm` | JVM GC 与 MySQL 刷盘的协调 |
| 06-log | `framework/spring-framework` | 事务抽象与两阶段提交的边界 |
| 06-log | `framework/jackson` | Canal 解析 binlog 与序列化 |
| 07-architecture | `framework/spring-framework` | 多数据源、`@DS`、`AbstractRoutingDataSource` |
| 07-architecture | `java-core/lambda` | 分布式 ID 算法实现 |
| 07-architecture | `framework/spring-framework` | 分布式事务集成 |

---

## 六、README 更新规则

遵循 AGENTS.md 的"README 自动更新规则"：

1. **`middleware/README.md`**：将 `mysql` 行从纯文本补充为带链接与文档数，并标记完成进度。
2. **根目录 `README.md`**：在 middleware 概要说明中同步标记 mysql 已完成。
3. **`middleware/mysql/README.md`**：作为本模块入口，含上述关联表。

---

## 七、验收标准

- [ ] 9 份文档全部创建，路径与目录结构一致
- [ ] 每份主题文档顶部含"一句话定位 / 面试热度 / 返回"导航
- [ ] 每份主题文档遵循五段式结构（概念定义 → 原理与流程 → 高频追问 → 实战关联 → 系统设计案例）
- [ ] Q&A 篇含 40+ 题速答 + 连环套问思维导图
- [ ] README.md 含知识图谱（mermaid mindmap）、导航表、两条学习路径、Java 模块关联表
- [ ] 与 ops 模块的交叉引用链接全部有效
- [ ] 与 Java 模块的关联链接全部有效
- [ ] middleware/README.md 的 mysql 行已更新
- [ ] 根目录 README.md 的 middleware 概要已同步
- [ ] 所有 Markdown 渲染正常，mermaid 图语法正确
