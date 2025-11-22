package Controller;

public class ContaInexistenteException extends RuntimeException {
    public ContaInexistenteException() {
        super("Erro: A conta selecionada não existe");
    }
}
