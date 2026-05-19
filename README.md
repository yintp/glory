# Glory — 个人学习记录

[![OSCS Status](https://www.oscs1024.com/platform/badge/yintp/java-core.svg?size=small)](https://www.oscs1024.com/project/yintp/java-core?ref=badge_small)

Java 全栈技术学习仓库，涵盖算法、Java 核心、设计模式、框架与中间件。

---

## 模块列表

| 模块 | 说明 |
|------|------|
| [algorithm](./algorithm) | LeetCode 算法题，按专题分阶段刷题计划 |
| [java-core](./java-core) | Java 核心技术：反射、动态代理、SPI、JVM 等 14 个子模块 |
| [design-pattern](./design-pattern) | 六大设计原则 + 23 种设计模式（已实现 6/23） |
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

| 模块 | 说明 |
|------|------|
| `agent` | Java 探针（JavaAgent / Instrumentation） |
| `annotation` | Java 注解（含可重复注解） |
| `apt` | 编译时注解处理器（APT） |
| `forkjoin` | ForkJoin 并行计算框架 |
| `jmx` | Java 管理扩展（JMX / MBean） |
| `jndi` | Java 命名和目录接口（JNDI） |
| `jvm` | JVM 类加载与初始化 |
| `lambda` | Lambda 表达式底层原理 |
| `proxy` | JDK 动态代理 |
| `reflect` | Java 反射机制 |
| `rmi` | 远程方法调用（RMI） |
| `service-provider-framework` | 服务提供者框架（类 JDBC DriverManager 模式） |
| `spi` | Java SPI 机制 |
| `stream` | Stream 流处理 |

详见 [java-core/README.md](./java-core/README.md)

---

## design-pattern

面向对象六大原则（SRP / OCP / LSP / ISP / DIP / LoD）及 23 种设计模式。

**实现进度（6 / 23）**

| 类型 | 模式 | 进度 |
|------|------|------|
| 创建型 | 单例模式 | ✅ |
| 创建型 | 工厂方法模式 | ✅ |
| 创建型 | 抽象工厂模式 | ✅ |
| 创建型 | 建造者模式 | ✅ |
| 创建型 | 原型模式 | ⬜ |
| 结构型 | 适配器模式 | ✅ |
| 结构型 | 代理模式 | ⬜ |
| 结构型 | 装饰器模式 | ⬜ |
| 结构型 | 外观模式 | ⬜ |
| 结构型 | 桥接模式 | ⬜ |
| 结构型 | 组合模式 | ⬜ |
| 结构型 | 享元模式 | ⬜ |
| 行为型 | 策略模式 | ✅ |
| 行为型 | 模板方法模式 | ✅ |
| 行为型 | 观察者模式 | ⬜ |
| 行为型 | 迭代器模式 | ⬜ |
| 行为型 | 责任链模式 | ⬜ |
| 行为型 | 命令模式 | ⬜ |
| 行为型 | 备忘录模式 | ⬜ |
| 行为型 | 状态模式 | ⬜ |
| 行为型 | 访问者模式 | ⬜ |
| 行为型 | 中介者模式 | ⬜ |
| 行为型 | 解释器模式 | ⬜ |

详见 [design-pattern/README.md](./design-pattern/README.md)

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
