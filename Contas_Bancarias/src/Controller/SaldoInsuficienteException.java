package Controller;

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException() {
        super("Erro: Saldo Insuficiente");
    }
}
