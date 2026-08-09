# Docker 面试知识体系 — 设计文档

> **创建日期**：2026-08-09
> **作者**：zihao
> **状态**：已确认，待写实现计划
> **适用对象**：Java 后端工程师面试（社招中高级）

---

## 一、目标与范围

### 1.1 目标

为 Java 后端工程师面试构建一套**结构化、系统化、有深度**的 Docker 知识文档体系，作为长期学习与面试冲刺的统一参考。

### 1.2 覆盖范围

- **面试核心**：容器本质（namespace/cgroups/unionfs/OCI）、镜像构建与分发、容器运行时与生命周期、网络模型、存储模型、Compose 编排、安全加固、Java 容器调优。
- **实战关联**：Spring Boot 应用容器化、JVM 容器感知、镜像分层与启动优化、ZGC 选型、密钥注入、优雅关闭。
- **与 Java 模块联动**：关联 `java-core/jvm`、`framework/spring-framework`、`framework/jackson`、`framework/valid`、`java-core/agent` 等。

### 1.3 深度标准

采用**面试宝典型**：每个知识点按五段式展开——概念定义 → 原理与流程 → 高频追问与面试题 → 实战关联（Java 后端视角） → 面试案例。Q&A 篇不套五段式，采用速答列表 + 连环套问思维导图。

### 1.4 交付方式

一次性全量交付 10 份 Markdown 文档（1 入口 + 8 主题 + 1 Q&A）。

### 1.5 设计决策记录

| 决策点 | 选择 | 理由 |
|--------|------|------|
| 核心定位 | 纯面试导向 | 对标 network 模块，面试热度标注、高频追问、连环套问 |
| 组织方式 | 分层目录多文件 | 与 network 模块风格一致，便于检索与增量扩充 |
| 覆盖范围 | 纯 Docker | K8s 由独立模块负责，Swarm 已退场仅标注边界 |
| 深度级别 | 系统原理级 | 讲 what/why/how，含源码路径、时序图、底层原理 |
| 实战比例 | Java 后端视角 | 与仓库 java-core/framework 模块联动 |
| Q&A 汇总 | 独立一篇 | 对标 network 的 06-interview-qa.md |
| 结构方案 | 方案 A（按架构层次切分） | 8 主题目录 + 1 Q&A，与 network 风格一致 |
| Compose 示例 | 关键点注释 | 聚焦考点，字段全集放在原理表格 |
| ZGC | 补充 | 容器内 GC 选型决策树、分代 ZGC、染色指针堆外预算 |

---

## 二、目录结构

在 `ops/docker/` 下按 Docker 架构层次组织，共 8 个主题目录 + 1 个 Q&A 文件 + 1 个入口 README。

```
ops/docker/
├── README.md                                  # 入口：简介 + 知识图谱(Mermaid) + 导航表 + 学习路径 + Java 模块关联
│
├── 01-foundation/                             # 容器基础
│   └── container-principle.md                 # 容器本质：namespace/cgroups/unionfs/OCI/runtime 调用链
│
├── 02-image/                                  # 镜像与构建
│   └── dockerfile-and-image.md               # Dockerfile 指令/构建上下文/缓存/多阶段/OCI 格式/Registry
│
├── 03-container/                              # 容器运行
│   └── container-runtime.md                  # 生命周期/状态机/PID 1/日志驱动/健康检查
│
├── 04-network/                                # 网络
│   └── docker-network.md                     # bridge/host/overlay/veth/iptables/CNM/DNS 发现
│
├── 05-storage/                                # 存储
│   └── docker-storage.md                      # OverlayFS/volume/bind/tmpfs/whiteout/驱动选型
│
├── 06-compose/                                # Compose 编排
│   └── docker-compose.md                      # YAML 结构/depends_on 陷阱/V2 升级/Kompose 边界
│
├── 07-security/                               # 安全
│   └── docker-security.md                    # capabilities/seccomp/userns-remap/rootless/镜像扫描
│
├── 08-performance/                            # Java 容器调优
│   └── java-container-tuning.md              # JVM 感知/堆外预算/Layertools/Jib/ZGC 选型
│
└── 09-interview-qa.md                         # 40 题速答 + 连环套问思维导图
```

共 **10 份**文档：入口 README（本文档体系入口）+ 上表 9 份主题/汇总文档。

---

## 三、统一风格约定

### 3.1 主题文档顶部模板

```markdown
# 标题

> **一句话定位**：xxx
> **面试热度**：⭐⭐⭐⭐⭐
> **返回**：[Docker 知识图谱](../README.md)

---
```

### 3.2 五段式结构（主题文档）

1. **概念定义**：含对比表、核心概念关系
2. **原理与流程**：含 mermaid 图、源码路径、时序图、调用链
3. **高频追问**：每题 3-5 句要点
4. **实战关联（Java 后端视角）**：关联仓库 Java 模块，示例代码用关键点注释（非字段级注释）
5. **面试案例**：3 分钟标准答法 + 追问链

### 3.3 Q&A 篇结构

不套五段式，采用：
- 使用说明
- 按主题分类的 Q&A 列表（每题 3-5 句要点 + 关联链接，连环追问标 🔗）
- 连环套问思维导图（mermaid mindmap）

---

## 四、各文档内容设计

### 4.1 README.md（模块入口）

**五大板块**：

1. **模块简介**：定位（面向 Java 后端面试的 Docker 知识体系）、适用对象、组织方式（8 个主题目录 + 1 个 Q&A 文件）、导航约定（顶部 `> 返回 [Docker 知识图谱](../README.md)` 链接）。

2. **知识图谱（Mermaid mindmap）**：根节点 `Docker`，9 大分支：
   - 容器基础：容器本质（namespace/cgroups/unionfs）、OCI 标准、运行时组件（runc/containerd/moby/dockerd）
   - 镜像与构建：Dockerfile 指令、构建上下文、镜像分层、多阶段构建、镜像分发（Registry/Manifest）
   - 容器运行：生命周期、资源限制 cgroup v1/v2、运行时陷阱、重启策略、日志驱动
   - 网络：bridge/host/none/overlay/macvlan、veth pair、iptables、CNM 模型、端口映射原理
   - 存储：volume/bind mount/tmpfs、OverlayFS、存储驱动选型、数据持久化
   - Compose：多容器编排、健康检查、依赖顺序、与 K8s 边界
   - 安全：root/user namespace、capabilities、seccomp/AppArmor、镜像扫描、密钥注入
   - Java 容器调优：JVM 容器感知、Jib、Spring Boot Layertools、分层镜像、ZGC 选型
   - 面试冲刺：40 题速答、连环套问思维导图

3. **导航表**：分层 | 文档 | 核心考点，9 行对应 9 份文档。

4. **两条学习路径**：
   - 路线一（系统学习）：01 基础 → 02 镜像 → 03 容器 → 04 网络 → 05 存储 → 06 Compose → 07 安全 → 08 Java 调优 → 09 Q&A
   - 路线二（面试冲刺）：03 容器运行 → 02 镜像 → 01 基础 → 04 网络 → 08 Java 调优 → 05 存储 → 07 安全 → 06 Compose → 09 Q&A

5. **与 Java 模块的关联表**：仿 network 模块第 5 节，列出 Docker 知识点与 java-core/framework 模块的关联要点。

### 4.2 01-foundation/container-principle.md（容器本质与底层原理）

**定位**：⭐⭐⭐⭐⭐，整章体系的根基，面试官最爱"讲讲容器原理"的入口题。

1. **概念定义**
   - 容器 vs 虚拟机对比表（隔离层级、资源开销、启动时间、安全边界、镜像体积、跨平台能力）
   - 容器的本质：受控的进程——通过 Linux 内核机制实现"轻量级隔离"，无 Guest OS
   - 三大基石：namespace（隔离视图）、cgroups（限制资源）、unionfs（分层文件系统）

