# MySQL 面试知识体系实施计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 在 `middleware/mysql/` 下构建 9 份文档的 MySQL 面试知识体系，深度对标 `ops/docker`、`ops/network`、`ops/linux` 模块。

**Architecture:** 纯文档项目，无代码无测试。按 spec 的分阶段交付节奏，每个 Task 完成一份文档并自检（结构校验、链接校验、体量校验）后提交。文档遵循 MySQL 专用五段式：概念定义 → 原理与流程 → 高频追问 → 实战关联（Java 后端视角）→ 系统设计案例。

**Tech Stack:** Markdown + Mermaid 图表，中文撰写。

## Global Constraints

- 语言：全部中文（遵循 AGENTS.md 约定）
- 模块路径：`middleware/mysql/`（目录骨架已创建）
- 文档结构：MySQL 专用五段式（概念定义/原理与流程/高频追问/实战关联/系统设计案例）
- 单份主题文档体量：600-900 行（MySQL 知识点密集，略宽于 Linux 500-800）
- Q&A 文档体量：500-700 行
- README 体量：150-250 行
- 深度：原理级 + 架构级 + 实战级（对标 docker/network/linux）
- 版本基线：MySQL 8.0，5.7 仅作差异对比
- 每份主题文档头部固定三行：`> **一句话定位**` / `> **面试热度**：⭐⭐⭐⭐⭐` / `> **返回**：[MySQL 知识图谱](../README.md)`
- README 自动更新规则：每完成一份主题文档，回填 `middleware/mysql/README.md` 导航表与知识图谱进度标记；完成任何模块内容变更同步更新 `middleware/README.md` 与根 `README.md`
- 提交规范：`docs(mysql): <描述>`，参照现有 `docs(linux):` / `docs(k8s):` / `docs(docker):` 风格
- 参考样本：`ops/docker/01-foundation/container-principle.md`（主题文档五段式）、`ops/docker/09-interview-qa.md`（Q&A）、`ops/docker/README.md`（入口）、`ops/linux/10-interview-qa.md`（50+ 题规模）
- 交叉引用原则：MySQL 章只讲"数据库场景下的实现与选择"，原理推导链回对应模块（ops/linux、ops/network、ops/docker、ops/k8s、java-core、framework），不重复展开

## File Structure

```
middleware/mysql/
├── README.md                                  # Task 1 创建（入口）
├── 01-index/
│   └── index-and-optimization.md              # Task 2（索引原理与优化）
├── 02-transaction/
│   └── transaction-and-mvcc.md                 # Task 3（事务与 MVCC）
├── 03-lock/
│   └── lock-mechanism.md                      # Task 4（锁机制）
├── 04-query/
│   └── query-optimization.md                   # Task 5（查询优化与执行计划）
├── 05-storage/
│   └── innodb-engine.md                       # Task 6（存储引擎底层）
├── 06-log/
│   └── log-system.md                          # Task 7（日志体系）
├── 07-architecture/
│   └── ha-and-sharding.md                     # Task 8（架构与高可用）
└── 08-interview-qa.md                         # Task 9（面试 Q&A 速答，含回填）
```

每份主题文档职责：覆盖该专题的底层机制 + 实战关联（Java 后端视角）+ 系统设计案例，独立可读。Q&A 篇不套五段式，采用速答列表 + 连环套问思维导图。

---

## Task 1: 创建 middleware/mysql/README.md 入口

**Files:**
- Create: `middleware/mysql/README.md`
- Modify: `middleware/README.md`（把 `mysql` 行从纯文本改为链接）
- Modify: 根 `README.md` middleware 概要（标注 mysql 已建文档体系）

**Interfaces:**
- Produces: `middleware/mysql/README.md`，作为后续所有主题文档的导航入口；导航表中的链接路径是后续 Task 的产出契约

- [ ] **Step 1: 编写 middleware/mysql/README.md**

按 spec 第四章 4.1 的五大板块编写，内容要点：

**一、模块简介**：
- 定位：面向 Java 后端高级/资深面试的 MySQL 知识体系，深度对标 `ops/docker`、`ops/network`、`ops/linux`
- 适用对象：Java 后端面试（社招高级/资深，5 年+）
- 组织方式：7 个主题目录 + 1 个 Q&A 文件，每份主题文档遵循五段式
- 导航约定：每份文档顶部含 `> 返回 [MySQL 知识图谱](../README.md)` 链接

**二、知识图谱（Mermaid mindmap）**：根节点 `MySQL`，8 大分支（参考 spec 4.1 第 2 点）：
- 索引原理：B+树、聚簇/二级索引、回表、覆盖索引、最左匹配、ICP、MRR、索引失效
- 事务与 MVCC：ACID、隔离级别、MVCC、ReadView、Undo Chain、RR vs RC
- 锁机制：行锁、Gap、Next-Key、意向锁、插入意向锁、死锁、锁升级
- 查询优化：Explain、JOIN Nested Loop、子查询、深分页、大表 DDL
- 存储引擎：Buffer Pool、Change Buffer、AHI、LSN、Checkpoint、WAL
- 日志体系：Undo Log、Redo Log、Binlog、Relay Log、两阶段提交、Crash Recovery
- 架构与高可用：主从复制、读写分离、分库分表、MHA、MGR、半同步
- 面试冲刺：40+ 题速答、连环套问思维导图

**三、导航表**：8 行表格，格式 `| 分层 | 文档 | 核心考点 |`，核心考点引用 spec 第四章：
```
| 索引原理 | [索引原理与优化](./01-index/index-and-optimization.md) ⬜ | B+树/聚簇·二级/回表/覆盖/最左匹配/ICP/MRR/索引失效 |
| 事务与 MVCC | [事务与 MVCC](./02-transaction/transaction-and-mvcc.md) ⬜ | ACID/隔离级别/MVCC/ReadView/Undo Chain/RR vs RC 幻读 |
| 锁机制 | [锁机制](./03-lock/lock-mechanism.md) ⬜ | 行锁/Gap/Next-Key/意向锁/插入意向锁/死锁/RR·RC 锁差异 |
| 查询优化 | [查询优化与执行计划](./04-query/query-optimization.md) ⬜ | Explain 全字段/JOIN Nested Loop/子查询/深分页/大表 DDL |
| 存储引擎 | [存储引擎底层](./05-storage/innodb-engine.md) ⬜ | Buffer Pool/Change Buffer/AHI/LSN/Checkpoint/WAL/刷盘 |
| 日志体系 | [日志体系](./06-log/log-system.md) ⬜ | Undo/Redo/Binlog/Relay Log/两阶段提交/Crash Recovery |
| 架构与高可用 | [架构与高可用](./07-architecture/ha-and-sharding.md) ⬜ | 主从复制/读写分离/分库分表/MHA/MGR/半同步/高可用选型 |
| 面试冲刺 | [Q&A 速答](./08-interview-qa.md) ⬜ | 40+ 题速答 + 连环套问思维导图 |
```

末尾加：`> 共 **9 份**文档：入口 README（本文档）+ 上表 8 份主题/Q&A 文档。`

**四、推荐学习路径**：两条路线
- 路线一（系统学习）：01 索引 → 02 事务 → 03 锁 → 04 查询优化 → 05 存储引擎 → 06 日志 → 07 架构 → 08 Q&A
- 路线二（面试冲刺）：01 索引 → 03 锁 → 02 事务 → 04 查询优化 → 06 日志 → 05 存储引擎 → 07 架构 → 08 Q&A

