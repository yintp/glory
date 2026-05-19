# Glory — 个人学习记录

[![OSCS Status](https://www.oscs1024.com/platform/badge/yintp/java-core.svg?size=small)](https://www.oscs1024.com/project/yintp/java-core?ref=badge_small)

Java 全栈技术学习仓库，涵盖算法、Java 核心、设计模式、框架与中间件。

---

## 模块列表

| 模块 | 说明 |
|------|------|
| [algorithm](./algorithm) | LeetCode 算法题，按专题分阶段刷题计划 |
| [java-core](./java-core) | Java 核心技术：反射、动态代理、SPI、JVM 等 |
| [design-pattern](./design-pattern) | 六大设计原则 + 常用设计模式 |
| [framework](./framework) | Spring、MyBatis、Netty、Dubbo 等框架 |
| [middleware](./middleware) | MySQL、Redis、Kafka、ES 等中间件 |
| [ops](./ops) | 运维：Linux、K8s、Docker、计算机网络 |

---

## algorithm

系统化 LeetCode 刷题，共 10 个专题，覆盖 50+ 核心题目。

```
第一阶段：基础数据结构（第1-2周）
第二阶段：基础算法专题（第3-10周）
          topic1 数组&哈希表 / topic2 双指针&滑动窗口 / topic3 链表
          topic4 栈&队列 / topic5 二叉树 / topic6 图&BFS/DFS
          topic7 二分搜索 / topic8 动态规划 / topic9 回溯
第三阶段：综合强化（第11-14周）
第四阶段：模拟面试冲刺（第15周起）
```

详见 [algorithm/README.md](./algorithm/README.md)

---

## java-core

- `agent` — Java 探针
- `spi` — SPI 机制
- `proxy` — 动态代理
- `lambda` / `stream` — Lambda 与 Stream 流处理
- `apt` / `annotation` — APT 与注解处理
- `reflect` — 反射机制
- `rmi` — 远程方法调用
- `jmx` / `jndi` / `forkjoin` — 管理扩展、命名目录、并行计算
- `jvm` — JVM 原理

---

## design-pattern

面向对象六大原则（SRP / LSP / DIP / ISP / LoD / OCP）及常用模式：

- `singleton` — 单例模式
- `strategy` — 策略模式
- `template` — 模板方法模式
- `builder` — 建造者模式
- `factory` — 工厂模式

---

## framework

- Spring / Spring Boot / Spring Cloud
- MyBatis / Sharding
- Netty / WebSocket
- Dubbo

---

## middleware

- MySQL / Redis
- Kafka / RocketMQ
- Elasticsearch / MongoDB

---

## ops（运维）

- Linux
- K8s
- Docker
- 计算机网络