2. **原理与流程**
   - **Namespace**：6+1 个 namespace 全表（PID/NET/MNT/IPC/UTS/USER/CGROUP），每个标注"隔离什么、典型命令、面试追问点"；重点讲 PID namespace 的"进程 1 与信号"、USER namespace 的 UID 映射陷阱
   - **Cgroups**：v1（controller-based，层级挂载）vs v2（统一层级，psí）对比；资源子系统（cpu/cpuacct/memory/blkio/pids）；docker run 参数与 cgroup 文件映射；重点讲 memory cgroup 的 OOM Killer 触发链与 memory.failcnt
   - **UnionFS / OverlayFS**：lowerdir/upperdir/workdir/merged 四层结构图；写时复制（CoW）原理；为什么 OverlayFS 替代 AUFS（性能、mainline）
   - **OCI 标准**：OCI Image Spec（manifest/config/layer）+ OCI Runtime Spec（config.json/bundle）+ OCI Distribution Spec；runc 作为 reference runtime 的地位
   - **Docker 架构与运行时组件**：dockerd / containerd / containerd-shim / runc 四层调用链时序图（mermaid sequenceDiagram），重点讲"为什么需要 shim"（容器父进程不挂靠 dockerd，daemon 重启不影响容器）
   - **容器创建全流程**：`docker run` → API 接收 → containerd 创建 task → shim 启动 runc → namespace/cgroups 设置 → entrypoint 执行（mermaid 流程图）

3. **高频追问**
   - 容器和虚拟机能同时跑吗？（嵌套虚拟化 + 云原生场景）
   - 为什么容器是"进程级"隔离？安全吗？（逃逸案例：dirty COW、runc CVE-2019-5736）
   - Docker 进程死了，容器会死吗？（shim 设计）
   - cgroup v1 和 v2 的区别对 Java 有什么影响？（部分 JDK 老版本读 v2 失败导致内存限制失效）
   - OverlayFS 与 bind mount 的差异？为什么 volume 比 bind mount 更安全？

4. **实战关联（Java 后端视角）**
   - Spring Boot 应用打包为镜像后，JVM 看到的"CPU 数"和"内存上限"如何被 namespace/cgroups 改写
   - 关联 `java-core/jvm`：JVM 在容器内的内存感知（-XX:+UseContainerSupport、cgroup v1/v2 的探测代码路径），引出第 8 章详细推导
   - 关联 `framework/spring-framework`：Spring 应用启动时的 PID 1 与 SIGTERM 优雅关闭问题（dumb-init/tini 的作用）

5. **面试案例**
   - "讲讲你对 Docker 容器原理的理解"——3 分钟标准答法结构（三大机制 → OCI → 调用链 → 与 VM 的差异）
   - "Docker daemon 重启，容器会不会死？"——shim 设计的追问链

### 4.3 02-image/dockerfile-and-image.md（镜像构建与分发）

**定位**：⭐⭐⭐⭐⭐，面试高频，"写个 Dockerfile"是起手题。

1. **概念定义**
   - 镜像本质：分层只读文件系统的快照，由 UnionFS 叠加而成；镜像不是"文件"，是"层 + 元数据"的组合
   - 三大核心概念关系：image（模板）→ container（运行实例）→ registry（分发仓库）
   - 镜像内部结构：manifest（清单）+ config（配置）+ layer（层 tar 包），对应 OCI Image Spec
   - 层 layer 的本质：每个 layer 是一个 tar.gz，记录相对上层的文件变更（add/modify/delete，通过 whiteout 文件标记删除）

2. **原理与流程**
   - **Dockerfile 指令全解**（按使用频率分组）：
     - 基础类：FROM（多 stage、--platform）、ARG、LABEL
     - 执行类：RUN（shell/exec 两种形式差异）、CMD vs ENTRYPOINT（重点表格对比 + 组合矩阵：都有/只 CMD/只 ENTRYPOINT/都无）
     - 文件类：COPY vs ADD（ADD 自动解压/远程 URL 的坑，推荐 COPY）、WORKDIR、VOLUME
     - 环境类：ENV、EXPOSE、USER、HEALTHCHECK
     - 构建类：ONBUILD（已不推荐）、SHELL、STOPSIGNAL
   - **构建上下文 Build Context**：`.dockerignore` 必要性（避免大目录被打包到 daemon）；构建上下文大小对构建速度的影响；远程上下文（git URL、tar URL）
   - **构建缓存与分层原理**（深度重点）：
     - 每条指令产生一个 layer；指令顺序决定缓存命中率
     - 缓存失效规则：指令变 / 上下文文件变（校验 tar 摘要）/ 父层变 → 该层及后续全部失效
     - 缓存优化实践：先 COPY pom.xml → 下载依赖 → 再 COPY src（Maven）的原理推导
     - BuildKit 新一代构建器：并行构建多 stage、`--mount=type=cache` 持久化缓存、`--mount=type=secret` 不留痕
   - **多阶段构建 Multi-stage Build**：
     - 动机：构建期需要 JDK/Maven，运行期只需 JRE
     - 语法：`FROM ... AS builder` → `COPY --from=builder`
     - 对镜像体积的影响（典型 400MB → 150MB）
     - 与 BuildKit `--target` 的配合
   - **镜像分发与 Registry 协议**：
     - Docker Registry HTTP API V2：push/pull 流程（manifest 先传 / layer 并行）
     - manifest list：多架构镜像（amd64/arm64）的分发机制
     - 镜像签名与可信分发（cosign / Notary v2 简介）
     - 镜像 GC 与存储回收（registry garbage-collect）

3. **高频追问**
   - CMD 和 ENTRYPOINT 的区别？都能被 `docker run` 覆盖吗？（覆盖矩阵 + 易错点：ENTRYPOINT 的 JSON 形式 vs shell 形式）
   - COPY 和 ADD 该用哪个？（官方推荐 COPY，ADD 仅在需自动解压时用）
   - 为什么我的 Dockerfile 构建很慢？缓存怎么失效了？（典型踩坑：先 COPY src 再 mvn install）
   - 镜像为什么这么大？怎么瘦小？（dive 工具 / 多阶段 / slim / distroless / alpine 的取舍）
   - `docker build` 和 `docker buildx build` 的区别？BuildKit 带来了什么？
   - 镜像的"层"存在哪？删除文件能减小镜像吗？（whiteout 文件原理，需要在最后一层删才有效，更稳妥用 squash/multi-stage）
   - 同一镜像在不同架构下怎么 pull？（manifest list）

4. **实战关联（Java 后端视角）**
   - **Spring Boot 应用 Dockerfile 最佳实践**：
     - 反面示例：单 stage 打 fat jar（每改一行代码，依赖层全部失效）
     - 正面示例（多 stage + 分层）：
       ```dockerfile
       FROM maven:3.8-eclipse-temurin-17 AS builder
       WORKDIR /app
       COPY pom.xml .
       RUN mvn dependency:go-offline       # 依赖层缓存，不随业务代码变更
       COPY src ./src
       RUN mvn package -DskipTests

       FROM eclipse-temurin:17-jre
       COPY --from=builder /app/target/*.jar /app/app.jar
       ENTRYPOINT ["java","-jar","/app/app.jar"]
       ```
     - 进阶：Spring Boot Layertools 分层（dependencies/spring-boot-loader/snapshot-dependencies/application），把不变层和易变层彻底分开，配合 BuildKit `--mount=type=cache` 把 Maven `~/.m2` 缓存跨构建复用
   - 关联 `framework/spring-framework`：Spring Boot 可执行 jar 的内部结构（BOOT-INF/classes vs BOOT-INF/lib）如何对应到分层
   - 关联 `framework/jackson`：镜像内的应用配置注入（环境变量 > JSON 配置文件的优先级）
   - distroless vs alpine vs temurin 的选型表（体积、调试工具、glibc vs musl、JDK 兼容性陷阱）

5. **面试案例**
   - "写一个 Spring Boot 的 Dockerfile"（白板题，重点考察分层与缓存）
   - "镜像 1.2GB，怎么减小到 200MB？"（多阶段 + distroless + slim）
   - "Dockerfile 改一行代码，为什么重新下载了所有依赖？"（缓存失效链追问）

### 4.4 03-container/container-runtime.md（容器运行时与生命周期）

**定位**：⭐⭐⭐⭐⭐，"docker run 后发生了什么"是面试连环追问的核心。

