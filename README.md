# BookStoreConsumer

Essa API tem como objetivo consumir as funcionalidades do sistema **BookStore**, gerenciando a autenticação e comunicação segura entre ambas as aplicações. Ela utiliza o sistema de autenticação **OAUTH** para obter um token e gerar um **JWT** (JSON Web Token), que é utilizado para trafegar dados de forma segura entre as duas APIs.

Além disso, a aplicação expõe endpoints para operações específicas relacionadas ao consumo dos dados e serviços disponibilizados pela aplicação **BookStore**.

Por padrão, a autenticação OAuth e a geração de JWTs estão ativadas. No entanto, essas funcionalidades podem ser desativadas configurando as variáveis de ambiente no arquivo `docker-compose.yml`. Para mais detalhes, consulte a documentação do **BookStore-API** no seguinte [link](https://github.com/sauloddiniz/bookstore).

---

## Guia de Uso

### Pré-requisitos para o Ambiente

Antes de começar, certifique-se de que o ambiente possui todas as ferramentas abaixo instaladas e configuradas corretamente:

- **Java 21**: Certifique-se de ter o JDK (Java Development Kit) 21. [Guia de instalação](https://openjdk.org/install/).
- **Maven**: Necessário para gerenciamento de dependências e configuração do projeto. [Guia de instalação do Maven](https://maven.apache.org/install.html).
- **Docker e Docker Compose**: Utilizados para executar a aplicação em conjunto com suas dependências, como o banco de dados PostgreSQL. [Guia de instalação do Docker](https://docs.docker.com/get-docker/).

---

### Executando via Docker Compose

1. Compile a aplicação para gerar o arquivo JAR. Execute o seguinte comando no diretório raiz do projeto:
   ```bash
   mvn clean package
   ```

2. Após a criação do JAR, inicie os containers utilizando o Docker Compose:
   ```bash
   docker-compose up
   ```
---


### Executando manualmente (dependência do Docker local)

1. Certifique-se de que o **PostgreSQL** e a API **BookStore** estejam configurado localmente ou inicie com o Docker:
   ```bash
   docker-compose -f docker-compose.local.yaml up
   ```

2. Para rodar a aplicação localmente com o **Maven**:
   ```bash
   mvn spring-boot:run
      ```
4. Ao realizar requisições, não esqueça de incluir o cabeçalho `Authorization`:

---

### Obtendo o Token de Autenticação

1. Com a aplicação em execução, acesse a seguinte URL para iniciar o processo de autenticação:
    - [Login - Ambiente Local](http://localhost:8081/bookstore-consumer-api/login)

2. Na página de login, selecione o serviço que deseja autenticar. Exemplo: escolha **BookStore** ou outro serviço disponível.

3. Após a autenticação bem-sucedida, você será redirecionado para uma página onde o token JWT será exibido.

4. Copie o token exibido.

5. Inclua o token copiado no cabeçalho das suas requisições utilizando o formato **Bearer Token**. Exemplo:

   ```http
   Authorization: Bearer <seu-token-aqui>
   ```

6. Agora você poderá acessar os endpoints da API com o token JWT, garantindo segurança no tráfego das informações.

---


### Documentação dos Endpoints

Ao iniciar a aplicação, você poderá acessar a documentação dos endpoints através do **Swagger UI**:

- **Ambiente local**:
    - [Swagger UI - Local](http://localhost:8081/bookstore-consumer-api/swagger-ui/index.html)

A documentação traz detalhes de cada endpoint, incluindo os parâmetros esperados e a estrutura de autenticação.

---

### Observações

- Certifique-se de que o \`JAVA_HOME\` esteja configurado corretamente no sistema.
- Tokens fornecidos pela aplicação BookStore devem ser usados nas requisições para validar permissões.
- Consulte a [documentação oficial do OAuth 2.0](https://oauth.net/2/) para entender mais sobre as operações de autenticação.

---

---

