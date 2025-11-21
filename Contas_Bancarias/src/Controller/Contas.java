package Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import Model.Conta;
import Model.Movimentacao;

public class Contas {
    private static List<Conta> contas = new ArrayList<>();

    private Conta buscarContaPorId(int idConta) throws ContaInexistenteException {
        Optional<Conta> resultado = contas.stream()
                .filter(c -> c.getIdConta() == idConta)
                .findFirst();
        if (resultado.orElse(null) == null) {
            throw new ContaInexistenteException();
        }
        return resultado.orElse(null);
    }

    public void criarConta(String nome, int cpf) {
        Conta novaConta = new Conta(nome, cpf);
        contas.add(novaConta);
        // System.out.println("Conta criada. ID: " + novaConta.getIdConta());
        // Retorna o ID no console para facilitar o teste
    }

    public List<Movimentacao> getMovimentacoes(int idConta) {
        Conta cc = buscarContaPorId(idConta);
        if (cc != null) {
            return cc.getMovimentacoes();
        }
        return new ArrayList<>(); // Retorna lista vazia se não achar conta
    }

    public void realizarMovimentacao(int idConta, int tipo, double valor, String descricao) throws SaldoInsuficienteException {
        Conta cc = buscarContaPorId(idConta);
        if (cc == null) return;

        if (tipo == 0) { // Depósito
            cc.setSaldo(cc.getSaldo() + valor);

        } else if (tipo == 1) { // Saque
            if (cc.getSaldo() >= valor) {
                cc.setSaldo(cc.getSaldo() - valor);
            } else {
                throw new SaldoInsuficienteException();
            }
        }

        cc.addMovimentacao(new Movimentacao(new Date(), valor, descricao, tipo));
    }

    public void realizarTransferencia(int idOrigem, int idDestino, double valor, String descricao) throws DadosInvalidosException {
        Conta origem = buscarContaPorId(idOrigem);
        Conta destino = buscarContaPorId(idDestino);

        if (origem != null && destino != null) {
            origem.setSaldo(origem.getSaldo() - valor);
            origem.addMovimentacao(new Movimentacao(new Date(), valor, "Transf. SAÍDA p/ " + idDestino, 1));

            destino.setSaldo(destino.getSaldo() + valor);
            destino.addMovimentacao(new Movimentacao(new Date(), valor, "Transf. ENTRADA de " + idOrigem, 0));
        } else {
            throw new DadosInvalidosException();
        }
    }

    public double consultarSaldo(int idConta) {
        Conta cc = buscarContaPorId(idConta);
        return (cc != null) ? cc.getSaldo() : -1;
    }

    public void fecharConta(int idConta) {
        Conta cc = buscarContaPorId(idConta);
        if (cc != null && cc.getSaldo() == 0) {
            contas.remove(cc);
        }
    }
}