# HTTP 协议全解

> **一句话定位**：HTTP 是 Java 后端面试最高频的应用层协议，演进/缓存/认证几乎必考。
> **面试热度**：⭐⭐⭐⭐⭐
> **返回**：[网络知识图谱](../README.md)

---

## 一、概念定义

### 1.1 HTTP 报文结构

HTTP（HyperText Transfer Protocol）是一种**无状态、基于请求-响应模型**的应用层协议，默认端口 80。报文分为**请求报文**与**响应报文**两类，均采用纯文本（HTTP/1.x）或二进制帧（HTTP/2+）形式承载。

**请求报文结构**（三段式）：

```
POST /api/v1/users HTTP/1.1          ← 请求行（方法 URI 协议版本）
Host: api.example.com                ← 请求首部
Content-Type: application/json
Authorization: Bearer xxx.yyy.zzz
Content-Length: 42

{"name":"yintp","role":"admin"}      ← 请求体（可选）
```

**响应报文结构**（三段式）：

```
HTTP/1.1 200 OK                      ← 状态行（版本 状态码 原因短语）
Content-Type: application/json       ← 响应首部
Cache-Control: max-age=600
Content-Length: 28

{"code":0,"msg":"success"}           ← 响应体（可选）
```

> **要点**：请求行/状态行与首部之间用 `CRLF`（`\r\n`）分隔，首部结束后必须有一个空行（即连续两个 CRLF）才标志实体体开始。

### 1.2 方法语义

HTTP 方法定义了资源的操作语义。RFC 7231/9110 对核心方法做了如下规范：

| 方法 | 语义 | 安全 | 幂等 | 可缓存 | 典型用途 |
|------|------|:----:|:----:|:------:|---------|
| GET | 获取资源表示 | ✅ | ✅ | ✅ | 查询、列表、详情 |
| POST | 创建资源 / 提交数据 | ❌ | ❌ | ❌ | 表单提交、上传、动作触发 |
| PUT | 用请求体替换目标资源 | ❌ | ✅ | ❌ | 整体更新、覆盖 |
| DELETE | 删除目标资源 | ❌ | ✅ | ❌ | 删除记录 |
| PATCH | 对资源做部分修改 | ❌ | ❌ | ❌ | 局部字段更新 |
| HEAD | 仅取响应首部，不含体 | ✅ | ✅ | ✅ | 预检、元信息探查 |
| OPTIONS | 询问服务器支持的方法/预检 | ✅ | ✅ | ❌ | CORS 预检 |
| TRACE | 回显请求用于诊断 | ✅ | ✅ | ❌ | 调试（生产禁用） |
| CONNECT | 建立隧道（HTTPS 代理） | ❌ | ❌ | ❌ | 正/反向代理 CONNECT |

**核心语义说明**：

- **幂等（Idempotent）**：同一请求执行一次与多次，对资源状态的影响一致。PUT/DELETE 天然幂等（重复执行结果不变），POST/PATCH 不保证幂等。
- **安全（Safe）**：方法不改变服务器状态，仅供读取。GET/HEAD/OPTIONS 安全，POST/PUT/DELETE/PATCH 不安全。
- **幂等 ≠ 安全**：DELETE 幂等但不安全（改变了状态），GET 既安全又幂等。

### 1.3 无状态性

HTTP 协议本身**无状态**：服务器默认不维护客户端会话，每个请求独立处理，不依赖之前的请求上下文。这一设计简化了服务器实现、利于水平扩展，但也带来登录态、购物车等业务难以表达的缺陷，因此工程上常通过 **Cookie + Session** 或 **Token（JWT）** 在应用层"补"上状态。

---

## 二、原理与流程

### 2.1 HTTP/1.0 → 1.1 演进

