// ── 工具 ─────────────────────────────────────────────────────────────
function esc(s) {
  return String(s)
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;')
    .replace(/'/g, '&#39;');
}

function showToast(msg, isError = false) {
  const t = document.getElementById('toast');
  t.textContent = msg;
  t.className = 'show' + (isError ? ' error' : '');
  setTimeout(() => t.className = '', 3000);
}

function dateRangeFor(range) {
  const end = new Date();
  const start = new Date();
  const map = { '1M': 1, '3M': 3, '6M': 6, '1Y': 12 };
  start.setMonth(start.getMonth() - (map[range] || 3));
  return {
    start: start.toISOString().slice(0, 10),
    end: end.toISOString().slice(0, 10),
  };
}

function heatBadge(score) {
  if (score >= 2)   return { text: '🔥🔥🔥', cls: 'heat-3' };
  if (score >= 1.5) return { text: '🔥🔥',   cls: 'heat-2' };
  if (score >= 1.2) return { text: '🔥',     cls: 'heat-1' };
  return { text: '—', cls: 'heat-0' };
}

function signalText(buy, sell) {
  if (buy)  return { text: '买入 ▲', cls: 'signal-buy' };
  if (sell) return { text: '卖出 ▼', cls: 'signal-sell' };
  return { text: '观察 —', cls: 'signal-watch' };
}

// ── Tab 切换 ─────────────────────────────────────────────────────────
document.querySelectorAll('.tab-btn').forEach(btn => {
  btn.addEventListener('click', () => {
    const tab = btn.dataset.tab;
    document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
    document.querySelectorAll('.tab-panel').forEach(p => p.classList.remove('active'));
    btn.classList.add('active');
    document.getElementById('tab-' + tab).classList.add('active');
    if (tab === 'rank')   loadRank();
    if (tab === 'manage') loadManage();
    if (tab === 'update') loadUpdateStatus();
  });
});

// ── 状态 ─────────────────────────────────────────────────────────────
let selectedSymbol = null;
let selectedRange = '3M';

// ── 热度侧边栏 ───────────────────────────────────────────────────────
async function loadHeatList() {
  try {
    const rank = await API.getHeatRank();
    const list = document.getElementById('heat-list');
    list.innerHTML = rank.map(r => {
      const b = heatBadge(r.heat_score || 0);
      return `<div class="heat-item${r.symbol === selectedSymbol ? ' selected' : ''}"
                   data-symbol="${esc(r.symbol)}" data-name="${esc(r.name)}">
                <span class="heat-item-name">${esc(r.name)}</span>
                <span class="heat-badge ${b.cls}">${b.text}</span>
              </div>`;
    }).join('');
    list.querySelectorAll('.heat-item').forEach(el => {
      el.addEventListener('click', () => selectEtf(el.dataset.symbol, el.dataset.name));
    });
    // If nothing selected yet, auto-select first
    if (!selectedSymbol && rank.length > 0) selectEtf(rank[0].symbol, rank[0].name);
  } catch (e) {
    // Heat list empty — show manage prompt
    document.getElementById('heat-list').innerHTML =
      '<div style="padding:12px;color:var(--text2);font-size:12px">请先在 ETF 管理中添加 ETF</div>';
  }
}

// ── ETF 图表加载 ──────────────────────────────────────────────────────
async function selectEtf(symbol, name) {
  selectedSymbol = symbol;
  document.getElementById('chart-title').textContent = name + ' (' + symbol + ')';
  document.querySelectorAll('.heat-item').forEach(el => {
    el.classList.toggle('selected', el.dataset.symbol === symbol);
  });
  await loadChartData();
}

async function loadChartData() {
  if (!selectedSymbol) return;
  const { start, end } = dateRangeFor(selectedRange);
  try {
    const result = await API.getEtfData(selectedSymbol, start, end);
    const data = result.data;
    if (data.length === 0) { showToast('该时间范围无数据', true); return; }

    renderCharts(data);

    // Info cards
    const last = data[data.length - 1];
    const sig = signalText(last.buy_signal, last.sell_signal);
    document.getElementById('info-signal').textContent = sig.text;
    document.getElementById('info-signal').className = 'value ' + sig.cls;

    const heatScore = (last.heat_score || 0).toFixed(2);
    const hb = heatBadge(last.heat_score || 0);
    document.getElementById('info-heat').textContent = heatScore + 'x ' + hb.text;
    document.getElementById('info-heat').className = 'value ' + hb.cls;

    if (result.holding) {
      const h = result.holding;
      document.getElementById('info-holding').textContent = h.inst_ratio.toFixed(1) + '%';
      document.getElementById('info-holding-period').textContent = '截至 ' + h.period;
    } else {
      document.getElementById('info-holding').textContent = '暂无数据';
      document.getElementById('info-holding-period').textContent = '';
    }
  } catch (e) {
    showToast('加载图表失败: ' + e.message, true);
  }
}

// ── 时间区间按钮 ──────────────────────────────────────────────────────
document.querySelectorAll('.range-btn').forEach(btn => {
  btn.addEventListener('click', () => {
    document.querySelectorAll('.range-btn').forEach(b => b.classList.remove('active'));
    btn.classList.add('active');
    selectedRange = btn.dataset.range;
    loadChartData();
  });
});

// ── ETF 管理 Tab ──────────────────────────────────────────────────────
async function loadManage() {
  const etfs = await API.getEtfList().catch(() => []);
  const tbody = document.getElementById('manage-tbody');
  tbody.innerHTML = etfs.map(e => `
    <tr>
      <td>${esc(e.symbol)}</td>
      <td>${esc(e.name)}</td>
      <td class="${e.active ? 'badge-active' : 'badge-inactive'}">${e.active ? '启用' : '停用'}</td>
      <td>${esc(e.last_date || '—')}</td>
      <td>
        <button class="btn btn-sm btn-danger" data-symbol="${esc(e.symbol)}">删除</button>
      </td>
    </tr>`).join('');
  tbody.querySelectorAll('button[data-symbol]').forEach(btn => {
    btn.addEventListener('click', () => removeEtf(btn.dataset.symbol));
  });
}

document.getElementById('btn-add').addEventListener('click', async () => {
  const symbol = document.getElementById('add-symbol').value.trim();
  const name   = document.getElementById('add-name').value.trim();
  if (!symbol || !name) { showToast('请填写代码和名称', true); return; }
  try {
    document.getElementById('btn-add').textContent = '拉取中…';
    document.getElementById('btn-add').disabled = true;
    await API.addEtf(symbol, name);
    showToast('添加成功：' + name);
    document.getElementById('add-symbol').value = '';
    document.getElementById('add-name').value = '';
    loadManage();
    loadHeatList();
  } catch (e) {
    showToast('添加失败: ' + e.message, true);
  } finally {
    document.getElementById('btn-add').textContent = '+ 添加';
    document.getElementById('btn-add').disabled = false;
  }
});

async function removeEtf(symbol) {
  if (!confirm(`确认删除 ${symbol}？`)) return;
  try {
    await API.deleteEtf(symbol);
    showToast('已删除 ' + symbol);
    loadManage();
    loadHeatList();
  } catch (e) {
    showToast('删除失败: ' + e.message, true);
  }
}

// ── 热度排行 Tab ──────────────────────────────────────────────────────
async function loadRank() {
  const rank = await API.getHeatRank().catch(() => []);
  const tbody = document.getElementById('rank-tbody');
  tbody.innerHTML = rank.map((r, i) => {
    const b = heatBadge(r.heat_score || 0);
    const sig = signalText(r.buy_signal, r.sell_signal);
    const chgCls = (r.change_pct || 0) >= 0 ? 'signal-buy' : 'signal-sell';
    return `<tr>
      <td>${i + 1}</td>
      <td>${esc(r.name)}</td>
      <td class="${b.cls}">${(r.heat_score || 0).toFixed(2)}x ${b.text}</td>
      <td class="${sig.cls}">${sig.text}</td>
      <td>${r.close ? r.close.toFixed(3) : '—'}</td>
      <td class="${chgCls}">${r.change_pct != null ? r.change_pct.toFixed(2) + '%' : '—'}</td>
    </tr>`;
  }).join('');
}

// ── 数据更新 Tab ──────────────────────────────────────────────────────
async function loadUpdateStatus() {
  const s = await API.getUpdateStatus().catch(() => ({}));
  document.getElementById('update-status-text').textContent =
    s.time ? `上次更新: ${s.time}，耗时 ${s.elapsed}s，成功 ${s.updated}，失败 ${s.failed}` : '尚未更新';
}

document.getElementById('btn-update').addEventListener('click', async () => {
  const log = document.getElementById('update-log');
  const btn = document.getElementById('btn-update');
  log.innerHTML = '<span style="color:var(--text2)">更新中，请稍候…</span>';
  btn.disabled = true;
  try {
    const result = await API.triggerUpdate();
    log.innerHTML = (result.results || []).map(r =>
      `<div class="${r.status === 'ok' ? 'log-ok' : 'log-fail'}">` +
      `[${r.status === 'ok' ? '✓' : '✗'}] ${r.symbol}` +
      (r.reason ? ` — ${r.reason}` : '') + '</div>'
    ).join('');
    loadUpdateStatus();
    loadHeatList();
    showToast(`更新完成: ${result.updated} 成功, ${result.failed} 失败`);
  } catch (e) {
    log.innerHTML = `<span class="log-fail">更新失败: ${e.message}</span>`;
    showToast('更新失败: ' + e.message, true);
  } finally {
    btn.disabled = false;
  }
});

// ── 初始化 ────────────────────────────────────────────────────────────
(async function init() {
  const status = await API.getUpdateStatus().catch(() => ({}));
  document.getElementById('nav-status').textContent =
    status.time ? '上次更新: ' + status.time : '';
  await loadHeatList();
})();
