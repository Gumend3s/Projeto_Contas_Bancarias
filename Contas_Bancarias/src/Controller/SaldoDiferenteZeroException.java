package Controller;

public class SaldoDiferenteZeroException extends RuntimeException {
    public SaldoDiferenteZeroException() {
        super("Erro: O saldo da conta selecionada não é zero");
    }
}