| 特性 | HTTP/1.0 | HTTP/1.1 |
|------|---------|---------|
| 连接模型 | 默认短连接（每请求一次 TCP） | 默认长连接 `Connection: keep-alive` |
| Host 头 | 无 | ✅ 必备（支持虚拟主机） |
| 管线化 | 无 | 可选（实际未流行） |
| 分块传输 | 不支持 | `Transfer-Encoding: chunked` |
| 缓存控制 | 仅 `Expires`/`If-Modified-Since` | 增 `Cache-Control`/`ETag`/`Vary` |
| 状态码 | 简单 | 新增 1xx/206/303/307/405/410 等 |

**关键演进点详解**：

1. **长连接 keep-alive**：一次 TCP 连接可串行承载多个请求，省去重复握手开销。默认开启，可由 `Connection: close` 关闭。
2. **Host 头**：支持同一 IP 多域名虚拟主机，是 Nginx/网关按域名路由的前提。
3. **管线化（Pipelining）**：允许客户端在收到前一个响应前发出下一个请求，但要求响应按序返回 → 仍存在队头阻塞，浏览器默认禁用。
4. **分块传输 chunked**：响应体大小未知时（如动态生成），按 `Transfer-Encoding: chunked` 分块发送，以 `0\r\n\r\n` 结束。

```
HTTP/1.1 200 OK
Transfer-Encoding: chunked

4\r\n
yint\r\n
5\r\n
p123\r\n
0\r\n
\r\n
```

### 2.2 HTTP/2 详解

HTTP/2（RFC 7540，2015）在不改 HTTP 语义（方法/状态码/首部含义不变）的前提下，对传输层做了大手术：

- **二进制分帧（Binary Framing）**：报文拆分为 Frame（HEADERS/DATA/SETTINGS/PING 等），二进制而非文本，解析更高效、容错更严格。
- **多路复用（Multiplexing）**：一个 TCP 连接上可并发多个 Stream，每个 Stream 内部双向独立，彻底解决应用层队头阻塞。
- **首部压缩 HPACK**：静态表 + 动态表 + 哈夫曼编码，重复首部只发索引，体积可降 80%+。
- **Server Push**：服务器主动推送资源（如 HTML 内联的 CSS/JS），减少往返（已被 Chrome 弃用，HTTP/3 不再支持）。
- **流优先级**：客户端可指定 Stream 权重与依赖，保证关键资源优先。
- **遗留问题**：TCP 层队头阻塞仍在：一旦丢包，整个连接所有 Stream 被阻塞等待重传。

**HTTP/2 多路复用时序**（一个连接上并发三路请求）：

```mermaid
sequenceDiagram
    participant Client
    participant Server
    Note over Client,Server: 单条 TCP 连接（含 N 个 Stream）
    Client->>Server: GET /index.html (Stream 1, HEADERS 帧)
    Client->>Server: GET /style.css (Stream 3, HEADERS 帧)
    Client->>Server: GET /app.js (Stream 5, HEADERS 帧)
    Note over Server: 并发处理，乱序返回
    Server-->>Client: 响应 /style.css (Stream 3, DATA 帧)
    Server-->>Client: 响应 /app.js (Stream 5, DATA 帧)
    Server-->>Client: 响应 /index.html (Stream 1, DATA 帧)
    Note over Client: 按 Stream ID 重组，无需等待顺序
```

### 2.3 HTTP/3 详解

HTTP/3（RFC 9114，2022）将传输层由 TCP 切换为 **QUIC**（基于 UDP，RFC 9000），带来：

- **基于 QUIC**：内建 TLS 1.3，握手 + 加密一次完成；UDP 自然支持连接迁移。
- **0-RTT 数据**：重连场景下首个包即可携带应用数据，延迟显著下降。
- **连接迁移**：基于 Connection ID 而非四元组识别连接，手机从 WiFi 切 4G 不掉线。
- **彻底解决 TCP 队头阻塞**：QUIC Stream 间独立，某 Stream 丢包只阻塞自身，不影响其他 Stream。
- **更快的握手**：1-RTT 首次连接（TCP+TLS 通常 2-3 RTT），0-RTT 重连。

### 2.4 HTTP/1.1 vs 2.0 vs 3.0 对比