**五、与 Java 模块的关联表**：引用 spec 第五章 5.2 的关联清单，列出 MySQL 知识点与 java-core/framework 模块的关联要点（14 行表）。

**六、与 ops 模块的交叉引用**：引用 spec 第五章 5.1 的交叉引用表（5 行）。

- [ ] **Step 2: 更新 middleware/README.md**

把 `- mysql` 改为：
```
- [mysql](./mysql) — MySQL 面试知识体系（9 份文档，面向 5 年+ 资深面试）
```
其余 redis/kafka/rocketmq/es/mongodb 保持原样（未建）。

- [ ] **Step 3: 更新根 README.md middleware 概要**

把 middleware 段落改为：
```
## middleware

- [MySQL](./mysql) — 面试知识体系（9 份文档，覆盖索引/事务/锁/查询优化/存储引擎/日志/架构）
- Redis / Kafka / RocketMQ / Elasticsearch / MongoDB（规划中）
```

- [ ] **Step 4: 结构校验**

Run: `ls -la middleware/mysql/`，确认 7 个子目录存在。
Run: `grep -c '^##' middleware/mysql/README.md`，确认至少 5 节标题。
Run: `grep '返回.*MySQL 知识图谱' middleware/mysql/README.md`，确认导航链接文本存在（链接在后续 Task 完成后可达）。
Run: `grep 'mindmap' middleware/mysql/README.md`，确认含知识图谱。
Expected: 目录结构正确，README 含 5+ 节，导航链接文本与 mindmap 存在。

- [ ] **Step 5: 提交**

```bash
git add middleware/mysql/README.md middleware/README.md README.md
git commit -m "docs(mysql): 新增 MySQL 模块 README 与目录骨架"
```

---

## Task 2: 01-index/index-and-optimization.md（索引原理与优化）

**Files:**
- Create: `middleware/mysql/01-index/index-and-optimization.md`

**Interfaces:**
- Consumes: `middleware/mysql/README.md` 的导航链接路径
- Produces: `./01-index/index-and-optimization.md`，README 导航表第一行的链接可达

**核心考点**（spec 第四章 4.2）：B+树结构、聚簇/二级索引、回表、覆盖索引、最左前缀匹配、ICP、MRR、索引失效、优化器选索引

- [ ] **Step 1: 编写文档**

按 MySQL 五段式编写，各段内容要点：

**头部**：
```
# 索引原理与优化

> **一句话定位**：索引是 MySQL 面试的起手题，"讲讲索引底层结构"几乎每场必问，能讲到 B+树页结构与三千万行推导才算合格。
> **面试热度**：⭐⭐⭐⭐⭐
> **返回**：[MySQL 知识图谱](../README.md)
```

**一、概念定义**（参考 spec 4.2 第 1 点）：
- 索引本质：有序数据结构，InnoDB 索引即 B+树
- B+树 vs B树 vs 红黑树 vs Hash 索引对比表（树高/范围查询/磁盘 IO 次数/有序性，4 列 5 行）
- 为什么 InnoDB 选 B+树：树矮（3-4 层存千万行）、叶子节点双向链表范围查询高效、非叶子只存键值单页可存更多键
- 聚簇索引 vs 二级索引对比表（叶子节点存什么/是否回表/一张表能几个/按什么排序，4 列 3 行）
- 一张表的索引组织：InnoDB 表即聚簇索引"索引即数据"，二级索引叶子存主键值

**二、原理与流程**（参考 spec 4.2 第 2 点，含 mermaid 图、源码路径、对比表）：
- **B+树结构详解**：页（16KB）结构（File Header/Page Header/User Records/Free Space/Page Directory/File Footer），单页内记录单向链表、页间双向链表，Page Directory 二分查找；推导 3 层 B+树可存 2000 万行（根 1 页 → 中间 1000 页 → 叶子 1000*1000 页），用 mermaid flowchart 画 B+树三层结构
- **聚簇索引**：按主键构建，叶子存完整行数据；主键选择策略（自增 ID vs UUID 的页分裂问题）；无主键时 InnoDB 选唯一非空索引或生成隐藏列 ROW_ID（6 字节）
- **二级索引（辅助索引）**：按非主键列构建，叶子存索引列值 + 主键值；查询需回表（先查二级索引拿主键，再查聚簇索引拿行数据），用 mermaid sequenceDiagram 画回表流程
- **覆盖索引**：查询字段全在索引列中无需回表；`Using index` vs `Using where` vs `Using index condition` 在 Explain 中的含义对比表
- **最左前缀匹配**：联合索引 (a,b,c) 的匹配规则；`WHERE a=1 AND c=3` 能用到 a（c 用不到除非 ICP）；`WHERE a>1 AND b=2` 范围终止原理；匹配规则表（等值/范围/排序/分组）
- **索引下推 ICP（5.6+）**：对 `WHERE a=1 AND c LIKE '%x%'`，Server 层下推 c 条件到引擎层减少回表次数；`Using index condition` 含义；ICP 前后对比 mermaid 时序图
- **MRR（5.6+）**：对二级索引范围查询，先缓存主键再排序后回表，将随机 IO 转为顺序 IO
- **索引失效场景全表**：函数运算、隐式类型转换、`LIKE '%x'`、`OR` 两边非全索引、`!=`/`<>`（通常）、`NOT IN`、`IS NULL`/`IS NOT NULL`（通常）、字符集不一致、优化器估算成本后选全表扫描（9 行表 + 案例 SQL）
- **优化器选索引**：基于成本估算（扫描行数/回表成本/排序成本）；`FORCE INDEX` 使用场景与副作用
- 关键源码路径：`storage/innobase/btr/btr0btr.cc`（B+树操作）、`storage/innobase/page/page0page.cc`（页操作）

**三、高频追问**（参考 spec 4.2 第 3 点，8 题，问答体每题 3-5 句要点）：
- Q1: 为什么不用红黑树/Hash/跳表做索引？
- Q2: 一千万数据的表，B+树大概几层？为什么？
- Q3: 主键选自增 ID 还是 UUID？为什么？
- Q4: 联合索引 (a,b,c)，`WHERE a=1 AND c=3` 能用几个？
- Q5: `WHERE a>1 AND b=2` 联合索引能用上 b 吗？为什么？
- Q6: `EXPLAIN` 里的 `key_len` 怎么算？有什么用？
- Q7: 索引建多了有什么坏处？
- Q8: count(*)/count(1)/count(列) 的区别与索引选择？

**四、实战关联（Java 后端视角）**（参考 spec 4.2 第 4 点）：
- MyBatis/JPA 慢查询排查思路（慢日志 + Explain + key_len + rows + Extra）
- 唯一索引 vs 业务代码校验的权衡（DB 兜底 vs 性能）
- 软删除 `is_deleted` 加索引导致查询慢的案例与优化
- 关联 `framework/spring-framework`：`@Transactional` 与索引选择关系（事务内统计信息可能不准）

**五、系统设计案例**（参考 spec 4.2 第 5 点）：
- 案例 1：亿级用户表如何设计索引与分页查询——3 分钟标准答法（聚簇主键选自增 → 二级索引覆盖 → 深分页用游标/延迟关联 → 考虑分表）+ 追问链 3 条
- 案例 2：订单表按 status 查询很慢怎么办——追问链（status 基数低 → 建索引无效 → 联合索引（status, create_time）→ 覆盖索引 → 分表）

