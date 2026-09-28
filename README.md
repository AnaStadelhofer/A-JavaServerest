# A-JavaServeRest

Projeto de automação de testes de API desenvolvido em **Java**, utilizando **RestAssured**, **JUnit 5**, **Maven** e **DataFaker**.

O projeto utiliza a API pública do [ServeRest](https://serverest.dev/) como ambiente de estudos e prática de automação de testes.

## Tecnologias

* Java 26
* Maven
* RestAssured 5.5.6
* JUnit 5
* DataFaker 2.5.3
* Jackson Databind
* IntelliJ IDEA

## Estrutura do projeto

```text
src
├── main
│   └── java
│       ├── client
│       │   └── ApiClient.java
│       ├── config
│       │   └── ApiConfig.java
│       ├── model
│       │   ├── Usuario.java
│       │   ├── Produto.java
│       │   └── Carrinho.java
│       └── utils
│           └── FakerUtils.java
│
└── test
    └── java
        ├── base
        │   └── BaseTest.java
        └── tests
            ├── usuarios
            │   └── UsuarioTest.java
            ├── produto
            │   └── ProdutoTest.java
            └── carrinho
                └── CarrinhoTest.java
```

### Organização

* **client**: centraliza as chamadas HTTP realizadas pela automação.
* **config**: contém configurações gerais da API, como a URL base.
* **model**: contém as classes que representam os dados utilizados nas requisições.
* **utils**: reúne métodos auxiliares, como geração de dados utilizando Faker.
* **base**: contém recursos compartilhados pelos testes.
* **tests**: contém os cenários de teste organizados por endpoint.

## Pré-requisitos

Para executar o projeto, é necessário ter instalado:

* **JDK 26**
* **IntelliJ IDEA** ou outra IDE compatível com Java
* **Git**

O Maven não precisa estar instalado separadamente, pois o projeto utiliza o **Maven Wrapper/configuração Maven da IDE**.

## Como executar

### 1. Clone o projeto

```bash
git clone https://github.com/AnaStadelhofer/A-JavaServerest.git
```

### 2. Abra o proje
