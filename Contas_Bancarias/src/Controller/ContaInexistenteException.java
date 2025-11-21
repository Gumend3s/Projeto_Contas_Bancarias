package Controller;

public class ContaInexistenteException extends RuntimeException {
    public ContaInexistenteException() {
        super("A conta selecionada não existe");
    }
}
