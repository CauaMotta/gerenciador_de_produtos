# 🧾 Gerenciador de Produtos

O Gerenciador de Produtos é um sistema fullstack desenvolvido para gerenciar produtos de forma simples e eficiente, oferecendo uma interface web intuitiva e uma API REST robusta.
A persistência dos dados é feita em PostgreSQL, assegurando consistência e confiabilidade.

## 🛠️ Tecnologias Utilizadas

### Frontend

- [**React**](https://react.dev/) — Biblioteca para construção da UI
- [**Vite**](https://vitejs.dev/) — Ferramenta de build moderna e rápida
- [**TypeScript**](https://www.typescriptlang.org/) — Linguagem que adiciona tipagem estática ao JavaScript
- [**Styled Components**](https://styled-components.com/) — Estilização com CSS-in-JS
- [**Font Awesome**](https://fontawesome.com/) — Ícones personalizáveis para a interface
- [**Formik**](https://formik.org/) — Biblioteca para gerenciamento de formulários no React
- [**Yup**](https://github.com/jquense/yup) — Validação de formulários de forma simples e eficiente
- [**React Select**](https://react-select.com/home) — Componente de seleção flexível e personalizável
- [**React IMask**](https://www.google.com/search?q=https://unform.dev/docs/react-imask) — Componente para aplicação de máscaras em campos de entrada
- [**React Spinners**](https://www.davidhu.io/react-spinners/) — Componentes de carregamento estilizados para React
- [**ESLint**](https://eslint.org/) — Ferramenta para identificar e reportar padrões problemáticos no código

### Backend

- [**Java**](https://www.java.com/) — Linguagem de programação principal
- [**Spring Boot**](https://spring.io/projects/spring-boot) — Framework para construção rápida e produtiva de APIs
- [**Spring Data JPA**](https://spring.io/projects/spring-data-jpa) — Abstração para persistência de dados
- [**Hibernate**](https://hibernate.org/) - Mapeamento objeto-relacional (ORM)
- [**Spring Web**](https://docs.spring.io/spring-framework/reference/web.html) — Criação de endpoints
- [**Lombok**](https://projectlombok.org/) — Aumento da produtividade com redução de código repetitivo (getters, setters, builders, etc.)
- [**JUnit**](https://junit.org/junit5/) — Testes unitários e de integração
- [**Mockito**](https://site.mockito.org/) — Framework para criação de mocks em testes unitários
- [**Maven**](https://maven.apache.org/) — Gerenciador de dependências e build

### Persistência de dados

- [**PostgreSQL**](https://www.postgresql.org/) — Banco de dados relacional

## 📦 Instalação e Execução

Para rodar o projeto localmente, siga os passos abaixo:

```sh
# Clone este repositório
git clone https://github.com/CauaMotta/gerenciador_de_produtos

# Acesse a pasta do projeto
cd gerenciador_de_produtos

# Pode ser executado via Docker com o seguinte comando
docker-compose up --build
# E fica acessivel atraves de: http://localhost:3000

# Ou manualmente com os passos a seguir:
# 1º passo: executar o servidor Backend
# Acesse a pasta
cd Gerenciador_De_Produtos_BackEnd

# Execute o comando
.\mvnw spring-boot:run
# Necessário configurar o banco de dados Postgres e as váriaveis de ambiente

#2º passo: executar o servidor Frontend
# Acesse a pasta
cd ../Gerenciador_De_Produtos_FrontEnd

# Execute o comando
npm run dev
# Ou faça o build do projeto e depois execute o servidor
npm run build
npm run preview

# Necessário configurar o arquivo .env com API_URL
```

## 🚧 Próximos Passos

### Backend

- Iniciar e configurar o projeto ✅
- CRUD completo de produtos (Create, Read, Update, Delete) ✅
- Listagem de produtos com filtros por categoria ✅
- Adicionar ordenação por preço ✅
- Criar testes unitários ✅

### Frontend

- Interface com listagem de produtos ✅
- Filtros por categoria ✅
- Ordenação por preço ✅
- Dashboard com total de produtos e média de preços ✅
- Cadastro de novos produtos ✅
- Atualização e exclusão de produtos ✅