| 维度 | HTTP/1.1 | HTTP/2 | HTTP/3 |
|------|---------|--------|--------|
| 传输层 | TCP | TCP | QUIC（基于 UDP） |
| 报文编码 | 文本 | 二进制分帧 | 二进制分帧（基于 QUIC 帧） |
| 多路复用 | ❌（串行，pipelining 失败） | ✅ 单连接多 Stream | ✅ 多 Stream 独立 |
| 队头阻塞 | 应用层 + TCP 层 | 应用层解决 / TCP 层遗留 | 彻底解决 |
| 首部压缩 | ❌ | HPACK | QPACK（适配乱序） |
| 加密 | 可选（HTTPS） | 可选（实践中强制 TLS） | 强制 TLS 1.3 |
| 握手 RTT | TCP 1 + TLS 1-2 = 2-3 RTT | 同 1.1 | 1-RTT 首次 / 0-RTT 重连 |
| 连接迁移 | ❌ | ❌ | ✅（基于 CID） |
| Server Push | ❌ | ✅（已弃用） | ❌ |
| 部署成熟度 | 100% | 主流 | 推进中（CDN/大厂已用） |

### 2.5 状态码全谱

状态码共 5 类，由 RFC 9110 统一规范。

| 类别 | 含义 | 常见码 |
|------|------|--------|
| 1xx | 信息性（继续/切换协议） | 100 Continue、101 Switching Protocols |
| 2xx | 成功 | 200 OK、201 Created、204 No Content、206 Partial Content |
| 3xx | 重定向 | 301/302/303/307/308、304 Not Modified |
| 4xx | 客户端错误 | 400/401/403/404/405/408/409/413/429 |
| 5xx | 服务器错误 | 500/502/503/504 |

**重点状态码详解**：

- **101 Switching Protocols**：协议升级，如 WebSocket 握手时由 HTTP 升级到 ws/wss。
- **301 Moved Permanently**：永久重定向，搜索引擎收录新 URL；**308** 同义且不改方法。
- **302 Found**：临时重定向，**303 See Other** 强制改 GET（POST→GET），**307** 临时重定向且保留方法。
- **304 Not Modified**：协商缓存命中，客户端用本地副本。
- **401 Unauthorized**：未认证，需带凭据（WWW-Authenticate 指明方案）。
- **403 Forbidden**：已认证但无权限。
- **408 Request Timeout**：请求超时。
- **429 Too Many Requests**：限流（配合 `Retry-After`）。
- **502 Bad Gateway**：网关/代理收到上游无效响应。
- **504 Gateway Timeout**：网关等待上游超时。

> **301 vs 302**：301 会被浏览器与 CDN 缓存，重启服务也无法临时改向；302 不缓存，每次都走原 URL。短链场景需根据"是否永久"择用。

### 2.6 缓存机制

HTTP 缓存分两层：**强缓存**（不与服务器通信，命中即用）+ **协商缓存**（与服务器校验，决定是否复用本地副本）。

**强缓存相关头**：

- `Cache-Control: max-age=600`：相对 600 秒内有效（高优先级）。
- `Cache-Control: no-cache`：**强制协商**，每次都要校验服务器。
- `Cache-Control: no-store`：**禁止缓存**（连磁盘都不许存）。
- `Cache-Control: public/private`：是否允许中间 CDN 缓存。
- `Expires: Wed, 07 Aug 2026 12:00:00 GMT`：绝对过期时间（HTTP/1.0 遗产，被 max-age 覆盖）。

**协商缓存相关头**（两对，优先 ETag）：

- `ETag: "v3-abc"` + `If-None-Match: "v3-abc"` → 304 / 200
- `Last-Modified: Wed, 07 Aug 2026 06:00:00 GMT` + `If-Modified-Since: ...` → 304 / 200

**缓存决策流程**：

