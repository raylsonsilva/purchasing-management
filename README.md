# Purchasing Management API

## Descrição
O **Purchasing Management API** é uma solução robusta e escalável para gerenciar compras e clientes de maneira eficiente. Este projeto foi projetado com foco em boas práticas de engenharia de software, garantindo alta qualidade de código, extensibilidade e fácil manutenção.

## Tecnologias Utilizadas
- **Java 21**: Linguagem principal para desenvolvimento, aproveitando os recursos modernos da versão mais recente.
- **Spring Boot 3.3.0**: Framework para criação de APIs robustas e escaláveis.
- **Spring Data JPA**: Para persistência de dados com suporte a H2 e bancos relacionais.
- **H2 Database**: Banco de dados em memória para testes e desenvolvimento rápido.
- **MapStruct**: Para mapeamento eficiente entre entidades e DTOs.
- **Swagger/OpenAPI**: Documentação automatizada dos endpoints.
- **Lombok**: Para reduzir boilerplate no código.
- **JUnit 5 e Mockito**: Frameworks para testes unitários e mock.

## Estrutura do Projeto
O projeto segue uma arquitetura em camadas bem definida:

1. **Controller**: Responsável por expor os endpoints REST.
2. **Service**: Contém a lógica de negócios.
3. **Repository**: Acesso e manipulação de dados no banco.
4. **Mapper**: Implementado com MapStruct para conversão entre DTOs e entidades.
5. **Exception Handling**: Centralizado com um `GlobalExceptionHandler` para padronizar as respostas de erro.

## Endpoints Disponíveis

### Produtos
- **POST /api/v1/produtos/importar**
    - Importa dados de produtos de um JSON remoto.

### Clientes
- **POST /api/v1/clientes/importar**
    - Importa dados de clientes de um JSON remoto.
- **GET /api/v1/clientes/fieis**
    - Retorna os clientes mais fiéis.
- **GET /api/v1/clientes/recomendacao/{cpf}/tipo**
    - Retorna uma recomendação de vinho com base no histórico de compras do cliente.

### Compras
- **GET /api/v1/compras?page={page}&size={size}**
    - Lista compras ordenadas pelo valor total, com suporte de paginação.
- **GET /api/v1/compras/maior-compra/{ano}**
    - Retorna a maior compra de um ano específico.

## Principais Funcionalidades
1. **Boas Práticas**
    - Uso de SOLID, Clean Code e Design Patterns (Mapper, Service Layer).
    - Respostas padronizadas para erros com o `GlobalExceptionHandler`.

2. **Testes Unitários**
    - Cobertura ampla com **JUnit 5** e **Mockito** para Controllers e Services.

3. **Padrão de Documentação**
    - Swagger UI disponível em `/swagger-ui.html` para visualização e teste dos endpoints.

4. **Integração de Dados**
    - Consumo de arquivos JSON remotos para popular banco de dados local.

5. **Paginação e Ordenação**
    - Suporte a paginação nos principais endpoints para melhorar a experiência do cliente.

## Configuração do Ambiente

### Requisitos
- **JDK 21**
- **Maven 3.8+**

### Executando Localmente

1. Clone o repositório:
   ```bash
   git clone <URL_DO_REPOSITORIO>
   cd purchasing-management
   ```

2. Compile o projeto:
   ```bash
   mvn clean install
   ```

3. Execute a aplicação:
   ```bash
   mvn spring-boot:run
   ```

4. Acesse o Swagger:
   ```
   http://localhost:8080/swagger-ui.html
   ```

### Banco de Dados H2
- Acesse a interface H2:
  ```
  http://localhost:8080/h2-console
  ```
- **URL JDBC**: `jdbc:h2:mem:testdb`
- **Usuário**: `sa`
- **Senha**: `password`

## Estrutura de Testes
- **Testes para Controllers**: Validação das respostas e integração com serviços.
- **Testes para Services**: Validação da lógica de negócio, incluindo cenários de sucesso e falha.
- Cobertura de tratamento de exceções para garantir robustez.

## Diferenciais
- **Escalabilidade**: Arquitetura projetada para suportar novos módulos e endpoints com facilidade.
- **Qualidade de Código**: Uso extensivo de práticas modernas como MapStruct, Swagger, Exception Handling centralizado e SOLID.
- **Documentação Completa**: Swagger e README detalhado para fácil compreensão do projeto.
- **Pronto para Produção**: Configuração simples e robustez para cenários reais.

## Contato
- **Autor:** Raylson Lima
- **LinkedIn:** [linkedin.com/in/raylsonslima](https://linkedin.com/in/raylsonslima)
