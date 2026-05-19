# java-core

个人学习 Java 核心技术记录项目

[![OSCS Status](https://www.oscs1024.com/platform/badge/yintp/java-core.svg?size=small)](https://www.oscs1024.com/project/yintp/java-core?ref=badge_small)

## 模块列表

| 模块 | 说明 |
|------|------|
| [agent](#agent) | Java 探针（JavaAgent / Instrumentation） |
| [annotation](#annotation) | Java 注解（含可重复注解） |
| [apt](#apt) | 编译时注解处理器（APT） |
| [forkjoin](#forkjoin) | ForkJoin 并行计算框架 |
| [jmx](#jmx) | Java 管理扩展（JMX / MBean） |
| [jndi](#jndi) | Java 命名和目录接口（JNDI） |
| [jvm](#jvm) | JVM 类加载与初始化 |
| [lambda](#lambda) | Lambda 表达式底层原理 |
| [proxy](#proxy) | JDK 动态代理 |
| [reflect](#reflect) | Java 反射机制 |
| [rmi](#rmi) | 远程方法调用（RMI） |
| [service-provider-framework](#service-provider-framework) | 服务提供者框架 |
| [spi](#spi) | Java SPI 机制 |
| [stream](#stream) | Stream 流处理 |

---

## agent

基于 `java.lang.instrument` 实现的 Java 探针 demo。

- 通过 `premain` 方法在 JVM 启动时加载，利用 `Instrumentation` 接口注册字节码转换器（`ClassFileTransformer`），实现对类字节码的动态修改。
- **应用场景**：IDE 调试（IntelliJ IDEA）、热部署（spring-loaded）、线上诊断（Arthas）、性能分析（VisualVM）、全链路追踪（Skywalking）。
- **关键概念**：JVMTI、JVMTIAgent、instrument、JVM Attach 机制。

**模块结构**

```
agent-api    # 探针实现（PreMainAgent + TraceTransformer）
agent-server # 被探针注入的目标服务
```

---

## annotation

Java 注解 demo，重点演示**可重复注解**（`@Repeatable`）。

- 定义容器注解 `@Persons` 和成员注解 `@Person`，通过 `@Repeatable` 将 `@Person` 设为可重复。
- 运行时通过反射获取注解信息并断言验证。

---

## apt

编译时注解处理器（Annotation Processing Tool）demo。

- 自定义 `@SayHello` 注解，配套实现 `SayHelloProcessor`（继承 `AbstractProcessor`）。
- 编译期扫描被 `@SayHello` 标注的类，利用 [JavaPoet](https://github.com/square/javapoet) 自动生成对应的 `XxxHello.java` 源文件。
- **应用场景**：Lombok、MapStruct 等框架的代码生成原理。

**模块结构**

```
apt-api    # 注解定义 + 注解处理器
apt-server # 使用注解的目标类（触发代码生成）
```

---

## forkjoin

Java ForkJoin 并行计算框架 demo。

- 实现 `RecursiveTask<Long>`，将大列表按阈值递归拆分为子任务并行求和，最终合并结果。
- 演示 `fork()` / `join()` 的分治模式。

---

## jmx

Java 管理扩展（Java Management Extensions）demo。

- 定义标准 MBean 接口（`HelloMBean`、`YintpMBean`）及其实现类，注册到平台 `MBeanServer`。
- 支持通过 JConsole / RMI 远程连接并管理 MBean。
- 演示 `NotificationListener` 实现 MBean 间事件通知。
- 包含内存监控示例（`MemoryMonitor`）。

**模块结构**

```
jmx-server # MBean 定义、注册与事件监听
jmx-client # 远程客户端连接
```

---

## jndi

Java 命名和目录接口（Java Naming and Directory Interface）demo。

- 演示通过 RMI 注册中心绑定/查找 Java 对象（Object 方式）和引用（Reference 方式）。
- **JNDI 架构**：JNDI API → Naming Manager → JNDI SPI → 具体实现（RMI / LDAP / DNS 等）。
- **核心概念**：Name（命名绑定）、Directory（属性存储）、`InitialContext`、`lookup` / `bind`。
- 包含 Spring Boot（Tomcat）环境下的 JNDI 使用示例。

**模块结构**

```
jndi-api    # 共享数据对象定义
jndi-server # JNDI RMI 服务端（对象绑定）
jndi-client # JNDI 客户端（对象查找）
jndi-tomcat # Tomcat 容器下的 JNDI 示例
```

---

## jvm

JVM 类加载与类初始化 demo。

- `classload`：类加载器（ClassLoader）相关测试。
- `classinit`：类初始化顺序、触发时机（主动引用 vs 被动引用）等场景测试（`ClassInitTest` 系列共 9 个用例）。

---

## lambda

Lambda 表达式底层原理探究。

- 揭示 Java 8 Lambda 的实现机制：编译器插入 `invokedynamic`（indy）指令，JVM 首次执行时调用 BootstrapMethod（`LambdaMetafactory.metafactory`），在内存中动态生成内部类（`Theory$$Lambda$1` 等）。
- 演示 Lambda 的类型转换（`IntStream` 相关操作）。

---

## proxy

JDK 动态代理 demo。

- 定义 `Subject` 接口与 `RealSubject` 实现，通过 `InvocationHandler`（`ProxyHandler`）在方法调用前后插入增强逻辑。
- 使用 `Proxy.newProxyInstance` 生成代理对象（`$Proxy0`）。

---

## reflect

Java 反射机制 demo。

> 模块代码待完善。

---

## rmi

Java 远程方法调用（Remote Method Invocation）demo。

- 定义继承 `Remote` 的远程接口 `HelloService`，服务端实现并通过 RMI 注册表发布，客户端通过 `lookup` 获取存根并调用。
- **关键点**：接口必须继承 `Remote`，方法必须声明 `throws RemoteException`，参数/返回值需实现 `Serializable`。

**模块结构**

```
rmi-api      # 远程接口定义 + 共享数据对象
rmi-provider # RMI 服务端（接口实现 + 注册表）
rmi-consumer # RMI 客户端
```

---

## service-provider-framework

服务提供者框架（类 JDBC DriverManager 模式）demo。

- 演示《Effective Java》中描述的服务提供者框架设计模式：
  - **服务接口**（`Service`）：定义服务契约。
  - **提供者接口**（`ProviderInterface`）：服务工厂。
  - **提供者注册 API**（`ServiceManager.registerProvider`）：注册具体实现。
  - **服务访问 API**（`ServiceManager.getService`）：客户端获取服务实例。
- 提供 MySQL 和 Oracle 两种实现作为对比示例。

**模块结构**

```
service-provider-framework-api          # 框架核心接口
service-provider-framework-impl-mysql   # MySQL 实现
service-provider-framework-impl-oracle  # Oracle 实现
service-provider-framework-server       # 客户端使用示例
```

---

## spi

Java SPI（Service Provider Interface）机制 demo。

- 定义 `SearchService` 接口，分别提供 DB 搜索和文件搜索两种实现，通过 `META-INF/services/` 配置文件注册。
- 使用 `ServiceLoader` 动态加载并选择实现（工厂类 `SearchServiceFactory` 封装加载逻辑）。

**模块结构**

```
spi-api        # 服务接口定义
spi-impl-db    # DB 搜索实现
spi-impl-file  # 文件搜索实现
spi-server     # 服务加载与调用
```

---

## stream

Java Stream 流处理 demo。

- 演示 Stream 的关闭处理器（`onClose`）机制：通过 `try-with-resources` 自动触发或手动调用 `close()` 触发注册的关闭回调。
