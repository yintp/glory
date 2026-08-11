# 跨主题高频面试 Q&A

> **一句话定位**：面试前冲刺用，41 题速答串联各主题，附连环套问思维导图
> **面试热度**：⭐⭐⭐⭐⭐
> **返回**：[Redis 知识图谱](../README.md)

---

## 使用说明

- 全部 41 题按主题分类，每题 3-5 句要点速答，末尾 **关联** 链接指向对应主题文档的详细推导。
- 连环追问题在题号后标注 🔗，配合文末「连环套问思维导图」把握面试官的追问路径。
- 建议先盖住答案自答，再对照要点查漏，最后跳转关联文档补全原理。
- 版本基线 Redis 7.x，5.x/6.x 仅作差异对比。
- 答案只给「要点 + 关键数字 + 为什么」，不展开推导——推导在关联文档里。

**各篇题目数与关联文档**：

| 篇章 | 题目数 | 关联文档 |
|------|--------|---------|
| 一、数据结构篇 | 8 题（Q1-Q8） | [数据结构与对象编码](./01-data-structure/data-structure-and-encoding.md) |
| 二、持久化篇 | 6 题（Q9-Q14） | [持久化机制](./02-persistence/persistence-mechanism.md) |
| 三、内存与淘汰篇 | 6 题（Q15-Q20） | [内存管理与淘汰策略](./03-memory/memory-and-eviction.md) |
| 四、事件与并发篇 | 5 题（Q21-Q25） | [事件与并发模型](./04-event/event-and-concurrency.md) |
| 五、复制与集群篇 | 6 题（Q26-Q31） | [复制与集群](./05-replication/replication-and-cluster.md) |
| 六、缓存实战与分布式锁篇 | 6 题（Q32-Q37） | [缓存实战与分布式锁](./06-cache-practice/cache-and-distributed-lock.md) |
| 七、高可用与运维篇 | 4 题（Q38-Q41） | [高可用与运维](./07-ops/ha-and-ops.md) |
| 合计 | **41 题** | 7 份主题文档 |

---

## 一、数据结构篇（8 题）

### Q1: Redis 有几种数据类型？底层数据结构是什么？🔗

**答**：Redis 有 5 种基础数据类型：String（字符串）、List（列表）、Hash（哈希）、Set（集合）、ZSet（有序集合），加上 5.0 引入的 Stream（流）共 6 种。底层数据结构有多种：String 用 SDS（int/embstr/raw 三种编码）、List 用 quicklist + listpack、Hash 用 listpack 或 hashtable、Set 用 intset 或 hashtable、ZSet 用 listpack 或 skiplist + dict 双结构、Stream 用 radix tree。Redis 通过 redisObject 的 type 与 encoding 字段解耦"接口"与"实现"，小数据用紧凑结构（listpack 连续内存省空间）、大数据用高效结构（hashtable O(1) 查找），编码转换由阈值参数控制（如 `hash-max-listpack-entries=128`）。

**关联**：→ [数据结构与对象编码](./01-data-structure/data-structure-and-encoding.md)

### Q2: String 底层 SDS 为什么不直接用 C 字符串？🔗

**答**：SDS（Simple Dynamic String）相比 C 字符串有三大优势：①**O(1) 获取长度**——SDS 头部 `len` 字段直接记录长度，C 字符串需 O(n) 遍历到 `\0`；②**二进制安全**——SDS 用 `len` 判断结束而非 `\0`，数据中可包含 `\0`（如图片字节流），C 字符串遇到 `\0` 即截断；③**扩容预分配**——SDS 修改时预分配 `min(len*2, 1MB)` 空间，减少 realloc 次数，C 字符串每次追加都需 realloc。SDS 还有惰性释放（缩容不立即 free，留后续追加用）。SDS 五种子类型 `sdshdr5/8/16/32/64` 按长度选型，短字符串用小头部省内存。

**关联**：→ [数据结构与对象编码](./01-data-structure/data-structure-and-encoding.md)

### Q3: 为什么 Redis 用跳表不用红黑树？🔗

**答**：跳表相比红黑树有三大优势：①**范围查询高效**——ZSet 的 `ZRANGE`/`ZRANGEBYSCORE` 需要范围查询，跳表叶子节点按顺序连接成链表，找到起点后顺链遍历即可 O(log n) + O(k)，红黑树需中序遍历回溯；②**实现简单**——跳表核心代码约 200 行，红黑树上千行（旋转/染色逻辑复杂）；③**内存灵活**——跳表每节点按层数分配指针，期望层高 1.33（`p=0.25`），内存开销可控。跳表查询复杂度 O(log n) 与红黑树相同。内存数据库无磁盘 IO，跳表无需像 B+树压缩到页内。`ZSKIPLIST_MAXLEVEL=32` 足够覆盖亿级元素。

**关联**：→ [数据结构与对象编码](./01-data-structure/data-structure-and-encoding.md)

### Q4: 渐进式 rehash 过程中，查询怎么走？增删改怎么走？🔗

