# 计算机网络面试知识体系 — 设计文档

> **创建日期**：2026-08-07
> **作者**：zihao
> **状态**：已确认，待写实现计划
> **适用对象**：Java 后端工程师面试（社招中高级）

---

## 一、目标与范围

### 1.1 目标

为 Java 后端工程师面试构建一套**结构化、系统化、有深度**的计算机网络知识文档体系，作为长期学习与面试冲刺的统一参考。

### 1.2 覆盖范围

- **面试核心**：OSI/TCP-IP 分层、TCP/UDP、HTTP/HTTPS、DNS、IP、NAT、路由、ARP 等全谱系基础知识。
- **大型系统设计**：
  - 经典网络架构案例（短链系统、IM 推送、弹幕、文件分片上传、接口限流、负载均衡）。
  - 云原生网络（Service Mesh、K8s CNI、零信任、eBPF、东西向流量）。

### 1.3 深度标准

采用**面试宝典型**：每个知识点按五段式展开——概念定义 → 原理与流程 → 高频追问与面试题 → 实战与 Java 生态关联 → 系统设计案例。

### 1.4 交付方式

一次性全量交付 17 份 Markdown 文档。

---

## 二、目录结构

在 `ops/network/` 下按 OSI/TCP-IP 分层自顶向下组织，共 4 个分层目录 + 1 个系统设计目录 + 1 个跨主题问答文件 + 1 个入口 README。

```
ops/network/
├── README.md                      # 入口：知识图谱(Mermaid) + 导航 + 复习路线
│
├── 01-application/                # 应用层
│   ├── http.md                    # HTTP/1.1、HTTP/2、HTTP/3、状态码、首部、Cookie/Session/Token/JWT
│   ├── https-tls.md               # TLS 1.2/1.3 握手、证书链、密钥协商、前向保密、Session 复用
│   ├── dns.md                     # 解析流程、层级缓存、DNSSEC、HTTPDNS、安全风险
│   └── application-protocols.md   # WebSocket、FTP、SMTP/IMAP、CDN、DNS over HTTPS
│
├── 02-transport/                  # 传输层
│   ├── tcp-connection.md          # 三次握手/四次挥手/状态机/半关闭/同时打开
│   ├── tcp-reliability.md         # 确认重传、滑动窗口、流量控制、粘包拆包、Nagle/Delayed ACK
│   ├── tcp-congestion.md          # 慢启动/拥塞避免/快重传/快恢复、CUBIC vs BBR、缓冲膨胀
│   ├── tcp-high-frequency.md      # TIME_WAIT/2MSL/KeepAlive/重传次数、SYN Flood、半连接队列
│   └── udp-quic.md                # UDP、QUIC、KCP、TCP vs UDP 选型
│
├── 03-network/                    # 网络层
│   ├── ip.md                      # IPv4/IPv6、CIDR、子网划分、分片重组、DHCP
│   ├── nat.md                     # NAT/NAPT、内外网穿透、STUN/TURN/ICE
│   └── routing.md                 # 静态/动态路由、OSPF、BGP、ICMP、Traceroute 原理
│
├── 04-link/                       # 数据链路层（精简）
│   └── ethernet.md                # ARP/MAC/VLAN/STP、CSMA/CD、交换机 vs 路由器
│
├── 05-system-design/              # 大型系统设计
│   ├── classic-cases.md           # 短链系统/IM 推送/弹幕/文件分片上传/接口限流/负载均衡
│   └── cloud-native.md            # Service Mesh(Istio)、K8s CNI(Calico/Flannel)、零信任、eBPF、东西向流量
│
└── 06-interview-qa.md             # 跨主题高频追问汇总（50+ 题速答 + 思维导图）
```

**文件总数**：1 个 README + 4（应用层）+ 5（传输层）+ 3（网络层）+ 1（链路层）+ 2（系统设计）+ 1（Q&A）= **17 份 Markdown 文件**。

### 2.1 设计取舍说明

| 决策 | 理由 |
|------|------|
| TCP 拆成 4 份 | TCP 是面试高频重点，单文件会超 2000 行；按"连接/可靠性/拥塞/高频追问"拆分，单文件 500-1000 行 |
| HTTP 与 HTTPS 分开 | TLS 握手本身是个独立深主题，合并会让 http.md 过长 |
| 链路层仅 1 份 | 面试占比低，合并避免冗余 |
| 系统设计拆 2 份 | "经典案例"偏应用层选型，"云原生"偏基础设施，受众与深度不同 |
| `06-interview-qa.md` 跨主题汇总 | 面试前冲刺用，串联各主题的高频追问 |

