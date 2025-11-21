package View;

import Controller.ContaInexistenteException;
import Controller.Contas;
import javax.swing.*;
import java.util.List;

import Controller.DadosInvalidosException;
import Controller.SaldoInsuficienteException;
import Model.Movimentacao;

public class Interface {
    public static void main(String[] args) {
        Contas contas = new Contas();
        boolean continuar = true;

        while (continuar) {
            String[] opcoes = {
                    "Abrir conta", "Depositar", "Sacar", "Transferir", // Adicionado Transferir
                    "Saldo", "Extrato", "Fechar conta", "Sair"
            };

            String escolha = (String) JOptionPane.showInputDialog(
                    null, "Escolha uma operação:", "Menu Banco",
                    JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]
            );

            if (escolha == null || escolha.equals("Sair")) {
                continuar = false;
            } else {
                try { // Try-Catch adicionado para evitar erro se digitar letra no lugar de número
                    switch (escolha) {
                        case "Abrir conta" -> {
                            String nome = JOptionPane.showInputDialog("Nome:");
                            int cpf = Integer.parseInt(JOptionPane.showInputDialog("CPF:"));
                            contas.criarConta(nome, cpf);
                            JOptionPane.showMessageDialog(null, "Conta Criada! Verifique o ID no Console da IDE.");
                        }
                        case "Depositar" -> {
                            int id = Integer.parseInt(JOptionPane.showInputDialog("ID da conta:"));
                            double valor = Double.parseDouble(JOptionPane.showInputDialog("Valor:"));
                            String desc = JOptionPane.showInputDialog("Descrição:");
                            contas.realizarMovimentacao(id, 0, valor, desc);
                            JOptionPane.showMessageDialog(null, "Operação processada.");
                        }
                        case "Sacar" -> {
                            int id = Integer.parseInt(JOptionPane.showInputDialog("ID da conta:"));
                            double valor = Double.parseDouble(JOptionPane.showInputDialog("Valor:"));
                            String desc = JOptionPane.showInputDialog("Descrição:");
                            contas.realizarMovimentacao(id, 1, valor, desc);
                            JOptionPane.showMessageDialog(null, "Operação processada.");
                        }
                        case "Transferir" -> {
                            int idOrig = Integer.parseInt(JOptionPane.showInputDialog("ID Conta Origem:"));
                            int idDest = Integer.parseInt(JOptionPane.showInputDialog("ID Conta Destino:"));
                            double valor = Double.parseDouble(JOptionPane.showInputDialog("Valor:"));
                            contas.realizarTransferencia(idOrig, idDest, valor, "Transferência via App");
                            JOptionPane.showMessageDialog(null, "Transferência processada.");
                        }
                        case "Saldo" -> {
                            int id = Integer.parseInt(JOptionPane.showInputDialog("ID da conta:"));
                            double saldo = contas.consultarSaldo(id);
                            if (saldo != -1)
                                JOptionPane.showMessageDialog(null, "Saldo: R$ " + saldo);
                            else
                                JOptionPane.showMessageDialog(null, "Conta não encontrada.");
                        }
                        case "Extrato" -> {
                            int id = Integer.parseInt(JOptionPane.showInputDialog("ID da conta:"));
                            // Agora chama o metodo que criamos no Controller
                            List<Movimentacao> movs = contas.getMovimentacoes(id);

                            if (movs.isEmpty() && contas.consultarSaldo(id) == -1) {
                                JOptionPane.showMessageDialog(null, "Conta ID " + id + " não encontrada.");
                            } else {
                                StringBuilder extrato = new StringBuilder("Extrato da Conta ID: " + id + "\n");
                                extrato.append("----------------------------------------------------\n");
                                if (movs.isEmpty()) {
                                    extrato.append("Nenhuma movimentação.");
                                } else {
                                    for (Movimentacao m : movs) {
                                        String tipo = (m.getTipo() == 0) ? "ENTRADA" : "SAÍDA ";
                                        extrato.append(tipo)
                                                .append(" | R$ ").append(String.format("%.2f", m.getValor()))
                                                .append(" | ").append(m.getDescricao()).append("\n");
                                    }
                                }
                                JOptionPane.showMessageDialog(null, extrato.toString());
                            }
                        }
                        case "Fechar conta" -> {
                            int id = Integer.parseInt(JOptionPane.showInputDialog("ID da conta:"));
                            contas.fecharConta(id);
                            JOptionPane.showMessageDialog(null, "Solicitação enviada (Só fecha se saldo for 0).");
                        }
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Erro: Por favor, digite apenas números nos campos de valor/ID.");
                } catch (SaldoInsuficienteException | DadosInvalidosException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
                }
            }
        }
    }
}