```mermaid
flowchart TD
    A[发起请求] --> B{本地有无缓存副本?}
    B -- 无 --> Z[向服务器请求 200]
    B -- 有 --> C{Cache-Control: no-store?}
    C -- 是 --> Z
    C -- 否 --> D{命中强缓存?<br/>max-age 未过期}
    D -- 是 --> R1[直接用本地副本 200 from cache]
    D -- 否 --> E{no-cache 或已过期?}
    E -- 是 --> F{带 If-None-Match/If-Modified-Since<br/>向服务器校验}
    F -- 304 --> R2[复用本地副本 304]
    F -- 200 --> Z
    E -- 否 --> R1
    Z --> END[渲染/返回]
    R1 --> END
    R2 --> END
```

> **坑点**：`no-cache` 不是"不缓存"，而是"缓存但要校验"；真正不缓存是 `no-store`。

### 2.7 Cookie / Session / Token / JWT 对比

| 维度 | Cookie | Session | Token | JWT |
|------|--------|---------|-------|-----|
| 存储位置 | 浏览器（键值对） | 服务端内存/Redis | 客户端（localStorage/Cookie） | 客户端 |
| 状态 | 客户端持有 | 服务端持有 | 无状态（自包含） | 无状态（自包含） |
| 传输 | 自动随请求带 `Cookie` 头 | 依赖 Cookie 传 sessionId | `Authorization: Bearer xxx` | `Authorization: Bearer xxx` |
| 跨域 | 受同源策略限制 | 受同源策略限制 | 天然跨域友好 | 天然跨域友好 |
| 服务端扩展 | 易 | 难（需分布式 Session） | 易（无状态） | 易（无状态） |
| 失效控制 | 客户端可删 | 服务端可主动失效 | 难（需黑名单） | 难（短时效 + 刷新） |
| 安全要点 | HttpOnly/Secure/SameSite | 固定 SessionId 泄漏风险 | 防 XSS 存 localStorage | 签名防篡改 + 加密敏感字段 |

**JWT 结构**（三段，点号分隔，均 Base64URL 编码）：

```
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5aW50cCJ9.SflKxwRJSM...
└─ Header ─┘  └─ Payload ─┘              └─ Signature ─┘
alg/typ       claims (sub/exp/iat/...)   HMACSHA256(base64(header)+"."+base64(payload), secret)
```

**网络层安全防御**：

- **CSRF（跨站请求伪造）**：利用浏览器自动带 Cookie 的特性。防御：`SameSite=Lax/Strict`、CSRF Token、`Origin`/`Referer` 校验。
- **CORS（跨源资源共享）**：浏览器同源策略的官方放行机制。预检 `OPTIONS` + 响应头 `Access-Control-Allow-Origin/Methods/Headers/Allow-Credentials`。
- **XSS（跨站脚本）**：注入恶意脚本读 Cookie/localStorage。防御：Cookie 设 `HttpOnly`、输出转义、CSP。

**CORS 预检流程**（非简单请求）：

```mermaid
sequenceDiagram
    participant Browser
    participant Server
    Browser->>Server: OPTIONS /api + Origin + Access-Control-Request-*
    Server-->>Browser: 200 + Access-Control-Allow-Origin/Methods/Headers
    Note over Browser: 预检通过
    Browser->>Server: POST /api + Origin + 实际请求头
    Server-->>Browser: 200 + Access-Control-Allow-Origin + 响应体
```

---

## 三、高频追问与面试题

### Q1：HTTP/1.1 的管线化为什么不流行？

**参考答案**：管线化允许同一 TCP 连接上"请求 i 响应 i 之前"就发出"请求 i+1"，理论上降低往返延迟。但其要求**响应必须按请求顺序返回**，前一个响应慢会阻塞后一个（典型的队头阻塞）；同时存在以下工程问题：
- 中间代理（部分老代理）不支持或不透传管线化；
- 幂等性约束：规范要求只有幂等方法才能管线化，限制范围；
- 取消与重试复杂：失败后如何回滚多个请求无标准。

因此主流浏览器默认关闭管线化，转而采用"每域名 6 个并发 TCP 连接"的并行方案，直到 HTTP/2 用多路复用彻底替代。