- [ ] **Step 2: 体量与结构校验**

Run: `wc -l middleware/mysql/01-index/index-and-optimization.md`
Expected: 600-900 行。

Run: `grep -c '^## ' middleware/mysql/01-index/index-and-optimization.md`
Expected: 5（五个二级标题：一~五）。

Run: `grep '一句话定位\|面试热度\|返回.*MySQL 知识图谱' middleware/mysql/01-index/index-and-optimization.md`
Expected: 头部三行齐全。

Run: `grep -c 'mermaid' middleware/mysql/01-index/index-and-optimization.md`
Expected: ≥ 3（B+树结构图、回表时序图、ICP 对比图）。

- [ ] **Step 3: 回填 README 进度标记**

把 `middleware/mysql/README.md` 导航表第一行末尾的 `⬜` 改为 `✅`。

- [ ] **Step 4: 提交**

```bash
git add middleware/mysql/01-index/index-and-optimization.md middleware/mysql/README.md
git commit -m "docs(mysql): 新增索引原理与优化"
```

---

## Task 3: 02-transaction/transaction-and-mvcc.md（事务与 MVCC）

**Files:**
- Create: `middleware/mysql/02-transaction/transaction-and-mvcc.md`

**Interfaces:**
- Consumes: `middleware/mysql/README.md` 导航链接
- Produces: `./02-transaction/transaction-and-mvcc.md`，README 导航表第二行链接可达

**核心考点**（spec 4.3）：ACID、隔离级别、MVCC、ReadView、Undo Chain、RR vs RC、幻读

- [ ] **Step 1: 编写文档**

**头部**：一句话定位"MVCC 是 MySQL 事务的灵魂，'讲讲 MVCC 原理'是资深面试的区分题，能讲到 ReadView 可见性算法与 Undo 版本链才算合格"

**一、概念定义**（spec 4.3 第 1 点）：
- ACID 四特性表（Atomicity/Consistency/Isolation/Durability，各自实现机制：Undo Log/业务约束/锁+MVCC/Redo Log，3 列 4 行）
- 并发问题四件套：脏读、不可重复读、幻读、丢失更新（定义+示例 SQL）
- 四种隔离级别表（READ UNCOMMITTED/READ COMMITTED/REPEATABLE READ/SERIALIZABLE，分别解决什么问题，3 列 4 行）
- MySQL 默认隔离级别：RR（可重复读），RR 下通过 Next-Key Lock 解决幻读
- 范式与反范式（1NF/2NF/3NF/BCNF，简要带过）

**二、原理与流程**（spec 4.3 第 2 点，含 mermaid 图与源码路径）：
- **MVCC 多版本并发控制**：
  - 每行隐藏列：`DB_TRX_ID`（6 字节事务 ID）、`DB_ROLL_PTR`（7 字节回滚指针指向 undo log）、`DB_ROW_ID`（6 字节行 ID，无主键时用）
  - Undo Log 版本链：每次更新生成 undo log，通过 `DB_ROLL_PTR` 串联成链表，用 mermaid flowchart 画版本链结构
  - ReadView（读视图）：4 个核心字段（`creator_trx_id`/`m_ids`活跃事务ID列表/`min_trx_id`/`max_trx_id`）
  - 可见性判断算法：访问某行的 undo 链，逐版本判断 trx_id 与 ReadView 的关系（< min_trx_id 可见、>= max_trx_id 不可见、在 m_ids 中不可见、否则可见），用 mermaid flowchart 画判断流程
- **RC vs RR 的 ReadView 生成时机差异**：
  - RC：每次 SELECT 都生成新 ReadView → 每次能看到最新已提交数据（不可重复读）
  - RR：事务第一次 SELECT 生成 ReadView，后续复用 → 可重复读
  - 用 mermaid sequenceDiagram 展示 RC 和 RR 下事务 A、B 的可见性差异（两个并发事务场景）
- **快照读 vs 当前读**：
  - 快照读：普通 SELECT，走 MVCC
  - 当前读：SELECT ... FOR UPDATE / UPDATE / DELETE / INSERT，读最新版本 + 加锁
  - RR 下 `SELECT * FROM t WHERE id=1` 与 `SELECT ... FOR UPDATE` 的差异对比表
- **幻读的解决**：
  - 快照读通过 MVCC 自然避免幻读
  - 当前读通过 Next-Key Lock（Gap + Record Lock）避免幻读
  - 幻读的特殊场景：先快照读后当前读，或事务中途 commit 后再次快照读（举例 SQL）
- 关键源码路径：`storage/innobase/read/read0read.cc`（ReadView）、`storage/innobase/trx/trx0undo.cc`（Undo Chain）

**三、高频追问**（spec 4.3 第 3 点，6 题）：
- Q1: MVCC 解决了什么问题？Undo Log 版本链怎么工作？
- Q2: RR 下幻读完全解决了吗？举一个还能幻读的例子
- Q3: RC 和 RR 的 ReadView 生成时机差异？为什么 RC 叫不可重复读？
- Q4: 为什么 MySQL 默认用 RR 而不是 RC？（历史：主从复制依赖 binlog statement 格式）
- Q5: 8.0 之后为什么很多公司改用 RC？（binlog row 格式为主、减少锁范围、减少死锁）
- Q6: 长事务为什么危险？（undo 链无法回收、占用表空间、历史版本堆积）

**四、实战关联（Java 后端视角）**（spec 4.3 第 4 点）：
- 关联 `framework/spring-framework`：`@Transactional` 传播行为（REQUIRED/REQUIRES_NEW/NESTED）与 MySQL 事务关系
- Spring 声明式事务失效场景：方法非 public、自调用（AOP 代理不生效）、异常被 catch、`rollbackFor` 未配置
- 长事务排查：`information_schema.innodb_trx` 查询事务时长、Undo Log 体积
- 读写分离场景下：主从延迟导致的事务内"读不到刚插入的数据"

**五、系统设计案例**（spec 4.3 第 5 点）：
- 案例 1：转账场景的并发安全设计——3 分钟答法（事务 + 行锁 + 余额校验 + 幂等）+ 追问链
- 案例 2：库存扣减超卖怎么办——追问链（SELECT FOR UPDATE → 乐观锁版本号 → Redis 预扣 → 分段锁）

- [ ] **Step 2: 体量与结构校验**

Run: `wc -l middleware/mysql/02-transaction/transaction-and-mvcc.md`，Expected: 600-900 行。
Run: `grep -c '^## ' middleware/mysql/02-transaction/transaction-and-mvcc.md`，Expected: 5。
Run: `grep '一句话定位\|面试热度\|返回.*MySQL 知识图谱' middleware/mysql/02-transaction/transaction-and-mvcc.md`，Expected: 头部三行齐全。
Run: `grep -c 'mermaid' middleware/mysql/02-transaction/transaction-and-mvcc.md`，Expected: ≥ 3（版本链图、可见性判断流程图、RC vs RR 时序图）。

- [ ] **Step 3: 回填 README 进度标记**

把导航表第二行末尾的 `⬜` 改为 `✅`。

- [ ] **Step 4: 提交**

```bash
git add middleware/mysql/02-transaction/transaction-and-mvcc.md middleware/mysql/README.md
git commit -m "docs(mysql): 新增事务与 MVCC"
```

---