**答**：dict 字典维护 `dictht[2]` 双哈希表，rehash 时 `rehashidx` 逐步迁移。**查询**：先查 ht[0]，未命中再查 ht[1]（两个表都查）。**增删改**：增（写）只写 ht[1]（保证 ht[0] 只减不增）；删/改在两个表都操作（先 ht[0] 再 ht[1]）。每次增删改查时顺带迁移 1 个桶（`dictRehash(d, 1)`），将 rehash 分摊到每次操作避免阻塞。为什么不能一次性 rehash？10 万元素一次性 rehash 约 10ms 阻塞，单线程下不可接受。负载因子 `used/size` 达 `dict_force_resize_ratio=5` 触发 rehash。

**关联**：→ [数据结构与对象编码](./01-data-structure/data-structure-and-encoding.md)

### Q5: listpack 为什么替代 ziplist？连锁更新是什么？🔗

**答**：ziplist 的 `prev_entry_length` 字段记录前一个 entry 的长度，当前一个 entry 长度变化（如从 < 254 字节变到 ≥ 254 字节，`prev_entry_length` 从 1 字节变 5 字节）时，会导致当前 entry 自身长度变化，进而影响后一个 entry 的 `prev_entry_length`，引发**连锁更新**——最坏 O(n²) 逐个重新分配。listpack 用 `backlen` 字段反向遍历，每个 entry 独立记录自身长度，不再依赖前一个 entry，规避连锁更新。7.0 起 listpack 全面替代 ziplist，List/Hash/ZSet 的小规模编码都用 listpack。

**关联**：→ [数据结构与对象编码](./01-data-structure/data-structure-and-encoding.md)

### Q6: ZSet 为什么用 skiplist + dict 两个结构？🔗

**答**：ZSet 用 skiplist + dict 双结构互补：**dict** 提供 O(1) 查 score（`ZSCORE` 命令），key 是 member，value 是 score；**skiplist** 提供 O(log n) 范围查询（`ZRANGE`/`ZRANGEBYSCORE`），按 score 排序。两者共用元素节点——skiplist node 内含 `ele` 与 `score`，dict 的 value 指向 skiplist node。为什么不能只用 skiplist？`ZSCORE` 需 O(log n) 遍历跳表。为什么不能只用 dict？dict 无序无法范围查询。双结构各取所长，内存开销可接受（指针共享）。

**关联**：→ [数据结构与对象编码](./01-data-structure/data-structure-and-encoding.md)

### Q7: Redis 的共享对象池是什么？为什么字符串不共享？🔗

**答**：Redis 共享对象池是 0-9999 的整数对象预先创建共享，多个 key 指向同一个 redisObject 节省内存。条件：`server.maxmemory_policy` 不含 LFU 时才启用（LFU 需独立记录每个 key 的访问频率，共享会干扰统计）。为什么字符串不共享？相等判断整数 O(1) 可直接比较，字符串相等判断需 O(n) 逐字节比较，共享前先判断相等反而比直接创建更贵，得不偿失。共享对象池在 `objectEncoding` 为 int 时生效，小整数频繁出现（如计数器、ID）可显著省内存。

**关联**：→ [数据结构与对象编码](./01-data-structure/data-structure-and-encoding.md)

### Q8: 各数据类型的编码转换阈值是什么？🔗

**答**：①List：listpack → quicklist，`list-max-listpack-entries=128`、`list-max-listpack-size=64`；②Hash：listpack → hashtable，`hash-max-listpack-entries=128`、`hash-max-listpack-value=64`；③ZSet：listpack → skiplist+dict，`zset-max-listpack-entries=128`、`zset-max-listpack-value=64`；④Set：intset → hashtable，`set-max-intset-entries=512`。设计思路：小数据用紧凑结构（listpack 连续内存无指针开销、intset 紧凑整数数组）省内存，大数据用高效结构（hashtable O(1) 查找、skiplist 范围查询）保性能。阈值可通过 `CONFIG SET` 动态调整。

**关联**：→ [数据结构与对象编码](./01-data-structure/data-structure-and-encoding.md)

---

## 二、持久化篇（6 题）

### Q9: RDB 和 AOF 怎么选？生产用哪个？🔗

**答**：生产推荐**混合持久化**（`aof-use-rdb-preamble yes`，7.x 默认开启）+ `appendfsync everysec`——AOF 重写时子进程生成 RDB 格式头 + 增量 AOF 命令尾，恢复时先加载 RDB（快）再回放 AOF（少），兼顾恢复速度与数据完整性。纯 RDB 体积小恢复快但数据丢失窗口大（取决于 `save` 触发频率），纯 AOF 数据丢失少（everysec 最多 1s）但恢复慢、体积大。单独 RDB 适合纯缓存允许丢失的场景，单独 AOF 适合对数据完整性要求极高的场景。

**关联**：→ [持久化机制](./02-persistence/persistence-mechanism.md)

### Q10: bgsave 时如果有写入怎么办？数据会丢吗？🔗

**答**：bgsave 通过 fork + COW（写时复制）保证快照一致性。fork 后父子进程共享物理页（只读），父进程写入触发缺页中断复制页（COW），子进程看到的是 fork 时刻的快照。**bgsave 期间写入不会丢**——写入会同步到主内存的新页，且同时写入 AOF 缓冲和 replication backlog。但 bgsave 生成的 RDB 不包含 fork 后的写入（那是 AOF 和 backlog 的职责）。COW 的代价：大页（THP 2MB）导致复制粒度放大 512 倍，必须关闭 THP（`echo never > /sys/kernel/mm/transparent_hugepage/enabled`）。

