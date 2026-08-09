# Linux 面试知识体系实施计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 在 `ops/linux/` 下构建 11 份文档的 Linux 面试知识体系，深度对标 `ops/docker`、`ops/k8s`。

**Architecture:** 纯文档项目，无代码无测试。按 spec 的分阶段交付节奏，每个 Task 完成一份文档并自检（结构校验、链接校验、体量校验）后提交。文档遵循 Linux 专用六段式：概述→核心机制→命令与示例→高频追问→Java/容器关联→故障排查案例。

**Tech Stack:** Markdown + Mermaid 图表，中文撰写。

## Global Constraints

- 语言：全部中文（遵循 AGENTS.md 约定）
- 模块路径：`ops/linux/`
- 文档结构：Linux 专用六段式（概述/核心机制/命令与示例/高频追问/Java/容器关联/故障排查案例）
- 单份文档体量：500-800 行
- 深度：对标 docker/k8s（内核机制、源码路径、数据结构，但不到极致源码级）
- 每份文档头部固定三行：`> **一句话定位**` / `> **面试热度**：⭐⭐⭐⭐⭐` / `> **返回**：[Linux 知识图谱](../README.md)`
- README 自动更新规则：每完成一份主题文档，回填 `ops/linux/README.md` 导航表与知识图谱进度标记；完成任何模块内容变更同步更新对应 README 与根 `ops/README.md`
- 提交规范：`docs(linux): <描述>`，参照现有 `docs(k8s):` / `docs(docker):` 风格
- 参考样本：`ops/docker/01-foundation/container-principle.md`（主题文档）、`ops/docker/09-interview-qa.md`（Q&A）、`ops/docker/README.md`（入口）

## File Structure

```
ops/linux/
├── README.md                                  # Task 1 创建
├── 01-foundation/
│   └── system-boot-and-runtime.md             # Task 2
├── 02-process/
│   └── process-and-thread.md                  # Task 3
├── 03-memory/
│   └── memory-management.md                   # Task 4
├── 04-io/
│   └── io-model-and-epoll.md                  # Task 5
├── 05-fs/
│   └── filesystem-and-vfs.md                  # Task 6
├── 06-network/
│   └── network-kernel.md                      # Task 7
├── 07-security/
│   └── security-and-permission.md             # Task 8
├── 08-shell/
│   └── shell-and-scripting.md                 # Task 9
├── 09-ops/
│   └── performance-and-troubleshooting.md     # Task 10
└── 10-interview-qa.md                        # Task 11（含回填更新）
```

每份主题文档职责：覆盖该专题的内核机制 + 命令实操 + 高频追问 + Java/容器关联 + 排障案例，独立可读。

---

## Task 1: 创建 ops/linux/README.md 入口

**Files:**
- Create: `ops/linux/README.md`
- Modify: `ops/README.md:6`（把 `linux` 行从纯文字改为链接）

**Interfaces:**
- Produces: `ops/linux/README.md`，作为后续所有主题文档的导航入口；导航表中的链接路径是后续 Task 的产出契约

- [ ] **Step 1: 创建目录骨架**

```bash
mkdir -p ops/linux/{01-foundation,02-process,03-memory,04-io,05-fs,06-network,07-security,08-shell,09-ops}
```

- [ ] **Step 2: 编写 ops/linux/README.md**

按 spec 第六章的五节结构编写，内容要点：

**一、模块简介**：
- 定位：面向 Java 后端面试的 Linux 知识体系，深度对标 `ops/docker`、`ops/k8s`
- 适用对象：Java 后端面试（初中级到高级），兼顾云原生与服务端架构方向
- 组织方式：9 个主题目录 + 1 个 Q&A 文件，每份主题文档遵循 Linux 专用六段式
- 导航约定：每份文档顶部含 `> 返回 [Linux 知识图谱](../README.md)` 链接

**二、知识图谱**：使用 spec 第六章的 mermaid mindmap（9 大主题完整展开）

**三、导航表**：11 行表格，格式 `| 分层 | 文档 | 核心考点 |`，核心考点引用 spec 第四章：

| 分层 | 文档 | 核心考点 |
|------|------|---------|
| 系统启动 | [系统启动与运行时](./01-foundation/system-boot-and-runtime.md) | BIOS/UEFI→Bootloader→kernel→init、systemd Unit、cgroup v1/v2 基础 |
| 进程 | [进程与线程](./02-process/process-and-thread.md) | task_struct、状态机、fork/exec/exit、CFS 调度、信号、PID 1 陷阱 |
| 内存 | [内存管理](./03-memory/memory-management.md) | 虚拟内存、页表、swap、OOM killer、伙伴系统、RSS/PSS/USS |
| IO | [IO 模型与 epoll](./04-io/io-model-and-epoll.md) | 5 种 IO 模型、select/poll/epoll、LT/ET、Reactor、零拷贝、页面缓存 |
| 文件系统 | [文件系统与 VFS](./05-fs/filesystem-and-vfs.md) | VFS 四对象、inode/dentry、OverlayFS、procfs/sysfs、fsync |
| 网络 | [网络内核](./06-network/network-kernel.md) | netfilter、iptables、conntrack、TCP 栈队列、NAPI/RPS、策略路由 |
| 安全 | [安全与权限](./07-security/security-and-permission.md) | DAC/MAC、Capability、SELinux、seccomp、AppArmor、PAM |
| Shell | [Shell 与脚本](./08-shell/shell-and-scripting.md) | Bash 启动层级、三剑客、进程替换、变量作用域、set -euo pipefail |
| 性能排障 | [性能与故障排查](./09-ops/performance-and-troubleshooting.md) | USE/RED、top/vmstat/iostat、perf、strace、tcpdump、eBPF、排障四步法 |
| 面试冲刺 | [Q&A 速答](./10-interview-qa.md) | 50+ 题速答 + 连环套问思维导图 |

末尾加：`> 共 **11 份**文档：入口 README（本文档）+ 上表 9 份主题/Q&A 文档。`

**四、推荐学习路径**：
- 路线一：系统学习（1-2 周准备期）—— `01 → 02 → 03 → 04 → 05 → 06 → 07 → 08 → 09 → 10`
- 路线二：面试冲刺（3-5 天突击）—— 按热度排序：
  1. 04 IO 模型 → 02 进程 → 03 内存
  2. 09 性能排障 → 08 Shell → 06 网络内核
  3. 01 启动 → 05 文件系统 → 07 安全 → 10 Q&A

**五、与 java-core / framework 模块的关联**：汇总关联表（引用 spec 第五章），加延伸阅读链接。

- [ ] **Step 3: 更新 ops/README.md**

把第 6 行 `| linux | Linux 基础与 Shell |` 改为：
```
| [linux](./linux) | Linux 面试知识体系（11 份文档，按抽象层级组织） |
```

- [ ] **Step 4: 结构校验**

Run: `ls -la ops/linux/`，确认 9 个子目录存在。
Run: `grep -c '^##' ops/linux/README.md`，确认至少 5 节标题。
Run: `grep '返回.*Linux 知识图谱' ops/linux/README.md`，确认导航链接文本存在（导航链接指向的文件尚未创建，链接在后续 Task 完成后可达）。
Expected: 目录结构正确，README 含 5 节，导航链接文本存在。

- [ ] **Step 5: 提交**

```bash
git add ops/linux/README.md ops/README.md
git commit -m "docs(linux): 新增 Linux 模块 README 与目录骨架"
```

---

## Task 2: 01-foundation/system-boot-and-runtime.md（系统启动与运行时）

