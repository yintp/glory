# ETF 本地分析平台 — 设计文档

**日期：** 2026-05-19  
**状态：** 已批准  
**范围：** 将现有 `quantitative/helloword.py` 改造为本地 Web 应用

---

## 1. 目标

将现有 Python ETF 轮动策略脚本改造为可通过浏览器本地访问的分析平台，支持：
- ETF 数据管理（CRUD + 时间区间查询）
- 技术指标可视化（MACD、MA、成交量）
- 机构/散户持仓比例展示（季度数据）
- ETF 热度排行（成交量异动倍数）
- 每日收盘后手动触发数据更新

---

## 2. 技术栈

| 层 | 选型 | 原因 |
|---|---|---|
| 后端 | Python FastAPI | 轻量、异步、复用现有 Python 生态 |
| 数据库 | SQLite（sqlite3 直连） | 本地无需部署，零配置 |
| 数据源 | akshare | 现有代码已使用，覆盖 A 股 ETF |
| 指标计算 | ta 库（现有） + pandas | 直接复用 helloword.py 逻辑 |
| 前端 | 原生 HTML + JS（无构建） | 本地随开随用，无 Node.js 依赖 |
| 图表 | Chart.js + chartjs-chart-financial | 支持 K 线、MACD、成交量图 |

---

## 3. 目录结构

```
quantitative/
├── app/
│   ├── main.py              # FastAPI 入口，挂载静态文件
│   ├── database.py          # SQLite 连接 & 建表初始化
│   ├── services/
│   │   ├── fetcher.py       # akshare 数据拉取（复用 helloword.py 逻辑）
│   │   ├── indicators.py    # MA/MACD/成交量指标计算
│   │   └── heat.py          # 热度评分（成交量倍数排名）
│   └── routes/
│       ├── etf.py           # ETF CRUD 路由
│       ├── analysis.py      # 指标数据查询路由
│       └── update.py        # 数据更新触发路由
├── frontend/
│   ├── index.html           # 单页入口（4 个 Tab）
│   ├── js/
│   │   ├── api.js           # 封装所有 fetch 调用
│   │   ├── charts.js        # Chart.js 图表配置与渲染
│   │   └── app.js           # Tab 路由 & 页面交互逻辑
│   └── css/
│       └── style.css        # 暗色主题样式
├── etf.db                   # SQLite 数据库文件（运行时生成）
└── helloword.py             # 保留原始脚本（参考用）
```

---

## 4. 数据库结构

### `etf_list`
| 列 | 类型 | 说明 |
|---|---|---|
| symbol | TEXT PK | ETF 代码（如 512480） |
| name | TEXT | ETF 名称（如 半导体ETF） |
| active | INTEGER | 1=启用，0=停用 |
| created_at | TEXT | 添加时间 |

### `etf_daily`
| 列 | 类型 | 说明 |
|---|---|---|
| symbol | TEXT | ETF 代码 |
| date | TEXT | 交易日（YYYY-MM-DD） |
| open | REAL | 开盘价 |
| high | REAL | 最高价 |
| low | REAL | 最低价 |
| close | REAL | 收盘价 |
| volume | REAL | 成交量 |
| PRIMARY KEY | (symbol, date) | 联合主键，幂等写入 |

### `etf_indicators`
| 列 | 类型 | 说明 |
|---|---|---|
| symbol | TEXT | ETF 代码 |
| date | TEXT | 交易日 |
| ma20 | REAL | 日线 MA20 |
| w_ma20 | REAL | 周线 MA20（前向填充） |
| m_ma12 | REAL | 月线 MA12（前向填充） |
| vol_ma20 | REAL | 成交量 20 日均量 |
| macd | REAL | MACD 线 |
| macd_signal | REAL | 信号线 |
| macd_hist | REAL | 柱状值（MACD - Signal） |
| heat_score | REAL | 热度得分（volume / vol_ma20） |
| buy_signal | INTEGER | 买入信号（0/1） |
| sell_signal | INTEGER | 卖出信号（0/1） |
| PRIMARY KEY | (symbol, date) | 联合主键 |

### `etf_holding`
| 列 | 类型 | 说明 |
|---|---|---|
| symbol | TEXT | ETF 代码 |
| period | TEXT | 数据期（如 2025Q3） |
| inst_ratio | REAL | 机构持仓比例（0-100） |
| retail_ratio | REAL | 散户持仓比例（0-100） |
| updated_at | TEXT | 入库时间 |
| PRIMARY KEY | (symbol, period) | 联合主键 |

---

## 5. API 设计