**关联**：→ [持久化机制](./02-persistence/persistence-mechanism.md)

### Q11: AOF 文件越来越大怎么办？🔗

**答**：AOF 重写——子进程遍历 db 生成最小命令集（如 `RPUSH list a; RPUSH list b; RPOP list` 重写为 `RPUSH list a b`），父进程同时把重写期间的新写入缓存在 `aof_rewrite_buf`，子进程完成后父进程追加缓冲并原子 `rename(2)` 替换旧 AOF。触发条件：`auto-aof-rewrite-percentage 100`（当前大小是上次重写后大小的 2 倍）和 `auto-aof-rewrite-min-size 64mb`（最小 64MB 才触发）。重写期间父进程仍处理命令，不阻塞。

**关联**：→ [持久化机制](./02-persistence/persistence-mechanism.md)

### Q12: fork 为什么会阻塞？怎么优化？🔗

**答**：fork 本身只复制页表不复制数据，但页表大小与内存成正比（1GB 内存页表约 2MB、10GB 约 20MB），复制页表期间主线程阻塞（10GB 实例 fork 约 200ms）。优化：①控制单实例 < 10GB（fork < 200ms 可接受）；②使用 Cluster 分片（每片 10GB）；③关闭 THP（大页导致 COW 复制粒度放大）；④使用 `repl-diskless-sync` 避免主从同步时 fork 落盘（流式 RDB 直接 socket 传输）；⑤监控 `info persistence` 的 `latest_fork_usec`（上次 fork 耗时微秒），超 100ms 需关注。

**关联**：→ [持久化机制](./02-persistence/persistence-mechanism.md)

### Q13: everysec 真的只丢 1 秒吗？🔗

**答**：理论上 everysec 每秒 fsync 一次最多丢 1s，但有例外：①AOF 重写期间，如果重写失败或父进程追加 `aof_rewrite_buf` 时 `aof_buf` 满阻塞，可能丢更多；②操作系统 fsync 不保证立即落盘（page cache 刷盘时机由 OS 决定，断电时 page cache 中未落盘的数据丢失）；③Redis 崩溃与 OS 断电不同——Redis 进程崩溃时 page cache 仍在，OS 重启后可恢复，但物理断电 page cache 丢失。真正 0 丢失需 `appendfsync always`（每次写都 fsync），但性能极差（单线程下 fsync 阻塞所有命令）。

**关联**：→ [持久化机制](./02-persistence/persistence-mechanism.md)

### Q14: 混合持久化怎么恢复？🔗

**答**：混合持久化的 AOF 文件结构是「RDB 格式头 + AOF 增量命令尾」。恢复时 Redis 检测 AOF 文件头是 RDB 格式（`REDIS` 魔数），先加载 RDB 部分（二进制快照恢复快，10GB 约 30-60s），再回放 AOF 增量命令（少量命令，恢复快）。相比纯 AOF 逐条回放命令（10GB 可能几分钟），混合持久化恢复速度接近纯 RDB，数据完整性接近纯 AOF（最多丢 1s 增量）。7.x 默认开启 `aof-use-rdb-preamble yes`，无需额外配置。

**关联**：→ [持久化机制](./02-persistence/persistence-mechanism.md)

---

## 三、内存与淘汰篇（6 题）

### Q15: Redis 过期 Key 怎么处理？🔗

**答**：Redis 用惰性删除 + 定期删除组合策略。**惰性删除**：访问 key 时检查 `expireIfNeeded`，过期则删除返回 nil。优点 CPU 友好，缺点无人访问的过期 key 常驻内存（"内存泄漏"）。**定期删除**：`serverCron` 每 100ms 触发 `activeExpireCycle`，抽样 20 个设了 TTL 的 key，过期比例 > 25% 则继续扫描（自适应），每次最多 25ms。为什么不用定时删除？千万级 key 每个一个定时器开销不可接受。两策略互补：惰性删除兜底（访问时清），定期删除主动扫（周期清）。

**关联**：→ [内存管理与淘汰策略](./03-memory/memory-and-eviction.md)

### Q16: 内存满了怎么办？8 种淘汰策略🔗

**答**：8 种淘汰策略：①`noeviction`（不淘汰，写入报错）；②`allkeys-lru`（所有 key 近似 LRU）；③`allkeys-random`（所有 key 随机）；④`allkeys-lfu`（所有 key LFU）；⑤`volatile-lru`（设 TTL 的 key 近似 LRU）；⑥`volatile-random`（设 TTL 的随机）；⑦`volatile-lfu`（设 TTL 的 LFU）；⑧`volatile-ttl`（设 TTL 的按 TTL 升序）。LFU 是 4.0 引入。选型：纯缓存用 `allkeys-lfu`（允许丢失、按频率保留热点），存 Session 用 `noeviction` + 容量监控（不允许丢失）。

**关联**：→ [内存管理与淘汰策略](./03-memory/memory-and-eviction.md)

### Q17: LRU 怎么实现的？为什么不用双向链表？🔗

**答**：Redis 用**近似 LRU**——采样 `maxmemory-samples` 个 key（默认 5），取 `lru` 字段最小的淘汰。不用双向链表 LRU 的原因：①内存开销大——每 key 两个额外指针（prev/next）；②维护成本高——每次访问需移动节点到头部，单线程下频繁指针操作影响性能；③采样 N 个已足够近似——N=5 接近 LRU 效果，N=10 更精确但更慢。redisObject 的 24 位 `lru` 字段记录最后访问时间戳（LRU 模式）或频率+衰减（LFU 模式）。

