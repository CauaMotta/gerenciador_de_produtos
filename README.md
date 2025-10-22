# 🧾 Gerenciador de Produtos

O Gerenciador de Produtos é um sistema fullstack desenvolvido para gerenciar produtos de forma simples e eficiente, oferecendo uma interface web intuitiva e uma API REST robusta.
A persistência dos dados é feita em PostgreSQL, assegurando consistência e confiabilidade.

## 🛠️ Tecnologias Utilizadas

### Backend

- [**Java**](https://www.java.com/) — Linguagem de programação principal
- [**Spring Boot**](https://spring.io/projects/spring-boot) — Framework para construção rápida e produtiva de APIs
- [**Spring Data JPA**](https://spring.io/projects/spring-data-jpa) — Abstração para persistência de dados
- [**Hibernate**](https://hibernate.org/) - Mapeamento objeto-relacional (ORM)
- [**Spring Web**](https://docs.spring.io/spring-framework/reference/web.html) — Criação de endpoints
- [**PostgreSQL**](https://www.postgresql.org/) — Banco de dados relacional
- [**Lombok**](https://projectlombok.org/) — Aumento da produtividade com redução de código repetitivo (getters, setters, builders, etc.)
- [**JUnit**](https://junit.org/junit5/) — Testes unitários e de integração
- [**Mockito**](https://site.mockito.org/) — Framework para criação de mocks em testes unitários
- [**Maven**](https://maven.apache.org/) — Gerenciador de dependências e build

## 📦 Instalação e Execução

Para rodar o projeto localmente, siga os passos abaixo:

```sh
# Clone este repositório
git clone https://github.com/CauaMotta/gerenciador_de_produtos

# Acesse a pasta do projeto
cd gerenciador_de_produtos

# Primeiro passo: Subir o servidor Backend
# Acesse a pasta do backend
cd Gerenciador_De_Produtos_BackEnd

# Execute o comando
.\mvnw spring-boot:run

# Necessário configurar o banco de dados Postgres e as váriaveis de ambiente
```

## 🚧 Próximos Passos

- Iniciar e configurar o projeto ✅
- Criar CRUD completo de produtos (Create, Read, Update, Delete) ✅
- Adicionar listagem de produtos com filtros por **categoria** ✅
- Adicionar ordenação por **preço** ✅
- Criar testes unitários ✅
