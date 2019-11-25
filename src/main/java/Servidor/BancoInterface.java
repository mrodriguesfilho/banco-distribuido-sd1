package Servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

public interface BancoInterface extends Remote {
/*
    -Determinar os métodos que o banco faz
        - Login
        - Saque, Deposito, Transferencia
        - Criar conta
        - Exibir extrato
        etc.
*/

    Conta login(Conta dadosLogin) throws RemoteException;
    boolean deposito(int Agencia, int numeroConta, double valor) throws RemoteException;
    double saldo(int agencia, int numeroConta) throws RemoteException;
}