**关联**：→ [内存管理与淘汰策略](./03-memory/memory-and-eviction.md)

### Q18: LRU 和 LFU 区别？哪个好？🔗

**答**：LRU 按访问时间（最近未访问先淘汰），LFU 按访问频率（访问次数少先淘汰）。LRU 的问题——"偶尔被访问的冷数据"会挤掉热点（如全表扫描把冷数据刷入缓存，热点被淘汰）。LFU 解决此问题——按频率保留热点，扫描全表不会提升冷数据的频率。4.0 后推荐 LFU。LFU 用 redisObject 24 位 `lru` 字段：16 位频率（对数计数器 `counter = (counter * lfu_log_factor + 1) / counter`，最大 255 对应 1000 万次）+ 8 位时间衰减（每 `lfu-decay-time` 分钟未访问 counter 减 1）。

**关联**：→ [内存管理与淘汰策略](./03-memory/memory-and-eviction.md)

### Q19: 怎么查内存碎片？怎么清理？🔗

**答**：查碎片：`info memory` 的 `mem_fragmentation_ratio = used_memory_rss / used_memory`，> 1.5 需关注，> 2 需处理。清理：①`activedefrag yes`（7.x）——`serverCron` 中占用 1% CPU（`active-defrag-cycle-min 1`）移动数据整理碎片，碎片率 > 10% 触发（`active-defrag-threshold-lower 10`）；②重启 Redis——重启后重新分配内存（最彻底但需停服）。碎片产生原因：jemalloc 分配器按 size class 分配（8/16/32/48/64 字节…），频繁删改导致 size class 内有空洞。

**关联**：→ [内存管理与淘汰策略](./03-memory/memory-and-eviction.md)

### Q20: 为什么 used_memory_rss 比 used_memory 大？🔗

**答**：`used_memory` 是 Redis 逻辑分配的内存（通过 `zmalloc` 分配的），`used_memory_rss` 是操作系统视角的物理内存（RSS，Resident Set Size）。rss 大于 used 的原因：①**内存碎片**——jemalloc 分配的 size class 内有空洞，如申请 17 字节实际分配 32 字节，多出的 15 字节算在 rss 不算在 used；②**fork 子进程**——bgsave/AOF 重写 fork 的子进程共享 COW 页，短暂导致 rss 翻倍（子进程退出后恢复）；③**Redis 自身开销**——哈希表桶、客户端缓冲等 overhead 算在 rss。

**关联**：→ [内存管理与淘汰策略](./03-memory/memory-and-eviction.md)

---

## 四、事件与并发篇（5 题）

### Q21: Redis 为什么快？🔗

**答**：Redis 快的四大原因：①**全内存操作**——数据在内存，读写 ns 级，瓶颈不在 CPU 而在网络与内存；②**单线程避免锁竞争**——命令执行串行，数据结构无需加锁，无上下文切换开销；③**IO 多路复用 epoll**——Reactor 模型，单线程处理数万连接，epoll_wait O(1) 返回就绪事件；④**高效数据结构**——SDS O(1) 取长度、dict O(1) 查找、skiplist O(log n) 范围查询。单线程内存操作吞吐 10 万+ QPS，6.0 后 IO 多线程进一步提升网络 IO 吞吐。

**关联**：→ [事件与并发模型](./04-event/event-and-concurrency.md)

### Q22: 单线程怎么处理并发请求？🔗

**答**：Redis 单线程指**命令执行单线程**（`processCommand` 串行），不是只有一个线程——实际上有主线程 + bio 后台线程（AOF fsync、关闭文件、lazyfree）+ IO 线程（6.0+）。并发请求通过 IO 多路复用 epoll 处理：epoll_wait 阻塞等待就绪事件 → 批量返回就绪 fd → 主线程逐个处理读事件（解析命令）→ 串行执行命令 → 批量处理写事件（返回响应）。单线程串行执行避免锁竞争，保证命令原子性。epoll 无 1024 fd 限制、O(1) 返回就绪，支持数万连接。

**关联**：→ [事件与并发模型](./04-event/event-and-concurrency.md)

### Q23: IO 多线程后还是单线程吗？🔗

**答**：IO 多线程（6.0+）只并行化**网络 IO**（读 socket、写 socket），**命令执行仍单线程**。`io-threads 4` 启用 4 个 IO 线程并行解析/发送，`io-threads-do-reads yes`（7.x）读也并行。为什么命令不并行？破坏原子性——`INCR` 是读-改-写三步，多线程需加锁，违背单线程无锁初衷。IO 多线程适用场景：高 QPS + 大 value（网络 IO 成为瓶颈时），纯小 value 场景提升不明显（瓶颈在命令执行不在 IO）。

**关联**：→ [事件与并发模型](./04-event/event-and-concurrency.md)

### Q24: Redis 事务能回滚吗？🔗

