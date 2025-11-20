import java.util.Date;

public class Movimentacao {
    private Date data;
    private double valor;
    private String descricao;
    private int tipo;

    public Movimentacao(Date data, double valor, String descricao, int tipo) {
        this.data = data;
        this.valor = valor;
        this.descricao = descricao;
        this.tipo = tipo;
    }
}
