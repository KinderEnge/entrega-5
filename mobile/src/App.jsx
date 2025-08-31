import React, { useEffect, useState } from 'react'

const API_BASE = 'http://localhost:8080'

function methodLabel(id){
  return id === 1 ? 'Crédito' : id === 2 ? 'Débito' : 'Outro'
}

function LoginScreen({ onLogin, error }){
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  return (
    <div className="page center">
      <div className="card login-card">
        <h2>Entrar</h2>
        {error && <div className="alert error">{error}</div>}
        <div className="form-row">
          <input placeholder="Usuário" value={username} onChange={e=>setUsername(e.target.value)} />
        </div>
        <div className="form-row">
          <input placeholder="Senha" type="password" value={password} onChange={e=>setPassword(e.target.value)} />
        </div>
        <div className="form-row">
          <button onClick={()=>onLogin(username, password)}>Entrar</button>
        </div>
      </div>
    </div>
  )
}

function PaymentsScreen(props){
  const { pagamentos, message, onLogout, onEdit, onDelete, onSave, form, setForm, loading, editingId } = props
  return (
    <div className="page">
      <header className="header">
        <h1>Pagamentos</h1>
        <div>
          <button className="btn-ghost" onClick={onLogout}>Logout</button>
        </div>
      </header>

      {message && <div className={`alert ${message.type === 'error' ? 'error' : 'success'}`}>{message.text}</div>}

      <div className="content">
        <section className="panel">
          <h3>Lista de pagamentos</h3>
          <ul className="list">
            {pagamentos.map(p => (
              <li className="item" key={p.id}>
                <div>
                  <strong>{p.nome}</strong>
                  <div className="meta">R$ {p.valor} • {methodLabel(p.formaDePagamentoId)} • ****{p.numeroDoCartao ? p.numeroDoCartao.slice(-4) : ''}{p.transactionDate ? ' • ' + formatDateDisplay(p.transactionDate) : ''}</div>
                  {p.descricao && <div className="description">{p.descricao}</div>}
                </div>
                <div className="actions">
                  <button onClick={()=>onEdit(p)}>Editar</button>
                  <button className="danger" onClick={()=>onDelete(p.id)}>Excluir</button>
                </div>
              </li>
            ))}
          </ul>
        </section>

        <section className="panel">
          <h3>{editingId ? 'Editar pagamento' : 'Criar pagamento'}</h3>
            <div className="form">
            <input placeholder="Nome" value={form.nome} onChange={e=>setForm({...form, nome: e.target.value})} maxLength={100} />
            <input placeholder="Valor (ex: 99.90)" value={form.valor} onChange={e=>setForm({...form, valor: e.target.value})} />
            <input placeholder="Número do cartão (apenas números)" value={form.numeroDoCartao} onChange={e=>setForm({...form, numeroDoCartao: e.target.value.replace(/\D/g,'')})} maxLength={19} />
            <input placeholder="Validade (MM/YY)" value={form.validade} onChange={e=>setForm({...form, validade: e.target.value})} maxLength={5} />
            <input placeholder="CVV (3-4 dígitos)" value={form.codigoDeSeguranca} onChange={e=>setForm({...form, codigoDeSeguranca: e.target.value.replace(/\D/g,'')})} maxLength={4} />
            <input placeholder="Descrição da compra" value={form.descricao} onChange={e=>setForm({...form, descricao: e.target.value})} maxLength={255} />
            <label style={{ fontSize: 12, color: '#666' }}>Data da transação</label>
            <input type="date" value={form.transactionDate || ''} onChange={e=>setForm({...form, transactionDate: e.target.value})} />
            <div style={{ display: 'flex', gap: 8 }}>
              <select value={form.formaDePagamentoId} onChange={e=>setForm({...form, formaDePagamentoId: parseInt(e.target.value)})}>
                <option value={1}>Crédito</option>
                <option value={2}>Débito</option>
              </select>
              <button onClick={onSave} disabled={loading}>{loading ? 'Enviando...' : (editingId ? 'Salvar' : 'Criar')}</button>
            </div>
            {editingId && <button className="link" onClick={()=>{ props.onCancelEdit() }}>Cancelar</button>}
          </div>
        </section>
      </div>
    </div>
  )
}

function formatDateDisplay(dateStr){
  try{
    const d = new Date(dateStr)
    if(isNaN(d)) return dateStr
    const day = String(d.getDate()).padStart(2,'0')
    const month = String(d.getMonth()+1).padStart(2,'0')
  const year = d.getFullYear()
  return `${day}/${month}/${year}`
  }catch(e){ return dateStr }
}