**答**：Redis 事务（MULTI/EXEC）**不支持回滚**。`MULTI` 开启命令队列，命令入队不立即执行，`EXEC` 原子执行所有入队命令。语法错误在入队时已检查（`MULTI` 后 `LPUSH string-key 1` 报错不入队）。运行时错误如对 string 执行 `LPUSH`，其他命令仍执行（无回滚）。为什么无回滚？Redis 不支持 undo log，且语法错误已提前检查，运行时错误多为类型错误（业务侧 bug），回滚反而掩盖问题。`WATCH key` 提供乐观锁 CAS——监视 key 的修改版本号，`EXEC` 前检查若有改动返回 nil（整个事务放弃）。

**关联**：→ [事件与并发模型](./04-event/event-and-concurrency.md)

### Q25: Lua 脚本为什么能保证原子性？🔗

**答**：Lua 脚本在 Redis 单线程内执行，执行期间不切换（`evalCommand` 期间 `aeProcessEvents` 不返回），天然原子。相当于把多条命令"打包"成一个不可分割的单元。适用场景：原子扣库存（`DECR` + 判断 + 返回剩余）、限流令牌桶（`INCR` + `EXPIRE` + 判断）、比较并交换（CAS）。7.x 引入 Function（`FUNCTION LOAD`）替代 `EVAL`——可缓存可管理（`FUNCTION LIST`/`FUNCTION DELETE`），避免每次传脚本字面量节省带宽。`lua-time-limit` 默认 5s，超时用 `SCRIPT KILL` 或 `SHUTDOWN NOSAVE`。

**关联**：→ [事件与并发模型](./04-event/event-and-concurrency.md)

---

## 五、复制与集群篇（6 题）

### Q26: 主从同步流程？🔗

**答**：首次同步走全量——从库 `SLAVEOF` 后发 `PSYNC ? -1`，主库 `+FULLRESYNC` 回复 replid 和 offset，`bgsave` 生成 RDB 发给从库，从库加载 RDB 后主库补发同步期间的增量命令。后续走增量——主库维护 replication backlog 环形缓冲区（默认 1MB），从库断线重连后发 `PSYNC replid offset`，如果 offset 在 backlog 内则 `+CONTINUE` 增量补发。全量同步开销大（fork + 网络传输 + 加载阻塞，10GB 约 5 分钟），生产应调大 backlog 避免全量。

**关联**：→ [复制与集群](./05-replication/replication-and-cluster.md)

### Q27: 断线重连怎么同步？🔗

**答**：断线重连后从库发 `PSYNC replid offset`。如果 offset 在主库的 replication backlog 范围内，主库回复 `+CONTINUE` 增量补发缺失命令。如果 offset 已被 backlog 覆盖（断线太久），回退全量同步。4.0 引入 psync2——主库故障切换后新主继承旧主的 replid（`replid2`），从库用旧 replid 也能在新主上增量同步，避免切换后全量。backlog 大小应设为 `峰值写入速率 × 最大容忍断线时长`，如 `10MB/s × 60s = 600MB`。

**关联**：→ [复制与集群](./05-replication/replication-and-cluster.md)

### Q28: Sentinel 怎么选主？🔗

**答**：分两步——先选 Leader Sentinel，再选新主库。Leader 选举用 Raft 协议：任一 Sentinel 发现主库客观下线后发起选举（term 递增），其他 Sentinel 先到先得投票（同 term 只投一次），获得多数票的成为 Leader。Leader 按优先级选新主：①`slave-priority` 小的优先（0 永不选）；②`slave_repl_offset` 最大的（数据最全）；③`runid` 字典序最小的（兜底）。选好后发 `SLAVEOF NO ONE` 提升新主，通知其他从库同步新主，PubSub `+switch-master` 通知客户端。

**关联**：→ [复制与集群](./05-replication/replication-and-cluster.md)

### Q29: Cluster 为什么是 16384 个槽？🔗

**答**：三条理由（antirez 经典回答）：①**心跳包压缩**——Gossip PING/PONG 携带槽位 bitmap，16384 槽需 2KB，65536 槽需 8KB，心跳包每秒发送，8KB 太大浪费带宽；②**节点数不超过 1000**——16384/1000=16 槽/节点够用，65536 冗余 4 倍；③**bitmap 压缩**——稀疏 bitmap 用游程编码压缩，16384 位压缩效果好。槽位计算 `CRC16(key) % 16384`，CRC16 均匀映射 key 到 0-16383 范围。

**关联**：→ [复制与集群](./05-replication/replication-and-cluster.md)

### Q30: MOVED 和 ASK 区别？🔗

**答**：MOVED 是永久重定向——槽位已迁移完成，客户端应更新本地槽位映射表，后续请求直连新节点。ASK 是临时重定向——槽位正在迁移中，key 可能在目标节点，客户端临时去目标节点（带 `ASKING` 命令），但不更新本地映射（因为迁移未完成，部分 key 还在源节点）。迁移完成后不再返回 ASK，改为 MOVED。`ASKING` 告诉目标节点"这是临时重定向来的请求，即使槽位还在 IMPORTING 状态也请处理"。Lettuce/Jedis 等客户端内置 MOVED/ASK 处理。

**关联**：→ [复制与集群](./05-replication/replication-and-cluster.md)

### Q31: Cluster 支持事务吗？hashtag 是什么？🔗