## Task 4: 03-lock/lock-mechanism.md（锁机制）

**Files:**
- Create: `middleware/mysql/03-lock/lock-mechanism.md`

**核心考点**（spec 4.4）：表级锁/行级锁三层、Record/Gap/Next-Key Lock 加锁规则、意向锁、插入意向锁、死锁、RR vs RC 锁差异

- [ ] **Step 1: 编写文档**

**头部**：一句话定位"MySQL 锁是面试难点也是分水岭，'讲讲 SELECT FOR UPDATE 锁什么'能瞬间区分背八股与懂原理的候选人"

**一、概念定义**（spec 4.4 第 1 点）：
- 全局锁 / 表级锁 / 行级锁三层结构
- 表级锁：表锁、元数据锁（MDL）、意向锁（IS/IX）
- 行级锁：Record Lock（记录锁）、Gap Lock（间隙锁）、Next-Key Lock（临键锁）、插入意向锁
- 共享锁（S）/ 排他锁（X）/ 意向共享（IS）/ 意向排他（IX）兼容矩阵表（4×4）
- 按思想分：悲观锁（SELECT FOR UPDATE）、乐观锁（版本号/CAS）

**二、原理与流程**（spec 4.4 第 2 点，含 mermaid 图与加锁规则表）：
- **Record/Gap/Next-Key Lock 的加锁规则**（重点中的重点）：
  - 唯一索引等值命中 → 退化为 Record Lock
  - 唯一索引等值未命中 → 退化为 Gap Lock
  - 非唯一索引等值 → Next-Key Lock + 下一个 Gap
  - 范围查询的加锁规则（左开右闭区间）
  - 完整加锁规则表（按索引类型 × 等值/范围 × 命中/未命中，4 列 6 行）
  - 典型案例 SQL + 加锁区间图（用 mermaid flowchart 画数轴上的加锁区间，至少 3 个案例：唯一等值命中、唯一等值未命中、非唯一等值）
- **意向锁的作用**：表锁与行锁的兼容判断；`IS` 与 `IX` 互相兼容；表 S/X 锁与行 S/X 锁的兼容矩阵
- **MDL（元数据锁）**：MDL_READ/MDL_WRITE；DDL 与 DML 冲突导致"卡住全表"的原理，用 mermaid sequenceDiagram 画 DDL 阻塞链
- **插入意向锁**：多个事务插入同一 Gap 不同位置时不互相阻塞；与 Gap Lock 的兼容矩阵
- **死锁**：产生四条件（互斥、持有并等待、不可剥夺、循环等待）；MySQL 死锁检测（`innodb_deadlock_detect`）；`SHOW ENGINE INNODB STATUS` 看死锁日志；`innodb_lock_wait_timeout` 等待超时
- **RR vs RC 下的锁差异**：RR 下 Gap Lock 防幻读，RC 下无 Gap Lock（除外键约束）；8.0 切 RC 减少锁范围
- 关键源码路径：`storage/innobase/lock/lock0lock.cc`（锁系统）

**三、高频追问**（spec 4.4 第 3 点，7 题）：
- Q1: `SELECT ... FOR UPDATE` 锁的是行还是表？
- Q2: 唯一索引等值命中加什么锁？未命中呢？
- Q3: 非唯一索引等值加什么锁？为什么多锁一个 Gap？
- Q4: 死锁怎么排查？怎么避免？
- Q5: `innodb_lock_wait_timeout` 和 `innodb_deadlock_detect` 的区别？
- Q6: 为什么 MDL 会导致全表卡住？
- Q7: 乐观锁和悲观锁怎么选？

**四、实战关联（Java 后端视角）**（spec 4.4 第 4 点）：
- Spring `@Transactional` + `SELECT FOR UPDATE` 的正确使用姿势
- 死锁案例：两个业务方法以不同顺序更新同一批行 → 死锁；解法：统一加锁顺序
- 关联 `framework/spring-framework`：`@Transactional(isolation=...)` 与 MySQL 隔离级别关系
- 分布式锁：DB 行锁 vs Redis vs ZooKeeper 的对比与选型

**五、系统设计案例**（spec 4.4 第 5 点）：
- 案例 1：秒杀场景的库存扣减如何防超卖——3 分钟答法（Redis 预扣 + DB 乐观锁兜底 + 唯一索引防重）+ 追问链
- 案例 2：两个事务互相死锁怎么排查——追问链（SHOW ENGINE INNODB STATUS → 加锁顺序分析 → 统一加锁顺序）

- [ ] **Step 2: 体量与结构校验**

Run: `wc -l middleware/mysql/03-lock/lock-mechanism.md`，Expected: 600-900 行。
Run: `grep -c '^## ' middleware/mysql/03-lock/lock-mechanism.md`，Expected: 5。
Run: `grep '一句话定位\|面试热度\|返回.*MySQL 知识图谱' middleware/mysql/03-lock/lock-mechanism.md`，Expected: 头部三行齐全。
Run: `grep -c 'mermaid' middleware/mysql/03-lock/lock-mechanism.md`，Expected: ≥ 3（加锁区间图、MDL 阻塞链、死锁循环图）。

- [ ] **Step 3: 回填 README 进度标记**

把导航表第三行末尾的 `⬜` 改为 `✅`。

- [ ] **Step 4: 提交**

```bash
git add middleware/mysql/03-lock/lock-mechanism.md middleware/mysql/README.md
git commit -m "docs(mysql): 新增锁机制"
```

---

## Task 5: 04-query/query-optimization.md（查询优化与执行计划）

**Files:**
- Create: `middleware/mysql/04-query/query-optimization.md`

**核心考点**（spec 4.5）：SQL 执行流程、Explain 全字段、JOIN Nested Loop、子查询优化、排序优化、分页优化、大表 DDL

- [ ] **Step 1: 编写文档**

**头部**：一句话定位"慢查询排查是面试高频实战题，能讲清 Explain 12 字段与深分页优化才算合格后端"

**一、概念定义**（spec 4.5 第 1 点）：
- SQL 执行流程：连接器 → 查询缓存（8.0 已移除）→ 分析器 → 优化器 → 执行器 → 存储引擎，用 mermaid flowchart 画执行流程
- 优化器的工作：基于成本的执行计划选择（全表扫描 vs 索引、JOIN 顺序、子查询改写）
- Explain 的 12 个字段全表（id/select_type/table/type/possible_keys/key/key_len/ref/rows/filtered/Extra/partitions，字段名+含义+示例值）

**二、原理与流程**（spec 4.5 第 2 点，含对比表与 mermaid 图）：
- **Explain 字段详解**：
  - `type` 访问类型级别：system > const > eq_ref > ref > range > index > ALL，每个级别含义与触发条件（7 行表 + 示例 SQL）
  - `key_len` 计算规则：单列固定长度（int=4, bigint=8, char(10) utf8mb4=40）+ 变长 + NULL 标志位；用于判断联合索引用了几列，含 3 个计算示例
  - `Extra` 关键值：`Using index`（覆盖索引）、`Using where`（Server 后过滤）、`Using index condition`（ICP）、`Using temporary`（临时表）、`Using filesort`（额外排序）、`Using join buffer`（BNL/BKA），6 行对比表
  - `rows` 与 `filtered`：优化器估算的扫描行数与过滤后剩余比例
