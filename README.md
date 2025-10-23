# 🧾 Gerenciador de Produtos

O Gerenciador de Produtos é um sistema fullstack desenvolvido para gerenciar produtos de forma simples e eficiente, oferecendo uma interface web intuitiva e uma API REST robusta.
A persistência dos dados é feita em PostgreSQL, assegurando consistência e confiabilidade.

## 📄 Como Rodar o Projeto

Para rodar o projeto localmente, siga os passos abaixo:

```sh
# Clone este repositório
git clone https://github.com/CauaMotta/gerenciador_de_produtos

# Acesse a pasta do projeto
cd gerenciador_de_produtos

# Pode ser executado via Docker com o seguinte comando
docker-compose up --build
# O frontend fica acessivel através de: http://localhost:3000
# E o backend através de: http://localhost:8080

# Ou manualmente com os passos a seguir:
# 1º passo: executar o servidor backend
# Acesse a pasta
cd Gerenciador_De_Produtos_BackEnd

# Execute o comando
.\mvnw spring-boot:run

# Necessário configurar o banco de dados Postgres e as váriaveis de ambiente

# 2º passo: executar o servidor frontend
# Acesse a pasta
cd ../Gerenciador_De_Produtos_FrontEnd

# Execute o comando
npm run dev
# Ou faça o build do projeto e depois execute o servidor
npm run build
npm run preview

# Necessário configurar o arquivo .env com API_URL
```

# 🧩 Decisões Técnicas

## 🖥️ Backend

### Model Produto

- _Enum para Categorias:_
  Optei por utilizar Enum para representar as categorias, pois são valores constantes e definidos. Essa abordagem garante padronização, legibilidade e facilita a manutenção do código.

- _Preço como Integer:_
  Para lidar com valores monetários, escolhi armazenar o preço como Integer, representando o valor em centavos. Essa decisão evita imprecisões de ponto flutuante (com float ou double) e possíveis vulnerabilidades em cálculos financeiros.

- _Atributos createdAt, updatedAt e deletedAt:_
  Esses campos servem para controle histórico dos registros. O deletedAt, em especial, permite a implementação de soft delete, ou seja, o registro é marcado como excluído sem ser realmente removido do banco — uma boa prática para auditoria e integridade dos dados.

### DTOs (Data Transfer Objects)

Implementei DTOs para trafegar dados entre o cliente e a API. Essa abordagem aumenta a segurança e desacopla a entidade Produto do banco de dados, evitando exposição direta dos modelos internos da aplicação.

### Tratamento de Exceções

Criei exceptions personalizadas para controlar erros de forma centralizada e segura.
Essas exceções são interceptadas por um handler global, que transforma os erros internos em respostas padronizadas e legíveis através do DTO ErrorResponse.
Isso evita o envio de logs sensíveis e garante mensagens mais claras e controladas para o cliente.

### Testes

Implementei testes unitários simples para validar o fluxo da aplicação e garantir o funcionamento das regras principais de negócio.

### Tecnologias Utilizadas

- _Java_ - Linguagem principal
- _Spring Boot_ - Framework para construção rápida de APIs REST
- _Spring Web_ - Suporte à criação de endpoints REST
- _Spring Data JPA_ - Abstração para persistência com JPA e Hibernate
- _Lombok_ - Simplifica e agiliza a produção de classes
- _Junit 5 e Mockito_ - Testes unitários e mocks

## 💻 Frontend

Criei uma interface simples utilizando Vite + React + TypeScript, conjunto que escolhi por experiência prévia e produtividade.
A interface foi estilizada com Styled Components para atender aos requisitos visuais e manter componentização clara.

### Tecnologias Utilizadas

- _Formik + Yup:_
  Validação robusta e simples dos formulários, garantindo que os dados enviados estejam corretos antes de chegar à API.

- _React IMask:_
  Restringe o campo de preço para aceitar apenas números, prevenindo erros de digitação.

- _React Select:_
  Garante que apenas categorias válidas sejam enviadas, eliminando erros de entrada.

- _Font Awesome + React Spinners:_
  Tornam a interface mais intuitiva e agradável.

- _ESLint:_
  Utilizado para padronizar o código, evitar más práticas e possíveis erros.

## 🧱 Arquitetura

- _Padrão MVC:_
  Separação clara de responsabilidades (Controller, Service, Repository).

- _API RESTful:_
  Comunicação entre front e back de forma padronizada e escalável.

- _Variáveis de ambiente:_
  Aplicadas tanto no frontend quanto no backend para proteger informações sensíveis e facilitar a configuração entre ambientes.

## ⚙️ Infraestrutura

- _Docker:_
  Utilizado para garantir que o ambiente seja reproduzível em qualquer sistema, facilitando a distribuição e execução do projeto.

## 🚀 Melhorias Futuras

### Backend

- **Testes e Qualidade**
  - Implementar testes de integração e ampliar a cobertura dos testes unitários.
  - Adicionar validações adicionais (ex: campos em branco, tamanhos máximos e mínimos, formatação de dados).
- **Segurança e Autenticação**
  - Adicionar autenticação com JWT para proteger rotas privadas.
  - Implementar controle de acesso baseado em papéis, permitindo permissões específicas por tipo de usuário.
  - Criptografar senhas e dados sensíveis.
- **Estrutura e Escalabilidade**
  - Criar camadas de cache (Redis) para melhorar o desempenho em consultas frequentes.
  - Expandir o domínio com entidades de Vendas e Clientes, relacionando-as com Produtos.
  - Integrar CI/CD com GitHub Actions, automatizando build, testes e deploy.

### Frontend

- **Testes e Qualidade**
  - Documentar os componentes e fluxos principais.
  - Adicionar testes unitários e de integração.
  - Implementar tratamento de erros de rede e de respostas da API, exibindo mensagens claras e amigáveis.
  - Tornar a interface responsiva para diferentes tamanhos de tela.
- **Funcionalidades**
  - Adicionar paginação, busca e filtros dinâmicos nas listas de produtos, clientes e vendas.
  - Implementar tela de login e autenticação de usuários integrada ao backend.
  - Criar interfaces para gerenciamento de Vendas e Clientes.
- **Estrutura e Escalabilidade**
  - Integrar CI/CD para o frontend com build automatizado e deploy contínuo.