**Files:**
- Create: `ops/linux/01-foundation/system-boot-and-runtime.md`

**Interfaces:**
- Consumes: `ops/linux/README.md` 的导航链接路径
- Produces: `./01-foundation/system-boot-and-runtime.md`，README 导航表第一行的链接可达

**核心考点**（spec 第四章）：BIOS/UEFI→Bootloader→kernel→init、runlevel/target、systemd Unit 类型与依赖、cgroup v1/v2 基础、主机名与时区

- [ ] **Step 1: 编写文档**

按 Linux 专用六段式编写，各段内容要点：

**头部**：
```
# 系统启动与运行时

> **一句话定位**：从按下电源到 systemd 拉起服务，Linux 启动链是理解一切运维行为的起点，面试官爱用"讲讲 Linux 启动流程"作开胃菜。
> **面试热度**：⭐⭐⭐⭐⭐
> **返回**：[Linux 知识图谱](../README.md)
```

**一、概述**：该主题在 Linux 体系中的位置（启动链 + 运行时管理）；与其他主题的边界（cgroup 基础在此，深入限制见 02 进程/03 内存；systemd Unit 与 08 Shell 脚本的协作）；关键术语速览表（BIOS/UEFI/MBR/GPT/Bootloader/grub2/initramfs/runlevel/target/unit/cgroup v1/v2）。

**二、核心机制**：
- 启动全流程时序图（mermaid sequenceDiagram）：BIOS POST → Bootloader（grub2 stage）→ 加载 kernel + initramfs → kernel 初始化 → 挂载 rootfs → exec init/systemd → 拉起 default.target
- systemd 架构：PID 1、Unit 类型对比表（service/socket/target/timer/mount/automount/path/slice/scope）、依赖关系（Requires/Wants/After/Before/Requisite）
- target vs runlevel 对照表
- cgroup v1 vs v2 对比表（层级结构/进程归属/控制器/systemd 集成/Java 影响），cgroup 层级结构图（mermaid flowchart）
- 关键源码路径：`init/main.c`（kernel 启动入口）、`systemd`（PID 1 进程）、`/sys/fs/cgroup/`

**三、命令与示例**：
- 命令族速查表：`systemctl`（status/start/stop/enable/disable/list-units/list-sockets）、`journalctl`（-u/-f/--since）、`hostnamectl`、`timedatectl`、`localectl`、`mount`（cgroup 查看）
- 实战 one-liner：`systemctl list-units --type=service --state=running`、`journalctl -u nginx --since "1 hour ago"`、`systemd-analyze blame`、`systemd-analyze critical-chain`
- 命令输出解读：`systemctl status` 各字段（Loaded/Active/Sub/Main PID/CGroup）、`/proc/cgroups` 含义、`mount | grep cgroup`

**四、高频追问**（8-12 题，问答体）：
- Q1: Linux 启动流程？从按电源到 login 提示符（含 BIOS/Bootloader/kernel/init 四阶段）
- Q2: systemd 比 SysV init 强在哪？（并行启动/依赖管理/按需启动/日志归并）
- Q3: runlevel 和 target 的对应关系？
- Q4: systemd Unit 的 Requires 和 Wants 有什么区别？Requisite 呢？
- Q5: cgroup v1 和 v2 有什么区别？对 Java 有什么影响？（高频追问，铺垫 03 内存）
- Q6: systemd 怎么管理 cgroup？slice/scope/service 的关系？
- Q7: initramfs 是什么？为什么需要它？
- Q8: grub2 的启动阶段？stage1/stage1.5/stage2 现在还是这样吗？
- Q9: hostnamectl 和 hostname 的区别？
- Q10: systemd timer 比 cron 强在哪？

**五、Java/容器关联**：
- JVM 启动参数与 systemd Unit 的协作（`ExecStart=java -Xmx2g -jar app.jar`、`SuccessExitStatus=143` 兼容 SIGTERM）
- systemd 的 `KillMode=control-group` 与 JVM ShutdownHook
- cgroup v2 与 JVM 容器感知（关联 `java-core/jvm`）
- 容器内为何没有 systemd（PID 1 是 entrypoint，关联 `ops/docker`）
- 实战映射表

**六、故障排查案例**：
- 案例：服务启动失败 `systemctl status` + `journalctl -xe` 排障链
- 案例：systemd-analyze blame 发现启动慢，定位耗时 Unit

- [ ] **Step 2: 体量与结构校验**

Run: `wc -l ops/linux/01-foundation/system-boot-and-runtime.md`
Expected: 500-800 行。

Run: `grep -c '^## ' ops/linux/01-foundation/system-boot-and-runtime.md`
Expected: 6（六个二级标题：一~六）。

Run: `grep '一句话定位\|面试热度\|返回.*Linux 知识图谱' ops/linux/01-foundation/system-boot-and-runtime.md`
Expected: 头部三行齐全。

- [ ] **Step 3: 提交**

```bash
git add ops/linux/01-foundation/system-boot-and-runtime.md
git commit -m "docs(linux): 新增系统启动与运行时"
```

---

## Task 3: 02-process/process-and-thread.md（进程与线程）

**Files:**
- Create: `ops/linux/02-process/process-and-thread.md`

**核心考点**：task_struct、进程状态机（R/S/D/T/Z/X）、fork/exec/exit、CFS 调度类与 nice、线程 vs 进程、信号机制与默认行为、PID 1 陷阱

- [ ] **Step 1: 编写文档**

**头部**：一句话定位点题"进程是 Linux 调度的基本单位，面试官爱从'讲讲进程状态机'切入"

**一、概述**：进程在 Linux 体系中的位置；与 03 内存（进程地址空间）、04 IO（进程阻塞在 IO）、07 安全（进程权限）的边界；关键术语表（task_struct/PCB/PID/PPID/TGID/LWP/nice/oom_score/信号）

**二、核心机制**：
- `task_struct` 结构（源码路径 `include/linux/sched.h`），关键字段表（pid/tgid/mm/fs/files/signal/policy/nice）
- 进程状态机图（mermaid stateDiagram-v2）：R/S/D/T/Z/X 六状态及转换条件
- fork/exec/exit 流程图：`fork()`→`exec()`→`exit()`→`wait()` 时序
- CFS 调度器：vruntime 计算、红黑树、调度类（idle/stop/fair/rt）、nice 与权重
- 线程模型：Linux 线程 = LWP（轻量进程），`clone(CLONE_THREAD)`，TLS
- 信号机制：信号类型表（1-31 标准 + 32-63 实时）、默认行为（Term/Ign/Core/Stop/Cont）、信号生命周期（生成→挂起→递送）
- PID 1 陷阱：PID 1 默认忽略 SIGTERM（除非自注册 handler），孤儿进程收养

**三、命令与示例**：
- 命令族速查表：`ps`（aux/-ef/--ppid/-L）、`top`/`htop`、`pstree`、`pgrep`/`pkill`、`kill`/`killall`/`pkill`、`nice`/`renice`、`taskset`、`strace -p`
- 实战 one-liner：`ps -eo pid,ppid,ni,cmd --sort=-ni`、`pstree -p`、`kill -SIGTERM $(pgrep -f 'java.*app')`
- 命令输出解读：`top` 各字段（PR/NI/VIRT/RES/SHR/S/%CPU/%MEM/TIME+）、`ps` 状态列（R/S/D/T/Z）、`/proc/<pid>/status` 各字段