**答**：不支持跨槽事务。`MULTI` 中的命令如果涉及不同槽位，`EXEC` 时报 `CROSSSLOT` 错误。要用事务操作多个 key，必须用 hashtag `{}` 保证同槽——如 `SET {user:1001}:profile val1` 和 `SET {user:1001}:orders val2`，CRC16 只计算 `{}` 内的 `user:1001`，两个 key 落在同一节点。hashtag 取 key 中第一个 `{` 到其后第一个 `}` 之间的内容。注意不要滥用——所有 `{user:1001}` 的 key 落同一节点，可能导致数据倾斜和热点。`MSET`/`MGET` 也要求同槽，同样用 hashtag。

**关联**：→ [复制与集群](./05-replication/replication-and-cluster.md)

---

## 六、缓存实战与分布式锁篇（6 题）

### Q32: 缓存穿透/击穿/雪崩区别和方案？🔗

**答**：三者都是缓存未命中导致请求打 DB。**穿透**是查询不存在的数据（布隆过滤器/空值缓存）；**击穿**是热点 key 过期瞬间并发打 DB（互斥锁/热点永不过期）；**雪崩**是大面积 key 同时过期或 Redis 宕机（随机过期/多级缓存/熔断降级）。记忆口诀：穿透是"查没有的"、击穿是"一个洞"、雪崩是"一片倒"。穿透防"查没有的"用布隆过滤器（bit 数组 + 多 hash，误判率约 1%），击穿防"单热点过期"用互斥锁（`SETNX` 加锁重建），雪崩防"大面积失效"用随机过期 + 多级缓存。

**关联**：→ [缓存实战与分布式锁](./06-cache-practice/cache-and-distributed-lock.md)

### Q33: 先删缓存还是先更新 DB？🔗

**答**：常用 Cache Aside——先删缓存再更新 DB。但存在并发不一致（线程 A 删缓存 → 线程 B 读 DB 旧值写入缓存 → 线程 A 更新 DB → 缓存是旧值）。改进方案：①延迟双删（更新 DB 后延迟 500ms 再删缓存）；②订阅 binlog（Canal 订阅 binlog 异步删缓存，最终一致）。为什么不能先更新缓存？并发覆盖问题——线程 A 先更新缓存 → 线程 B 更新 DB+缓存 → 线程 B 先完成 → 线程 A 后完成缓存被覆盖为旧值。生产推荐延迟双删 + binlog 双保险。

**关联**：→ [缓存实战与分布式锁](./06-cache-practice/cache-and-distributed-lock.md)

### Q34: 布隆过滤器为什么不能删除？🔗

**答**：布隆过滤器的 bit 位是共享的——多个 key 的 hash 可能映射到同一位。删除一个 key 会把共享位清零，导致其他 key 被误判为"不存在"。变体 Counting Bloom Filter 用计数器代替 bit 支持删除（减 1），但内存开销增大，Redis 的 RedisBloom 模块未原生支持。误判率公式 `p ≈ (1 - e^{-kn/m})^k`，典型 100 万元素 1% 误判率需 1.2MB + 7 个 hash 函数。布隆过滤器只能加不能删，适合"只增不减"的缓存防穿透场景。

**关联**：→ [缓存实战与分布式锁](./06-cache-practice/cache-and-distributed-lock.md)

### Q35: 分布式锁怎么实现？🔗

**答**：演进五阶段：①SETNX + EXPIRE 两步非原子（宕机死锁）；②`SET NX EX` 原子加锁（2.6.12+）；③UUID 防误删（Lua 脚本判断 value 再 DEL）；④Redlock 多节点投票（N=5 独立节点，半数以上成功获锁）；⑤Redisson 看门狗自动续期（每 10s 续到 30s）。生产推荐 Redisson——封装了原子加锁、UUID 防误删、看门狗续期、可重入（Hash 结构记录重入次数）、公平锁、读写锁等特性。`lock.lock()` 默认开看门狗，`tryLock(wait, lease, unit)` 指定 leaseTime 则不开。

**关联**：→ [缓存实战与分布式锁](./06-cache-practice/cache-and-distributed-lock.md)

### Q36: Redlock 有什么争议？🔗

**答**：Martin Kleppmann 指出两个问题：①GC 暂停——客户端获锁后长 GC，锁已过期但客户端不知，其他客户端获锁导致双持；②时钟漂移——多节点时钟不同步导致锁失效判断错误。Antirez 回应：GC 暂停概率极低且非 Redlock 独有，时钟漂移可 NTP 校准。结论：对绝大多数业务（库存扣减、任务调度）Redisson 单节点锁够用；对正确性要求极高的场景（金融转账）用 ZK（基于会话而非过期时间，CP 强一致）或 DB 悲观锁。

**关联**：→ [缓存实战与分布式锁](./06-cache-practice/cache-and-distributed-lock.md)

### Q37: Redisson 看门狗原理？🔗

**答**：`lockWatchdogTimeout` 默认 30s，加锁成功后启动定时任务每 10s（`timeout/3`）用 Lua 脚本续期到 30s（判断 value==UUID 再 EXPIRE）。为什么需要续期？业务执行时间不可预测，避免锁提前过期被其他客户端获取。`tryLock(waitTime, leaseTime, unit)` 指定 leaseTime 则不开看门狗——业务确知执行时间时用，避免看门狗开销。Redisson 还支持可重入（Hash 结构 `field=线程ID, value=重入次数`）、公平锁（FIFO 队列）、读写锁（读共享写排他）。

**关联**：→ [缓存实战与分布式锁](./06-cache-practice/cache-and-distributed-lock.md)

