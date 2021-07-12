# craftbeer

API para gerenciamento de cervejas.

## Desenvolvimento

### Dependências

- Docker
- Docker Compose
- JDK 11+
- Maven

### Executar aplicação (via Spring Boot Maven Plugin)

Execute:

```sh
mvn spring-boot:run -Pdev
```

A ativação explícita do profile `dev` é necessária, para configurar alguns aspectos da aplicação que são importantes em tempo de desenvolvimento (ex. iniciar o banco de dados).

É também através deste profile que o arquivo `.env` é criado na pasta-raíz do projeto. Este arquivo é importante para executar comandos com o Docker Compose (ex. iniciar instância banco de dados).

O _Hot reloading_ já está configurado no profile `dev` (por meio do Spring DevTools), mas seu funcionamento correto pode depender de configuração adicional na sua IDE ou Editor (especialmente no IntelliJ; vide [este issue](https://youtrack.jetbrains.com/issue/IDEA-159298#focus=streamItem-27-2719444.0-0)). Por conta disto, se for necessário depurar o código, deve-se desabilitar o _fork_ explicitamente:

```sh
mvn -Dspring-boot.run.fork=false spring-boot:run -Pdev
```

### Iniciar banco de dados local

Para desenvolvimento local, é utilizada uma instância do PostgreSQL via Docker. Por padrão, ao executar o `mvn spring-boot:run -Pdev`, o banco de dados é instanciado, via Docker Compose. Opcionalmente, se o `.env` na pasta-raíz do projeto já estiver adequadamente instanciado/configurado, pode-se instanciar o banco de dados manualmente, através do seguinte comando:

```sh
docker-compose up -d --remove-orphans db
```

### Parar banco de dados local

O banco de dados local não é automaticamente parado ao término da execução da aplicação. Para isso, é necessário explicitamente parar o BD, através do comando:

```sh
docker-compose down db
```

Para "zerar" o BD, pode-se simplesmente eliminar os volumes do container:

```sh
docker-compose down -v db
```


### Executar aplicação (via Docker Compose)

Esta forma de execução é mais próxima à um ambiente final da aplicação (ex. produção, homologação), já que a aplicação será executada via Docker. Ainda assim, esta é uma execução inteiramente local, e com o intuito de ser usada apenas em tempo de desenvolvimento.

Execute:

```sh
docker-compose up -d --remove-orphans app
```

Remova o `-d` (detached) se pertinente. Se não removido o flag, pode-se ver os logs dos containers continuamente via:

```sh
docker-compose logs -f
```

Pare a aplicação com:

```sh
docker-compose down app
```

### Fazer o build do JAR

Execute:

```sh
mvn package
```

### Fazer o build da imagem Docker

Há duas formas:

```sh
# Antes de executar este comando, tenha certeza de executar a aplicação ao menos uma vez, com o profile Maven `dev`,
# assim como demonstrado anteriormente

docker-compose build app

# Ou, opcionalmente, pode-se executar apenas `docker`. Esta forma é mais própria para execução em um ambiente de CI
# por exemplo, já que dispensa a criação de um arquivo `.env`, ou quaisquer execuções prévias de alguma fase/profile do Maven.
# O comando `mvn package` (usado no Dockerfile) já executa os testes unitários:

docker build -t craft-beer:latest .
```