**四、高频追问**（10-12 题）：
- Q1: 进程有哪些状态？D 状态是什么？能 kill -9 吗？
- Q2: 僵尸进程怎么产生的？怎么清理？
- Q3: fork 之后子进程的执行顺序？
- Q4: Linux 线程和进程的区别？为什么说 Linux 没有"线程"？
- Q5: CFS 调度器原理？nice 调整的是优先级还是时间片？
- Q6: 信号 Kill -9 和 kill -15 有什么区别？为什么 PID 1 默认不被 kill -15 杀？
- Q7: 孤儿进程和僵尸进程的区别？
- Q8: 线程池里一个线程 OOM 了其他线程会怎样？
- Q9: 怎么查看一个进程打开了哪些文件？哪些端口？
- Q10: 多线程程序怎么排查哪个线程吃 CPU？
- Q11: 容器内 PID 1 是什么？为什么 Spring Boot fat jar 的 PID 1 有坑？
- Q12: CPU 亲和性怎么设置？对 Java 线程池有什么影响？

**五、Java/容器关联**：
- Java 线程 = Linux LWP（`ps -eLf` 可见），`java.lang.Thread` 映射
- `ForkJoinPool` 与 CPU 亲和（关联 `java-core/forkjoin`）
- `parallelStream` 默认用公共 ForkJoinPool，与 nice/CPU limit 的冲突
- JVM ShutdownHook 与 SIGTERM/SIGINT（关联 `java-core/jvm`）
- 容器 PID 1 陷阱与 Spring Boot graceful shutdown（关联 `ops/docker`、`framework/spring-framework`）
- 实战映射表

**六、故障排查案例**：
- 案例：Java 服务 CPU 100%，`top -H -p <pid>` + `printf '%x\n' <tid>` + `jstack` 定位热点线程
- 案例：容器内 `kill -TERM 1` 不生效，定位 PID 1 未注册信号 handler

- [ ] **Step 2: 体量与结构校验**

Run: `wc -l ops/linux/02-process/process-and-thread.md`，Expected: 500-800 行。
Run: `grep -c '^## ' ops/linux/02-process/process-and-thread.md`，Expected: 6。
Run: `grep '一句话定位\|面试热度\|返回.*Linux 知识图谱' ops/linux/02-process/process-and-thread.md`，Expected: 头部三行齐全。

- [ ] **Step 3: 提交**

```bash
git add ops/linux/02-process/process-and-thread.md
git commit -m "docs(linux): 新增进程与线程"
```

---

## Task 4: 03-memory/memory-management.md（内存管理）

**Files:**
- Create: `ops/linux/03-memory/memory-management.md`

**核心考点**：虚拟内存与页表、缺页中断、swap 与 swappiness、OOM killer 选主、伙伴系统与 slub、RSS/PSS/USS、mmap

- [ ] **Step 1: 编写文档**

**头部**：一句话定位"内存是 Java 后端面试的高频区，OOM killer 选主策略和 JVM 堆感知是两个必考点"

**一、概述**：内存管理在 Linux 体系中的位置；与 02 进程（地址空间）、04 IO（页面缓存/mmap）、09 性能排障（内存泄漏排查）的边界；关键术语表（虚拟内存/物理内存/页表/TLB/缺页/swap/swappiness/OOM/伙伴系统/slab/slub/RSS/PSS/USS/mmap）

**二、核心机制**：
- 虚拟内存到物理内存的映射（mermaid flowchart）：MMU → 页表（多级）→ TLB → 缺页中断 → 分配物理页
- 进程地址空间布局图（text/data/bss/heap/mmap/stack）
- 缺页中断处理流程：major fault vs minor fault
- swap 与 swappiness（`vm.swappiness` 0-200）、zswap/zram
- OOM killer 流程图（mermaid）：内存不足 → `oom_kill.c::out_of_memory()` → `select_bad_process()` 按 `oom_score` 排序 → SIGKILL
- 伙伴系统与 slub/slub 分配器对比表
- RSS/PSS/USS 定义与 `/proc/<pid>/smaps`、`/proc/<pid>/status` 的 VmRSS/VmSize
- mmap 原理与文件映射/匿名映射

**三、命令与示例**：
- 命令族速查表：`free`（-h/-m/-g）、`vmstat`（-w/s/m）、`top`（内存列）、`ps`（--sort=-rss）、`pmap`（-x/-d）、`smem`（-r/-k）、`cat /proc/meminfo`、`cat /proc/<pid>/status`、`sysctl vm.swappiness`
- 实战 one-liner：`ps -eo pid,rss,cmd --sort=-rss | head -10`、`pmap -x <pid> | sort -k3 -rn | head`、`smem -r -k | head -20`
- 命令输出解读：`free` 各列（total/used/free/shared/buff/cache/available）、`/proc/meminfo` 关键字段（MemFree/Cached/SwapCached/Slab/SReclaimable）、`/proc/<pid>/smaps_rollup`

**四、高频追问**（10-12 题）：
- Q1: 虚拟内存是什么？为什么需要它？
- Q2: 缺页中断是什么？major 和 minor fault 有什么区别？
- Q3: swap 是什么？swappiness 调高调低有什么影响？
- Q4: OOM killer 怎么选进程？能禁用吗？
- Q5: 伙伴系统和 slub 是什么关系？
- Q6: RSS/PSS/USS 有什么区别？怎么看一个进程真实占用？
- Q7: mmap 读文件和 read 有什么区别？
- Q8: buff/cache 占很高是问题吗？
- Q9: 一个 Java 进程内存 = 堆 + 什么？
- Q10: 容器内 free 看到的是宿主内存吗？怎么限制？
- Q11: 怎么定位一个进程的内存泄漏？
- Q12: transparent huge page（THP）对 Java 有什么影响？

**五、Java/容器关联**：
- JVM 堆/元空间/直接内存/栈 在 Linux 地址空间的映射
- 堆外内存（DirectByteBuffer、Netty、JNI）与 RSS
- OOM killer 杀 JVM 的现象与 `dmesg` 排查（关联 `java-core/jvm`）
- cgroup memory 与 JVM 堆感知（`UseContainerSupport`，关联 `java-core/jvm`、`ops/docker`）
- 容器内 `free` 显示宿主内存的坑（关联 `ops/docker`）
- 实战映射表

**六、故障排查案例**：
- 案例：Java 服务频繁被 OOM Killed，`dmesg` + `jcmd <pid> VM.native_memory` 定位堆外内存泄漏
- 案例：容器内 `free` 显示 64G，实际 cgroup 限制 2G，JVM 按宿主算堆导致 OOM

- [ ] **Step 2: 体量与结构校验**

Run: `wc -l ops/linux/03-memory/memory-management.md`，Expected: 500-800 行。
Run: `grep -c '^## ' ops/linux/03-memory/memory-management.md`，Expected: 6。
Run: `grep '一句话定位\|面试热度\|返回.*Linux 知识图谱' ops/linux/03-memory/memory-management.md`，Expected: 头部三行齐全。

- [ ] **Step 3: 提交**

```bash
git add ops/linux/03-memory/memory-management.md
git commit -m "docs(linux): 新增内存管理"
```

---

## Task 5: 04-io/io-model-and-epoll.md（IO 模型与 epoll）

**Files:**
- Create: `ops/linux/04-io/io-model-and-epoll.md`

**核心考点**：5 种 IO 模型、select/poll/epoll 对比、epoll 源码路径与 LT/ET、Reactor 模式、sendfile/mmap/splice 零拷贝、页面缓存与脏页

- [ ] **Step 1: 编写文档**

**头部**：一句话定位"IO 模型是 Java NIO/Netty 的底层，面试官爱从'讲讲 epoll'切到 Reactor"