### 2.2 入口 README 结构

`ops/network/README.md` 包含：

1. **模块简介** — 一句话定位 + 适用对象（Java 后端面试）
2. **知识图谱** — Mermaid mindmap，展示全貌
3. **导航表** — 表格列出所有主题文档及核心考点
4. **推荐学习路径** — 自顶向下（应用层→传输层→网络层）vs 面试冲刺路线
5. **与 java-core/framework 模块的关联** — 比如 Netty（framework）、Socket（java-core）

---

## 三、单份文档内部结构模板

每份主题文档遵循统一的**五段式结构**（"面试宝典型"）：

```markdown
# <主题名>（如：TCP 连接管理）

> **一句话定位**：用一句话说明本主题在面试中的地位与出现频率。
> **面试热度**：⭐⭐⭐⭐⭐（1-5 星）
> **返回**：[网络知识图谱](../README.md)

## 一、概念定义
- 严谨的定义 + 关键术语解释
- 配 ASCII 图或 Mermaid 图示意（如 TCP 状态机）

## 二、原理与流程
- 核心机制的逐步拆解（如三次握手每一步做什么）
- 时序图 / 流程图 / 报文格式图
- 关键参数与状态转移

## 三、高频追问与面试题
> 每题用 ### Q<n> 形式，含"参考答案"和"追问"两层
### Q1 三次握手为什么不是两次？
**参考答案**：...
**追问**：那能不能是四次？SYN-ACK 拆成两步发会怎样？
### Q2 ...

## 四、实战与 Java 生态关联
- Netty/JDK HttpClient/okhttp 中的对应实现
- 性能调优参数（如 Linux `tcp_*` 内核参数）
- 抓包工具使用（tcpdump/wireshark 命令示例）

## 五、系统设计案例
- 本主题在真实系统中的应用（如 TCP 在 IM 系统中的选型权衡）
- 与其他主题的关联点
```

### 3.1 模板弹性

不同主题的文档按内容性质有弹性：

| 文档 | 强调段落 | 弹性说明 |
|------|---------|---------|
| `tcp-connection.md` | 二、三段最重 | 状态机图 + 大量追问 |
| `tcp-congestion.md` | 二段最重 | 拥塞算法原理深挖 |
| `http.md` | 一、三段最重 | 演进对比 + 首部细节 |
| `dns.md` | 二段最重 | 解析流程时序图 |
| `system-design/*.md` | 五段最重 | 案例为主，前四段精简 |
| `ethernet.md` | 一段最重 | 概念为主，二三段较轻 |

### 3.2 图示规范

- **时序图/流程图**：优先用 Mermaid（GitHub 原生渲染）
- **ASCII 图**：用于简单的报文格式、状态示意（如 TCP 首部）
- **表格**：对比型内容（如 HTTP/1.1 vs 2.0 vs 3.0）

### 3.3 交叉引用规范

- 文档间互相引用用相对链接，如：`tcp-congestion.md` 引用 `tcp-connection.md` 的状态机 → `[TCP 状态机](./tcp-connection.md#状态机)`
- README 引用各主题 → `[TCP 连接管理](./02-transport/tcp-connection.md)`
- 每份文档顶部含 `> 返回 [网络知识图谱](../README.md)` 导航链接

---

## 四、各主题文档知识点清单

### 4.1 应用层（01-application/）

#### `http.md` — HTTP 协议全解
- HTTP 报文结构（请求行/首部/体）、方法语义（幂等性/安全性）
- 状态码全谱（1xx-5xx），重点 101/301/302/304/401/403/408/502/504
- HTTP/1.0 → 1.1（长连接、管线化、Host 头、分块传输）
- HTTP/2（多路复用、二进制分帧、首部压缩 HPACK、Server Push、队头阻塞解决与遗留）
- HTTP/3（QUIC、0-RTT、连接迁移）
- Cookie/Session/Token/JWT 对比、CSRF/CORS/XSS 网络层防御
- 缓存机制（强缓存 Cache-Control/Expires、协商缓存 ETag/Last-Modified）
- 跨域（同源策略、CORS 预检、OPTIONS、简单/非简单请求）