- **JOIN 的实现**：
  - Nested Loop Join：驱动表逐行查被驱动表，被驱动表走索引，用 mermaid sequenceDiagram 画 NLJ 流程
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
  - 延迟关联：先走覆盖索引拿主键，再 JOIN 回表（含 SQL 示例）
  - 游标分页：`WHERE id > last_id ORDER BY id LIMIT 10`（要求有序且无断点）
- **大表 DDL**：
  - Online DDL 三阶段：copy（5.5 之前）、inplace（5.6+ 仍可能阻塞）、instant（8.0+ 部分操作元数据级）
  - 加列/加索引对线上影响；gh-ost / pt-osc 的影子表方案
  - DDL 期间的 MDL 锁阻塞链

**三、高频追问**（spec 4.5 第 3 点，7 题）：
- Q1: `type` 的级别有哪些？`ref` 和 `eq_ref` 区别？
- Q2: `key_len` 怎么算？有什么用？
- Q3: `Extra` 里 `Using filesort` 怎么优化？
- Q4: JOIN 时怎么选驱动表？被驱动表没索引会怎样？
- Q5: `LIMIT 1000000, 10` 怎么优化？
- Q6: 大表加索引会锁表吗？怎么办？
- Q7: `SELECT COUNT(*)` 慢怎么办？

**四、实战关联（Java 后端视角）**（spec 4.5 第 4 点）：
- MyBatis 的 `PageHelper` 深分页慢查询案例与改写
- 慢查询日志 + pt-query-digest 的排查链路
- `SELECT *` 的危害：覆盖索引失效、网络传输、序列化成本
- 关联 `framework/spring-framework`：`@Transactional(readOnly=true)` 对查询优化的意义

**五、系统设计案例**（spec 4.5 第 5 点）：
- 案例 1：慢查询排查全流程——3 分钟答法（慢日志定位 → Explain 分析 → 索引/写法/架构三层优化）+ 追问链
- 案例 2：大表加字段怎么办——追问链（instant DDL → gh-ost 影子表 → 分库分表后变更协调）

- [ ] **Step 2: 体量与结构校验**

Run: `wc -l middleware/mysql/04-query/query-optimization.md`，Expected: 600-900 行。
Run: `grep -c '^## ' middleware/mysql/04-query/query-optimization.md`，Expected: 5。
Run: `grep '一句话定位\|面试热度\|返回.*MySQL 知识图谱' middleware/mysql/04-query/query-optimization.md`，Expected: 头部三行齐全。
Run: `grep -c 'mermaid' middleware/mysql/04-query/query-optimization.md`，Expected: ≥ 2（SQL 执行流程图、NLJ 时序图）。

- [ ] **Step 3: 回填 README 进度标记**

把导航表第四行末尾的 `⬜` 改为 `✅`。

- [ ] **Step 4: 提交**

```bash
git add middleware/mysql/04-query/query-optimization.md middleware/mysql/README.md
git commit -m "docs(mysql): 新增查询优化与执行计划"
```

---

## Task 6: 05-storage/innodb-engine.md（存储引擎底层）

**Files:**
- Create: `middleware/mysql/05-storage/innodb-engine.md`

**核心考点**（spec 4.6）：InnoDB vs MyISAM、Buffer Pool 改进 LRU、Change Buffer、AHI、Doublewrite、LSN、Checkpoint、WAL、刷盘策略

- [ ] **Step 1: 编写文档**

**头部**：一句话定位"InnoDB 存储引擎底层是资深面试的区分度题，能讲清 Buffer Pool 改进 LRU 与 WAL 刷盘策略才算懂 MySQL"

**一、概念定义**（spec 4.6 第 1 点）：
- InnoDB vs MyISAM 对比表（事务/锁粒度/外键/聚簇索引/崩溃恢复/全文索引，6 列 2 行）
- InnoDB 内存架构：Buffer Pool / Change Buffer / Adaptive Hash Index / Log Buffer
- InnoDB 磁盘架构：系统表空间 / 独立表空间 / Undo 表空间 / Redo Log / 临时表空间
- InnoDB 后台线程：Master Thread / IO Thread / Purge Thread / Page Cleaner Thread

**二、原理与流程**（spec 4.6 第 2 点，含 mermaid 图与源码路径）：
- **Buffer Pool**：
  - 作用：缓存热点数据页与索引页，减少磁盘 IO
  - 结构：基于页（16KB）的 LRU 链表，改进版 young/old 两段（young 5/12、old 7/12），新页插到 old 头部，存活超 `innodb_old_blocks_time`（默认 1s）才升 young，用 mermaid flowchart 画改进 LRU 结构
  - 改进 LRU 的目的：防全表扫描冲刷热点
  - Flush List / Free List / LRU List 三链表对比表
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
  - 防止页撕裂（partial page write）：若写数据页时 crash，从 doublewrite 恢复完整副本，用 mermaid 画 doublewrite 流程
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
  - `innodb_flush_log_at_trx_commit`：0（每秒刷）/1（每次提交刷，默认）/2（每次提交写 OS Cache，每秒 fsync），3 级对比表
  - `innodb_flush_method`：O_DIRECT（绕过 OS Page Cache）/ fsync（默认）
  - `sync_binlog`：0/1/N，与 Redo 的两阶段提交配合
- 关键源码路径：`storage/innobase/buf/buf0buf.cc`（Buffer Pool）、`storage/innobase/log/log0log.cc`（Redo Log）

**三、高频追问**（spec 4.6 第 3 点，7 题）：
- Q1: Buffer Pool 的 LRU 为什么改进？怎么改进？
- Q2: Change Buffer 为什么只对二级索引有效？
- Q3: 什么是页撕裂？Doublewrite 怎么解决？
- Q4: WAL 是什么？为什么这么设计？
- Q5: `innodb_flush_log_at_trx_commit=2` 安全吗？
- Q6: `sync_binlog=1` 和 `innodb_flush_log_at_trx_commit=1` 怎么配合？
- Q7: LSN 是什么？Checkpoint 推进什么？

**四、实战关联（Java 后端视角）**（spec 4.6 第 4 点）：
- Buffer Pool 调优：生产环境 `innodb_buffer_pool_size` 一般配物理内存 60%-70%
- 关联 `java-core/jvm`：JVM 堆外内存（DirectByteBuffer）与 MySQL Buffer Pool 的内存预算协调
- 性能压测时 `innodb_flush_log_at_trx_commit=2 + sync_binlog=0` 的临时调优与风险
- JDBC `rewriteBatchedStatements` 与批量写入性能

**五、系统设计案例**（spec 4.6 第 5 点）：
- 案例 1：MySQL 宕机会丢数据吗——3 分钟答法（Redo Log WAL → binlog 两阶段提交 → crash recovery 三步）+ 追问链
- 案例 2：高并发写入场景怎么调 InnoDB 参数——追问链（Buffer Pool → 刷盘策略 → Change Buffer → IO 线程数）

- [ ] **Step 2: 体量与结构校验**

Run: `wc -l middleware/mysql/05-storage/innodb-engine.md`，Expected: 600-900 行。
Run: `grep -c '^## ' middleware/mysql/05-storage/innodb-engine.md`，Expected: 5。
Run: `grep '一句话定位\|面试热度\|返回.*MySQL 知识图谱' middleware/mysql/05-storage/innodb-engine.md`，Expected: 头部三行齐全。
Run: `grep -c 'mermaid' middleware/mysql/05-storage/innodb-engine.md`，Expected: ≥ 2（改进 LRU 结构、doublewrite 流程）。