**一、概述**：IO 模型在 Linux 体系中的位置；与 02 进程（阻塞/非阻塞）、05 文件系统（页面缓存）、06 网络（socket 在内核栈）、09 性能排障（IO 排障）的边界；关键术语表（同步/异步/阻塞/非阻塞/IO 多路复用/Reactor/epoll/select/poll/LT/ET/零拷贝/sendfile/mmap/splice/page cache/dirty page）

**二、核心机制**：
- 5 种 IO 模型对比图（mermaid）：阻塞 IO/非阻塞 IO/IO 多路复用/信号驱动 IO/异步 IO，每种的时序图
- select/poll/epoll 对比表（数据结构/复杂度/fd 上限/内核开销/触发方式）
- epoll 原理：`eventpoll` 结构（源码路径 `fs/eventpoll.c`），红黑树管理 fd + 就绪链表 + 等待队列；`epoll_create`/`epoll_ctl`/`epoll_wait` 流程图
- LT vs ET 触发模式对比（何时触发/是否需要循环读/适用场景）
- Reactor 模式（单 Reactor 单线程/单 Reactor 多线程/主从 Reactor 多线程），mermaid 架构图
- 零拷贝对比表：`sendfile`（2 次上下文切换，全内核态拷贝）/`mmap`（用户态映射）/`splice`（管道缓冲），数据流向图
- 页面缓存与脏页：writeback 机制、`dirty_ratio`/`dirty_background_ratio`

**三、命令与示例**：
- 命令族速查表：`iostat`（-x/-d/-c）、`vmstat`（-w）、`iotop`、`fio`（压测）、`dd`（测吞吐）、`strace -e trace=read,write,epoll_wait`
- 实战 one-liner：`iostat -xmt 1`、`iotop -oPa`、`dd if=/dev/zero of=/tmp/test bs=1M count=1024 oflag=direct`
- 命令输出解读：`iostat -x` 各列（r/s/w/s await %util）、`/proc/diskstats`、`/proc/<pid>/io`

**四、高频追问**（10-12 题）：
- Q1: 5 种 IO 模型分别是什么？阻塞和非阻塞的本质区别？
- Q2: select/poll/epoll 有什么区别？为什么 epoll 性能好？
- Q3: epoll 的 LT 和 ET 模式有什么区别？为什么 ET 必须非阻塞读？
- Q4: epoll 内部用了什么数据结构？
- Q5: Reactor 模式是什么？主从 Reactor 多线程怎么分工？
- Q6: 零拷贝是什么？sendfile/mmap/splice 各适用什么场景？
- Q7: Java NIO 用的是哪种 IO 模型？
- Q8: Netty 的 EventLoop 是 Reactor 模式的哪种？
- Q9: page cache 是什么？写文件经过 page cache 吗？
- Q10: 怎么让写文件不经过 page cache？（O_DIRECT）
- Q11: 一个 Java 服务读文件很慢，怎么排查？
- Q12: 为什么 Redis 单线程性能好？（IO 多路复用 + 内存操作）

**五、Java/容器关联**：
- Java NIO Selector 底层 = epoll（关联 `java-core/lambda`，Pipeline 与函数式回调）
- Netty 主从 Reactor 与 Boss/Worker Group
- `parallelStream` 默认用 ForkJoinPool，IO 密集任务阻塞线程池（关联 `java-core/stream`）
- 容器内 epoll 的行为（共享内核，关联 `ops/docker`）
- 实战映射表

**六、故障排查案例**：
- 案例：Java 服务读文件慢，`strace -e trace=read` 发现 sysclock 开销，定位是 page cache 抖动
- 案例：Netty 服务连接数高，`ss -s` + `cat /proc/<pid>/net/sockstat` 定位 epoll 管理 fd 数

- [ ] **Step 2: 体量与结构校验**

Run: `wc -l ops/linux/04-io/io-model-and-epoll.md`，Expected: 500-800 行。
Run: `grep -c '^## ' ops/linux/04-io/io-model-and-epoll.md`，Expected: 6。
Run: `grep '一句话定位\|面试热度\|返回.*Linux 知识图谱' ops/linux/04-io/io-model-and-epoll.md`，Expected: 头部三行齐全。

- [ ] **Step 3: 提交**

```bash
git add ops/linux/04-io/io-model-and-epoll.md
git commit -m "docs(linux): 新增 IO 模型与 epoll"
```

---

## Task 6: 05-fs/filesystem-and-vfs.md（文件系统与 VFS）

**Files:**
- Create: `ops/linux/05-fs/filesystem-and-vfs.md`

**核心考点**：VFS 四对象、inode/dentry/open fd table、OverlayFS 原理、procfs/sysfs/debugfs、硬软链接、fsync 与写屏障

- [ ] **Step 1: 编写文档**

**头部**：一句话定位"VFS 是理解一切文件操作的根，OverlayFS 是容器镜像的底层"

**一、概述**：文件系统在 Linux 体系中的位置；与 03 内存（mmap/page cache）、04 IO（文件 IO）、07 安全（文件权限）的边界；关键术语表（VFS/superblock/inode/dentry/file/fd/inode number/hard link/symlink/mount/OverlayFS/procfs/sysfs）

**二、核心机制**：
- VFS 四对象图（mermaid flowchart）：superblock（超级块）/inode（索引节点）/dentry（目录项）/file（打开文件），关系图
- 文件描述符表层次：进程级 `files_struct`（fd table）→ 系统级 `file`（open file description）→ inode（磁盘级）
- inode 与 dentry 关系：dentry 构成目录树，指向 inode
- 硬链接 vs 软链接对比表（inode/跨文件系统/删除影响/创建权限）
- OverlayFS 原理：lowerdir/upperdir/workdir/merged 四层，CoW 机制，whiteout 文件（关联 `ops/docker`）
- 伪文件系统对比表：procfs（`/proc`）/sysfs（`/sys`）/debugfs（`/sys/kernel/debug`），各自职责
- fsync 与写屏障：`fsync()` 刷盘流程、`fdatasync()`、`O_SYNC`、磁盘 write cache 与 `barrier` 挂载选项

**三、命令与示例**：
- 命令族速查表：`ls`（-i/-l）、`stat`、`df`（-i/-h）、`du`（-sh/--max-depth）、`mount`（-t/-o）、`find`（-inum/-type/-size）、`lsof`（-p/-i）、`ln`（硬链接/`-s` 软链接）、`fsck`、`xfs_growfs`/`resize2fs`
- 实战 one-liner：`ls -li`、`stat file.txt`、`lsof -p <pid> | wc -l`、`df -i`、`find / -inum 12345`
- 命令输出解读：`stat` 各字段（Inode/Links/Size/Blocks）、`df -i`（inode 用量）、`/proc/<pid>/maps`、`/proc/<pid>/fd`

**四、高频追问**（10-12 题）：
- Q1: VFS 是什么？为什么需要它？
- Q2: inode 和 dentry 有什么区别？
- Q3: 硬链接和软链接的区别？为什么硬链接不能跨文件系统？
- Q4: 一个文件被打开后，别人删了它，读取还会成功吗？
- Q5: OverlayFS 是什么？Docker 镜像怎么用它的？
- Q6: /proc 是什么？为什么程序能读自己的内存？
- Q7: fsync 和 fdatasync 有什么区别？为什么数据库要 fsync？
- Q8: 文件描述符是什么？0/1/2 是什么？
- Q9: 一个进程能打开多少文件？ulimit 和 file-max 的区别？
- Q10: 怎么查看一个进程打开了哪些文件？
- Q11: du 和 df 统计不一致是什么原因？（删除被打开的文件）
- Q12: 为什么删除大文件后磁盘空间没释放？