#### `https-tls.md` — HTTPS 与 TLS
- TLS 在协议栈的位置、HTTPS = HTTP + TLS
- TLS 1.2 握手全流程（ClientHello → ServerHello → 证书 → 密钥交换 → Finished）
- TLS 1.3 简化（1-RTT/0-RTT、移除 RSA 密钥交换、强制 PFS）
- 证书链验证（根 CA → 中间 CA → 终端证书）、吊销机制（CRL/OCSP）
- 对称加密 + 非对称加密 + CA 的"为什么"
- Session 复用（Session ID/Session Ticket）、PSK
- 前向保密（Forward Secrecy）原理与意义
- 常见攻击与防御（降级攻击、中间人、BEAST/CRIME）

#### `dns.md` — DNS 域名解析
- DNS 分层架构（根 → TLD → 权威 → 本地递归）
- 解析全流程（浏览器缓存 → OS hosts → 本地 DNS 递归查询）
- 记录类型（A/AAAA/CNAME/MX/TXT/NS/SOA）
- 缓存层次与 TTL、负载均衡（轮询/智能 DNS）
- DNSSEC 原理、DNS 劫持/污染与 HTTPDNS
- DNS over HTTPS/TLS

#### `application-protocols.md` — 其他应用层协议
- WebSocket（握手升级、帧格式、心跳、与 HTTP/2 对比）
- CDN 原理（回源、缓存策略、调度、动态加速）
- FTP/SMTP/IMAP 简述

### 4.2 传输层（02-transport/）

#### `tcp-connection.md` — TCP 连接管理
- TCP 首部格式（序号/确认号/标志位/窗口/选项）
- 三次握手详图（SYN/SYN-ACK/ACK、序号变化、初始化策略）
- 四次挥手详图（FIN/ACK/FIN/ACK、半关闭状态）
- TCP 11 个状态完整状态机图
- 同时打开/同时关闭、半连接队列与全连接队列
- MSS/窗口缩放、SACK、Timestamps

#### `tcp-reliability.md` — TCP 可靠性机制
- 确认与重传（超时重传 RTT/RTO、快重传）
- 滑动窗口（发送窗口/接收窗口、窗口字段）
- 流量控制（窗口探测、零窗口）
- 粘包拆包（成因 + 三种解法：定长/分隔符/长度字段）
- Nagle 算法 vs Delayed ACK、Cork

#### `tcp-congestion.md` — TCP 拥塞控制
- 拥塞控制四大阶段（慢启动、拥塞避免、快重传、快恢复）
- cwnd/ssthresh 状态转移
- CUBIC 算法、BBR 算法（基于带宽与延迟探测）
- 公平性与收敛、缓冲膨胀（Bufferbloat）
- 不同算法在 Linux 内核中的实现与切换

#### `tcp-high-frequency.md` — TCP 高频面试追问
- TIME_WAIT 作用、2MSL 来由、过多怎么办（`tcp_tw_reuse`/`tcp_tw_recycle`/`tcp_max_tw_buckets`）
- KeepAlive 机制、应用层心跳必要性
- SO_REUSEADDR/SO_REUSEPORT、端口耗尽
- SYN Flood 攻击与 SYN Cookies
- 连接队列溢出排查（`ss -lnt`/`netstat -s | grep overflowed`）

#### `udp-quic.md` — UDP、QUIC、KCP
- UDP 首部与特点、应用场景（DNS/视频流/游戏）
- QUIC（基于 UDP 实现可靠传输、多路复用无队头阻塞、0-RTT、连接迁移）
- KCP（ARQ + 前向纠错、快速 vs 正常模式）
- TCP vs UDP 选型决策树

### 4.3 网络层（03-network/）

#### `ip.md` — IP 协议
- IPv4 首部、TTL、Protocol 字段
- 子网掩码、CIDR、子网划分计算
- IPv6（地址格式、无 NAT、双栈、隧道）
- 分片与重组（MTU/PMTU Discovery）
- DHCP 工作流程（DORA）

#### `nat.md` — NAT 与穿透
- NAT 类型（Full Cone/Restricted/Port-Restricted/Symmetric）
- NAPT 端口多路复用
- 内网穿透方案（STUN/TURN/ICE、frp/反向 SOCKS）
- 为什么 P2P 需要 NAT 穿透

#### `routing.md` — 路由与 ICMP
- 静态 vs 动态路由、路由表
- OSPF（链路状态、区域、SPF）
- BGP（路径向量、AS、互联网骨干）
- ICMP（ping/traceroute 原理）
- 路由环路、TTL 防环

### 4.4 链路层（04-link/）

#### `ethernet.md` — 以太网与 ARP
- MAC 地址、帧格式
- ARP 工作原理、ARP 欺骗
- VLAN/Trunk、802.1Q
- STP 防环、交换机 vs 路由器