**追问**：HTTP/2 多路复用和管线化本质区别？
> 管线化是"请求并发、响应串行"；HTTP/2 多路复用是"请求并发、响应乱序"，每个 Stream 独立，从根本上消除应用层队头阻塞。

### Q2：HTTP/2 解决了 HTTP/1.1 的队头阻塞吗？彻底吗？

**参考答案**：**部分解决**。HTTP/2 通过二进制分帧 + Stream 多路复用，解决了**应用层队头阻塞**——同一连接上各 Stream 响应乱序返回，互不阻塞。但 HTTP/2 仍跑在 TCP 上，**TCP 层队头阻塞依旧存在**：一旦某序号包丢失，TCP 必须等待重传才能把后续数据交给应用层，于是该连接上所有 Stream 都会被阻塞。丢包率高的网络（如移动弱网）反而可能比 HTTP/1.1 多连接更差。

**追问**：为什么 HTTP/2 不直接换掉 TCP？
> 历史包袱：HTTP/2 设计于 2012-2015，QUIC 尚不成熟；TCP 普适且内核态优化好；改造 TCP 等于重做传输层，风险大。HTTP/3 才选择直接基于 QUIC 彻底解决。

### Q3：HTTP/3 为什么弃用 TCP 改用 UDP？

**参考答案**：四点核心动机：
1. **彻底消除 TCP 层队头阻塞**：QUIC 的 Stream 间独立重传，丢包只影响该 Stream。
2. **握手与加密合并**：QUIC 内建 TLS 1.3，1-RTT 首次握手、0-RTT 重连，远低于 TCP+TLS 的 2-3 RTT。
3. **连接迁移**：基于 Connection ID 识别连接，IP 切换（WiFi↔4G）不断流，符合移动场景。
4. **用户态实现利于演进**：QUIC 跑在用户态，迭代无需等内核升级，协议演进快。

**追问**：UDP 不可靠，QUIC 怎么保证可靠？
> QUIC 在 UDP 之上自己实现了**序号、确认、重传、拥塞控制**（CUBIC/BBR 可插拔）等可靠传输机制，相当于把 TCP 的可靠能力搬到用户态，且做了 Stream 级隔离与更细的 ACK 设计。

### Q4：强缓存和协商缓存的优先级？304 怎么触发？

**参考答案**：优先级：**强缓存 > 协商缓存**。
1. 浏览器先看本地副本是否在 `max-age` 内未过期 → 命中强缓存，直接用（响应 `200 from disk/memory cache`），**不发请求**。
2. 强缓存过期或带 `no-cache` 时，进入协商缓存：浏览器带上 `If-None-Match`（对应 ETag）和 `If-Modified-Since`（对应 Last-Modified）向服务器校验。
3. 服务器若判定资源未变，回 **304 Not Modified**（无响应体，体积小），浏览器用本地副本；若变了，回 **200 + 新资源 + 新 ETag/Last-Modified**。
4. ETag 优先于 Last-Modified（精度到字节 vs 精度到秒）。

**追问**：浏览器 F5 与 Ctrl+F5 的缓存行为差异？
> F5：跳过强缓存，发起协商缓存（带 If-None-Match/If-Modified-Since）；Ctrl+F5：彻底禁用缓存，不发条件头，强制服务器返回 200 全量资源。

### Q5：GET 和 POST 的本质区别？POST 一定不幂等吗？

**参考答案**：
- **本质区别（语义层）**：GET 用于获取资源（安全+幂等+可缓存），POST 用于提交数据创建资源（不安全+不幂等+不可缓存）。
- **协议层差异**：
  - GET 参数在 URL query（有长度限制、被日志/历史/收藏夹记录、被 CDN 缓存），POST 在 body（相对私密、可传大文件）；
  - GET 应为安全方法，不应有副作用，但浏览器对此不强制。
- **POST 一定不幂等？** 否。**幂等性是设计语义而非方法名**。例如 `POST /orders/123/cancel` 重复调用结果不变，是幂等的；而 `POST /orders` 创建订单不幂等。RFC 并未禁止 POST 幂等，只是"通常"不保证。