**五、Java/容器关联**：
- Spring Boot Layertools 分层 = OverlayFS 的应用（关联 `framework/spring-framework`、`ops/docker`）
- `@Value` 与配置文件加载顺序（关联 `framework/spring-framework`）
- 日志文件 fsync 与磁盘 IO 压力
- `FileChannel` 与 `sendfile`/`transferTo` 零拷贝
- 实战映射表

**六、故障排查案例**：
- 案例：`du` 显示 50G，`df` 显示 200G，定位被删除但被进程持有的文件，`lsof +L1` 找到后 kill 进程
- 案例：Docker 容器内写文件慢，定位 OverlayFS upperdir 在慢盘

- [ ] **Step 2: 体量与结构校验**

Run: `wc -l ops/linux/05-fs/filesystem-and-vfs.md`，Expected: 500-800 行。
Run: `grep -c '^## ' ops/linux/05-fs/filesystem-and-vfs.md`，Expected: 6。
Run: `grep '一句话定位\|面试热度\|返回.*Linux 知识图谱' ops/linux/05-fs/filesystem-and-vfs.md`，Expected: 头部三行齐全。

- [ ] **Step 3: 提交**

```bash
git add ops/linux/05-fs/filesystem-and-vfs.md
git commit -m "docs(linux): 新增文件系统与 VFS"
```

---

## Task 7: 06-network/network-kernel.md（网络内核）

**Files:**
- Create: `ops/linux/06-network/network-kernel.md`

**核心考点**：netfilter 五钩子、iptables 表链、conntrack 表与耗尽、TCP 栈各队列（accept/synq/recvq）、路由与策略路由、网卡中断与 NAPI/RPS

- [ ] **Step 1: 编写文档**

**头部**：一句话定位"netfilter/iptables 是容器网络的底层，conntrack 耗尽是高并发服务的经典坑"

**一、概述**：网络内核在 Linux 体系中的位置；与 `ops/network`（协议层）、`ops/docker`（容器网络）、`ops/k8s`（CNI）的边界；关键术语表（netfilter/iptables/表/链/钩子/conntrack/NAPI/RPS/RFS/softirq/accept queue/synq/recvq）

**二、核心机制**：
- netfilter 五钩子图（mermaid flowchart）：PRE_ROUTING/LOCAL_IN/FORWARD/LOCAL_OUT/POST_ROUTING，在协议栈的位置
- iptables 表链关系表：raw/mangle/nat/filter，五链（INPUT/OUTPUT/FORWARD/PREROUTING/POSTROUTING），表与链的对应
- conntrack 表：`/proc/net/nf_conntrack`，状态（NEW/ESTABLISHED/RELATED/INVALID），表条目上限与耗尽现象
- TCP 栈队列图（mermaid）：半连接队列（synq）/全连接队列（accept queue）/接收队列（recvq），`ss -lnt` 的 Recv-Q/Send-Q 含义
- 网卡收包流程：硬中断 → NAPI 轮询 → softirq → 协议栈，RPS/RFS 多核分发
- 路由与策略路由：`ip rule` + `ip route`，多路由表

**三、命令与示例**：
- 命令族速查表：`iptables`（-L/-t nat -L/-A/-D/-save）、`ss`（-lnt/-s/-tn）、`conntrack`（-L/-C/-S）、`ip`（rule/route/addr/link）、`tcpdump`（-i/-nn/-X/-c）、`ethtool`（-S/-g/-k）、`nstat`、`netstat`（-s）
- 实战 one-liner：`ss -lnt | grep :8080`、`iptables -t nat -L PREROUTING -n -v`、`conntrack -L -d 1.2.3.4`、`tcpdump -i eth0 -nn 'port 80' -c 100`
- 命令输出解读：`ss -lnt` 的 Recv-Q（当前 accept queue 镟列长度）和 Send-Q（listen backlog）、`/proc/net/snmp`（TCP 各指标）

**四、高频追问**（10-12 题）：
- Q1: netfilter 的五个钩子是什么？在协议栈什么位置？
- Q2: iptables 的表和链是什么关系？哪个表做 NAT？哪个做过滤？
- Q3: conntrack 是什么？耗尽了会怎样？
- Q4: accept 队列满会怎样？怎么排查？
- Q5: SYN Flood 攻击原理？内核怎么防？（syncookies）
- Q6: TIME_WAIT 太多怎么处理？
- Q7: 网卡硬中断和软中断的关系？NAPI 是什么？
- Q8: RPS 和 RFS 解决什么问题？
- Q9: Docker 的端口映射 iptables 规则长什么样？（关联 docker 网络）
- Q10: K8s Service 的 conntrack 陷阱？
- Q11: 怎么抓包看 TCP 三次握手？
- Q12: 策略路由是什么？什么时候用？

**五、Java/容器关联**：
- Tomcat/Netty 的 accept queue 与 listen backlog（关联 `framework/spring-framework`）
- 高并发 Java 服务的 conntrack 耗尽（关联 `ops/docker`、`ops/k8s`）
- TCP 栈参数调优与网络模块对照（关联 `ops/network`）
- 容器网络 veth + iptables 的底层（关联 `ops/docker`）
- 实战映射表

**六、故障排查案例**：
- 案例：Java 服务偶发连接超时，`ss -lnt` 发现 accept queue 满，调大 `somaxconn` + `backlog`
- 案例：K8s NodePort 服务高并发时丢包，`dmesg` + `conntrack -S` 定位 conntrack 表满

- [ ] **Step 2: 体量与结构校验**

Run: `wc -l ops/linux/06-network/network-kernel.md`，Expected: 500-800 行。
Run: `grep -c '^## ' ops/linux/06-network/network-kernel.md`，Expected: 6。
Run: `grep '一句话定位\|面试热度\|返回.*Linux 知识图谱' ops/linux/06-network/network-kernel.md`，Expected: 头部三行齐全。

- [ ] **Step 3: 提交**

```bash
git add ops/linux/06-network/network-kernel.md
git commit -m "docs(linux): 新增网络内核"
```

---

## Task 8: 07-security/security-and-permission.md（安全与权限）

**Files:**
- Create: `ops/linux/07-security/security-and-permission.md`

**核心考点**：用户/组/ACL、Capability 集合、SELinux MAC vs DAC、seccomp BPF、AppArmor、PAM 鉴权链、sudoers 配置陷阱

- [ ] **Step 1: 编写文档**

**头部**：一句话定位"Linux 安全模型是容器安全的底层，Capability 和 seccomp 是 Docker/K8s 安全的基石"

**一、概述**：安全与权限在 Linux 体系中的位置；与 02 进程（进程权限）、05 文件系统（文件权限）、`ops/docker`（容器安全）的边界；关键术语表（DAC/MAC/UGO/ACL/Capability/SELinux/AppArmor/seccomp/PAM/sudoers/LSM）

**二、核心机制**：
- 安全模型层次图（mermaid flowchart）：DAC（UGO + ACL）→ MAC（SELinux/AppArmor）→ Capability（细分 root 权限）→ seccomp（系统调用过滤）→ LSM hook
- UGO 权限模型：rwx 三组、SUID/SGID/Sticky bit
- ACL：`getfacl`/`setfacl`，比 UGO 更细
- Capability 集合表：CAP_NET_BIND_SERVICE/CAP_SYS_ADMIN/CAP_KILL 等，root = 全 Capability，`drop` 机制
- SELinux：label（`user:role:type:level`）、策略、 enforcing/permissive 模式；与 DAC 的关系（先 DAC 后 MAC）
- seccomp BPF：系统调用黑名单，`SECCOMP_RET_KILL`/`SECCOMP_RET_ERRNO`，Docker 默认 seccomp profile
- AppArmor：path-based MAC，profile 文件
- PAM 鉴权链：`/etc/pam.d/` 配置，auth/account/session/password 四阶段

