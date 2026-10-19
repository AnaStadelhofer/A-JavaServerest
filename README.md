# A-JavaServeRest

![Java](https://img.shields.io/badge/Java-26-ED8B00?logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-build-C71A36?logo=apachemaven&logoColor=white)
![RestAssured](https://img.shields.io/badge/RestAssured-5.5.6-green)
![JUnit5](https://img.shields.io/badge/JUnit-5-25A162?logo=junit5&logoColor=white)
![Allure](https://img.shields.io/badge/Allure-Report-orange)
![CI](https://github.com/AnaStadelhofer/A-JavaServerest/actions/workflows/regression.yml/badge.svg)

Projeto de automação de testes de API desenvolvido em **Java**, utilizando **RestAssured**, **JUnit 5**, **Maven**, **DataFaker** e **Allure Report**, com execução contínua via **GitHub Actions** e publicação automática do relatório no **GitHub Pages**.

A aplicação alvo é a API pública [ServeRest](https://serverest.dev/), criada para estudos e prática de automação de testes.

---

## Sumário

- [Objetivo](#objetivo)
- [Tecnologias](#tecnologias)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Pré-requisitos](#pré-requisitos)
- [Como executar localmente](#como-executar-localmente)
- [Relatório Allure](#relatório-allure)
- [CI/CD com GitHub Actions](#cicd-com-github-actions)
- [Configuração do GitHub Pages](#configuração-do-github-pages)
- [Cenários cobertos](#cenários-cobertos)
- [Boas práticas adotadas](#boas-práticas-adotadas)
- [Próximos passos](#próximos-passos)
- [Autora](#autora)

---

## Objetivo

Praticar e demonstrar a construção de uma suíte de testes automatizados de API seguindo boas práticas de organização, reuso de código e geração de evidências, cobrindo os principais recursos do ServeRest: **usuários**, **produtos** e **carrinhos**.

## Tecnologias

| Tecnologia | Versão | Finalidade |
| --- | --- | --- |
| Java | 26 | Linguagem principal |
| Maven | - | Gerenciamento de dependências e build |
| RestAssured | 5.5.6 | Requisições e validações de API REST |
| JUnit 5 | 5.13.4 | Framework de testes |
| JUnit Platform Suite | 1.13.4 | Agrupamento de testes em suítes (ex.: `RegressionSuite`) |
| DataFaker | 2.5.3 | Geração de dados dinâmicos |
| Jackson Databind | 2.20.0 | Serialização/desserialização JSON |
| Allure JUnit 5 | 2.29.1 | Coleta de resultados e relatórios |
| Allure Maven Plugin | 2.12.0 | Geração do relatório HTML |
| Maven Surefire Plugin | 3.5.4 | Execução dos testes |
| GitHub Actions | - | Pipeline de CI/CD |
| GitHub Pages | - | Hospedagem do relatório |
| IntelliJ IDEA | - | IDE de desenvolvimento |

## Estrutura do projeto

```
.
├── .github
│   └── workflows
│       └── regression.yml        # Pipeline de CI/CD
├── src
│   ├── main
│   │   └── java
│   │       ├── client
│   │       │   └── ApiClient.java
│   │       ├── config
│   │       │   └── ApiConfig.java
│   │       ├── model
│   │       │   ├── Usuario.java
│   │       │   ├── Produto.java
│   │       │   └── Carrinho.java
│   │       └── utils
│   │           └── FakerUtils.java
│   └── test
│       └── java
│           ├── base
│           │   └── BaseTest.java
│           ├── suites
│           │   └── RegressionSuite.java
│           └── tests
│               ├── usuarios
│               │   └── UsuarioTest.java
│               ├── produto
│               │   └── ProdutoTest.java
│               └── carrinho
│                   └── CarrinhoTest.java
├── .gitignore
├── pom.xml
└── README.md
```

### Responsabilidade de cada pasta

| Pasta / Arquivo | Descrição |
| --- | --- |
| `.github/workflows` | Define o pipeline de CI/CD que executa a regressão e publica o relatório. |
| `client` | Centraliza as chamadas HTTP, evitando repetição de código nos testes. |
| `config` | Configurações gerais da API, como a URL base. |
| `model` | Classes que representam os dados enviados e recebidos nas requisições (`Usuario`, `Produto`, `Carrinho`). |
| `utils` | Métodos auxiliares, como a geração de dados aleatórios com o DataFaker. |
| `base` | Recursos compartilhados entre os testes (configuração inicial, specs e setup comum). |
| `suites` | Suítes JUnit que agrupam os testes, como a `RegressionSuite`. |
| `tests` | Cenários de teste organizados por endpoint/recurso. |
| `pom.xml` | Dependências, plugins e propriedades do projeto. |

## Pré-requisitos

- **JDK 26**
- **Maven** (instalado localmente ou o embutido na IDE)
- **Git**
- **IntelliJ IDEA** ou outra IDE compatível com Java (opcional)

Verifique sua instalação:

```bash
java -version
mvn -version
```

## Como executar localmente

### 1. Clone o repositório

```bash
git clone https://github.com/AnaStadelhofer/A-JavaServerest.git
cd A-JavaServerest
```

### 2. Execute todos os testes

```bash
mvn clean test
```

### 3. Execute apenas a suíte de regressão

```bash
mvn -B -Dtest=RegressionSuite test
```

### 4. Execute uma classe de teste específica

```bash
mvn -Dtest=UsuarioTest test
```

## Relatório Allure

Após a execução dos testes, os resultados brutos são gravados em `target/allure-results/`.

Para gerar o relatório HTML:

```bash
mvn allure:report
```

O relatório fica disponível em `target/site/allure-maven-plugin/`.

Para abrir localmente com servidor embutido:

```bash
mvn allure:serve
```

O relatório publicado pelo pipeline pode ser acessado em:

**https://anastadelhofer.github.io/A-JavaServerest/**

## CI/CD com GitHub Actions

O pipeline está definido em `.github/workflows/` e é composto por dois jobs encadeados.

### Gatilhos

| Gatilho | Descrição |
| --- | --- |
| `push` na branch `master` | Executa a regressão a cada novo commit. |
| `schedule` (`0 11,15 * * *`) | Execução agendada diariamente às 11h e 15h UTC (8h e 12h no horário de Brasília). |
| `workflow_dispatch` | Permite disparar manualmente pela aba **Actions**. |

### Permissões

O workflow utiliza permissões mínimas necessárias:

- `contents: read`: leitura do código.
- `pages: write`: publicação no GitHub Pages.
- `id-token: write`: autenticação do deploy no Pages.

### Fluxo

```
┌───────────────────────┐        ┌──────────────────────────────┐
│  Job: regression      │        │  Job: report-allure          │
│                       │        │                              │
│  1. Checkout          │        │  1. Checkout                 │
│  2. Setup Java 26     │  ───►  │  2. Setup Java 26            │
│  3. mvn test          │        │  3. Download allure-results  │
│     (RegressionSuite) │        │  4. mvn allure:report        │
│  4. Upload            │        │  5. Upload Pages artifact    │
│     allure-results    │        │  6. Deploy GitHub Pages      │
└───────────────────────┘        └──────────────────────────────┘
```

## Configuração do GitHub Pages

Para o deploy do relatório funcionar, configure uma única vez:

1. Acesse **Settings → Pages**.
2. Em **Build and deployment → Source**, selecione **GitHub Actions**.
3. Em **Settings → Environments → github-pages**, confirme que a branch `master` está autorizada a fazer deploy.

## Cenários cobertos

| Recurso | Classe de teste | Exemplos de validações |
| --- | --- | --- |
| Usuários | `UsuarioTest` | Cadastro, listagem, busca por ID, edição e exclusão |
| Produtos | `ProdutoTest` | Cadastro, listagem, busca por ID, edição e exclusão (com autenticação) |
| Carrinhos | `CarrinhoTest` | Criação, consulta, conclusão e cancelamento de compra |

## Boas práticas adotadas

- **Separação de responsabilidades**: cliente HTTP, configuração, modelos e testes em camadas distintas.
- **Dados dinâmicos** com DataFaker, evitando conflitos entre execuções (por exemplo, e-mails duplicados).
- **Testes organizados por endpoint**, facilitando a manutenção.
- **Suíte de regressão** dedicada, separada da execução individual dos testes.
- **Relatórios Allure** como evidência das execuções.
- **Pipeline automatizado**, com execução em cada push e em horários agendados.
- **Pipeline resiliente**: o relatório é gerado e publicado mesmo quando há falhas.

## Próximos passos

- [ ] Manter o histórico de execuções do Allure entre os deploys
- [ ] Adicionar `@Step`, `@Epic`, `@Feature` e `@Story` do Allure para enriquecer o relatório
- [ ] Incluir testes de contrato (validação de JSON Schema)
- [ ] Executar os testes em paralelo
- [ ] Adicionar notificação (Slack/e-mail) em caso de falha no agendamento

## Autora

Desenvolvido por **Ana Stadelhofer**.

- GitHub: [@AnaStadelhofer](https://github.com/AnaStadelhofer)