---

## 七、高可用与运维篇（4 题）

### Q38: 怎么排查大 Key？怎么处理？🔗

**答**：排查三种方式：①`redis-cli --bigkeys` 采样统计（每隔 100 个 key 抽样，快速但不精确）；②`MEMORY USAGE key` 精确查单 key 字节数；③`SCAN 0 COUNT 1000` 遍历不阻塞结合 `MEMORY USAGE` 逐 key 检查。处理：①`DEL` 改 `UNLINK`——`UNLINK` 异步删除，bio 线程后台释放内存不阻塞主线程；②拆分——String 分块（`content:{id}:part1`）、Hash 分桶（`hash(user_id) % 100`）、List 分段（每段 1000 元素）、Set/ZSet 分片。大 Key 危害：删除阻塞（10MB 约 10ms）、网络传输慢、Cluster 迁移卡顿、淘汰延迟。

**关联**：→ [高可用与运维](./07-ops/ha-and-ops.md)

### Q39: 热 Key 怎么发现和处理？🔗

**答**：发现：①`redis-cli --hotkeys`（需 `maxmemory-policy = allkeys-lfu`）；②`MONITOR` 抓取命令（生产慎用本身消耗性能）；③`OBJECT FREQ key`（需 LFU 模式，返回 0-255 对数频率）；④代理层/客户端侧统计 key 访问频率。处理：①本地 Caffeine 缓存热 Key 减少对 Redis 的访问；②多副本打散——写多个 key `hotkey:1`/`hotkey:2`，读时随机选一个。Cluster 分片对热 Key 无效（热 Key 只在一个节点，分片不能解决单节点 CPU 瓶颈），需本地缓存或副本。

**关联**：→ [高可用与运维](./07-ops/ha-and-ops.md)

### Q40: `KEYS *` 为什么危险？用什么替代？🔗

**答**：`KEYS *` 遍历 Redis 中所有 key，单线程下期间不处理任何其他请求。10 万 key 约 40ms（影响不大），百万级 key 阻塞秒级，生产环境绝对禁止。替代方案是 `SCAN 0 COUNT 1000`——基于游标的分页遍历，每次返回一个新游标和一批 key，单次返回少不影响主线程，可多次调用直到游标为 0。缺点是 rehash 期间可能重复返回 key（业务侧需去重）。大集合遍历也用 `SSCAN`/`HSCAN`/`ZSCAN` 分页。其他危险命令：`FLUSHALL`（用 `FLUSHALL ASYNC`）、`DEL` 大 Key（用 `UNLINK`）。

**关联**：→ [高可用与运维](./07-ops/ha-and-ops.md)

### Q41: `info` 你关注哪些指标？🔗

**答**：五大类：①`info memory`——`used_memory_rss`（真实占用，接近 maxmemory 告警）、`mem_fragmentation_ratio`（碎片率 >1.5 需 activedefrag）、`used_memory_peak`（峰值）；②`info clients`——`connected_clients`（突增说明连接泄漏）、`blocked_clients`（阻塞命令如 `BLPOP`）；③`info stats`——`instantaneous_ops_per_sec`（当前 QPS）、`keyspace_hits`/`keyspace_misses`（命中率 <95% 需关注）、`evicted_keys`（淘汰突增说明内存不足）；④`info persistence`——`rdb_bgsave_in_progress`/`aof_rewrite_in_progress`（长时间 1 说明 fork 慢）；⑤`info replication`——`role`/`connected_slaves`/`master_repl_offset` 差值（主从延迟）。

**关联**：→ [高可用与运维](./07-ops/ha-and-ops.md)

---

## 连环套问思维导图

面试官常沿一条链追问到底，下图梳理 6 条高频追问链，把握「上一题答完下一题会被怎么问」的路径：

每条链都是"入口题 → 原理 → 陷阱 → 实战"的递进，面试官常沿一条链追问到底。

**数据结构链详解**：从"有哪些数据类型"入口，追问 SDS 为什么不用 C 字符串（O(1) 取长度 + 二进制安全 + 预分配），到跳表 vs 红黑树（范围查询 + 实现简单 + 内存灵活），渐进式 rehash 的查询/增删改走法（双表都查、写只写 ht[1]），listpack 替代 ziplist（连锁更新 O(n²)），ZSet 双结构（dict O(1) 查 score + skiplist 范围查询），最后落到编码转换阈值与共享对象池。一条链覆盖数据结构篇 8 题。

**持久化链详解**：从"RDB 和 AOF 怎么选"入口，追问 bgsave 的 COW 机制（fork 只复制页表、写触发缺页复制），到 AOF 重写（子进程生成最小命令集 + 父进程增量缓冲 + rename 替换），fork 阻塞的优化（控制单实例 < 10GB + 关闭 THP），everysec 的丢数据边界（理论 1s 但有例外），最后落到混合持久化的 RDB 头 + AOF 尾恢复流程。

**内存链详解**：从"过期 Key 怎么删"入口（惰性 + 定期），追问内存满了的 8 种淘汰策略（noeviction/allkeys-lru/lfu/volatile-*），到 LRU 近似实现（采样 N 个不用双向链表，省内存），LRU vs LFU（LFU 解决"扫描全表挤掉热点"），内存碎片（jemalloc size class + activedefrag），最后落到 used_memory_rss vs used_memory 的差异（碎片 + fork + overhead）。

