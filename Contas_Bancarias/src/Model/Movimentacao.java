package Model;

import java.util.Date;

public class Movimentacao {
    private Date data;
    private double valor;
    private String descricao;
    private int tipo; // 0=Depósito, 1=Saque/Débito

    public Movimentacao(Date data, double valor, String descricao, int tipo) {
        this.data = data;
        this.valor = valor;
        this.descricao = descricao;
        this.tipo = tipo;
    }

    public Date getData() { return data; }
    public double getValor() { return valor; }
    public String getDescricao() { return descricao; }
    public int getTipo() { return tipo; }
}