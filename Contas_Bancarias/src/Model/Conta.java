package Model;

import java.util.ArrayList; // Mudei para ArrayList para padronizar, mas LinkedList funciona
import java.util.List;

public class Conta {
    private static int contadorId = 0; // Adicionei contador automático para facilitar
    private int idConta;
    private String nome;
    private int cpf;
    private double saldo;
    private List<Movimentacao> movimentacoes = new ArrayList<>();

    public Conta(String nome, int cpf) {
        this.idConta = ++contadorId; // ID gera automático: 1, 2, 3...
        this.nome = nome;
        this.cpf = cpf;
        this.saldo = 0;
    }

    public int getIdConta() { return idConta; } // Necessário para buscar por ID
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    public List<Movimentacao> getMovimentacoes() { return movimentacoes; }
    public void addMovimentacao(Movimentacao m) { movimentacoes.add(m); }
    public String getNome() { return nome; }
}