import React, { useEffect, useState } from 'react';
import { Text, View, FlatList, Button, TextInput, StyleSheet } from 'react-native';

const API_BASE = 'http://10.0.2.2:8080'; // Android emulator -> localhost of host machine

export default function App() {
  const [pagamentos, setPagamentos] = useState([]);
  const [nome, setNome] = useState('Fulano');
  const [valor, setValor] = useState('10.00');
  const [numero, setNumero] = useState('4242424242424242');
  const [validade, setValidade] = useState('12/25');
  const [codigo, setCodigo] = useState('123');

  useEffect(() => {
    fetchList();
  }, []);

  async function fetchList() {
    try {
      const res = await fetch(`${API_BASE}/pagamentos`);
      const data = await res.json();
      setPagamentos(data);
    } catch (e) {
      console.warn('Erro ao buscar pagamentos', e.message);
    }
  }

  async function handleCreate() {
    const payload = {
      nome,
      valor: parseFloat(valor),
      numeroDoCartao: numero,
      validade,
      codigoDeSeguranca: codigo,
      formaDePagamentoId: 1
    };

    try {
      const res = await fetch(`${API_BASE}/pagamentos`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        // If your backend requires basic auth for write, change below:
        // headers: { 'Content-Type': 'application/json', 'Authorization': 'Basic ' + btoa('manager:!L3tm3iN!') }
        body: JSON.stringify(payload)
      });
      if (res.status === 201) {
        setNome('Fulano');
        setValor('10.00');
        setNumero('4242424242424242');
        setValidade('12/25');
        setCodigo('123');
        fetchList();
      } else {
        const text = await res.text();
        console.warn('Erro criação', res.status, text);
      }
    } catch (e) {
      console.warn('Erro criar pagamento', e.message);
    }
  }

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Pagamentos</Text>
      <FlatList
        data={pagamentos}
        keyExtractor={(item) => String(item.id)}
        renderItem={({ item }) => (
          <View style={styles.item}>
            <Text style={styles.itemText}>{item.nome} - R$ {item.valor}</Text>
          </View>
        )}
      />

      <Text style={styles.subtitle}>Criar Pagamento</Text>
      <TextInput style={styles.input} value={nome} onChangeText={setNome} placeholder="Nome" />
      <TextInput style={styles.input} value={valor} onChangeText={setValor} placeholder="Valor" keyboardType="numeric" />
      <TextInput style={styles.input} value={numero} onChangeText={setNumero} placeholder="Número do cartão" />
      <TextInput style={styles.input} value={validade} onChangeText={setValidade} placeholder="Validade" />
      <TextInput style={styles.input} value={codigo} onChangeText={setCodigo} placeholder="Código" />
      <Button title="Criar" onPress={handleCreate} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, padding: 20, paddingTop: 50 },
  title: { fontSize: 24, fontWeight: 'bold', marginBottom: 10 },
  subtitle: { fontSize: 18, marginTop: 20 },
  input: { borderWidth: 1, borderColor: '#ccc', padding: 8, marginVertical: 6, borderRadius: 4 },
  item: { padding: 10, borderBottomWidth: 1, borderBottomColor: '#eee' },
  itemText: { fontSize: 16 }
});