- [ ] **Step 3: 回填 README 进度标记**

把导航表第五行末尾的 `⬜` 改为 `✅`。

- [ ] **Step 4: 提交**

```bash
git add middleware/mysql/05-storage/innodb-engine.md middleware/mysql/README.md
git commit -m "docs(mysql): 新增存储引擎底层"
```

---

## Task 7: 06-log/log-system.md（日志体系）

**Files:**
- Create: `middleware/mysql/06-log/log-system.md`

**核心考点**（spec 4.7）：Undo Log、Redo Log、Binlog、Relay Log、两阶段提交、Crash Recovery、组提交、并行复制

- [ ] **Step 1: 编写文档**

**头部**：一句话定位"MySQL 日志体系是面试热点也是理解 crash recovery 与主从复制的根，'讲讲两阶段提交'是资深面试必问"

**一、概念定义**（spec 4.7 第 1 点）：
- 四大日志总览表：Undo Log / Redo Log / Binlog / Relay Log（作用/产生层/内容/写入时机/生命周期，5 列 4 行）
- 物理日志（Redo，页级别物理修改）vs 逻辑日志（Binlog，SQL/行变更）vs 逻辑-物理混合（Undo）

**二、原理与流程**（spec 4.7 第 2 点，含 mermaid 时序图与源码路径）：
- **Undo Log（回滚日志）**：
  - 作用：事务回滚 + MVCC 版本链
  - 内容：记录修改前的行旧值（逻辑日志，行级）
  - 存放：Undo 表空间，`innodb_undo_tablespaces`、`innodb_undo_log_truncate`（8.0 自动 truncate）
  - 活跃 Undo 与 History 链；事务提交后不再活跃但 MVCC 可能还要用，由 Purge 线程清理
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
  - 格式：STATEMENT（记 SQL）/ROW（记行变更，默认）/MIXED，3 种格式对比表
  - 写入模式：`sync_binlog` 0/1/N；`binlog_group_commit_sync_delay` 组提交
  - 与 Redo Log 的区别对比表（层/内容/物理·逻辑/写入方式/生命周期，5 列 2 行）
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
- 关键源码路径：`storage/innobase/trx/trx0undo.cc`（Undo）、`storage/innobase/log/log0log.cc`（Redo）、`sql/binlog.cc`（Binlog）

**三、高频追问**（spec 4.7 第 3 点，7 题）：
- Q1: Undo Log 和 Redo Log 有什么区别？
- Q2: Binlog 和 Redo Log 有什么区别？为什么需要两个？
- Q3: 两阶段提交是什么？为什么需要？
- Q4: crash recovery 的逻辑是什么？
- Q5: 长事务为什么导致 Undo 膨胀？
- Q6: `sync_binlog=1` 和 `innodb_flush_log_at_trx_commit=1` 必须都配吗？
- Q7: 从库延迟怎么解决？

**四、实战关联（Java 后端视角）**（spec 4.7 第 4 点）：
- Canal 原理：伪装 MySQL 从库，解析 Binlog 推送下游；ROW 格式的必要性
- 数据恢复：`mysqlbinlog --start-datetime --stop-datetime` 解析恢复
- 大事务导致 Binlog 单事务过大的排查（`binlog_rows_query_events`）
- 关联 `framework/spring-framework`：事务传播行为与两阶段提交的边界

**五、系统设计案例**（spec 4.7 第 5 点）：
- 案例 1：MySQL 宕机后数据怎么恢复——3 分钟答法（crash recovery 三步：Redo 重放 → Undo 回滚 → Binlog 补齐）+ 追问链
- 案例 2：主从延迟导致业务异常怎么设计——追问链（半同步 → 并行复制 → 读写分离策略 → 强制走主）

- [ ] **Step 2: 体量与结构校验**

Run: `wc -l middleware/mysql/06-log/log-system.md`，Expected: 600-900 行。
Run: `grep -c '^## ' middleware/mysql/06-log/log-system.md`，Expected: 5。
Run: `grep '一句话定位\|面试热度\|返回.*MySQL 知识图谱' middleware/mysql/06-log/log-system.md`，Expected: 头部三行齐全。
Run: `grep -c 'mermaid' middleware/mysql/06-log/log-system.md`，Expected: ≥ 1（两阶段提交时序图）。

- [ ] **Step 3: 回填 README 进度标记**

把导航表第六行末尾的 `⬜` 改为 `✅`。

- [ ] **Step 4: 提交**

```bash
git add middleware/mysql/06-log/log-system.md middleware/mysql/README.md
git commit -m "docs(mysql): 新增日志体系"
```

---

## Task 8: 07-architecture/ha-and-sharding.md（架构与高可用）

**Files:**
- Create: `middleware/mysql/07-architecture/ha-and-sharding.md`

**核心考点**（spec 4.8）：主从复制、半同步、MGR、读写分离、分库分表、分布式 ID、分布式事务、高可用选型

- [ ] **Step 1: 编写文档**

**头部**：一句话定位"架构与高可用是资深面试区分度题，'订单系统怎么分库分表'能考察从分片键到分布式事务的全链路思维"

**一、概念定义**（spec 4.8 第 1 点）：
- 主从复制：异步复制、半同步复制、全同步复制（3 种对比表）
- 读写分离：主写从读、从库延迟容忍
- 分库分表：垂直分库、水平分表、垂直分表（3 种对比表）
- 高可用方案：MHA、Orchestrator、MGR、MySQL InnoDB Cluster（4 种对比表）
- 中间件边界：ShardingSphere、MyCat、Vitess、ProxySQL（仅标注边界，不展开）

**二、原理与流程**（spec 4.8 第 2 点，含 mermaid 时序图与对比表）：
- **主从复制原理**：
  - 三个线程：主库 Binlog Dump Thread、从库 IO Thread、SQL Thread
  - 流程时序图（mermaid sequenceDiagram）：主库写 Binlog → Dump Thread 推送 → IO Thread 写 Relay Log → SQL Thread 回放
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
  - 水平分表：按 hash/range/时间分片（3 种分片策略对比表）
  - 分片键选择：高频查询条件、避免数据倾斜
  - 全局唯一 ID：UUID、Snowflake、号段模式（Leaf），3 种对比表
  - 跨片查询：广播、汇总表、ES 宽表补齐
  - 分布式事务：XA、TCC、本地消息表、Saga，4 种对比表
- **高可用方案选型对比表**：
  - MHA：基于 SSH 的 failover，已老旧
  - Orchestrator：Go 写的拓扑管理，活跃
  - MGR：原生集群，适合金融场景
  - 中间件 + 分库分表：ShardingSphere 等
- 交叉引用：分布式锁、幂等的 Redis 方案对照（`middleware/README.md` redis 待建）；本地消息表与 Kafka 互补（`middleware/README.md` kafka 待建）

**三、高频追问**（spec 4.8 第 3 点，7 题）：
- Q1: 主从复制原理？延迟怎么解决？
- Q2: 半同步复制是什么？什么时候降级为异步？
- Q3: 分库分表怎么选分片键？跨片查询怎么办？
- Q4: 分布式 ID 方案有哪些？Snowflake 时钟回拨怎么办？
- Q5: 分布式事务怎么选？（强一致 XA vs 最终一致消息表）
- Q6: MGR 和半同步怎么选？
- Q7: 读写分离如何解决主从延迟？

