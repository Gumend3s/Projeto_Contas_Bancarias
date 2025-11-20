import java.util.LinkedList;
import java.util.List;

public class Conta {
    private String nome;
    private int cpf;
    private double saldo;
    private List<Movimentacao> movimentacoes = new LinkedList<Movimentacao>();

    public Conta(String nome, int cpf) {
        this.nome = nome;
        this.cpf = cpf;
        this.saldo = 0;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public List<Movimentacao> getMovimentacoes() {
        return movimentacoes;
    }

    public void addMovimentacao(Movimentacao m) {
        movimentacoes.add(m);
    }
}
