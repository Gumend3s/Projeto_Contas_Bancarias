**RELATÓRIO TÉCNICO: SISTEMA BANCÁRIO**

**1. ESTRUTURAÇÃO DO SISTEMA**

O sistema foi desenvolvido em Java seguindo uma arquitetura modular para separar a lógica de negócios da interface com o usuário:

  - **Pacote Model (Modelo):** Contém as classes que representam os dados.

      - Classe 'Conta': Responsável por armazenar o estado de cada conta (ID, nome, CPF, saldo e lista de movimentações).
      - Classe 'Movimentacao': Representa o registro imutável de uma transação (data, valor, descrição e tipo).

  - **Pacote Controller (Controlador):** Contém a classe 'Contas'.

      - Esta classe gerencia a lista de contas em memória e contém toda a regra de negócio (validações de saldo, geração de IDs sequenciais e execução de transferências). Ela impede que a interface manipule os dados diretamente.

  - **Pacote View (Visualização):** Contém a classe 'Interface'.

      - Responsável exclusivamente pela interação com o usuário (menu de opções e exibição de mensagens), delegando as operações para o Controller.

-----

**2. PADRÕES DE PROJETO APLICADOS**

Para garantir a organização e a extensibilidade do código, foram utilizados três padrões de projeto principais:

**A. MVC (Model-View-Controller)**
Utilizado para estruturar a arquitetura do software. A lógica de negócio (Controller) está totalmente desacoplada da entrada/saída de dados (View). Isso permite, por exemplo, que a interface gráfica seja substituída por uma interface web ou mobile no futuro sem necessidade de alterar as regras de negócio.

**B. Factory Method (Método Fábrica)**
Aplicado no método 'criarConta' dentro do Controller. Em vez de permitir que a classe Interface instancie objetos 'new Conta()' diretamente, centralizamos a criação no Controller. Isso garante que toda nova conta receba automaticamente um ID único e tenha suas listas internas inicializadas corretamente antes de ser usada.

**C. Singleton (Gerenciador de Estado Único)**
Embora implementado de forma simplificada através de atributos estáticos ('static List\<Conta\>'), o Controller atua como um Singleton. Isso assegura que exista apenas uma única lista de contas ativa na memória durante a execução do programa, garantindo que todas as operações acessem o mesmo conjunto de dados, independentemente de onde sejam chamadas.

-----

**3. ROTEIRO DE TESTES**

O sistema possui uma interface de linha de comando que permite testar todas as funcionalidades. Sugere-se o seguinte fluxo de teste:

1.  **Criação:** Execute a opção "Abrir conta", informe Nome e CPF. O sistema deve confirmar e exibir o ID gerado (ex: ID 1).
2.  **Depósito:** Selecione "Depositar", informe o ID 1 e um valor (ex: 1000.00). Verifique se o saldo atualizou.
3.  **Validação de Saque:** Tente realizar um "Sacar" com valor superior ao saldo (ex: 2000.00). O sistema deve exibir erro de saldo insuficiente. Em seguida, faça um saque válido.
4.  **Transferência:** Abra uma segunda conta (ID 2). Use a opção "Transferir" para enviar valores da Conta 1 para a Conta 2 e valide os saldos de ambas.
5.  **Extrato:** Utilize a opção "Extrato" no ID 1 para visualizar o histórico cronológico de depósitos, saques e transferências.
6.  **Fechamento:** Tente usar "Fechar conta" em uma conta com saldo positivo (o sistema deve bloquear). Zere o saldo via saque e tente fechar novamente (o sistema deve permitir).
