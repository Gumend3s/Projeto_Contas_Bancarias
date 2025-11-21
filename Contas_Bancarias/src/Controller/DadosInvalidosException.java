package Controller;

public class DadosInvalidosException extends RuntimeException {
    public DadosInvalidosException() {
        super("Erro: Os dados digitados são inválidos");
    }
}
