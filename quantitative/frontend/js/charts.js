let priceChart = null, macdChart = null, volChart = null;

function destroyCharts() {
  [priceChart, macdChart, volChart].forEach(c => c && c.destroy());
  priceChart = macdChart = volChart = null;
}

function renderCharts(data) {
  destroyCharts();

  const labels = data.map(d => d.date);

  // ── 价格 K 线 + MA 线 ──────────────────────────────────────────────
  const ohlc = data.map(d => ({
    x: new Date(d.date).getTime(),
    o: d.open, h: d.high, l: d.low, c: d.close
  }));
  const maLine   = data.map(d => ({ x: new Date(d.date).getTime(), y: d.ma20 }));
  const wMaLine  = data.map(d => ({ x: new Date(d.date).getTime(), y: d.w_ma20 }));
  const mMaLine  = data.map(d => ({ x: new Date(d.date).getTime(), y: d.m_ma12 }));

  const commonTimeAxis = {
    type: 'time', time: { unit: 'month', displayFormats: { month: 'yy/MM' } },
    grid: { color: '#1e1e30' }, ticks: { color: '#666' }
  };

  priceChart = new Chart(document.getElementById('chart-price'), {
    data: {
      datasets: [
        { type: 'candlestick', label: '价格', data: ohlc,
          color: { up: '#ef5350', down: '#26a69a', unchanged: '#999' } },
        { type: 'line', label: 'MA20', data: maLine,
          borderColor: '#ffa726', borderWidth: 1, pointRadius: 0, tension: 0.3 },
        { type: 'line', label: 'W_MA20', data: wMaLine,
          borderColor: '#7c9eff', borderWidth: 1.5, pointRadius: 0, tension: 0.3 },
        { type: 'line', label: 'M_MA12', data: mMaLine,
          borderColor: '#ce93d8', borderWidth: 1.5, pointRadius: 0,
          borderDash: [4, 2], tension: 0.3 },
      ]
    },
    options: {
      animation: false,
      plugins: { legend: { labels: { color: '#888', boxWidth: 12, font: { size: 11 } } } },
      scales: { x: commonTimeAxis, y: { grid: { color: '#1e1e30' }, ticks: { color: '#666' } } }
    }
  });

  // ── MACD ──────────────────────────────────────────────────────────
  const histColors = data.map(d =>
    (d.macd_hist || 0) >= 0 ? 'rgba(239,83,80,0.6)' : 'rgba(38,166,154,0.6)'
  );
  macdChart = new Chart(document.getElementById('chart-macd'), {
    data: {
      datasets: [
        { type: 'bar', label: 'MACD柱', data: data.map(d => ({ x: new Date(d.date).getTime(), y: d.macd_hist })),
          backgroundColor: histColors },
        { type: 'line', label: 'MACD', data: data.map(d => ({ x: new Date(d.date).getTime(), y: d.macd })),
          borderColor: '#7c9eff', borderWidth: 1.5, pointRadius: 0 },
        { type: 'line', label: 'Signal', data: data.map(d => ({ x: new Date(d.date).getTime(), y: d.macd_signal })),
          borderColor: '#ffa726', borderWidth: 1.5, pointRadius: 0 },
      ]
    },
    options: {
      animation: false,
      plugins: { legend: { labels: { color: '#888', boxWidth: 12, font: { size: 11 } } } },
      scales: { x: commonTimeAxis, y: { grid: { color: '#1e1e30' }, ticks: { color: '#666' } } }
    }
  });

  // ── 成交量 ──────────────────────────────────────────────────────────
  const volColors = data.map((d, i) => {
    const prev = data[i - 1];
    return (!prev || d.close >= prev.close) ? 'rgba(239,83,80,0.6)' : 'rgba(38,166,154,0.6)';
  });
  volChart = new Chart(document.getElementById('chart-volume'), {
    data: {
      datasets: [
        { type: 'bar', label: '成交量', data: data.map(d => ({ x: new Date(d.date).getTime(), y: d.volume })),
          backgroundColor: volColors },
        { type: 'line', label: 'Vol MA20', data: data.map(d => ({ x: new Date(d.date).getTime(), y: d.vol_ma20 })),
          borderColor: '#ffa726', borderWidth: 1.5, pointRadius: 0 },
      ]
    },
    options: {
      animation: false,
      plugins: { legend: { labels: { color: '#888', boxWidth: 12, font: { size: 11 } } } },
      scales: { x: commonTimeAxis, y: { grid: { color: '#1e1e30' }, ticks: { color: '#666', callback: v => (v / 1e6).toFixed(0) + 'M' } } }
    }
  });
}