**三、命令与示例**：
- 命令族速查表：`id`/`whoami`/`who`/`w`、`chmod`/`chown`/`chgrp`、`umask`、`getfacl`/`setfacl`、`sudo`/`visudo`、`su -`/`su`、`getenforce`/`setenforce`/`sestatus`/`getsebool`/`setsebool`、`ls -Z`/`ps -Z`/`chcon`/`restorecon`、`capsh`/`getcap`/`setcap`
- 实战 one-liner：`getfacl file`、`getcap /usr/bin/ping`、`ls -Z /var/www`、`ps -eZ | grep httpd`
- 命令输出解读：`ls -l` 权限位、`ls -Z` 的 SELinux label、`getcap` 的 capability 集

**四、高频追问**（10-12 题）：
- Q1: SUID 是什么？为什么 `/usr/bin/passwd` 需要 SUID？
- Q2: Capability 是什么？解决了什么问题？
- Q3: Docker 默认 drop 了哪些 Capability？为什么不能 drop CAP_NET_RAW？
- Q4: SELinux 和 AppArmor 有什么区别？
- Q5: SELinux 的 enforcing 和 permissive 模式有什么区别？怎么排查 SELinux 拦截？
- Q6: seccomp 是什么？Docker 的默认 seccomp 禁了什么？
- Q7: sudo 配置错了会有什么安全问题？
- Q8: 为什么 `su -` 和 `su` 不同？
- Q9: Sticky bit 是什么？为什么 /tmp 要设它？
- Q10: ACL 和 UGO 的关系是什么？
- Q11: PAM 是什么？怎么自定义鉴权？
- Q12: 怎么让一个非 root 用户绑定 80 祥口？（CAP_NET_BIND_SERVICE）

**五、Java/容器关联**：
- Docker seccomp/Capability 的底层（关联 `ops/docker`）
- K8s PodSecurity Standards 替代 PSP（关联 `ops/k8s`）
- Java agent attach 的权限要求（关联 `java-core/agent`）
- Java 进程以非 root 运行绑定低端口（setcap 或 CAP_NET_BIND_SERVICE）
- 实战映射表

**六、故障排查案例**：
- 案例：Nginx 启动失败 `permission denied`，`ls -Z` + `getenforce` 定位 SELinux 拦截
- 案例：Docker 容器内 `ping` 失败，`getcap` 定位 CAP_NET_RAW 被 drop

- [ ] **Step 2: 体量与结构校验**

Run: `wc -l ops/linux/07-security/security-and-permission.md`，Expected: 500-800 行。
Run: `grep -c '^## ' ops/linux/07-security/security-and-permission.md`，Expected: 6。
Run: `grep '一句话定位\|面试热度\|返回.*Linux 知识图谱' ops/linux/07-security/security-and-permission.md`，Expected: 头部三行齐全。

- [ ] **Step 3: 提交**

```bash
git add ops/linux/07-security/security-and-permission.md
git commit -m "docs(linux): 新增安全与权限"
```

---

## Task 9: 08-shell/shell-and-scripting.md（Shell 与脚本）

**Files:**
- Create: `ops/linux/08-shell/shell-and-scripting.md`

**核心考点**：Bash 启动文件层级、三剑客（grep/sed/awk）、进程替换与管道、环境变量作用域、信号与 trap、here doc 与子 shell、set -euo pipefail

- [ ] **Step 1: 编写文档**

**头部**：一句话定位"Shell 是 Linux 的交互层，三剑客与 set -euo pipefail 是脚本健壮性的核心"

**一、概述**：Shell 在 Linux 体系中的位置；与 02 进程（子 shell）、07 安全（sudoers）的边界；关键术语表（Bash/sh/login shell/non-login shell/交互式/非交互式/管道/子 shell/进程替换/here doc/环境变量/位置参数/特殊变量）

**二、核心机制**：
- Bash 启动文件加载顺序图（mermaid flowchart）：login shell（`/etc/profile` → `~/.bash_profile`/`~/.bash_login`/`~/.profile` → `~/.bash_logout`）vs non-login shell（`/etc/bash.bashrc` → `~/.bashrc`），交互式 vs 非交互式
- 管道与子 shell：`|` 创建子 shell，`$()` 与反引号，进程替换 `<()` `>()`
- 环境变量作用域：`export` 才能被子进程继承，`source`/`.` 在当前 shell 执行
- 三剑客对比表：`grep`（行过滤）/`sed`（行编辑）/`awk`（列处理），各自擅长场景
- here doc 与 here string：`<<EOF` vs `<<<`
- `set -euo pipefail` 各选项：`-e` 错误即退/`-u` 未定义变量报错/`-o pipefail` 管道失败传递/`-x` 调试
- 信号与 trap：`trap 'cleanup' EXIT INT TERM`，在脚本中捕获信号做清理

**三、命令与示例**：
- 命令族速查表：`grep`（-r/-v/-E/-o/-A/-B/-C）、`sed`（-i/-n/-e s/A/B/g）、`awk`（-F/-v/-f）、`find`（-name/-type/-mtime/-exec）、`xargs`（-I/-n/-P）、`cut`/`sort`/`uniq`/`tr`/`tee`/`paste`/`column`
- 实战 one-liner：`grep -rn 'pattern' .`、`sed -i 's/old/new/g' file`、`awk -F: '{print $1,$3}' /etc/passwd | sort -k2 -n`、`find . -name '*.log' -mtime +7 -exec gzip {} \;`、`ps -eo pid,rss,cmd --sort=-rss | head -10 | awk '{printf "%s %.0fMB %s\n",$1,$2/1024,$3}'`
- 脚本模板：含 `set -euo pipefail` + `trap` 清理的健壮脚本骨架

**四、高频追问**（10-12 题）：
- Q1: login shell 和 non-login shell 的区别？加载哪些文件？
- Q2: 为什么要 `export`？不 export 子进程能拿到吗？
- Q3: source 和 ./script.sh 有什么区别？
- Q4: 管道会创建子 shell 吗？为什么管道里的变量改了不生效？
- Q5: 进程替换 `<()` 是什么？解决什么问题？
- Q6: grep/sed/awk 各自擅长什么？
- Q7: `set -euo pipefail` 各是什么意思？什么时候不适用？
- Q8: here doc 和 here string 的区别？
- Q9: 怎么写一个能在 Ctrl+C 时清理临时文件的脚本？
- Q10: `xargs -P` 并发执行的陷阱？
- Q11: 怎么让一个脚本既能交互式跑又能 cron 跑？
- Q12: Bash 脚本里怎么处理错误码？`||` 和 `&&` 的短路怎么用？

**五、Java/容器关联**：
- Dockerfile ENTRYPOINT 与 Shell 协作（关联 `ops/docker`）
- `kubectl` 排障 one-liner：`kubectl get pods -o wide | awk '{print $1,$3,$7}'`（关联 `ops/k8s`）
- Java 启动脚本模板（`set -euo` + JVM 参数 + trap 优雅关闭）
- `jps`/`jstack`/`jcmd` 输出用 awk 解析
- 实战映射表

