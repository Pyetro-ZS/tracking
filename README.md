# 📊 QR Code Access Tracking (Spring Boot)

Projeto simples de monitoramento de acessos baseado em QR Code e links diretos, com foco em coleta passiva de dados e testes via API.

## 📌 Objetivo

Simular um cenário real onde acessos vindos de QR Codes (como em eventos, cartazes ou campanhas físicas) possam ser registrados automaticamente, sem exigir login ou qualquer interação do usuário.

---

## ⚙️ Tecnologias utilizadas

* Java 17+
* Spring Boot
* Spring Data JPA
* H2 Database
* Insomnia (para testes)

---

## 🚀 Como funciona

1. O usuário acessa um link (ou escaneia um QR Code)
2. O endpoint `/track` recebe a requisição
3. O sistema registra automaticamente os dados
4. O usuário é redirecionado imediatamente

---

## 🔗 Endpoint principal

```bash
GET /track?qrId=abc123
```

---

## 📥 Dados coletados

* `timestamp` → data e hora do acesso
* `qrId` → identificador do QR Code
* `userAgent` → informações do navegador/dispositivo
* `ipHash` → IP anonimizado (SHA-256 + salt)
* `referer` (opcional)

---

## 📊 Endpoints disponíveis

### 🔹 Registrar acesso

```bash
GET /track?qrId=abc123
```

Retorna:

* Status 302 (redirect)

---

### 🔹 Listar todos os acessos

```bash
GET /logs
```

Header obrigatório:

```bash
Authorization: Bearer admin123
```

---

### 🔹 Filtrar por QR Code

```bash
GET /logs?qrId=abc123
```

---

### 🔹 Estatísticas básicas

```bash
GET /stats
```

Retorna:

* total de acessos
* quantidade por qrId
* últimos acessos

---

## 🔐 Segurança básica

* Hash de IP com SHA-256 + salt
* Validação de parâmetros (`qrId`)
* Proteção de endpoints (`/logs` e `/stats`) com token simples

---

## 🧪 Testes com Insomnia

Exemplo de requisição:

### Track

```bash
GET http://localhost:8080/track?qrId=teste123
```

Headers:

```bash
User-Agent: Insomnia-Test
X-Forwarded-For: 123.45.67.89
```

---

### Logs

```bash
GET http://localhost:8080/logs
```

Header:

```bash
Authorization: Bearer admin123
```

---

## 🛠️ Como rodar o projeto

### 1. Clonar repositório

```bash
git clone https://github.com/seu-usuario/tracking.git
cd tracking
```

### 2. Rodar aplicação

```bash
./mvnw spring-boot:run
```

---

### 3. Acessar

* API: http://localhost:8080
* H2 Console: http://localhost:8080/h2-console

---

## 💡 Possíveis aplicações

* Monitoramento de campanhas com QR Code
* Análise de acessos offline → online
* Base para sistemas simples de analytics

---

## 📈 Próximos passos

* Dashboard de visualização
* Integração com banco real (PostgreSQL)
* Deploy em ambiente externo
* Rate limiting

---

## ⚠️ Observações

Este projeto é um protótipo com foco educacional e de portfólio.
Não deve ser utilizado em produção sem melhorias adicionais de segurança.

---

## 🤝 Contribuição

Sugestões e melhorias são bem-vindas.
