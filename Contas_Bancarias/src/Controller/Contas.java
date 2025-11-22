package Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import Model.Conta;
import Model.Movimentacao;

public class Contas {
    private static List<Conta> contas = new ArrayList<>();

    private Conta buscarContaPorId(int idConta) throws ContaInexistenteException {
        Optional<Conta> resultado = contas.stream()
                .filter(c -> c.getIdConta() == idConta)
                .findFirst();
        if (resultado.orElse(null) == null) {
            throw new ContaInexistenteException();
        }
        return resultado.orElse(null);
    }

    public void criarConta(String nome, int cpf) {
        Conta novaConta = new Conta(nome, cpf);
        contas.add(novaConta);
        System.out.println("Conta criada. ID: " + novaConta.getIdConta());
        // Retorna o ID no console para facilitar o teste
    }

    public List<Movimentacao> getMovimentacoes(int idConta) throws ContaInexistenteException  {
        Conta c = buscarContaPorId(idConta);
        if (c != null) {
            return c.getMovimentacoes();
        } else {
            throw new ContaInexistenteException();
        }
    }

    public void realizarMovimentacao(int idConta, int tipo, double valor, String descricao, String data) throws SaldoInsuficienteException, DataRetroativaException, DataInvalidaException {
        Date dataCorrigida = corrigeData(data);

        Conta c = buscarContaPorId(idConta);
        if (c == null) return;

        if (tipo == 0) { // Depósito
            c.setSaldo(c.getSaldo() + valor);

        } else if (tipo == 1) { // Saque
            if (c.getSaldo() >= valor) {
                c.setSaldo(c.getSaldo() - valor);
            } else {
                throw new SaldoInsuficienteException();
            }
        }

        c.addMovimentacao(new Movimentacao(dataCorrigida, valor, descricao, tipo));
    }

    public void realizarTransferencia(int idOrigem, int idDestino, double valor, String descricao, String data) throws DadosInvalidosException, DataRetroativaException, DataInvalidaException {
        Date dataCorrigida = corrigeData(data);

        Conta origem = buscarContaPorId(idOrigem);
        Conta destino = buscarContaPorId(idDestino);

        if (origem != null && destino != null) {
            origem.setSaldo(origem.getSaldo() - valor);
            origem.addMovimentacao(new Movimentacao(dataCorrigida, valor, "Transf. SAÍDA p/ " + idDestino + "\n\n" + descricao, 1));

            destino.setSaldo(destino.getSaldo() + valor);
            destino.addMovimentacao(new Movimentacao(dataCorrigida, valor, "Transf. ENTRADA de " + idOrigem  + "\n\n" + descricao, 0));
        } else {
            throw new DadosInvalidosException();
        }
    }

    public double consultarSaldo(int idConta) throws ContaInexistenteException {
        Conta c = buscarContaPorId(idConta);
        if (c == null) throw new ContaInexistenteException();
        return c.getSaldo() ;
    }

    public void fecharConta(int idConta) throws ContaInexistenteException {
        Conta c = buscarContaPorId(idConta);
        if (c == null) throw new ContaInexistenteException();
        if (c.getSaldo() == 0) {
            contas.remove(c);
        } else {
            throw new SaldoDiferenteZeroException();
        }
    }

    private Date corrigeData(String data) throws DataRetroativaException, DataInvalidaException {
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        Date agora = new Date();
        Date dataFormatada = null;
        try {
            agora = formatador.parse(formatador.format(agora));
            System.out.println(agora);
            dataFormatada = formatador.parse(data);
            if (agora.compareTo(dataFormatada) > 0) throw new DataRetroativaException();
        } catch (ParseException e) {
            throw new DataInvalidaException();
        }

        return dataFormatada;
    }
}