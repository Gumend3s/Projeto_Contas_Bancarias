import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Contas {
    private static List<Conta> c = new ArrayList<Conta>();

    public void criarConta(String nome, int cpf) {
        c.add(new Conta(nome, cpf));
    }

    public void realizarMovimentacao(int id, int tipo, double valor, String descricao) {
        Conta cc = c.get(id);
        switch (tipo) {
            case 0: // deposito
                cc.setSaldo(cc.getSaldo() + valor);
                break;
            case 1: // saque
                if (cc.getSaldo() > valor) {
                    cc.setSaldo(cc.getSaldo() - valor);
                } else {
                    // Exibe erro de saldo insuficiente
                }
                break;
            case 2: // transferencia

                break;
            default:

                break;
        }
        cc.addMovimentacao(new Movimentacao(new Date(), valor, descricao, tipo));
    }

    public double consultarSaldo(int id) {
        return c.get(id).getSaldo();
    }

    public void emitirExtrato() {

    }

    public void fecharConta(int id) {
        if (c.get(id).getSaldo() == 0) {
            c.remove(id);
        } else {
            // Exibe mensage de saldo maior que zero
        }
    }
}