**六、故障排查案例**：
- 案例：cron 任务里脚本跑失败，定位是 non-login shell 没加载 `~/.bashrc` 的 PATH
- 案例：管道里的变量改动不生效，用 `while read; do; done < <(cmd)` 进程替换替代

- [ ] **Step 2: 体量与结构校验**

Run: `wc -l ops/linux/08-shell/shell-and-scripting.md`，Expected: 500-800 行。
Run: `grep -c '^## ' ops/linux/08-shell/shell-and-scripting.md`，Expected: 6。
Run: `grep '一句话定位\|面试热度\|返回.*Linux 知识图谱' ops/linux/08-shell/shell-and-scripting.md`，Expected: 头部三行齐全。

- [ ] **Step 3: 提交**

```bash
git add ops/linux/08-shell/shell-and-scripting.md
git commit -m "docs(linux): 新增 Shell 与脚本"
```

---

## Task 10: 09-ops/performance-and-troubleshooting.md（性能与故障排查）

**Files:**
- Create: `ops/linux/09-ops/performance-and-troubleshooting.md`

**核心考点**：USE/RED 方法论、top/vmstat/iostat/sar、perf top/record/report、strace/-e trace、tcpdump/wireshark、eBPF/bpftrace、排障四步法

- [ ] **Step 1: 编写文档**

**头部**：一句话定位"性能排查方法论比工具更重要，USE/RED 模型 + 排障四步法是面试加分项"

**一、概述**：性能与故障排查在 Linux 体系中的位置；与前面所有主题（02 进程/03 内存/04 IO/06 网络）的关联（这里是它们的综合应用）；关键术语表（USE/RED/load average/CPU utilization/CPU load/softirq/hardirq/sys/usr/iowait/steal/perf/strace/eBPF/bpftrace/BCC）

**二、核心机制**：
- USE 方法论：Utilization（使用率）/Saturation（饱和度）/Errors（错误）三维度，对每个资源（CPU/内存/磁盘/网络）分别看
- RED 方法论：Rate（请求率）/Errors（错误）/Duration（延迟），面向服务
- load average 三数含义（1/5/15 分钟平均运行队列长度），与 CPU 核数的关系
- CPU 各状态：usr/sys/idle/iowait/steal/softirq/hardirq，top/vmstat 解读
- perf 工作原理：`perf record` 采样 + `perf report` 分析，`perf top` 实时
- strace 原理：`ptrace` 系统调用，`-e trace=` 过滤，性能开销
- eBPF：内核可编程，`bpftrace` one-liner，BCC 工具集，比 strace 性能好
- 排障四步法图（mermaid flowchart）：现象 → 假设 → 验证 → 根因，每步用对应工具

**三、命令与示例**：
- 命令族速查表：`top`/`htop`/`atop`、`vmstat`（-w/s/m/d）、`mpstat`（-P ALL）、`iostat`（-xmt）、`sar`（-u/-r/-d/-n）、`free`、`pidstat`（-d/-r/-u）、`perf`（top/record/report/stat）、`strace`（-p/-e/-c/-f）、`tcpdump`、`ss`/`netstat`、`bpftrace`/`BCC` 工具（`execsnoop`/`opensnoop`/`runqlat`/`biosnoop`）
- 实战 one-liner：`vmstat 1`、`iostat -xmt 1`、`pidstat -urd 1`、`perf top -p <pid>`、`strace -p <pid> -e trace=read,write -c`、`tcpdump -i eth0 -nn 'port 80' -c 100 -w out.pcap`
- bpftrace one-liner：`bpftrace -e 'tracepoint:syscalls:sys_enter_openat { @[comm] = count(); }'`、`execsnoop`、`runqlat`

**四、高频追问**（10-12 题）：
- Q1: load average 是什么？和 CPU 使用率有什么区别？
- Q2: USE 和 RED 方法论是什么？分别适用什么场景？
- Q3: top 里的 wa（iowait）高说明什么？怎么排查？
- Q4: vmstat 各列什么意思？r 列高说明什么？b 列高说明什么？
- Q5: iostat 的 %util 和 await 各代表什么？SSD 该多少？
- Q6: perf 是什么？怎么定位一个进程的 CPU 热点？
- Q7: strace 的原理是什么？为什么生产慎用？
- Q8: eBPF 是什么？比 strace 强在哪？
- Q9: 怎么抓 TCP 包分析慢请求？
- Q10: 一个 Java 服务 CPU 100%，完整的排查链是什么？
- Q11: 一个 Java 服务内存涨，怎么区分是堆泄漏还是堆外？
- Q12: 怎么排查网络偶发延迟？用什么工具？

**五、Java/容器关联**：
- JMX 指标采集与 Prometheus 暴露（关联 `java-core/jmx`）
- Java agent attach 排障与 Arthas 原理（关联 `java-core/agent`）
- 容器内 top 看到的是宿主 CPU/内存，cgroup 限制下的真实负载（关联 `ops/docker`、`ops/k8s`）
- JVM 性能排查工具链：`jcmd`/`jstack`/`jmap`/`arthas` 与 Linux 工具的配合
- 实战映射表

**六、故障排查案例**：
- 案例：Java 服务 CPU 飙升，完整排障链：`top` 定位进程 → `top -H -p` 定位线程 → `printf '%x\n'` → `jstack` → 定位死循环代码
- 案例：容器内服务偶发超时，`bpftrace` 定位 cgroup 调度延迟 + iowait 高，根因是宿主磁盘抖动

- [ ] **Step 2: 体量与结构校验**

Run: `wc -l ops/linux/09-ops/performance-and-troubleshooting.md`，Expected: 500-800 行。
Run: `grep -c '^## ' ops/linux/09-ops/performance-and-troubleshooting.md`，Expected: 6。
Run: `grep '一句话定位\|面试热度\|返回.*Linux 知识图谱' ops/linux/09-ops/performance-and-troubleshooting.md`，Expected: 头部三行齐全。

- [ ] **Step 3: 提交**

```bash
git add ops/linux/09-ops/performance-and-troubleshooting.md
git commit -m "docs(linux): 新增性能与故障排查"
```

---

## Task 11: 10-interview-qa.md（面试 Q&A 速答）

**Files:**
- Create: `ops/linux/10-interview-qa.md`

**核心考点**：50+ 高频题速答 + 连环套问思维导图（按主题串联，如"epoll→Reactor→Netty→线程模型"）

- [ ] **Step 1: 编写文档**

参考 `ops/docker/09-interview-qa.md` 的风格：按主题分类，每题 3-5 句要点速答，末尾加 `**关联**：→ [对应主题文档](./0X-xxx/xxx.md)` 链接。连环追问题在题号后标 🔗。

**头部**：
```
# 跨主题高频面试 Q&A

> **一句话定位**：面试前冲刺用，50+ 题速答串联各主题，附连环套问思维导图。
> **面试热度**：⭐⭐⭐⭐⭐
> **返回**：[Linux 知识图谱](../README.md)
```

**使用说明**（参考 docker Q&A 风格）：
- 全部 50+ 题按主题分类，每题 3-5 句要点速答，末尾 **关联** 链接指向对应主题文档的详细推导
- 连环追问题在题号后标注 🔗，配合文末「连环套问思维导图」把握面试官的追问路径
- 建议先盖住答案自答，再对照要点查漏，最后跳转关联文档补全原理

**题目分组**（每组 5-6 题，总计 50+ 题）：