| 方法 | 路径 | 说明 |
|---|---|---|
| GET | `/api/etf` | 获取所有 ETF 列表 |
| POST | `/api/etf` | 新增 ETF（body: `{symbol, name}`） |
| PUT | `/api/etf/{symbol}` | 修改 ETF 名称/备注 |
| DELETE | `/api/etf/{symbol}` | 删除 ETF（软删除，active=0） |
| GET | `/api/etf/{symbol}/data` | 查询日线+指标，参数：`?start=YYYY-MM-DD&end=YYYY-MM-DD` |
| GET | `/api/heat/rank` | 获取所有 ETF 热度排名（按 heat_score 降序） |
| POST | `/api/update` | 触发全量增量数据更新 |
| GET | `/api/update/status` | 返回最后更新时间 & 状态 |

---

## 6. 前端功能

### Tab 1 — 图表分析（默认页）
- 左侧：热度榜单（所有 ETF 按热度排序，点击切换右侧详情）
- 右侧顶部：时间区间选择器（1M / 3M / 6M / 1Y / 自定义）
- 右侧图表区（上至下）：
  1. 价格 K 线图（OHLC） + MA20 / W_MA20 / M_MA12 均线叠加
  2. MACD 图（柱状 hist + MACD 线 + Signal 线）
  3. 成交量柱状图（红涨绿跌） + vol_ma20 均线
- 右侧信息卡片：当前信号状态（买入/卖出/观察）、机构持仓比例（标注数据期）

### Tab 2 — ETF 管理
- 表格展示所有 ETF（代码、名称、状态、最后更新日期）
- 新增：输入代码 + 名称，点击添加，后端自动拉取历史数据
- 删除：软删除，可恢复
- 编辑：修改名称/备注

### Tab 3 — 热度排行
- 全量 ETF 表格，列：排名 / ETF名称 / 热度得分（volume/vol_ma20 倍数） / 信号状态 / 最新收盘价 / 当日涨跌幅
- 热度着色：≥2x 🔥🔥🔥红色，≥1.5x 🔥🔥橙色，≥1.2x 🔥黄色，其余灰色

### Tab 4 — 数据更新
- 显示每只 ETF 的最后更新日期
- 一键更新按钮（POST /api/update）
- 更新进度：逐条显示更新结果（成功/失败）
- 上次更新时间 & 更新耗时

---

## 7. 数据流

### 数据更新流程（每日收盘后手动触发）
```
用户点击"更新数据"
  → POST /api/update
  → fetcher.py 遍历所有 active ETF
  → akshare 增量拉取（取 etf_daily 中 last_date 之后的数据）
  → INSERT OR REPLACE 写入 etf_daily
  → indicators.py 对新数据追加计算指标
  → heat.py 重新计算全量热度排名写入 etf_indicators
  → 返回 {updated: N, failed: M, last_date: "YYYY-MM-DD"}
```

### 新增 ETF 流程
```
用户输入 symbol → POST /api/etf
  → akshare 拉取全量历史数据（2021-01-01 至今）
  → 计算指标全量写库
  → 拉取季度持仓数据写入 etf_holding
  → 返回成功，列表刷新
```

### 图表查询流程
```
用户选择 ETF + 时间区间
  → GET /api/etf/{symbol}/data?start=&end=
  → SQLite JOIN etf_daily + etf_indicators
  → 返回 JSON 数组
  → Chart.js 重绘三图
```

---

## 8. 错误处理

| 场景 | 处理方式 |
|---|---|
| akshare 拉取失败 | 跳过该 symbol，记录错误日志，不中断整体更新 |
| SQLite 写入重复 | `INSERT OR REPLACE` 幂等写入，安全重跑 |
| 前端请求失败 | 右下角 toast 显示错误信息，不白屏崩溃 |
| symbol 不存在 | FastAPI 返回 404 + 中文错误消息 |
| 指标计算数据不足 | 数据不足 N 日时对应指标返回 null，图表跳过该点 |

---

## 9. 关于机构持仓数据

akshare 的机构持仓来自季度披露报告，非日频数据。
- 数据源：`fund_etf_fund_info_em()` 或基金定期报告
- 更新频率：每季度一次（Q1/Q2/Q3/Q4 报告期）
- 展示方式：信息卡片文字展示，标注"数据截至 2025Q3"
- 不参与热度评分计算

---

## 10. 启动方式

```bash
cd quantitative
pip install fastapi uvicorn akshare pandas ta
python -m uvicorn app.main:app --reload --port 8000
# 浏览器访问 http://localhost:8000
```