**追问**：那为什么不直接用 POST 替代所有方法？
> 失去语义会导致：缓存策略错乱（POST 不可缓存却用了 GET 风格）、安全审计失真、网关/代理行为不确定、CDN 无法缓存、幂等重试机制无法依赖。RESTful 强调用方法语义驱动架构。

### Q6：Cookie/Session/Token/JWT 四者区别？

**参考答案**：
- **Cookie**：浏览器侧的键值存储，自动随同源请求带，是"载体"，可装 sessionId 或 JWT。
- **Session**：服务端存储用户态，靠 Cookie 里的 sessionId 关联；扩展难（需分布式 Session/Redis）、可被服务端主动失效。
- **Token**：自包含凭据，无状态，服务端只校验签名/有效性；天然跨域；难主动失效（需黑名单）。
- **JWT**：Token 的一种实现标准（RFC 7519），三段式 Header.Payload.Signature，签名防篡改、可携带 claims；短时效 + 刷新 token 解决失效难题。

选型：单体/可控 → Session；微服务/多端 → JWT；高安全要求 → JWT + 服务端黑名单 + 短时效。

**追问**：JWT 如何做"主动登出"？
> 无状态天然不支持。常见方案：①Redis 维护黑名单（jti + 过期时间），每次校验查黑名单；②Access Token 短时效（15min）+ Refresh Token 长时效，登出只废 Refresh，Access 自然过期；③版本号校验，user 表加 token_version，刷新即递增。

### Q7：CORS 预检请求什么时候触发？如何减少预检开销？

**参考答案**：**非简单请求**触发预检。简单请求需同时满足：方法为 GET/HEAD/POST、首部仅 `Accept/Accept-Language/Content-Language/Content-Type`（且值为 `text/plain`/`application/x-www-form-urlencoded`/`multipart/form-data`）、无 ReadableStream。任一不满足（如自定义头 `Authorization` 是例外允许、`Content-Type: application/json` 触发）即触发 `OPTIONS` 预检。

减少预检开销：
- 服务端返回 `Access-Control-Max-Age: 600` 让浏览器缓存预检结果 600 秒；
- 尽量用简单请求（`Content-Type` 用 form 格式）；
- 自定义头收敛到一组；
- 用同域代理绕过 CORS（dev 环境常用 vite/nginx proxy）。

**追问**：带 Cookie 的跨域请求有什么特殊要求？
> 必须：①服务端 `Access-Control-Allow-Origin` 不能为 `*`，必须精确域名；②`Access-Control-Allow-Credentials: true`；③前端 `fetch(url, { credentials: 'include' })`；④Cookie 需 `SameSite=None; Secure`（仅 HTTPS）。

### Q8：301 和 302 在缓存层有什么差异？短链该用哪个？

**参考答案**：
- **301 永久重定向**：浏览器与 CDN 默认**永久缓存**映射，后续请求直接跳新 URL，**不再访问短链服务器**。流量不再回流，无法做访问统计、无法临时切改。
- **302 临时重定向**：默认**不缓存**，每次都访问短链服务器再跳转，可统计 PV/UV、可动态切目标，但服务压力与延迟更大。

**短链选型**：通常用 **302**，理由：
1. 需要统计点击量、地域、UA → 必须回流服务器；
2. 短链可能临时切到活动页/失效页/风控页，需要动态决策；
3. CDN 不缓存，保证 301 切换后用户立即生效。

例外：永久业务下线且不统计 → 用 301 减压。

**追问**：短链跳转如何兼顾"统计"和"低延迟"？
> 多级缓存：①Redis 缓存短码→长链映射，命中直接 302；②本地 Caffeine 缓存做二级；③布隆过滤器拦截不存在的短码，避免缓存穿透；④CDN 边缘节点缓存（适合纯静态跳转），但需配合短时效（如 30s）兼顾动态切改。

---

## 四、实战与 Java 生态关联

### 4.1 JDK HttpClient（Java 11+）

`java.net.http.HttpClient` 是 JDK 内置的现代 HTTP 客户端，支持 HTTP/2、同步/异步、连接池。