1. **概念定义**
   - 容器生命周期状态机：created → running → paused → stopped → deleted（mermaid stateDiagram）
   - 容器与镜像的关系：容器 = 镜像 + 可写层（upperdir）+ 运行时配置（cgroup/namespace/网络）
   - 容器配置的三层来源：镜像 Dockerfile（CMD/ENV/EXPOSE）→ docker run 参数（覆盖）→ 运行时动态（IP/挂载点）
   - 容器与进程的关系：容器是受 namespace/cgroups 约束的进程树，PID 1 的特殊性

2. **原理与流程**
   - **`docker run` 完整调用链**（深度重点，接第 1 章 shim 设计）：
     ```
     docker CLI → dockerd API → containerd 创建 container task
       → containerd-shim fork → runc create（设置 namespace/cgroups/根文件系统）
       → runc start → entrypoint 作为 PID 1
     ```
     mermaid sequenceDiagram 绘制（CLI/dockerd/containerd/shim/runc/内核）
   - **容器状态转换全解**：
     - created：runc create 已设置环境但未 start（CRI 的"容器已创建未启动"状态）
     - running：runc start 后，PID 1 执行
     - paused：cgroup freezer 子系统冻结所有进程（不是 SIGSTOP，是内核级冻结）
     - stopped：PID 1 退出或收到 SIGKILL/SIGTERM；可写层仍保留，可 restart
     - deleted：runc delete 清理 namespace/cgroups，可写层回收
   - **重启策略 Restart Policy**（表格）：
     - no（默认）/ on-failure[:max] / always / unless-stopped
     - 退出码语义：非 0 退出算 failure；always 和 unless-stopped 的区别在 daemon 重启时是否拉起
     - 与 `docker run --restart` 的协同；重启计数的重置时机
   - **PID 1 与信号处理**（深度重点）：
     - PID 1 的特殊性：内核默认不向 PID 1 转发 SIGTERM（除非显式注册 handler）
     - Java 应用的典型坑：`java -jar app.jar` 作为 PID 1，收到 SIGTERM 不响应优雅关闭
     - 解决方案对比表：tini / dumb-init / bash -c "exec java" / Spring Boot 2.4+ 的 `SIGTERM` 优雅关闭
     - STOPSIGNAL 指令与 `docker stop` 默认 10 秒超时后 SIGKILL 的链路
   - **日志驱动 Log Driver**：
     - json-file（默认，默认 100MB 单文件、轮转 1 个，会撑爆磁盘）/ journald / syslog / fluentd / gelf / none
     - `docker logs` 仅对 json-file/journald 生效；双缓冲与实时性
     - 生产推荐：json-file + 轮转参数，或直接走 fluentd/gelf 到 ELK
   - **健康检查 Healthcheck**：
     - HEALTHCHECK 指令与 `--health-cmd`；start-period / interval / timeout / retries
     - health status: starting → healthy → unhealthy → none
     - unhealthy 不会自动重启容器，需配合 restart policy 或编排层（Compose/K8s）处理
   - **容器资源限制入门**（详细推导放第 8 章 Java 调优，这里讲机制）：
     - `-m / --memory`：memory.limit_in_bytes；-m 与 --memory-swap 的关系
     - `--cpus`（cgroup v2 cpu.max）/ `-cp`（相对权重，用于争抢场景）
     - OOM 时的行为：memory cgroup OOM Killer 杀死 PID 1，--oom-kill-disable 的危险

3. **高频追问**
   - `docker run` 之后到底发生了什么？（完整调用链）
   - `docker stop` 和 `docker kill` 的区别？（SIGTERM+超时 vs SIGKILL）
   - 为什么 Java 应用 `docker stop` 后要等 10 秒才死？（PID 1 信号陷阱）
   - 容器 paused 后还能被访问吗？（freezer 原理 + 网络连接的坑）
   - `docker run -d` 后容器为什么立刻退出了？（CMD 是 shell 形式 / 前台 vs 后台进程）
   - always 和 unless-stopped 在什么场景下不一样？（daemon 重启）
   - `--restart=on-failure:5` 的 5 是什么意思？计数什么时候清零？
   - 容器的日志在哪？怎么轮转？（json-file 默认坑）
   - HEALTHCHECK unhealthy 为什么不会重启容器？怎么解决？

4. **实战关联（Java 后端视角）**
   - **Spring Boot 容器优雅关闭**：
     - Spring Boot 2.4+ `server.shutdown=graceful` + `spring.lifecycle.timeout-per-shutdown-phase` 的配合
     - Dockerfile 标配：`STOPSIGNAL SIGTERM` + 合理的 `--stop-timeout`
     - PID 1 问题的解决方案对比：用 tini 做 init（`docker run --init`）vs Spring Boot 内建优雅关闭
   - 关联 `framework/spring-framework`：Spring 的 ContextClosedEvent 与 Servlet 容器的 shutdown hook 执行顺序
   - 关联 `java-core/jvm`：JVM ShutdownHook 在容器里的执行时机与 SIGTERM 丢失的踩坑
   - JVM 进程作为 PID 1 的 `XX:+UseContainerSupport` 之外的隐藏坑：Runtime.getRuntime().availableProcessors() 与 cgroup cpu 限制
   - 容器内拿到的 CPU 数与 thread pool 配置的陷阱（Tomcat maxThreads 按宿主机 CPU 配）

5. **面试案例**
   - "docker run 之后发生了什么？"（调用链时序图，3 分钟标准答法）
   - "你的 Spring Boot 应用 `docker stop` 后立刻被 SIGKILL，怎么排查？"（PID 1 信号陷阱 + STOPSIGNAL + timeout）
   - "容器日志把磁盘写满，怎么排查和处理？"（json-file 默认配置 + 轮转方案）

### 4.5 04-network/docker-network.md（Docker 网络模型）

**定位**：⭐⭐⭐⭐，与 `ops/network` 模块强联动，面试高频追问。

1. **概念定义**
   - Docker 网络的本质：基于 Linux 虚拟网络设备（veth pair / bridge / iptables）实现的二层隔离
   - CNM（Container Network Model）三要素：Sandbox（namespace）→ Endpoint（veth）→ Network（bridge）
   - 与 K8s CNI 的边界：CNM 是 Docker 自有模型，CNI 是 CNCF 标准；本章只讲 CNM，K8s 看独立模块
   - 五大内置网络驱动一览表：bridge / host / none / overlay / macvlan（作用、适用场景、隔离级别）

2. **原理与流程**
   - **bridge 网络（默认，深度重点）**：
     - docker0 网桥的本质：Linux bridge，二层转发，无路由能力
     - 容器接入流程：创建 veth pair → 一端入容器 eth0（netns）→ 一端挂到 docker0 → 分配 172.17.0.0/16 子网 IP
     - 容器间通信：同 bridge 直接二层转发；跨 bridge 需要路由（默认不通）
     - NAT 出网链路：容器 → docker0 → iptables MASQUERADE（POSTROUTING）→ eth0 出网
     - 端口映射原理：`-p 8080:80` → iptables DNAT（PREROUTING + OUTPUT）把宿主 8080 转到容器 80
     - iptables 规则完整链路图（mermaid flowchart：PREROUTING → DNAT → docker0 → 容器 → SNAT → POSTROUTING）
   - **host 网络**：
     - 容器直接使用宿主 netns，无 veth/docker0；性能最好但无隔离
     - 端口冲突陷阱：宿主已占用端口会启动失败
   - **none 网络**：仅有 lo 回环，完全隔离，用于安全基线与自定义网络栈
   - **overlay 网络（跨主机通信）**：
     - 动机：多主机容器需要二层互通
     - 关键组件：键值存储（etcd/consul）+ VXLAN 隧道
     - VXLAN 封装原理：原始 L2 帧封装进 UDP（默认 4789），跨主机传输后解封装
     - 容器跨主机通信流程时序图（mermaid sequenceDiagram）
     - 性能代价：MTU 缩小 50 字节、封解开销
   - **macvlan 网络**：
     - 容器直接获得宿主网段的 MAC 地址，绕过 docker0，性能近原生
     - 陷阱：宿主网卡需 promiscuous mode，多数云厂商/虚拟化平台禁用
   - **自定义网络与 DNS 发现**：
     - `docker network create` 自定义 bridge：自带内嵌 DNS server（127.0.0.11）
     - 容器名即域名：同自定义网络的容器可用容器名互访（默认 bridge 不支持 DNS）
     - 这一点是 Compose 多容器互访的基础（第 6 章衔接）
   - **网络与 namespace 的对应**：每个容器一个 netns，docker0 属于宿主 netns，veth 跨 netns 连接

