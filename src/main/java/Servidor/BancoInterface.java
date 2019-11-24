package Servidor;

import java.rmi.Remote;

public interface BancoInterface extends Remote {
/*
    -Determinar os métodos que o banco faz
        - Saque, Deposito, Transferencia
        - Criar conta
        - Exibir extrato
        etc.
*/

    void criarConta(String nomeCliente, String senhaCliente) throws Exception;
}
