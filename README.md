# Api-Santander

# 🧪 API Mock - CEP (Spring Boot + WireMock)

🔧 Esta é uma **API mock** construída com **Spring Boot** e **WireMock** embutido. Ela simula a consulta de CEPs, sendo ideal para testes e desenvolvimento local sem depender de APIs reais.

---

## 🚀 Objetivo

✅ Simular respostas reais para a rota de consulta de CEP  
✅ Permitir testes automatizados  
✅ Facilitar o desenvolvimento desacoplado  

---

## 🛠️ Tecnologias utilizadas

- ☕ **Java 17+**
- 🌱 **Spring Boot**
- 🧱 **WireMock**
- 🐘 **Maven** e **Docker**

- 📡 Endpoint
🔎 Consulta de CEP
📥 Requisição
GET /api/v1/consultaCep/{cep}

📤 Exemplo

GET http://localhost:8080/api/v1/consultaCep/14810089

📦 Resposta mockada

{
  "cep": "12345678",
  "logradouro": "Avenida São Sebastião",
  "bairro": "Centro",
  "localidade": "Araraquara",
  "uf": "SP"
}