### 4.5 系统设计（05-system-design/）

#### `classic-cases.md` — 经典网络架构案例
每个案例遵循：需求分析 → 整体架构 → 协议选型 → 容量/带宽估算 → 热点追问
- 短链系统（发号/跳转/缓存/防刷）
- IM 消息推送系统（长连接选型、消息可靠投递、ACK、消息漫游）
- 弹幕系统（WebSocket vs UDP、房间隔离、扇出）
- 大文件分片上传/断点续传
- 接口限流（令牌桶/漏桶、分布式限流）
- 负载均衡（四层 vs 七层、LVS/Nginx、一致性哈希）

#### `cloud-native.md` — 云原生网络
- 微服务通信（同步 RPC vs 异步 MQ、序列化）
- Service Mesh（Istio 架构、Envoy sidecar、控制面 vs 数据面）
- K8s 网络（CNI、Calico BGP vs Flannel VXLAN、Service/Endpoint、kube-proxy iptables/IPVS）
- 东西向 vs 南北向流量、零信任网络
- eBPF 在网络中的应用（XDP、Cilium）

### 4.6 跨主题（06-interview-qa.md）

#### `06-interview-qa.md` — 高频面试题速答汇总
- 50+ 题一问一答，每题 3-5 句要点速答
- 按主题分类（TCP/HTTP/DNS/IP/系统设计）
- 附思维导图：哪些题是连环套问

---

## 五、仓库集成与一致性规则

### 5.1 与 `ops/` 模块的集成

当前 `ops/README.md` 为占位状态：

```markdown
个人学习运维记录项目

- linux
- k8s
- docker
- network（计算机网络）
```

**修改方案**：将 `ops/README.md` 升级为与 `quantitative/README.md` 同级的结构化入口，network 作为子模块链接：

```markdown
# ops — 运维与基础设施

## 模块列表

| 模块 | 说明 |
|------|------|
| linux | Linux 基础与 Shell |
| k8s | Kubernetes 编排 |
| docker | 容器化 |
| [network](./network) | 计算机网络面试知识体系（17 份文档，按分层组织） |

详见 [network/README.md](./network/README.md)
```

### 5.2 与根 `README.md` 的集成

根 `README.md` 第 18 行和第 117-124 行已有 network 描述，需要同步更新 ops 段落为：

```markdown
## ops（运维）

- Linux
- K8s
- Docker
- 计算机网络（按 OSI 分层的 17 份面试知识文档，含系统设计案例）

详见 [ops/network/README.md](./ops/network/README.md)
```

### 5.3 文档风格一致性规则

沿用 `quantitative/cxmt-supply-chain.md` 已确立的仓库文档风格：

| 要素 | 规范 |
|------|------|
| 标题 | `#` 一级标题为文档名，`##` 为大段落，`###` 为知识点或 Q&A |
| 引用块 | 文档开头用 `> ` 放一句话定位与面试热度 |
| 图表 | 优先 Mermaid（GitHub 原生渲染），其次 ASCII 图 |
| 表格 | 对比型内容用表格，含表头分隔行 |
| 语言 | 全中文（与 AGENTS.md 要求一致） |
| 文件 | UTF-8，末尾保留空行 |
| 导航 | 每份文档顶部含 `> 返回 [网络知识图谱](../README.md)` |

### 5.4 不做的事（YAGNI）

- ❌ 不写示例代码仓库（这是文档模块，不是 java-core 那样的代码模块）
- ❌ 不引入额外的构建工具（纯 Markdown）
- ❌ 不创建多余的子 README（只有 `ops/network/README.md` 一个入口）
- ❌ 不重复造轮子：HTTP 演进只讲协议本身，不重复讲 Web 框架（framework 模块的职责）

---

## 六、验收标准

1. **文件完整性**：`ops/network/` 下存在 17 份 Markdown 文件，路径与目录结构一致。
2. **结构一致性**：每份主题文档遵循五段式结构，顶部含定位/热度/返回导航。
3. **内容深度**：每份文档覆盖第四节列出的知识点清单，每知识点含原理+追问+实战关联。
4. **图示规范**：关键流程用 Mermaid 或 ASCII 图说明。
5. **仓库集成**：
   - `ops/README.md` 升级为结构化入口，含 network 子模块链接。
   - 根 `README.md` 的 ops 段落同步更新。
6. **风格一致**：全中文、UTF-8、遵循仓库文档风格规则。
