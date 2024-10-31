# Desafio Grupo Primo - Sistema de Controle Bancário

Este projeto é um sistema de controle bancário que permite a criação de contas e a realização de transações como depósitos, saques e transferências.

## Tecnologias Utilizadas

- Kotlin
- Spring Boot
- JPA (Hibernate)
- H2 Database (em memória)
- Coroutines
- Maven

## Como Executar o Projeto

1. **Clone o repositório**:

   ```bash
   git clone https://github.com/ArtRagazzi/teste-backend-grupo-primo.git
   cd desafio_grupo_primo

2. **Compile o Projeto**:

   ```bash
   mvn clean install

3. **Execute a Aplicaçao**:

   ```bash
   mvn spring-boot:run

## Rotas disponiveis

1. **Criar Conta Bancária**
    ```bash
    Método: POST

    Endpoint: /contas

    Parâmetros: saldo (Double) - Saldo inicial da conta.

    Descrição: Cria uma nova conta bancária com o saldo inicial fornecido.

    Exemplo: curl -X POST http://localhost:8080/contas?saldo=1000.00

2. **Depositar**
    ```bash
    Método: POST

    Endpoint: /contas/{numeroConta}/deposito

    Parâmetros: 
        numeroConta (Long) - Número da conta bancária.
        valor (Double) - Valor a ser depositado.

    Descrição: Realiza um depósito na conta especificada.

    Exemplo: curl -X POST http://localhost:8080/contas/1/deposito?valor=500

3. **Sacar**
    ```bash
    Método: POST

    Endpoint: /contas/{numeroConta}/saque

    Parâmetros: 
        numeroConta (Long) - Número da conta bancária.
        valor (Double) - Valor a ser sacado.

    Descrição: Realiza um saque da conta especificada, se houver saldo suficiente.

    Exemplo: curl -X POST http://localhost:8080/contas/1/saque?valor=200

4. **Transferir**
    ```bash
    Método: POST

    Endpoint: /contas/transferencia

    Parâmetros: 
        numContaOrigem (Long) - Número da conta de origem.
        numContaDestino (Long) - Número da conta de destino.
        valor (Double) - Valor a ser transferido.

    Descrição: Realiza uma transferência entre duas contas, se a conta de origem tiver saldo suficiente.

    Exemplo: curl -X POST http://localhost:8080/contas/transferencia?numContaOrigem=1&numContaDestino=2&valor=300

## Testes com Maven
1. Abra o terminal na raiz do projeto.
2. Execute o seguinte comando para rodar todos os testes:

   ```bash
   mvn test

## Testes com IntelliJ IDEA
1. Abra o Projeto: Certifique-se de que o projeto foi importado corretamente na IntelliJ IDEA.
2. Navegue até a Classe de Teste:
3. No painel lateral, vá até *src/test/kotlin* e encontre a classe TransacaoServiceTest.
4. Se quiser rodar todos os testes da classe, clique com o botão direito sobre a classe e selecione a opção *"Run 'TransacaoServiceTest'"*.
