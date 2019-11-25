package Servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BancoInterface extends Remote {
/*
    -Determinar os métodos que o banco faz
        - Login
        - Saque, Deposito, Transferencia
        - Criar conta
        - Exibir extrato
        etc.
*/

    Conta login(Conta loginConta) throws RemoteException;
}