3. **高频追问**
   - `docker run -p 8080:80` 之后网络数据流向是什么？（iptables 链路）
   - 为什么默认 bridge 下容器间不能用容器名通信，自定义 bridge 可以？（内嵌 DNS）
   - 容器访问外网走的是什么？（docker0 → SNAT）
   - 外部如何访问容器内服务？（DNAT 端口映射 / macvlan / host）
   - overlay 网络的 VXLAN 是什么？有什么性能代价？
   - 两个容器互相 ping 不通，怎么排查？（同 bridge？iptables FORWARD 默认 DROP？）
   - docker0 与宿主 eth0 的关系？（docker0 是独立网桥，通过 iptables 与 eth0 联通）
   - 为什么生产环境很少用 Docker 默认 bridge？（无 DNS、固定子网、单点）

4. **实战关联（Java 后端视角）**
   - **Spring Boot + MySQL 多容器互访**：
     - 反面：用 `--link`（已废弃，靠 /etc/hosts 注入，单向且不可重连）
     - 正面：自定义 bridge 网络，容器名 DNS 解析（`spring.datasource.url=jdbc:mysql://db:3306`）
     - 衔接到第 6 章 Compose 的 depends_on / networks 配置
   - 关联 `ops/network` 模块：
     - [TCP 连接管理](../network/02-transport/tcp-connection.md)：容器内服务端 TIME_WAIT 堆积与端口耗尽
     - [NAT](../network/03-network/nat.md)：docker0 的 SNAT 就是 NAPT，可对照四种 NAT 类型
     - [云原生网络](../network/05-system-design/cloud-native.md)：overlay/VXLAN 与 K8s CNI、Service Mesh 的边界
   - 关联 `framework/spring-framework`：Spring Boot 的 `server.address` 与容器网络绑定的坑（默认 0.0.0.0 才能被外部访问）
   - 关联 `framework/valid`：API 网关在容器内的端口暴露与健康检查端点设计

5. **面试案例**
   - "讲讲 Docker 的网络模型"（CNM → bridge 默认 → veth/iptables → DNS 发现，3 分钟答法）
   - "`docker run -p 8080:80` 后外部访问，数据流向是什么？"（iptables 完整链路）
   - "容器间互相访问怎么做？默认 bridge 行不行？"（DNS 发现 + 默认 bridge 无 DNS）
   - "overlay 网络怎么实现的跨主机通信？"（VXLAN 封装 + 键值存储）

### 4.6 05-storage/docker-storage.md（Docker 存储模型）

**定位**：⭐⭐⭐，相对网络章热度略低，但"数据丢了吗"是生产事故的高频根因。

1. **概念定义**
   - 容器存储的两层：只读镜像层（lowerdir）+ 可写容器层（upperdir）= merged 视图
   - 存储持久化的本质：可写层随容器删除而消失，需要挂载外部存储绕过 CoW
   - Docker 存储驱动一览表：overlay2（默认）/ overlay / aufs / devicemapper / btrfs / zfs / vfs（兼容性、性能、稳定性）
   - 三种数据挂载方式对比：volume / bind mount / tmpfs（管理方、生命周期、性能、跨主机、典型场景）

2. **原理与流程**
   - **OverlayFS 详解**（衔接第 1 章 unionfs，这里讲存储视角）：
     - 四层结构：lowerdir（只读镜像层，可多个）+ upperdir（可写容器层）+ workdir（OverlayFS 内部工作目录）+ merged（挂载点）
     - 写时复制（CoW）流程图：修改文件 → 复制到 upperdir → 修改副本 → lowerdir 原文件不变
     - 删除文件机制：在 upperdir 创建 whiteout 文件（字符设备 0/0），掩盖 lowerdir 同名文件
     - whiteout 陷阱：在中间层删文件不会减小镜像，因为删除动作本身也是一个层
     - overlay2 vs overlay 区别：lowerdir 从单层改为多层，性能与稳定性提升
   - **Volume（推荐方式）**：
     - 本质：由 dockerd 管理的命名目录，默认位于 `/var/lib/docker/volumes/<name>/_data`
     - 生命周期：独立于容器，容器删除后 volume 仍存在；需显式 `docker volume rm`
     - 第三方驱动：对接 NFS / 云盘 / 分布式存储（flocker、portworx、Ceph RBD）
     - named volume vs anonymous volume 的区别
     - 初始化行为：volume 为空时，首次挂载会自动复制镜像挂载点的内容到 volume
   - **bind mount**：
     - 本质：挂载宿主绝对路径到容器，绕过 docker 存储
     - 典型场景：开发期挂源码、挂配置文件、挂宿主 /etc 到容器调试
     - 三大陷阱：
       1. 挂载点不存在时 Docker 自动创建**目录**（而非文件），导致挂配置文件踩坑
       2. 宿主文件 owner/uid 与容器内不一致，权限报错
       3. 覆盖镜像内容：挂载点会遮蔽镜像里同名路径，导致容器内该路径内容被"清空"
   - **tmpfs mount**：
     - 本质：挂载在内存（tmpfs），不落盘
     - 典型场景：敏感信息（密钥、session）、高频临时文件（编译缓存）
     - 限制：仅 Linux、容量受 `--tmpfs` 参数限制、容器停止即消失
   - **存储驱动选型与生产实践**：
     - overlay2 几乎是唯一推荐（mainline、性能好、稳定）
     - devicemapper 的 loop-lvm 是历史包袱（生产禁用，直接用 direct-lvm 或换 overlay2）
     - 镜像层与容器层的 GC：删除容器 → upperdir 回收；删除镜像 → layer 引用计数减一
   - **数据持久化模式**：
     - 数据库容器：volume + 命名持久化 + 定期备份
     - 日志收集：bind mount 或 volume + 外部采集（Promtail/Filebeat）
     - 配置注入：config 对象（Swarm 机制）/ bind mount / 环境变量

3. **高频追问**
   - 容器删除后数据还在吗？（看是否用了 volume/bind）
   - volume 和 bind mount 该用哪个？（生产用 volume，开发挂源码用 bind）
   - 在容器里删了文件，镜像会变小吗？（whiteout 陷阱，需最后一层删 / squash / 多阶段）
   - `docker volume rm` 删不掉怎么办？（有容器引用 / dangling volume / `docker volume prune`）
   - bind mount 挂载点变空了是什么原因？（路径不存在自动创建目录 / 覆盖镜像内容）
   - overlay2 和 overlay 有什么区别？（多 lowerdir）
   - 怎么备份数据库容器的数据？（volume 快照 / `docker run --volumes-from` / 物理备份）

4. **实战关联（Java 后端视角）**
   - **Spring Boot 应用的配置注入与数据持久化**：
     - 配置外部化：bind mount `application.yml` vs 环境变量 vs `--env-file`
     - Spring Boot 的 `spring.config.import` 与 `SPRING_APPLICATION_JSON` 在容器内的使用
     - 关联 `framework/spring-framework`：Spring 的 `@Value` 与配置优先级在容器化部署下的行为
   - **日志持久化**：
     - Spring Boot 默认 console 输出 → docker json-file driver → 轮转
     - 文件日志 + bind mount 方案的取舍（避开容器层，直接写宿主目录）
     - 关联 `framework/valid`：健康检查端点 + 日志聚合的服务质量监控
   - **数据库容器化**：
     - MySQL/PostgreSQL 容器的 volume 挂载与初始化脚本（`/docker-entrypoint-initdb.d`）
     - 生产数据库到底该不该容器化？（持久化、性能、备份、迁移的权衡表）
   - 关联 `java-core/jvm`：JVM 堆外内存（DirectBuffer/Metaspace）与容器可写层写入的陷阱

