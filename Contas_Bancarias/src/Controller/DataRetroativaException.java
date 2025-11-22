package Controller;

public class DataRetroativaException extends RuntimeException {
    public DataRetroativaException() {
        super("Erro: Datas retroativas não são permitidas");
    }
}