```java
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

HttpClient client = HttpClient.newBuilder()
    .version(HttpClient.Version.HTTP_2)          // 默认 HTTP/2，协商失败回退 1.1
    .connectTimeout(Duration.ofSeconds(3))
    .followRedirects(HttpClient.Redirect.NORMAL) // 跟随 301/302/303/307/308
    .build();

HttpRequest req = HttpRequest.newBuilder()
    .uri(URI.create("https://api.example.com/users"))
    .timeout(Duration.ofSeconds(5))
    .header("Authorization", "Bearer xxx.yyy.zzz")
    .header("Accept-Encoding", "gzip")
    .GET()
    .build();

// 同步
HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
// resp.statusCode() / resp.headers() / resp.body()

// 异步
client.sendAsync(req, HttpResponse.BodyHandlers.ofString())
      .thenApply(HttpResponse::body)
      .thenAccept(System.out::println)
      .exceptionally(e -> { e.printStackTrace(); return null; });
```

要点：
- `version(HTTP_2)` 会与服务器 ALPN 协商，失败自动降级。
- 连接池由 JDK 内部维护，单 HttpClient 实例复用，建议做单例。
- `BodyHandlers` 提供 `ofString/ofByteArray/ofFile/ofInputStream`，可流式处理大响应。

### 4.2 okhttp / RestTemplate / WebClient 选型

| 客户端 | 维护方 | 同步/异步 | HTTP/2 | 适用场景 |
|--------|--------|----------|:------:|---------|
| JDK HttpClient | Oracle | 同步+异步 | ✅ | 不引三方依赖、JDK 11+ |
| okhttp 4 | Square | 同步+异步 | ✅（ALPN） | Android、通用后端，API 友好 |
| RestTemplate | Spring | 同步 | ✅（底层可选） | 传统 Spring MVC，**维护模式**（不推荐新项目） |
| WebClient | Spring | 响应式（Reactor） | ✅ | Spring WebFlux/响应式栈，高并发 IO |
| RestClient | Spring 6.1+ | 同步流畅 | ✅ | Spring 官方替代 RestTemplate |

选型建议：①新项目 Spring 6+ → RestClient（同步）/ WebClient（响应式）；②Android/无框架 → okhttp；③无依赖洁癖 → JDK HttpClient；④老 Spring Boot 1.x/2.x → RestTemplate。

### 4.3 Spring Boot 中的缓存控制

**响应层 Cache-Control**（Controller 直接设头）：

```java
@GetMapping("/users/{id}")
public ResponseEntity<User> get(@PathVariable Long id) {
    User u = userService.findById(id);
    return ResponseEntity.ok()
        .cacheControl(CacheControl.maxAge(600, TimeUnit.SECONDS).cachePublic())
        .eTag("v" + u.getVersion())   // 配合协商缓存
        .body(u);
}
```

**方法层 @Cacheable**（Spring Cache 抽象，本地/Redis/caffeine）：

```java
@Cacheable(value = "users", key = "#id", unless = "#result == null")
public User findById(Long id) {
    return userRepo.findById(id).orElse(null);
}
```

二者关系：`Cache-Control` 控制**浏览器/CDN 层缓存**（HTTP 缓存），`@Cacheable` 控制**应用进程内/分布式缓存**。最佳实践是双层：边缘用 HTTP 缓存扛读，应用用 @Cacheable 防穿透。

### 4.4 抓包实战

```bash
# 抓本地 8080 端口的 HTTP 报文（含明文头与体）
sudo tcpdump -A -i lo port 8080

# 过滤 GET 请求行
sudo tcpdump -A -i lo port 8080 | grep -A2 'GET '

# Wireshark：捕获 lo0/eth0 → 过滤器 http 或 http.request.method == "POST"
```

注意：HTTPS 抓包需用 `SSLKEYLOGFILE` 配合 Wireshark 解密，或在客户端用 Charles/mitmproxy 做中间人。

---

## 五、系统设计案例