5. **面试案例**
   - "Docker 的存储模型是什么？"（镜像层 + 容器层 + 三种挂载）
   - "容器删除后数据还在吗？怎么保证数据不丢？"（CoW 层消失 / volume / bind）
   - "为什么挂载配置文件后容器内变空目录了？"（bind mount 路径不存在自动创建目录）
   - "镜像里删了文件，为什么镜像还变大？"（whiteout 层）

### 4.7 06-compose/docker-compose.md（Docker Compose 多容器编排）

**定位**：⭐⭐⭐，面试中频，"写个 docker-compose.yml 编排多容器"是实操题。

1. **概念定义**
   - Compose 的定位：单机多容器声明式编排工具，YAML 描述"应用栈 = 服务 + 网络 + 卷"
   - Compose 的适用边界：开发/测试/CI 场景；生产用 Swarm（已退场）/K8s（看独立模块）
   - Compose 与 K8s 的本质差异：单机 vs 集群、无调度重排、无自愈、无滚动升级（对比表）
   - Compose 规范（Compose Specification）：Docker 官方推出的跨工具 YAML 规范，被 Kompose 转换为 K8s 资源

2. **原理与流程**
   - **compose.yml 结构全解**（按顶层键组织）：
     - `services`：服务定义（image/build/ports/volumes/environment/depends_on/healthcheck）
     - `networks`：网络定义（driver: bridge/overlay、external 引用已有网络）
     - `volumes`：命名卷定义（driver: local/nfs、external 引用已有卷）
     - `configs` / `secrets`：Swarm 才有效，Compose 单机版降级处理
   - **服务编排核心指令详解**（深度重点）：
     - `depends_on` 与"启动顺序"陷阱：只保证创建顺序，不保证就绪；长链依赖时仍踩坑
     - `depends_on` 的 condition 形式（service_healthy / service_completed_successfully / service_started）
     - `healthcheck` 在 Compose 中的角色：配合 condition 实现真正的就绪等待
     - `environment` vs `.env` 文件 vs `env_file` 的优先级与安全陷阱（密钥不要进 .env）
     - `ports` vs `expose`：端口映射到宿主 vs 仅在 Compose 内部网络暴露
     - `restart` 策略与 `deploy.restart_policy`（Swarm 模式才生效的陷阱）
     - `build` 与 `image` 组合：构建并打标签，配合 `target` 多阶段选择
   - **服务发现机制**：
     - Compose 默认创建一个自定义 bridge 网络，服务名即 DNS 名
     - 衔接第 4 章"自定义网络与 DNS 发现"，Compose 是其最大受益者
     - 跨 Compose 项目通信：external networks
   - **Compose V2 升级要点**：
     - V1（Python/docker-compose）→ V2（Go/docker compose 子命令）
     - 字段名变化、`version` 字段废弃、命名规则（项目名-服务名-副本号）变化
     - Compose 与 Swarm 的剥离：`deploy` 字段在 `docker compose` 不生效，仅 `docker stack deploy` 才解释

3. **高频追问**
   - depends_on 能保证 MySQL 就绪吗？（不能，需 condition: service_healthy）
   - 多个服务怎么通信？（服务名 DNS，默认自定义 bridge）
   - `docker compose up` 和 `docker-compose up` 区别？（V1 vs V2）
   - 修改 compose.yml 后 up 会重建容器吗？（配置 hash 变化触发重建）
   - `docker compose down` 和 `stop` 区别？（down 删容器/网络，卷保留需 -v）
   - 同一 compose.yml 跑多份怎么隔离？（project name，-p 参数）
   - 怎么把 compose.yml 转成 K8s YAML？（Kompose 工具 + 局限性）

4. **实战关联（Java 后端视角）**
   - **Spring Boot + MySQL + Redis 本地开发栈**（关键点注释，非字段级注释）：
     ```yaml
     services:
       app:
         build: .
         depends_on:
           db:
             condition: service_healthy  # depends_on 的正确姿势，默认只保证创建顺序
           redis:
             condition: service_started
         environment:
           SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/app  # 服务名 DNS 解析
         ports: ["8080:8080"]
       db:
         image: mysql:8.0
         environment:
           MYSQL_ROOT_PASSWORD: ${MYSQL_PASSWORD}  # 从 .env 注入，密钥不入仓
         volumes: ["db_data:/var/lib/mysql"]  # 命名卷持久化
         healthcheck:
           test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
           interval: 5s
           retries: 10
       redis:
         image: redis:7-alpine
     volumes:
       db_data:
     ```
   - 关联 `framework/spring-framework`：
     - 多 profile（dev/prod）配置与 Compose `environment` 的映射
     - Spring 配置优先级在容器化下的行为（env > application.yml）
   - 关联 `framework/valid`：API 服务健康检查端点（`/actuator/health`）作为 Compose healthcheck 的 test
   - 关联 `ops/network`：容器间互访的本质就是第 4 章自定义 bridge + DNS
   - **从 Compose 到 K8s 的迁移边界**：
     - Kompose 能转什么（Deployment/Service/ConfigMap）转不了什么（StatefulSet/PVC 调度/HPA）
     - 生产迁移信号：多机部署、滚动升级、自愈、灰度发布 → 该上 K8s

5. **面试案例**
   - "写一个 Spring Boot + MySQL + Redis 的 compose.yml"（白板题，重点考察 depends_on condition 与 healthcheck）
   - "depends_on 能保证 MySQL 就绪吗？怎么解决？"（condition + healthcheck）
   - "Compose 能用于生产吗？什么场景该换 K8s？"（单机局限 + 迁移信号）

### 4.8 07-security/docker-security.md（Docker 安全模型）

**定位**：⭐⭐⭐，面试中频但区分度高，"容器安全吗"是高级岗位的筛选题。

1. **概念定义**
   - 容器安全的本质：共享内核 → 隔离不彻底 → 需多层纵深防御
   - 容器 vs VM 的安全边界对比表（内核共享、逃逸难度、攻击面）
   - 纵深防御六层模型：内核 namespace/cgroups → Linux capabilities → seccomp → AppArmor/SELinux → user namespace → 镜像扫描与签名
   - 容器逃逸（Container Escape）定义：容器内进程突破隔离获取宿主权限

2. **原理与流程**
   - **Linux Capabilities 机制**（深度重点）：
     - 传统 Unix 的 root / non-root 二分法问题
     - capabilities 细分：37 个（kernel 5.x），分 rootful 与 rootless
     - Docker 默认丢弃的 caps 集合（--cap-drop=ALL 后按需 --cap-add）
     - 常见危险 caps：CAP_SYS_ADMIN（"新 root"）、CAP_NET_ADMIN、CAP_SYS_PTRACE
     - Java 后端场景：一般业务容器只需 NET_BIND_SERVICE（绑定 <1024 端口）
   - **seccomp（Secure Computing Mode）**：
     - BPF 过滤器限制容器可调用的系统调用
     - Docker 默认 seccomp profile：白名单约 300 个 syscall，拦截 ptrace/mount/keyctl 等
     - `--security-opt seccomp=unconfined` 的危险（仅调试用）
   - **AppArmor / SELinux**：
     - AppArmor（Ubuntu 默认）：基于路径的强制访问控制
     - SELinux（RHEL/CentOS 默认）：基于标签的强制访问控制
     - Docker 默认 AppArmor profile：docker-default
   - **User Namespace 重映射（userns-remap）**：
     - 容器内 root（uid=0）→ 官主非特权用户（如 uid=100000）
     - 即使逃逸到宿主文件系统，也是非特权 uid，无法破坏系统
     - 启用陷阱：文件权限、volume 挂载、已存在镜像的兼容
   - **Rootless 模式（Docker 20.10+）**：
     - dockerd 以非 root 用户运行，根本性降低逃逸影响
     - 限制：无法使用 --privileged、部分网络驱动、volume 挂载宿主路径受限
   - **镜像安全生命周期**：
     - 构建期：基础镜像来源（官方 vs 随便拉）、最小化镜像（distroless）、不硬编码密钥
     - 扫描：Trivy / Grype / Snyk 扫描 CVE，CI 集成
     - 运行期：read-only 根文件系统（--read-only + tmpfs 挂载可写目录）
     - 分发：镜像签名（cosign / Notary v2）、供应链安全（SLSA）