1. **一、启动与运行时篇**（5 题）：启动流程、systemd vs SysV、Unit 类型、cgroup v1/v2、initramfs
2. **二、进程与线程篇**（6 题）：进程状态、D 状态、僵尸进程、CFS、PID 1 陷阱、线程 vs 进程
3. **三、内存管理篇**（6 题）：虚拟内存、缺页中断、swap/swappiness、OOM killer 选主、RSS/PSS/USS、容器内 free
4. **四、IO 模型篇**（6 题）：5 种 IO 模型、select/poll/epoll、LT/ET、Reactor、零拷贝、page cache
5. **五、文件系统篇**（5 题）：VFS 四对象、硬软链接、OverlayFS、/proc、fsync
6. **六、网络内核篇**（6 题）：netfilter 五钩子、iptables 表链、conntrack、accept 队列、NAPI、策略路由
7. **七、安全与权限篇**（5 题）：Capability、SELinux vs AppArmor、seccomp、SUID、PAM
8. **八、Shell 与脚本篇**（5 题）：login vs non-login、export、管道子 shell、进程替换、set -euo pipefail
9. **九、性能与排障篇**（6 题）：load average、USE/RED、iowait 高、perf、strace/eBPF、Java CPU 100% 排查链

**连环套问思维导图**（mermaid mindmap）：按主题串联，例如：
- 启动链：BIOS → Bootloader → kernel → systemd → cgroup v1/v2 → JVM 容器感知
- 进程链：进程状态 → 僵尸进程 → PID 1 陷阱 → 信号 → JVM ShutdownHook → 容器优雅关闭
- IO 链：5 种 IO 模型 → epoll → LT/ET → Reactor → Netty → 线程模型
- 内存链：虚拟内存 → page cache → cgroup memory → OOM killer → JVM 堆感知 → 容器 OOM
- 排障链：top → vmstat → iostat → perf → strace/eBPF → jstack/jmap

每题格式示例（参考 docker Q&A）：
```markdown
### Q1: Linux 启动流程？🔗

**答**：从按下电源到 login 提示符，经历四阶段：①BIOS/UEFI POST 自检并加载 Bootloader（grub2）；②Bootloader 加载 kernel 与 initramfs 到内存，kernel 自解压并初始化；③kernel 挂载 rootfs 并 exec 第一个用户态进程 init（systemd，PID 1）；④systemd 根据 default.target 拉起多组服务（网络/SSH/登录等），最终呈现 login 提示符。关键点：initramfs 是临时根文件系统，提供 kernel 启动早期所需的驱动和工具；systemd 替代 SysV init 实现并行启动和依赖管理。

**关联**：→ [系统启动与运行时](./01-foundation/system-boot-and-runtime.md)
```

- [ ] **Step 2: 体量与结构校验**

Run: `wc -l ops/linux/10-interview-qa.md`，Expected: 400-600 行（Q&A 文档可略短于主题文档）。
Run: `grep -c '^### Q' ops/linux/10-interview-qa.md`，Expected: ≥ 50（50+ 题）。
Run: `grep -c '关联.*\.md' ops/linux/10-interview-qa.md`，Expected: ≥ 50（每题都有关联链接）。
Run: `grep '连环套问思维导图\|mindmap' ops/linux/10-interview-qa.md`，Expected: 末尾含思维导图。

- [ ] **Step 3: 全模块链接可达性校验**

```bash
# 校验 README 导航表所有链接可达
for link in $(grep -oP '\./[^)]+' ops/linux/README.md); do test -f "ops/linux/${link#./}" || echo "BROKEN: $link"; done
# 校验 Q&A 文档所有关联链接可达
for link in $(grep -oP '\./[^)]+' ops/linux/10-interview-qa.md); do test -f "ops/linux/${link#./}" || echo "BROKEN: $link"; done
```
Expected: 无 BROKEN 输出（所有链接可达）。

- [ ] **Step 4: 提交**

```bash
git add ops/linux/10-interview-qa.md
git commit -m "docs(linux): 新增跨主题高频面试 Q&A"
```

---

## Task 12: 全模块验收

**Files:**
- Verify: `ops/linux/` 整个目录

- [ ] **Step 1: 文档清单完整性校验**

```bash
ls ops/linux/README.md ops/linux/01-foundation/system-boot-and-runtime.md ops/linux/02-process/process-and-thread.md ops/linux/03-memory/memory-management.md ops/linux/04-io/io-model-and-epoll.md ops/linux/05-fs/filesystem-and-vfs.md ops/linux/06-network/network-kernel.md ops/linux/07-security/security-and-permission.md ops/linux/08-shell/shell-and-scripting.md ops/linux/09-ops/performance-and-troubleshooting.md ops/linux/10-interview-qa.md
```
Expected: 11 个文件全部存在。

- [ ] **Step 2: 每份主题文档六段式校验**

```bash
for f in ops/linux/0*/<topic>.md; do
  echo "=== $f ==="
  grep -c '^## ' "$f"  # 应为 6
  grep '一句话定位\|面试热度\|返回.*Linux 知识图谱' "$f"  # 头部三行
  wc -l "$f"  # 500-800 行
done
```
Expected: 9 份主题文档各 6 段、头部三行齐全、500-800 行。

- [ ] **Step 3: 全模块链接可达性校验**

```bash
# 所有文档间的链接都可达
grep -rP '\[.+\]\(\./[^)]+\)' ops/linux/ --include='*.md' | grep -oP '\./[^)]+' | sort -u | while read link; do
  base=$(dirname "${link}")
  target=$(basename "${link}")
  test -f "ops/linux/${base}/${target}" || test -f "ops/linux/${link#./}" || echo "BROKEN: $link"
done
```
Expected: 无 BROKEN 输出。

- [ ] **Step 4: README 知识图谱与导航表完整性校验**

```bash
grep -c '^|' ops/linux/README.md  # 导航表行数（含表头）
grep 'mindmap' ops/linux/README.md  # 知识图谱存在
```
Expected: 导航表 11+ 行，知识图谱含 mermaid mindmap。

- [ ] **Step 5: 最终提交（如有修复）**

如有任何修复，提交：
```bash
git add ops/linux/
git commit -m "docs(linux): Linux 模块全文档验收修复"
```

无修复则跳过。

---

## Self-Review

完成计划编写后逐项检查：

1. **Spec 覆盖**：spec 第二章目录结构 11 份文档 → Task 1-11 各对应一份。spec 第四章核心考点 → 每个 Task 的"核心考点"段。spec 第五章 Java 关联 → 每个 Task 的第五段。spec 第六章 README 结构 → Task 1。spec 第七章四阶段输出节奏 → Task 1-2 阶段一、Task 3-6 阶段二、Task 7-9 阶段三、Task 10-11 阶段四（注：原 spec 阶段一含 Q&A，计划把 Q&A 放最后以保证关联链接可达，已在 Task 11 Step 3 全模块链接校验）。spec 第九章验收标准 → Task 12。✅

2. **占位符扫描**：无 TBD/TODO/实现细节缺失。每段内容要点具体到"关键术语表/源码路径/mermaid 图类型/对比表字段/命令族清单/追问问题清单/案例场景"。✅

3. **一致性检查**：
   - 文件路径在 Task 间的引用一致（`./01-foundation/system-boot-and-runtime.md` 在 README、Q&A、各主题"参见"链接中一致）。✅
   - 六段式结构在 Global Constraints、Task 模板、各 Task Step 1 内容要点、Task 12 校验中一致。✅
   - 头部三行格式在 Global Constraints、各 Task Step 1 头部、Task 12 Step 2 校验中一致。✅
   - 体量 500-800 行在 Global Constraints、各 Task Step 2、Task 12 Step 2 一致。Q&A 略短（400-600），已在 Task 11 Step 2 说明。✅

无修改需要。
