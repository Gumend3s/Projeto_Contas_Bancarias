# RELATÓRIO TÉCNICO: SISTEMA BANCÁRIO SIMULADO EM JAVA

## 1. ESTRUTURAÇÃO DO SISTEMA

O sistema foi desenvolvido em Java, utilizando uma arquitetura modular para separar a lógica de negócios da interface com o usuário. A organização do sistema é feita através de pacotes:

- **Pacote Model (Modelo):** Contém as classes responsáveis pelos dados.

    - **Classe 'Conta':** Responsável por armazenar o estado de cada conta (ID, nome, CPF, saldo e lista de movimentações).
    - **Classe 'Movimentacao':** Representa o registro imutável de uma transação (data, valor, descrição e tipo).

- **Pacote Controller (Controlador):** Contém a classe **'Contas'**.

    - A classe **'Contas'** gerencia a lista de contas em memória e contém toda a lógica de negócios (validações de saldo, geração de IDs sequenciais e execução de transações). Ela impede que a interface manipule os dados diretamente, mantendo a separação de responsabilidades.

- **Pacote View (Visualização):** Contém a classe **'Interface'**.

    - A classe **'Interface'** é responsável exclusivamente pela interação com o usuário (menu de opções e exibição de mensagens via `JOptionPane`). Ela delega as operações para o Controller, garantindo que a lógica de negócios seja isolada.

-----

## 2. PADRÕES DE PROJETO APLICADOS

Para garantir a organização, manutenibilidade e a extensibilidade do código, foram aplicados os seguintes padrões de projeto:

### **A. MVC (Model-View-Controller)**

Utilizado para estruturar a arquitetura do software. A lógica de negócios (Controller) está desacoplada da entrada/saída de dados (View). Isso permite que a interface gráfica seja facilmente substituída por outras interfaces (web ou mobile), sem a necessidade de modificar as regras de negócio.

### **B. Factory Method (Método Fábrica)**

Aplicado no método **'criarConta'** dentro do Controller. A criação de novos objetos **'Conta'** é centralizada no Controller, garantindo que toda nova conta receba automaticamente um ID único e tenha suas listas internas corretamente inicializadas antes de ser usada.

### **C. Singleton (Gerenciador de Estado Único)**

O Controller atua como um Singleton, implementado de forma simplificada com um atributo estático (`private static List<Conta> contas`). Isso garante que apenas uma lista de contas esteja ativa na memória durante toda a execução do programa, assegurando que todas as operações acessem o mesmo conjunto de dados.

-----

## 3. ROTEIRO DE TESTES

O sistema pode ser testado através de sua interface gráfica (via `JOptionPane`) para validar todas as funcionalidades e regras de negócio. O fluxo de testes sugerido é o seguinte:

1. **Criação de Conta:**
    - Execute a opção **"Abrir conta"**, informe o **Nome** e **CPF**.
    - O sistema deve confirmar e exibir o **ID gerado** (ex: ID 1).

2. **Depósito:**
    - Selecione a opção **"Depositar"**, informe o **ID da conta** (ex: ID 1) e um valor (ex: 1000.00).
    - O sistema deve atualizar o saldo da conta e exibir a confirmação.

3. **Validação de Saque (Inválido):**
    - Tente realizar um **"Saque"** com um valor superior ao saldo (ex: 2000.00).
    - O sistema deve exibir um erro de **saldo insuficiente**.

4. **Saque (Válido):**
    - Após o erro de saque, tente realizar um **"Saque"** com um valor válido.
    - O saldo deve ser atualizado corretamente, e a movimentação registrada.

5. **Transferência:**
    - Abra uma **segunda conta** (ex: ID 2).
    - Use a opção **"Transferir"** para enviar valores da Conta 1 para a Conta 2.
    - Valide se os saldos de ambas as contas foram atualizados corretamente e se as movimentações foram registradas.

6. **Extrato:**
    - Utilize a opção **"Extrato"** para visualizar o histórico cronológico das transações realizadas (depósitos, saques e transferências) na Conta 1.

7. **Fechamento de Conta (Inválido):**
    - Tente usar a opção **"Fechar conta"** em uma conta com saldo positivo.
    - O sistema deve **bloquear o fechamento** e exibir um erro informando que o saldo precisa ser zero para o fechamento.

8. **Fechamento de Conta (Válido):**
    - Zere o saldo da conta via **Saque**.
    - Tente usar a opção **"Fechar conta"** novamente.
    - O sistema deve **permitir o fechamento** e remover a conta do sistema.