3. **高频追问**
   - 容器和虚拟机哪个更安全？（共享内核的固有风险 + 纵深防御）
   - `docker run --privileged` 危险在哪？（禁用所有隔离，接近宿主 root）
   - 容器逃逸怎么发生？（runc CVE-2019-5736、dirty COW、CAP_SYS_ADMIN 滥用）
   - 容器内 root 是真 root 吗？（默认是；userns-remap 后不是）
   - Java 应用需要什么 capabilities？（通常只需 NET_BIND_SERVICE 或无）
   - 镜像怎么扫漏洞？CI 怎么集成？（Trivy + CI 流水线）
   - 怎么防止镜像被篡改？（cosign 签名 + 注册策略）
   - --read-only 怎么用？Spring Boot 能跑吗？（tmpfs 挂 /tmp + 日志走 stdout）

4. **实战关联（Java 后端视角）**
   - **Spring Boot 容器的最小权限配置**：
     - Dockerfile 用 `USER` 切非 root（如 `USER 1000`），需注意文件权限
     - `EXPOSE 8080` 避开 <1024 端口，无需 CAP_NET_BIND_SERVICE
     - `--read-only` + tmpfs 挂 `/tmp`（Spring Boot multipart 上传、Tomcat work 目录）
   - **密钥注入方案对比**（深度重点，关联 `framework/spring-framework`）：
     - 禁止：写进 Dockerfile ENV / 打包进镜像 / 提交到 Git
     - 可选：docker run -e / --env-file（简单但 ps 可见）
     - 推荐：Docker Secrets（Swarm）/ Vault / 云厂商 KMS / K8s Secrets（生产看 K8s 模块）
     - Spring Boot 配合：`SPRING_DATASOURCE_PASSWORD_FILE` 读取文件注入（文件由 secret 挂载）
   - 关联 `framework/valid`：API 鉴权 token 在容器化部署下的注入与轮转
   - 关联 `java-core/agent`：Java agent 在容器内的 attach 陷阱（CAP_SYS_PTRACE 与 nsenter）
   - **镜像供应链安全实践**：
     - 基础镜像锁定 digest（`FROM eclipse-temurin:17-jre@sha256:...`）
     - CI 流水线：Maven 构建 → Trivy 扫描 → cosign 签名 → 推送 Registry

5. **面试案例**
   - "Docker 容器安全吗？怎么加固？"（纵深防御六层 + Java 视角）
   - "容器内 root 和宿主 root 一样吗？"（userns-remap + capabilities）
   - "数据库密码怎么传给容器？"（密钥注入方案矩阵）

### 4.9 08-performance/java-container-tuning.md（Java 容器调优）

**定位**：⭐⭐⭐⭐⭐，Java 后端面试的高级区分题，与 `java-core/jvm` 联动最深。

1. **概念定义**
   - JVM 容器化的核心矛盾：JVM 诞生于"独占物理机"时代，默认按宿主资源估算堆；容器化后资源被 cgroup 限制，老版本 JVM 感知不到导致 OOM 或 CPU 浪费
   - 演进时间线：JDK 8u131（-XX:+UseCGroupLimits）→ 8u191（UseContainerSupport 默认开启）→ 10+（正式支持）→ 11+（cgroup v2 支持）
   - 两类问题：内存类（堆超限 OOM Killed）与 CPU 类（线程数与可用 CPU 不匹配）
   - 本章与第 3 章的边界：第 3 章讲"机制与现象"，本章讲"JVM 感知原理与调优方法论"

2. **原理与流程**
   - **JVM 容器内存感知全链路**（深度重点）：
     - JDK 8u191+ 的 `UseContainerSupport`（默认开启）源码路径：`os::Linux::container`
     - 探测顺序：cgroup v2 `/sys/fs/cgroup/` → cgroup v1 `/sys/fs/cgroup/memory/memory.limit_in_bytes`
     - 关键参数矩阵表：
       | 参数 | 作用 | 默认值 | 注意事项 |
       |------|------|--------|---------|
       | -XX:+UseContainerSupport | 总开关 | true（8u191+） | 一般无需关 |
       | -XX:MaxRAMPercentage=75.0 | 堆占总内存百分比 | 25%（老）/ 自定义 | 替代 -Xms/-Xmx 固定值 |
       | -XX:InitialRAMPercentage | 初始堆占比 | 同上 | 建议等于 Max |
       | -XX:MinRAMPercentage | 小容器（<250MB）特殊处理 | 50 | 小内存容器注意 |
       | -Xmx | 固定堆上限 | - | 与 MaxRAMPercentage 二选一 |
     - 陷阱：堆外内存（DirectBuffer / Metaspace / Thread Stack / JNI）不计入堆，堆设 100% 仍会 OOM Killed
     - 堆内 + 堆外 + JVM 自身的内存预算公式：`容器内存 > 堆 + Metaspace + DirectBuffer + ThreadStack × 线程数 + CodeCache + JVM 自身`
   - **JVM 容器 CPU 感知**：
     - `Runtime.availableProcessors()` 与 cgroup cpu 限制的关系
     - cgroup v1：`cpu.cfs_quota_us / cpu.cfs_period_us` → 核数 = ⌈quota/period⌉
     - cgroup v2：`cpu.max` 单字段
     - JDK 10+ 正确感知，JDK 8u191+ 部分感知（历史坑：8u131 之前读宿主 CPU）
     - 陷阱：Tomcat/ForkJoinPool/CompletableFuture 并行度都依赖 availableProcessors()，按宿主 CPU 配会导致过多线程
   - **OOM Killed 与 GC 诊断**：
     - 容器内 OOM Killer（memory cgroup OOM）vs JVM OOM（堆 OOM）的区别：前者杀进程无堆栈，后者有
     - 诊断信号：容器退出码 137（128 + SIGKILL）→ 疑似 OOM Killed
     - 排查链路：`docker inspect` 看 OOMKilled 字段 → `dmesg | grep -i kill` 看内核日志 → 确认堆/堆外哪一类
     - GC 日志配置：`-Xlog:gc*=info:file=/tmp/gc.log:time,level,tags:filecount=5,filesize=10m`（JDK 9+）
   - **启动优化与分层镜像**（衔接第 2 章多阶段构建）：
     - **Spring Boot Layertools**：
       - 原理：解包 fat jar 为 `dependencies/` `spring-boot-loader/` `snapshot-dependencies/` `application/` 四层
       - 价值：依赖层几乎不变 → 镜像缓存命中率极高，重建只重打 application 层
       - 与多阶段构建配合：`COPY --from=builder layers/dependencies/ /app/` 逐层 COPY
       - 与 CDS（Class Data Sharing）配合：预生成共享归档进一步压缩启动时间
     - **Jib（Google 出品）**：
       - 原理：不需要 Dockerfile/Docker daemon，Maven/Gradle 插件直接构造分层镜像并推 Registry
       - 优势：CI 无需 Docker-in-Docker、分层与缓存全自动、与 BuildKit 互补
       - 与 Layertools 对比：Jib 按依赖/资源/类自动分层，Layertools 更可控但需手写 Dockerfile
     - **启动加速全景表**：
       | 手段 | 原理 | 启动收益 | 适用 |
       |------|------|---------|------|
       | 分层镜像 | 缓存命中，重建快 | 构建 | 所有 |
       | Spring Boot Layertools | 依赖不变层复用 | 构建 | Spring Boot |
       | Jib | 无 daemon 自动分层 | 构建/CI | 所有 Java |
       | CDS/AppCDS | 预归档类元数据 | JVM | ≥ JDK 10 |
       | GraalVM Native Image | AOT 编译，无 JVM 预热 | 运行 | 兼容性受限 |
       | CRaC（JDK 17+） | 检查点/恢复快照 | 运行 | 实验性 |
   - **GC 选型在容器化场景的变化**（深度重点）：
     - JDK 8 默认 ParallelGC → JDK 9+ 默认 G1 → JDK 15+ ZGC 转正，容器化场景的选型需结合内存上限与延迟目标
     - **容器内 GC 选型对比表**：
       | GC | 引入版本 | 停顿目标 | 容器内存上限 | 适用场景 | 容器化陷阱 |
       |----|---------|---------|-------------|---------|-----------|
       | Serial | JDK 1.3+ | 百ms级 | <100MB | 极小容器、嵌入式 | 单线程，容器也单核时可用 |
       | Parallel | JDK 1.5+ | 百ms级 | 无上限约束 | 批处理 | JDK 8 默认，容器内吞吐优先但停顿大 |
       | G1 | JDK 9 默认 | 200ms 可调 | 支持上限 | 通用服务 | 默认 1/4 容器内存作堆，MaxRAMPercentage 生效 |
       | ZGC | JDK 15 转正 | <10ms（JDK 16+ 并发栈） | 支持上限（最大 16TB） | 低延迟大堆服务 | 需要额外预留 colored pointer 内存（堆外元数据，约堆的 1/64） |
       | Shenandoah | JDK 12+ | <10ms | 支持上限 | RedHat 发行版 | 上游 OpenJDK 不含，仅 Temurin/Adopt 可用 |
     - **ZGC 在容器内的深度要点**：
       1. **核心机制**（简述原理，详细推导看 `java-core/jvm`）：
          - 染色指针（Colored Pointer）：在 64 位指针的高 4 位编码 GC 元数据，对象状态不依赖对象头
          - 读屏障（Load Barrier）：每次对象引用读取时检查指针颜色，按需重定位
          - 整理阶段并发：标记、转移、重定位全并发，STW 仅在初始标记与再标记（<1ms）
       2. **容器化的三个陷阱**：
          - **堆外内存预算**：ZGC 的染色指针需要额外的 multi-mapping 映射，元数据约占堆的 1/64（需额外内存预算）
          - **CPU 开销**：读屏障带来约 5-10% 吞吐损失，cgroup CPU 限制下更明显，低负载容器收益不明显
          - **小堆无收益**：ZGC 设计目标是大堆（>8GB）低延迟，小堆容器（<2GB）反而不如 G1
       3. **容器内启用与参数**：
          ```dockerfile
          ENTRYPOINT ["java", \
            "-XX:+UseZGC", \
            "-XX:MaxRAMPercentage=75.0", \
            "-XX:ZUncommitDelay=300", \
            "-XX:+ZGenerational", \         # JDK 21+ 分代 ZGC
            ...]
          ```
          - `ZUncommitDelay`：堆内存归还给操作系统（归还给 cgroup）的延迟，容器自动缩容时有用
          - `ZGenerational`（JDK 21+）：分代 ZGC，改善小堆场景的分配停顿，容器化更友好
       4. **选型决策树**（mermaid flowchart）：
          - 容器内存 < 2GB → G1
          - 容器内存 2-8GB 且无强延迟要求 → G1
          - 容器内存 > 8GB 且停顿 < 10ms → ZGC
          - 容器内存 > 8GB 且 JDK 21+ → ZGenerational ZGC
          - RedHat 系且无 ZGC → Shenandoah
       5. **关联 `java-core/jvm`**：
          - ZGC 的染色指针与读屏障源码推导
          - 分代 ZGC（JEP 439）的分代设计动机
          - 容器内 GC 日志采集与 Prometheus + Grafana 监控链路
     - **与第 8 章其他调优手段的衔接**：
       - ZGC 解决"运行期 GC 停顿"，Layertools/CDS/GraalVM 解决"启动期 + 镜像构建"，互补不冲突
       - GraalVM Native Image 是"无 JVM 预热"的极端方案，但牺牲了 ZGC 这类动态优化能力，选型时需权衡

