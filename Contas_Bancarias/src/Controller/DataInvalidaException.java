package Controller;

public class DataInvalidaException extends RuntimeException {
    public DataInvalidaException() {
        super("Erro: A data digitada não está no padrão correto");
    }
}