export default function App(){
  const [pagamentos, setPagamentos] = useState([])
  const [form, setForm] = useState({ nome: '', valor: '', numeroDoCartao: '', validade: '', codigoDeSeguranca: '', formaDePagamentoId: 1, transactionDate: '', descricao: '' })
  const [loading, setLoading] = useState(false)
  const [message, setMessage] = useState(null)
  const [authHeader, setAuthHeader] = useState(null)
  const [editingId, setEditingId] = useState(null)

  useEffect(()=>{ fetchList() }, [])

  async function fetchList(){
    try{
      const res = await fetch(`${API_BASE}/pagamentos`)
      const data = await res.json()
      setPagamentos(data)
    }catch(e){ console.error('Erro fetch', e); setMessage({ type:'error', text: 'Erro ao buscar pagamentos' }) }
  }

  function headers(){
    const h = { 'Content-Type': 'application/json' }
    if(authHeader) h['Authorization'] = authHeader
    return h
  }

  function doLogin(username, password){
    if(!username || !password){ setMessage({ type:'error', text: 'Informe usuário e senha' }); return }
    const token = btoa(`${username}:${password}`)
    const header = 'Basic ' + token
    // validate with backend
    fetch(`${API_BASE}/auth/validate`, { method: 'GET', headers: { 'Authorization': header } })
      .then(res => {
        if(res.ok){
          setAuthHeader(header)
          setMessage({ type:'success', text: 'Logado com sucesso' })
        }else{
          setMessage({ type:'error', text: 'Credenciais inválidas' })
        }
      })
      .catch(err => { console.error('Erro validar auth', err); setMessage({ type:'error', text: 'Erro ao validar credenciais' }) })
  }

  function doLogout(){ setAuthHeader(null); setMessage({ type:'success', text: 'Logout realizado' }) }

  function startEdit(p){
    setEditingId(p.id)
    setForm({
      nome: p.nome || '',
      valor: p.valor != null ? String(p.valor) : '',
      // accept either old or new server property names if present
      numeroDoCartao: p.numeroDoCartao || p.numeroDoCarto || '',
      validade: p.validade || '',
      codigoDeSeguranca: p.codigoDeSeguranca || p.codigoDeSeguranaca || '',
      formaDePagamentoId: p.formaDePagamentoId || 1,
      transactionDate: p.transactionDate || '',
      descricao: p.descricao || ''
    })
    window.scrollTo({ top: 0, behavior: 'smooth' })
  }

  function cancelEdit(){ setEditingId(null); setForm({ nome: '', valor: '', numeroDoCartao: '', validade: '', codigoDeSeguranca: '', formaDePagamentoId: 1, transactionDate: '', descricao: '' }) }

  function validate(){
    if(!form.nome || form.nome.length < 2) return 'Nome deve ter ao menos 2 caracteres'
    if(!form.valor || isNaN(parseFloat(form.valor)) || parseFloat(form.valor) <= 0) return 'Valor inválido'
  if(!form.numeroDoCartao || form.numeroDoCartao.replace(/\s+/g,'').length < 12) return 'Número de cartão inválido (mín 12 dígitos)'
  if(!form.validade) return 'Validade obrigatória (MM/AA)'
  if(!form.codigoDeSeguranca || form.codigoDeSeguranca.length < 3) return 'Código de segurança inválido'
    return null
  }

  async function save(){
    const v = validate()
    if(v){ setMessage({ type:'error', text: v}); return }
    setLoading(true)
    setMessage(null)
    try{
      const url = editingId ? `${API_BASE}/pagamentos/${editingId}` : `${API_BASE}/pagamentos`
      const method = editingId ? 'PUT' : 'POST'
  const payload = { ...form, valor: parseFloat(form.valor) }
  // ensure transactionDate is either null or ISO date string
  if(!payload.transactionDate) delete payload.transactionDate
  const res = await fetch(url, { method, headers: headers(), body: JSON.stringify(payload) })
      if(res.status === 200 || res.status === 201){
        setMessage({ type:'success', text: editingId ? 'Pagamento atualizado' : 'Pagamento criado' })
        await fetchList()
        cancelEdit()
      }else if(res.status === 401){
        setMessage({ type:'error', text: 'Operação não autorizada. Faça login (Admin) para criar/editar/excluir.' })
      }else{
        const t = await res.text()
        setMessage({ type:'error', text: `Erro: ${res.status} ${t}` })
      }
    }catch(e){ console.error('Erro save', e); setMessage({ type:'error', text: 'Erro ao salvar pagamento' }) }
    setLoading(false)
  }

  async function remove(id){
    if(!confirm('Confirmar exclusão?')) return
    setLoading(true)
    try{
      const res = await fetch(`${API_BASE}/pagamentos/${id}`, { method: 'DELETE', headers: headers() })
      if(res.status === 204){
        setMessage({ type:'success', text: 'Pagamento removido' })
        await fetchList()
      }else if(res.status === 401){
        setMessage({ type:'error', text: 'Não autorizado. Faça login (Admin).' })
      }else{
        const t = await res.text()
        setMessage({ type:'error', text: `Erro ao excluir: ${res.status} ${t}` })
      }
    }catch(e){ console.error('Erro delete', e); setMessage({ type:'error', text: 'Erro ao excluir pagamento' }) }
    setLoading(false)
  }

  return (
    <div>
      {!authHeader ? (
        <LoginScreen onLogin={doLogin} error={message && message.type === 'error' ? message.text : null} />
      ) : (
        <PaymentsScreen
          pagamentos={pagamentos}
          message={message}
          onLogout={doLogout}
          onEdit={startEdit}
          onDelete={remove}
          onSave={save}
          form={form}
          setForm={setForm}
          loading={loading}
          editingId={editingId}
          onCancelEdit={cancelEdit}
        />
      )}
    </div>
  )
}
