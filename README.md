
# Microsserviços de E-commerce - Warehouse e Storefront

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-brightgreen)
![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)

Este projeto foi desenvolvido como parte do curso **"Criando um Microsserviço de Controle de E-commerce"**,  
ministrado por **José Luiz Abreu Cardoso Junior** na **Digital Innovation One (DIO)**.  

A solução implementa uma arquitetura de **microsserviços** para um sistema de e-commerce, composta por dois serviços principais que se comunicam via **APIs REST** e **RabbitMQ**.

---

## 📋 Descrição do Projeto

O sistema é formado por dois microsserviços independentes:

- **Warehouse** → Gerencia o catálogo de produtos, estoque e preços  
- **Storefront** → Responsável pela vitrine de produtos e processamento de compras  

---

## 🏗️ Arquitetura

### Visão Geral

```mermaid
flowchart LR
    Cliente --> Storefront[Storefront (8081)]
    Storefront --> Warehouse[Warehouse (8080)]
    Storefront --> RabbitMQ[(RabbitMQ 5672)]
    Warehouse --> RabbitMQ
```

### Tecnologias Utilizadas

- **Java 21** com Spring Boot 3.5.4  
- **Spring Data JPA** (persistência)  
- **H2 Database** (banco em memória para dev)  
- **RabbitMQ** (mensageria assíncrona)  
- **MapStruct** (mapeamento DTO ↔ entidade)  
- **Docker & Docker Compose** (containerização)  
- **OpenAPI/Swagger** (documentação de APIs)  

---

## 🚀 Como Executar o Projeto

### Pré-requisitos

- Docker e Docker Compose instalados  
- Java 21 (para execução local)  
- Gradle (para execução local)  

### Executando com Docker Compose

1. Clone o repositório:
   ```bash
   git clone https://github.com/rodrigobsjava/desafio-dio-microsservico-de-controle-de-ecommerce.git
   cd desafio-dio-microsservico-de-controle-de-ecommerce
   ```

2. Crie a rede compartilhada:
   ```bash
   docker network create ecommerce-net
   ```

3. Suba os microsserviços (em terminais separados):
   ```bash
   docker-compose -f docker-compose-storefront.yml up --build
   docker-compose -f docker-compose-warehouse.yml up --build
   ```

### Executando Localmente (Desenvolvimento)

1. Execute o RabbitMQ:
   ```bash
   docker-compose -f docker-compose-storefront.yml up rabbitmq
   ```

2. Execute os microsserviços:
   ```bash
   ./gradlew bootRun --project-dir=warehouse
   ./gradlew bootRun --project-dir=storefront
   ```

---

## 📡 Endpoints da API

### Warehouse Service (Porta 8080)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/warehouse/products` | Criar um novo produto |
| GET | `/warehouse/products/{id}` | Buscar detalhes de um produto |
| POST | `/warehouse/products/{id}/purchase` | Realizar compra de um produto |
| POST | `/warehouse/stocks` | Adicionar stock a um produto |
| PUT | `/warehouse/stocks/{id}/release` | Liberar stock para venda |
| DELETE | `/warehouse/stocks/{id}/inactive` | Inativar stock |

### Storefront Service (Porta 8081)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/storefront/products` | Criar produto na vitrine |
| GET | `/storefront/products` | Listar produtos disponíveis |
| GET | `/storefront/products/{id}` | Buscar detalhes de um produto |
| POST | `/storefront/products/{id}/purchase` | Realizar compra de um produto |

---

## 🔄 Fluxo de Comunicação

1. **Cadastro de Produto**  
   - Produto criado no Warehouse  
   - Warehouse envia mensagem para Storefront via RabbitMQ  
   - Storefront atualiza status do produto  

2. **Processamento de Compra**  
   - Cliente solicita compra no Storefront  
   - Storefront chama Warehouse para processar a compra  
   - Warehouse atualiza estoque e notifica Storefront  

3. **Gestão de Estoque**  
   - Alterações no Warehouse geram notificações via RabbitMQ  
   - Storefront atualiza a disponibilidade  

### Exemplo de Mensagem (RabbitMQ)
```json
{
  "productId": "123",
  "status": "AVAILABLE",
  "stock": 10
}
```

---

## 🗃️ Estrutura do Banco de Dados

### Warehouse
- **ProductEntity**: ID, nome  
- **StockEntity**: quantidade, preços, status  

### Storefront
- **ProductEntity**: cache de produtos e disponibilidade  

---

## 🧪 Testando a Aplicação

### Interfaces Disponíveis

- **Swagger UI**  
  - Warehouse → [http://localhost:8080/warehouse/swagger-ui.html](http://localhost:8080/warehouse/swagger-ui.html)  
  - Storefront → [http://localhost:8081/storefront/swagger-ui.html](http://localhost:8081/storefront/swagger-ui.html)  

- **H2 Console**  
  - Warehouse → [http://localhost:8080/warehouse/h2-console](http://localhost:8080/warehouse/h2-console)  
  - Storefront → [http://localhost:8081/storefront/h2-console](http://localhost:8081/storefront/h2-console)  

- **RabbitMQ Management**  
  - [http://localhost:15672](http://localhost:15672) (usuário: `admin`, senha: `admin`)  

### Exemplo de Fluxo com `curl`

1. Criar um produto no Warehouse:
   ```bash
   curl -X POST "http://localhost:8080/warehouse/products"    -H "Content-Type: application/json"    -d '{"name": "Produto Exemplo"}'
   ```

2. Adicionar stock ao produto:
   ```bash
   curl -X POST "http://localhost:8080/warehouse/stocks"    -H "Content-Type: application/json"    -d '{
     "amount": 10,
     "boughtPrice": 50.00,
     "soldPrice": 100.00,
     "productId": "<ID_DO_PRODUTO>"
   }'
   ```

3. Listar produtos disponíveis no Storefront:
   ```bash
   curl -X GET "http://localhost:8081/storefront/products"
   ```

---

## 🛠️ Possíveis Melhorias

- [ ] Implementar autenticação e autorização  
- [ ] Adicionar monitoramento com Spring Boot Actuator  
- [ ] Implementar circuit breaker (Resilience4j)  
- [ ] Adicionar logging distribuído  
- [ ] Criar testes de integração e carga  
- [ ] Implementar cache com Redis  
- [ ] Adicionar suporte a múltiplos idiomas  
- [ ] Implementar feature flags  

---

## 📝 Decisões de Projeto

1. **Comunicação Síncrona vs Assíncrona**  
   - REST para operações imediatas  
   - RabbitMQ para notificações e atualizações de estado  

2. **Banco de Dados por Serviço**  
   - Cada microsserviço com seu próprio banco  
   - Evita acoplamento e garante evolução independente  

3. **Mapeamento Entidade ↔ DTO**  
   - Uso do MapStruct para reduzir boilerplate  
   - Camada de API desacoplada da persistência  

---

## 📚 Aprendizados

- Comunicação síncrona e assíncrona entre microsserviços  
- Configuração de Docker Compose com múltiplos serviços  
- Integração com RabbitMQ  
- Uso de MapStruct para simplificar mapeamentos  
- Boas práticas de separação de responsabilidade  

---

## 👨‍💻 Desenvolvido por

Rodrigo Barbosa – [GitHub](https://github.com/rodrigobsjava)  

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.  

---

**Nota**: Projeto criado como parte do curso *Criando um Microsserviço de Controle de E-commerce* na DIO, ministrado por **José Luiz Abreu Cardoso Junior**.  
