# Projeto CRUD Livraria com JDBC

Este projeto é um CRUD de gerenciamento de uma livraria digital desenvolvido em **Java 21**, utilizando **JDBC** para conexão com banco de dados **PostgreSQL**. O sistema permite o **CRUD de livros**, **criação de pedidos** e **geração de relatórios** ,
feito como primeiro Projeto da disciplina de [**Banco de dados**](https://sites.google.com/site/marceloiury/cursos) do professor [**Marcelo Iury**](https://sites.google.com/site/marceloiury).


## 🚀 Como Executar o Projeto

### **Pré-requisitos**
- **Java 21** instalado
- **PostgreSQL** instalado e rodando
- **Maven** instalado (para gerenciamento de dependências)

### **Configuração do Banco de Dados**
1. Crie um banco de dados chamado **`livraria_banco_jdbc`** no PostgreSQL.
2. Altere o arquivo **`.env`** na raiz do projeto:

```properties
DB_URL=jdbc:postgresql://localhost:5432/livraria_banco_jdbc
DB_USER=seu_user
DB_PASSWORD=sua_senha
```


## 📦 Estrutura de Pacotes

| **Pacote**             | **Descrição**                                      |
|------------------------|----------------------------------------------------|
| `infra`               | Conexão com o banco de dados                   |
| `domain.models`       | Entidades do sistema (`Livro`, `Pedido`, `ItemPedido`) |
| `domain.repository`   | Operações de banco de dados (**CRUD**)             |
| `LivrariaApp`         | Classe principal (**Main**)                       |

## 🎯 Funcionalidades

### 📖 Gestão de Livros
- ✅ Cadastro de livros (*título, autor, preço, estoque*)
- ✅ Atualização de informações de livros
- ❌ Remoção de livros
- 📊 Listagem de livros com **relatório de estoque**

### 🛒 Gestão de Pedidos
- 🆕 Criação de pedidos
- ➕ Adição de itens ao pedido (**com validação de estoque**)
- 📋 Listagem de pedidos com **relatório de vendas**

### 🔍 Validações Implementadas
- ✅ **Campos obrigatórios** para cadastros
- 🔡 **Tamanho mínimo/máximo** para textos
- 💲 **Preço positivo**
- 📉 **Estoque não negativo**
- 📦 **Verificação de disponibilidade** ao vender


## 📐 Diagrama de Classes UML

![Image](https://github.com/user-attachments/assets/97c5af93-fea2-47b7-904b-28045dfc265a)

## ▶️ Como Executar

### 📌 Passo a Passo:

1. **Navegue até a pasta do projeto:**
  ```bash
     cd caminho/para/ProjetoBancoJDBC
  ```
  
3. **Compile o projeto:**
  ```bash
    mvn clean compile
  ```
3. **Execute a aplicação**:
  ```bash
    mvn exec:java
  ```

## 🖥️ Saída Esperada

Após executar, você verá:

![Image](https://github.com/user-attachments/assets/9683157f-6bfc-43a4-9d12-c8ec9d6d1e42)
