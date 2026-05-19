const API = {
  async _fetch(url, opts = {}) {
    const r = await fetch(url, opts);
    if (!r.ok) {
      const err = await r.json().catch(() => ({ detail: r.statusText }));
      throw new Error(err.detail || r.statusText);
    }
    return r.json();
  },

  getEtfList()                { return this._fetch('/api/etf'); },
  addEtf(symbol, name)        { return this._fetch('/api/etf', {
    method: 'POST', headers: {'Content-Type':'application/json'},
    body: JSON.stringify({ symbol, name }) }); },
  updateEtf(symbol, name)     { return this._fetch(`/api/etf/${symbol}`, {
    method: 'PUT', headers: {'Content-Type':'application/json'},
    body: JSON.stringify({ name }) }); },
  deleteEtf(symbol)           { return this._fetch(`/api/etf/${symbol}`, { method: 'DELETE' }); },

  getEtfData(symbol, start, end) {
    const p = new URLSearchParams();
    if (start) p.set('start', start);
    if (end)   p.set('end', end);
    return this._fetch(`/api/etf/${symbol}/data?${p}`);
  },

  getHeatRank()               { return this._fetch('/api/heat/rank'); },
  triggerUpdate()             { return this._fetch('/api/update', { method: 'POST' }); },
  getUpdateStatus()           { return this._fetch('/api/update/status'); },
};