**四、实战关联（Java 后端视角）**（spec 4.8 第 4 点）：
- ShardingSphere-JDBC 与 ShardingSphere-Proxy 的选型
- Spring Boot 多数据源配置：`@DS` 注解、`AbstractRoutingDataSource`
- 全局唯一 ID 在订单系统中的实践（Snowflake + 业务前缀）
- 关联 `framework/spring-framework`：`@Transactional` 与 XA 分布式事务的集成

**五、系统设计案例**（spec 4.8 第 5 点）：
- 案例 1：订单系统分库分表方案设计——3 分钟答法（按 user_id hash 分表 → Snowflake ID → 异步消息保证最终一致）+ 追问链
- 案例 2：高可用 MySQL 集群怎么设计——追问链（一主多从 + 半同步 → MGR/Orchestrator → 跨机房 → 分库分表）

- [ ] **Step 2: 体量与结构校验**

Run: `wc -l middleware/mysql/07-architecture/ha-and-sharding.md`，Expected: 600-900 行。
Run: `grep -c '^## ' middleware/mysql/07-architecture/ha-and-sharding.md`，Expected: 5。
Run: `grep '一句话定位\|面试热度\|返回.*MySQL 知识图谱' middleware/mysql/07-architecture/ha-and-sharding.md`，Expected: 头部三行齐全。
Run: `grep -c 'mermaid' middleware/mysql/07-architecture/ha-and-sharding.md`，Expected: ≥ 1（主从复制时序图）。

- [ ] **Step 3: 回填 README 进度标记**

把导航表第七行末尾的 `⬜` 改为 `✅`。

- [ ] **Step 4: 提交**

```bash
git add middleware/mysql/07-architecture/ha-and-sharding.md middleware/mysql/README.md
git commit -m "docs(mysql): 新增架构与高可用"
```

---

## Task 9: 08-interview-qa.md（面试 Q&A 速答）

**Files:**
- Create: `middleware/mysql/08-interview-qa.md`

**核心考点**（spec 4.9）：41 题速答 + 连环套问思维导图（按主题串联）

- [ ] **Step 1: 编写文档**

参考 `ops/docker/09-interview-qa.md` 与 `ops/linux/10-interview-qa.md` 的风格：按主题分类，每题 3-5 句要点速答，末尾加 `**关联**：→ [对应主题文档](./0X-xxx/xxx.md)` 链接。连环追问题在题号后标 🔗。

**头部**：
```
# 跨主题高频面试 Q&A

> **一句话定位**：面试前冲刺用，41 题速答串联各主题，附连环套问思维导图。
> **面试热度**：⭐⭐⭐⭐⭐
> **返回**：[MySQL 知识图谱](../README.md)
```

**使用说明**（参考 docker/linux Q&A 风格）：
- 全部 41 题按主题分类，每题 3-5 句要点速答，末尾 **关联** 链接指向对应主题文档的详细推导
- 连环追问题在题号后标注 🔗，配合文末「连环套问思维导图」把握面试官的追问路径
- 建议先盖住答案自答，再对照要点查漏，最后跳转关联文档补全原理

**题目分组**（参考 spec 4.9 第 2 点，共 41 题）：

1. **一、索引篇（8 题）**：Q1-Q8
   - Q1: MySQL 索引底层是什么结构？为什么用 B+树？🔗
   - Q2: 聚簇索引和二级索引的区别？🔗
   - Q3: 什么是回表？怎么避免？🔗
   - Q4: 什么是覆盖索引？🔗
   - Q5: 最左前缀匹配是什么？🔗
   - Q6: 索引下推 ICP 是什么？🔗
   - Q7: 索引失效有哪些场景？🔗
   - Q8: 主键选自增 ID 还是 UUID？为什么？🔗

2. **二、事务与 MVCC 篇（6 题）**：Q9-Q14
   - Q9: ACID 是什么？各自怎么实现？🔗
   - Q10: 并发问题有哪些？分别对应什么隔离级别？🔗
   - Q11: MVCC 原理是什么？ReadView 怎么判断可见性？🔗
   - Q12: RR 下幻读解决了吗？🔗
   - Q13: RC 和 RR 的 ReadView 生成时机差异？🔗
   - Q14: 为什么 MySQL 默认 RR？8.0 后为什么很多公司改 RC？🔗

3. **三、锁机制篇（6 题）**：Q15-Q20
   - Q15: MySQL 有哪些锁？表级、行级？🔗
   - Q16: Record/Gap/Next-Key Lock 分别是什么？🔗
   - Q17: `SELECT FOR UPDATE` 锁的是行还是表？🔗
   - Q18: 唯一索引等值命中加什么锁？未命中呢？🔗
   - Q19: 死锁怎么排查与避免？🔗
   - Q20: 乐观锁和悲观锁怎么选？🔗

4. **四、查询优化篇（6 题）**：Q21-Q26
   - Q21: Explain 各字段含义？`type` 有哪些级别？🔗
   - Q22: `key_len` 怎么算？有什么用？🔗
   - Q23: `Extra` 里 `Using filesort` 怎么优化？🔗
   - Q24: JOIN 时怎么选驱动表？🔗
   - Q25: `LIMIT 1000000, 10` 怎么优化？🔗
   - Q26: 大表加字段/索引会锁表吗？怎么办？🔗

5. **五、存储引擎篇（5 题）**：Q27-Q31
   - Q27: InnoDB 和 MyISAM 区别？🔗
   - Q28: Buffer Pool 的 LRU 为什么改进？🔗
   - Q29: Change Buffer 是什么？为什么只对二级索引有效？🔗
   - Q30: Doublewrite 解决什么问题？🔗
   - Q31: WAL 是什么？为什么这么设计？🔗

6. **六、日志体系篇（5 题）**：Q32-Q36
   - Q32: Undo Log 和 Redo Log 区别？🔗
   - Q33: Binlog 和 Redo Log 区别？为什么需要两个？🔗
   - Q34: 两阶段提交是什么？为什么需要？🔗
   - Q35: crash recovery 怎么保证数据不丢？🔗
   - Q36: 主从复制原理？延迟怎么解决？🔗

7. **七、架构与高可用篇（5 题）**：Q37-Q41
   - Q37: 读写分离如何解决主从延迟？🔗
   - Q38: 半同步复制是什么？什么时候降级？🔗
   - Q39: 分库分表怎么选分片键？跨片查询怎么办？🔗
   - Q40: 分布式 ID 方案有哪些？Snowflake 时钟回拨怎么办？🔗
   - Q41: 分布式事务怎么选？🔗

每题格式示例（参考 docker Q&A）：
```markdown
### Q1: MySQL 索引底层是什么结构？为什么用 B+树？🔗

**答**：InnoDB 索引底层是 B+树。B+树的特点是：非叶子节点只存键值，单页（16KB）可存上千个键，3-4 层即可存千万行数据；叶子节点存完整数据并用双向链表连接，范围查询只需遍历叶子链表。对比红黑树树高过高（log₂(1000万)≈24），磁盘 IO 次数多；Hash 索引不支持范围查询；跳表虽 O(logN) 但无磁盘页优化。InnoDB 选 B+树是因为它兼顾了树矮（减少磁盘 IO）、范围查询高效（叶子链表）、有序（支持排序）三大需求。

**关联**：→ [索引原理与优化](./01-index/index-and-optimization.md)
```

