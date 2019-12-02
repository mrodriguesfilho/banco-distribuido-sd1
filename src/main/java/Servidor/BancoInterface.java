package Servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

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
    boolean saque(int Agencia, int numeroConta, double valor) throws RemoteException;
    double saldo(int agencia, int numeroConta) throws RemoteException;
    double transferir(int agenciaOrigem, int numeroContaOrigem, double valor, int agenciaDestino, int numeroContaDestino) throws  RemoteException;
    List<String> extrato(int Agencia, int numeroConta) throws RemoteException;

}
