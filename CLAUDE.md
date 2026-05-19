# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## README 自动更新规则

**每次新增或修改任何模块内容时，必须同步更新：**

1. **对应子模块的 `README.md`** — 反映该模块的最新实现状态、进度标记（✅/⬜）、类/包路径等
2. **根目录 `README.md`** — 同步更新对应模块的概要说明（如设计模式进度计数、算法专题列表等）

这条规则不可省略，即使是添加一道算法题或实现一个新设计模式也要执行。

---

## 项目构建与测试

各模块独立使用 Maven 管理，无根级 `pom.xml`。以下命令在各模块目录下执行：

```bash
# 构建
mvn compile

# 运行所有测试
mvn test

# 运行单个测试类
mvn test -Dtest=ClassName

# 运行单个测试方法
mvn test -Dtest=ClassName#methodName
```

Java 版本：1.8（JDK 8），编码：UTF-8，测试框架：JUnit 4.11。

Python 量化模块（`quantitative/`）直接运行：`python helloword.py`

---

## 代码架构

### 模块结构

```
glory/
├── algorithm/          LeetCode 刷题（Java，Maven）
├── design-pattern/     23 种设计模式（Java，Maven）
├── java-core/          14 个 Java 核心技术子模块（Java，Maven）
├── framework/          框架学习：Jackson / Spring / Hibernate Validator
├── middleware/         文档模块（MySQL、Redis、Kafka 等）
├── ops/                文档模块（Linux、K8s、Docker 等）
└── quantitative/       量化交易策略（Python）
```

### 包命名约定

所有 Java 代码统一使用 `com.yintp.*` 包前缀：
- `com.yintp.algorithm.leetcode.topicN.*`
- `com.yintp.design.pattern.*`
- `com.yintp.annotation.*`、`com.yintp.agent.*` 等

### algorithm 模块

`src/main/java/com/yintp/algorithm/leetcode/` 下按专题组织：
- `structure/` — 公共数据结构（`ListNode`、`TreeNode`、`Node`）
- `topic1/`～`topic9/` — 9 个算法专题（每个专题含 `WeekPlan.java` 和题目文件）
- `phase3/` — 第三阶段综合强化

每道题对应 `src/test/java/` 下同路径的测试文件。

### design-pattern 模块

`src/main/java/com/yintp/design/pattern/` 下按模式类型组织：
- `singleton/`、`factory/`（含 `simple/`、`method/`、`devise/`）、`builder/`
- `adapter/`、`strategy/`、`template/`

### java-core 模块

14 个独立子模块，每个有自己的 `pom.xml`：
`agent`、`annotation`、`apt`、`forkjoin`、`jmx`、`jndi`、`jvm`、`lambda`、`proxy`、`reflect`、`rmi`（含 api/provider/consumer）、`service-provider-framework`、`spi`、`stream`

### framework 模块

3 个子模块：`jackson`（自定义序列化）、`spring-framework`（注解驱动配置）、`valid`（Hibernate Validator 自定义校验器）