3. **高频追问**
   - 容器内 JVM 堆怎么配？（MaxRAMPercentage，别用固定 -Xmx）
   - 为什么配了 -Xmx 容器还是 OOM Killed？（堆外内存预算漏了）
   - availableProcessors() 在容器里返回的是几？（8u191+ 感知 cgroup，老版本读宿主）
   - Tomcat 线程数在容器里怎么配？（别按宿主 CPU，按 cgroup 限制）
   - 容器退出码 137 是什么？（OOM Killed 或 docker kill）
   - Spring Boot 启动慢，镜像构建慢，怎么优化？（分层 + Layertools + CDS）
   - Jib 和 Dockerfile 构建有什么区别？（无 daemon、自动分层）
   - GraalVM Native Image 能替代 JVM 容器吗？（AOT 优势 + 兼容性陷阱，权衡表）
   - cgroup v2 和 v1 对 JVM 的影响？（8u131 之前都不支持 v2，升级 JDK）
   - ZGC 在容器内怎么选？（小堆用 G1，大堆用 ZGC，JDK 21+ 用分代 ZGC）
   - ZGC 的染色指针为什么需要额外内存？（multi-mapping 堆外元数据）

4. **实战关联（Java 后端视角）**
   - **生产 Dockerfile 标准模板**（Spring Boot + Layertools + 非 root + 健康检查）：
     ```dockerfile
     FROM eclipse-temurin:17-jre-jammy
     WORKDIR /app
     COPY --from=builder layers/dependencies/ ./
     COPY --from=builder layers/spring-boot-loader/ ./
     COPY --from=builder layers/snapshot-dependencies/ ./
     COPY --from=builder layers/application/ ./
     USER 1000:1000
     ENTRYPOINT ["java", \
       "-XX:MaxRAMPercentage=75.0", \
       "-XX:+HeapDumpOnOutOfMemoryError", \
       "-XX:HeapDumpPath=/tmp/heapdump.hprof", \
       "-Xlog:gc*=info:file=/tmp/gc.log:time,level,tags:filecount=5,filesize=10m", \
       "org.springframework.boot.loader.launch.JarLauncher"]
     ```
     关键点注释：MaxRAMPercentage 替代固定堆；HeapDump 落 /tmp（配合 tmpfs）；GC 日志轮转；非 root；JarLauncher（Spring Boot 3.x）
   - 关联 `java-core/jvm`：
     - 堆外内存预算与 JVM 内存模型推导
     - GC 选型在容器化场景的变化（G1 默认、ZGC 低延迟 + 容器内存上限）
     - `-XX:+UseContainerSupport` 源码路径与探测逻辑
   - 关联 `framework/spring-framework`：
     - Spring Boot 3.x 的 JarLauncher 与 2.x 的 JarLauncher 路径变化
     - Spring Boot 优雅关闭 `server.shutdown=graceful`（衔接第 3 章 PID 1）
   - 关联 `framework/valid`：健康检查端点 `/actuator/health` 作为 K8s/Compose probe（衔接第 6 章 healthcheck）
   - **调优决策树**（mermaid flowchart）：容器 OOM → 退出码 137？→ 堆 OOM 还是 cgroup OOM？→ 调整堆/堆外预算/换 native image

5. **面试案例**
   - "Java 应用容器化后 OOM Killed，怎么排查？"（退出码 137 → 堆 vs 堆外 → 预算公式 → 调整 MaxRAMPercentage）
   - "容器内 JVM 怎么配堆？"（MaxRAMPercentage + 预算公式）
   - "Spring Boot 镜像构建太慢，每次改代码都重打依赖层，怎么优化？"（Layertools + 多阶段）
   - "Tomcat 在容器里线程数暴涨，为什么？"（availableProcessors 老版本读宿主 CPU）
   - "ZGC 在容器内怎么选？"（小堆 G1、大堆 ZGC、JDK 21+ 分代 ZGC）

### 4.10 09-interview-qa.md（跨主题高频面试 Q&A）

**定位**：⭐⭐⭐⭐⭐，面试前冲刺用，40 题速答串联各主题，附连环套问思维导图。

**内容结构**（不套五段式）：

1. **使用说明**
   - 全部 40 题按主题分类，每题 3-5 句要点速答，末尾 **关联** 链接指向对应主题文档
   - 连环追问题在题号后标注 🔗，配合文末"连环套问思维导图"把握面试官追问路径
   - 建议先盖住答案自答，再对照要点查漏，最后跳转关联文档补全原理