**连环套问思维导图**（mermaid mindmap）：6 条完整追问链（参考 spec 4.9 第 2 点第 8 项）：
- 索引链：索引底层结构 → B+树 → 聚簇 vs 二级 → 回表 → 覆盖索引 → 最左匹配 → ICP → 索引失效
- 事务链：ACID → 隔离级别 → MVCC → ReadView → RR vs RC → 幻读 → 为什么默认 RR
- 锁链：表锁 vs 行锁 → Record/Gap/Next-Key → 加锁规则 → 死锁排查 → 乐观 vs 悲观
- 日志链：四大日志 → Undo vs Redo → Binlog vs Redo → 两阶段提交 → crash recovery → 主从复制
- 优化链：慢查询 → Explain → type/key_len/Extra → JOIN 驱动表 → 深分页 → 大表 DDL
- 架构链：主从复制 → 半同步 → MGR → 读写分离 → 分库分表 → 分布式 ID → 分布式事务

- [ ] **Step 2: 体量与结构校验**

Run: `wc -l middleware/mysql/08-interview-qa.md`，Expected: 500-700 行。
Run: `grep -c '^### Q' middleware/mysql/08-interview-qa.md`，Expected: ≥ 41。
Run: `grep -c '关联.*\.md' middleware/mysql/08-interview-qa.md`，Expected: ≥ 41（每题都有关联链接）。
Run: `grep '连环套问思维导图\|mindmap' middleware/mysql/08-interview-qa.md`，Expected: 末尾含思维导图。

- [ ] **Step 3: 全模块链接可达性校验**

```bash
# 校验 README 导航表所有链接可达
for link in $(grep -oP '\./[^)]+' middleware/mysql/README.md); do test -f "middleware/mysql/${link#./}" || echo "BROKEN: $link"; done
# 校验 Q&A 文档所有关联链接可达
for link in $(grep -oP '\./[^)]+' middleware/mysql/08-interview-qa.md); do test -f "middleware/mysql/${link#./}" || echo "BROKEN: $link"; done
```
Expected: 无 BROKEN 输出（所有链接可达）。

- [ ] **Step 4: 回填 README 进度标记**

把导航表第八行末尾的 `⬜` 改为 `✅`。

- [ ] **Step 5: 提交**

```bash
git add middleware/mysql/08-interview-qa.md middleware/mysql/README.md
git commit -m "docs(mysql): 新增跨主题高频面试 Q&A"
```

---

## Task 10: 全模块验收

**Files:**
- Verify: `middleware/mysql/` 整个目录

- [ ] **Step 1: 文档清单完整性校验**

```bash
ls middleware/mysql/README.md middleware/mysql/01-index/index-and-optimization.md middleware/mysql/02-transaction/transaction-and-mvcc.md middleware/mysql/03-lock/lock-mechanism.md middleware/mysql/04-query/query-optimization.md middleware/mysql/05-storage/innodb-engine.md middleware/mysql/06-log/log-system.md middleware/mysql/07-architecture/ha-and-sharding.md middleware/mysql/08-interview-qa.md
```
Expected: 9 个文件全部存在。

- [ ] **Step 2: 每份主题文档五段式校验**

```bash
for f in middleware/mysql/0*/*.md; do
  echo "=== $f ==="
  grep -c '^## ' "$f"  # 应为 5
  grep '一句话定位\|面试热度\|返回.*MySQL 知识图谱' "$f"  # 头部三行
  wc -l "$f"  # 600-900 行
done
```
Expected: 7 份主题文档各 5 段、头部三行齐全、600-900 行。

- [ ] **Step 3: 全模块链接可达性校验**

```bash
# 所有文档间的链接都可达
grep -rP '\[.+\]\(\./[^)]+\)' middleware/mysql/ --include='*.md' | grep -oP '\./[^)]+' | sort -u | while read link; do
  base=$(dirname "${link}")
  target=$(basename "${link}")
  test -f "middleware/mysql/${base}/${target}" || test -f "middleware/mysql/${link#./}" || echo "BROKEN: $link"
done
```
Expected: 无 BROKEN 输出。

- [ ] **Step 4: README 知识图谱与导航表完整性校验**

```bash
grep -c '^|' middleware/mysql/README.md  # 导航表行数（含表头）
grep 'mindmap' middleware/mysql/README.md  # 知识图谱存在
grep -c '✅' middleware/mysql/README.md  # 进度标记
```
Expected: 导航表 8+ 行，知识图谱含 mermaid mindmap，8 个 ✅（全部完成）。

- [ ] **Step 5: Q&A 题目数与关联链接校验**

```bash
grep -c '^### Q' middleware/mysql/08-interview-qa.md  # 题目数
grep -c '关联.*\.md' middleware/mysql/08-interview-qa.md  # 关联链接数
grep 'mindmap' middleware/mysql/08-interview-qa.md  # 思维导图
```
Expected: ≥ 41 题，≥ 41 个关联链接，含 mindmap 思维导图。

- [ ] **Step 6: middleware/README.md 与根 README.md 同步校验**

```bash
grep 'mysql' middleware/README.md  # mysql 行已更新为链接
grep -A2 '## middleware' README.md  # 根 README 已同步标注
```
Expected: middleware/README.md 含 mysql 链接行，根 README middleware 段含 MySQL 链接。

- [ ] **Step 7: 最终提交（如有修复）**

如有任何修复，提交：
```bash
git add middleware/mysql/ middleware/README.md README.md
git commit -m "docs(mysql): MySQL 模块全文档验收修复"
```

无修复则跳过。

---

## Self-Review

完成计划编写后逐项检查：

1. **Spec 覆盖**：
   - spec 第二章目录结构 9 份文档 → Task 1-9 各对应一份（Task 1 README + Task 2-8 七份主题 + Task 9 Q&A）。✅
   - spec 第三章统一风格约定（五段式结构、头部模板、Q&A 结构）→ Global Constraints + 各 Task Step 1。✅
   - spec 第四章各文档内容设计 → 每个 Task 的"核心考点"与"内容要点"段。✅
   - spec 第五章交叉引用与 Java 模块关联 → Task 1 README 关联表 + 各 Task 第四段"实战关联"。✅
   - spec 第六章 README 更新规则 → Global Constraints + Task 1 Step 2-3。✅
   - spec 第七章验收标准 → Task 10。✅

2. **占位符扫描**：无 TBD/TODO/实现细节缺失。每段内容要点具体到"对比表列数行数/mermaid 图类型/源码路径/案例场景/追问问题清单"。✅

3. **一致性检查**：
   - 文件路径在 Task 间的引用一致（`./01-index/index-and-optimization.md` 在 README、Q&A、各主题"关联"链接中一致）。✅
   - 五段式结构在 Global Constraints、Task 模板、各 Task Step 1 内容要点、Task 10 Step 2 校验中一致。✅
   - 头部三行格式在 Global Constraints、各 Task Step 1 头部、Task 10 Step 2 校验中一致。✅
   - 体量 600-900 行（主题）/ 500-700 行（Q&A）/ 150-250 行（README）在 Global Constraints、各 Task Step 2、Task 10 Step 2 一致。✅
   - 进度标记 `⬜ → ✅` 在 Task 1 创建、Task 2-9 回填、Task 10 校验一致。✅
   - 提交规范 `docs(mysql):` 在 Global Constraints 与各 Task Step 4/5 一致。✅

无修改需要。