### 5.1 设计一个 RESTful 短链服务的 HTTP 接口

**需求**：输入长 URL，生成 6-8 位短码，访问短码跳转到原 URL，支持访问统计、防刷限流、缓存策略。

**接口设计（RESTful）**：

| 方法 | 路径 | 说明 | 响应 |
|------|------|------|------|
| POST | `/api/v1/shorten` | 提交长链生成短链 | 201 + `{shortUrl}` |
| GET | `/{code}` | 访问短链跳转 | 302 → 长链 |
| GET | `/api/v1/links/{code}/stats` | 查询访问统计 | 200 + `{pv,uv,...}` |
| DELETE | `/api/v1/links/{code}` | 失效短链 | 204 |

**关键设计决策**：

1. **跳转码选择 301 vs 302**：采用 **302**。
   - 理由：①需要统计点击回流服务器；②支持临时切活动/风控页；③CDN 不缓存，失效立即生效。
   - 代价：服务压力大 → 用多级缓存 + 布隆过滤器化解。

2. **缓存策略**（结合 HTTP 缓存 + 应用缓存）：
   - 跳转响应：`Cache-Control: no-store`（每次回流统计），但服务内部用 Redis 缓存 `code→longUrl`；
   - 统计接口：`Cache-Control: max-age=10, public`（10s 内允许多端复用）；
   - 生成的短链映射：Redis 缓存，旁路写回数据库。

3. **防刷限流**：
   - 网关层：令牌桶 / 滑动窗口（如 Sentinel），对单 IP/单 code 限流；
   - 响应 429 + `Retry-After: 60`；
   - 异常频次触发风控：跳验证页或 403。

4. **幂等设计**：
   - `POST /api/v1/shorten` 不幂等（重复提交会产生新短码），但业务上要求"同长链同短码" → 用 `长链 hash → code` 做幂等键，存在 Redis 去重；
   - 客户端可带 `Idempotency-Key` 头，服务端去重表保证不重复创建。

**整体流程**（一次短链访问）：

```mermaid
sequenceDiagram
    participant User
    participant CDN
    participant Gateway as 网关(限流)
    participant App as 短链服务
    participant Redis
    participant DB
    User->>CDN: GET /abc123
    CDN->>Gateway: 回源（302 不缓存）
    Gateway->>App: 通过限流
    App->>Redis: GET code:abc123
    Redis-->>App: 命中 longUrl
    Note over App: 异步累加 PV/UV
    App-->>CDN: 302 Location: https://long.url/x
    CDN-->>User: 302
    User->>User: 跳转长链
    Note over App: 布隆过滤器预拦截非法码<br/>避免缓存穿透打穿 DB
```

**容量评估**（面试常问）：
- 假设日活 1 亿次跳转、平均响应 302 体积 300B → 日流量 ~30GB、峰值 QPS ~5k；
- 短码 6 位 Base62 可表示 62^6 ≈ 568 亿，足够支撑百亿级长链；
- Redis 单节点 10w QPS 足够，多级缓存可扛百万级。

**Java 生态落地**：
- 框架：Spring Boot + Spring WebFlux（响应式提升并发）；
- 缓存：Caffeine（本地）+ Redis（分布式）；
- 限流：Sentinel 或 Bucket4j；
- HttpClient：服务间用 RestClient/JDK HttpClient 调用统计/风控下游。

---

## 六、参考与延伸

- RFC 9110（HTTP 语义）、RFC 9112（HTTP/1.1）、RFC 9113（HTTP/2）、RFC 9114（HTTP/3）
- RFC 9000（QUIC）、RFC 7519（JWT）、RFC 6454（同源策略）、Fetch 标准（CORS）
- 延伸阅读：[HTTPS/TLS](./https-tls.md)、[DNS](./dns.md)、[TCP 连接](../02-transport/tcp-connection.md)
- 仓库内关联：`framework/spring-framework`（REST 接口）、`framework/valid`（参数校验/幂等）、`framework/jackson`（JSON 序列化）

> **返回**：[网络知识图谱](../README.md)