2. **一、容器基础篇（6 题）**：Q1-Q6
   - Q1: 容器和虚拟机的区别？🔗
   - Q2: Docker 容器的本质是什么？（受控进程 + namespace/cgroups/unionfs）
   - Q3: Docker 进程死了，容器会死吗？（shim 设计）
   - Q4: cgroup v1 和 v2 有什么区别？对 Java 有什么影响？🔗
   - Q5: OverlayFS 的写时复制原理是什么？
   - Q6: runc、containerd、dockerd 之间的调用关系？🔗

3. **二、镜像与构建篇（8 题）**：Q7-Q14
   - Q7: CMD 和 ENTRYPOINT 的区别？🔗
   - Q8: COPY 和 ADD 该用哪个？
   - Q9: 为什么 Dockerfile 构建很慢？缓存怎么失效了？🔗
   - Q10: 多阶段构建解决了什么问题？
   - Q11: 镜像为什么这么大？怎么减小？🔗
   - Q12: 删除文件能让镜像变小吗？（whiteout 陷阱）
   - Q13: BuildKit 带来了什么改进？
   - Q14: 同一镜像怎么支持多架构？（manifest list）

4. **三、容器运行篇（6 题）**：Q15-Q20
   - Q15: `docker run` 之后发生了什么？🔗
   - Q16: `docker stop` 和 `docker kill` 的区别？
   - Q17: 为什么 Java 应用 `docker stop` 后要等 10 秒才死？🔗
   - Q18: 容器退出码 137 是什么意思？🔗
   - Q19: always 和 unless-stopped 区别？
   - Q20: 容器日志把磁盘写满怎么办？🔗

5. **四、网络篇（5 题）**：Q21-Q25
   - Q21: Docker 的网络模型是什么？🔗
   - Q22: `docker run -p 8080:80` 后数据流向是什么？🔗
   - Q23: 容器间互相访问怎么做？默认 bridge 行不行？🔗
   - Q24: overlay 网络怎么实现跨主机通信？（VXLAN）
   - Q25: 自定义网络为什么支持容器名 DNS？

6. **五、存储篇（4 题）**：Q26-Q29
   - Q26: 容器删除后数据还在吗？🔗
   - Q27: volume 和 bind mount 该用哪个？
   - Q28: bind mount 挂载点变空目录是什么原因？🔗
   - Q29: 镜像里删了文件为什么镜像还变大？

7. **六、Compose 编排篇（3 题）**：Q30-Q32
   - Q30: depends_on 能保证 MySQL 就绪吗？🔗
   - Q31: Compose 能用于生产吗？什么场景该换 K8s？
   - Q32: `docker compose` 和 `docker-compose` 区别？

8. **七、安全篇（4 题）**：Q33-Q36
   - Q33: Docker 容器安全吗？怎么加固？🔗
   - Q34: `--privileged` 危险在哪？
   - Q35: 容器内 root 是真 root 吗？🔗
   - Q36: 数据库密码怎么传给容器？（密钥注入方案矩阵）

9. **八、Java 容器调优篇（4 题）**：Q37-Q40
   - Q37: 容器内 JVM 堆怎么配？🔗
   - Q38: 为什么配了 -Xmx 容器还是 OOM Killed？🔗
   - Q39: Spring Boot 镜像构建太慢怎么优化？（Layertools + Jib + CDS）🔗
   - Q40: ZGC 在容器内怎么选？什么场景用？

10. **九、连环套问思维导图**（mermaid mindmap）：
    模拟面试官的追问路径，6 条完整追问链：
    - 容器原理链：容器和 VM 区别 → namespace/cgroups/unionfs → cgroup v1 vs v2 → 对 Java 的影响 → UseContainerSupport 源码
    - 镜像构建链：写个 Dockerfile → CMD vs ENTRYPOINT → 缓存失效原理 → 多阶段构建 → Layertools 分层
    - 容器运行链：docker run 发生了什么 → 调用链时序 → PID 1 信号陷阱 → Java 优雅关闭 → STOPSIGNAL 与 timeout
    - 网络链：端口映射数据流向 → iptables DNAT/SNAT → 自定义网络 DNS → overlay VXLAN → 与 K8s CNI 边界
    - 存储链：容器删除数据丢了吗 → volume vs bind → OverlayFS CoW → whiteout 陷阱 → 多阶段减小镜像
    - Java 调优链：容器内 OOM Killed → 退出码 137 → 堆 vs 堆外预算 → MaxRAMPercentage → ZGC 选型

    每条链都是"入口题 → 原理 → 陷阱 → Java 关联"的递进。

---

## 五、交叉引用与 Java 模块关联

### 5.1 与 ops/network 模块的交叉引用清单

| Docker 文档 | 跳转到 network 模块 | 对照要点 |
|---|---|---|
| 04-network | `network/03-network/nat.md` | docker0 SNAT = NAPT |
| 04-network | `network/02-transport/tcp-high-frequency.md` | 容器内 TIME_WAIT 堆积 |
| 04-network | `network/05-system-design/cloud-native.md` | overlay/VXLAN 与 K8s CNI 边界 |

**处理原则**：Docker 章只讲"容器化场景下的现象与排查"，原理推导链回 network 模块，不重复展开。

### 5.2 与 Java 模块的关联清单

| Docker 文档 | 关联 Java 模块 | 关联要点 |
|---|---|---|
| 01-foundation | `java-core/jvm` | JVM 容器内存感知源码路径 |
| 01-foundation | `framework/spring-framework` | PID 1 与 SIGTERM 优雅关闭 |
| 02-image | `framework/spring-framework` | Spring Boot 可执行 jar 结构与分层 |
| 02-image | `framework/jackson` | 镜像内配置注入优先级 |
| 03-container | `framework/spring-framework` | ContextClosedEvent 与 shutdown hook |
| 03-container | `java-core/jvm` | JVM ShutdownHook 执行时机 |
| 04-network | `framework/spring-framework` | server.address 与容器网络绑定 |
| 04-network | `framework/valid` | API 网关端口暴露与健康检查端点 |
| 05-storage | `framework/spring-framework` | @Value 与配置优先级在容器化下的行为 |
| 05-storage | `framework/valid` | 健康检查端点 + 日志聚合 |
| 05-storage | `java-core/jvm` | 堆外内存与容器可写层 |
| 06-compose | `framework/spring-framework` | 多 profile 配置与 Compose environment |
| 06-compose | `framework/valid` | /actuator/health 作为 healthcheck |
| 07-security | `framework/spring-framework` | 密钥注入与 Spring 配置 |
| 07-security | `framework/valid` | API 鉴权 token 注入与轮转 |
| 07-security | `java-core/agent` | Java agent 在容器内的 attach 陷阱 |
| 08-performance | `java-core/jvm` | 堆外内存预算、ZGC 选型、UseContainerSupport 源码 |
| 08-performance | `framework/spring-framework` | Spring Boot 3.x JarLauncher、优雅关闭 |
| 08-performance | `framework/valid` | /actuator/health 作为 probe |

---

## 六、README 更新规则

遵循 AGENTS.md 的"README 自动更新规则"：

1. **`ops/README.md`**：docker 行从 `| docker | 容器化 |` 补充为带链接与文档数（类似 network 行的格式）。
2. **`ops/docker/README.md`**：作为本模块入口，含上述关联表。

---

## 七、验收标准

- [ ] 10 份文档全部创建，路径与目录结构一致
- [ ] 每份主题文档顶部含"一句话定位 / 面试热度 / 返回"导航
- [ ] 每份主题文档遵循五段式结构
- [ ] Q&A 篇含 40 题速答 + 连环套问思维导图
- [ ] README.md 含知识图谱（mermaid mindmap）、导航表、两条学习路径、Java 模块关联表
- [ ] 与 ops/network 模块的交叉引用链接全部有效
- [ ] 与 Java 模块的关联链接全部有效
- [ ] ops/README.md 的 docker 行已更新
- [ ] 所有 Markdown 渲染正常，mermaid 图语法正确