**事件链详解**：从"为什么快"入口（全内存 + 单线程无锁 + epoll + 高效数据结构），追问单线程怎么处理并发（epoll 多路复用 + 串行执行），IO 多线程后还是单线程吗（IO 并行命令仍串行，为什么不并行——破坏原子性），事务能回滚吗（无 undo log，运行时错误不回滚），最后落到 Lua 脚本的原子性（单线程执行不切换）与 Function 替代 EVAL。

**集群链详解**：从"主从同步流程"入口（全量 RDB + 增量 backlog），追问断线重连的 psync2（replid2 继承跨主续传），Sentinel 选主（Raft 选举 Leader + 优先级选新主），Cluster 为什么 16384 槽（心跳包 2KB + 节点不超 1000），MOVED/ASK 重定向（永久 vs 临时），最后落到 Cluster 限制（跨槽事务 + hashtag + Sharded PubSub）。

**缓存链详解**：从"穿透/击穿/雪崩"入口（布隆过滤器/互斥锁/多级缓存），追问先删缓存还是先更新 DB（Cache Aside + 延迟双删 + binlog），布隆过滤器为什么不能删除（bit 共享），分布式锁演进（SETNX → SET NX EX → UUID → Redlock → Redisson 看门狗），最后落到 Redlock 争议（GC 暂停 + 时钟漂移）与 ZK 锁选型。

```mermaid
mindmap
  root((Redis 连环套问))
    数据结构链
      数据类型与底层结构
        type 5 种 / encoding 多种
      SDS vs C 字符串
        O(1) 取长度 / 二进制安全
      跳表 vs 红黑树
        范围查询 / 实现简单
      渐进式 rehash
        双哈希表 / 增删改走法
      listpack 替代 ziplist
        连锁更新 O(n²)
      ZSet 双结构
        dict O(1) + skiplist 范围
      编码转换阈值
        listpack→hashtable/skiplist
      共享对象池
        0-9999 整数 / 字符串不共享
    持久化链
      RDB vs AOF 选型
        混合持久化 / everysec
      bgsave + COW
        fork 复制页表 / 写触发复制
      AOF 重写
        子进程最小命令集 + 增量缓冲
      fork 阻塞优化
        单实例 < 10GB / 关闭 THP
      everysec 丢数据边界
        理论 1s / page cache 例外
      混合持久化恢复
        RDB 头 + AOF 尾
    内存链
      过期 Key 删除
        惰性 + 定期 / 采样 20 个
      8 种淘汰策略
        noeviction / allkeys-lru/lfu
      LRU 近似实现
        采样 N 个 / 不用双向链表
      LRU vs LFU
        频率 vs 时间 / 扫描挤掉热点
      内存碎片
        jemalloc size class / activedefrag
      rss vs used
        碎片 + fork + overhead
    事件链
      为什么快
        全内存 + 单线程 + epoll
      单线程处理并发
        IO 多路复用 + 串行执行
      IO 多线程
        IO 并行 / 命令仍串行
      事务无回滚
        无 undo log / WATCH 乐观锁
      Lua 原子性
        单线程不切换 / Function 7.x
    集群链
      主从同步流程
        全量 RDB + 增量 backlog
      断线重连 psync2
        replid2 继承 / 跨主续传
      Sentinel 选主
        Raft 选举 / 优先级选新主
      16384 槽
        心跳包 2KB / 节点不超 1000
      MOVED vs ASK
        永久 vs 临时 / ASKING
      Cluster 限制
        跨槽事务 / hashtag / Sharded PubSub
    缓存链
      穿透击穿雪崩
        布隆过滤器 / 互斥锁 / 多级缓存
      先删缓存还是先更新 DB
        Cache Aside + 延迟双删 + binlog
      布隆过滤器不能删除
        bit 共享 / Counting BF 变体
      分布式锁演进
        SETNX → SET NX EX → UUID → Redlock → Redisson
      Redlock 争议
        GC 暂停 / 时钟漂移
      ZK vs Redisson
        CP 强一致 vs AP 高性能
```

> **使用提示**：面试前盖住答案自答 41 题，对照思维导图检查每条追问链是否答得完整；答不上来的题跳转 **关联** 文档补原理推导。

## 附：高频面试场景速查

| 场景 | 核心题 | 关联文档 |
|------|--------|---------|
| "讲讲 Redis 数据类型" | Q1-Q8 | [数据结构与对象编码](./01-data-structure/data-structure-and-encoding.md) |
| "Redis 持久化怎么选" | Q9-Q14 | [持久化机制](./02-persistence/persistence-mechanism.md) |
| "Redis 内存满了怎么办" | Q15-Q20 | [内存管理与淘汰策略](./03-memory/memory-and-eviction.md) |
| "Redis 为什么这么快" | Q21-Q25 | [事件与并发模型](./04-event/event-and-concurrency.md) |
| "Redis 集群怎么搞" | Q26-Q31 | [复制与集群](./05-replication/replication-and-cluster.md) |
| "缓存三大问题" | Q32-Q37 | [缓存实战与分布式锁](./06-cache-practice/cache-and-distributed-lock.md) |
| "Redis 生产怎么运维" | Q38-Q41 | [高可用与运维](./07-ops/ha-and-ops.md) |
