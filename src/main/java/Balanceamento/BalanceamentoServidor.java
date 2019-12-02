package Balanceamento;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import Servidor.BancoImplementacao;
import Servidor.BancoInterface;
import Servidor.Conta;

public class BalanceamentoServidor extends UnicastRemoteObject implements BancoInterface {
    private static Integer connCounter = 0;
    private BancoInterface banco1, banco2;

    protected BalanceamentoServidor() throws RemoteException, NotBoundException {
        super();
        connCounter++;
        Registry registry1, registry2;
        registry1 = LocateRegistry.getRegistry("localhost", 27017);
        registry2 = LocateRegistry.getRegistry("192.168.0.10", 27017);
        this.banco1 = (BancoInterface) registry1.lookup("banco");
        this.banco2 = (BancoInterface) registry2.lookup("banco");
    }

    public static void main(String[] args) {
        try {
            BalanceamentoServidor bsv = new BalanceamentoServidor();
            Registry registry = LocateRegistry.createRegistry(27015);
            registry.rebind("banco", bsv);
            System.out.println("=== BALANCEAMENTO INICIADO ===");
        } catch (Exception e) {
            System.out.println("Caixa erro: " + e.getMessage());
        }

    }

    @Override
    public Conta login(Conta contaLogin) throws RemoteException {
        connCounter++;
        return (connCounter % 2 == 0) ? this.banco1.login(contaLogin) : this.banco2.login(contaLogin);
    }

    @Override
    public boolean deposito(int Agencia, int numeroConta, double valor) throws RemoteException {
        connCounter++;
        return (connCounter % 2 == 0) ? this.banco1.deposito(Agencia, numeroConta, valor) : this.banco2.deposito(Agencia, numeroConta, valor);
    }

    @Override
    public boolean saque(int Agencia, int numeroConta, double valor) throws RemoteException {
        connCounter++;
        return (connCounter % 2 == 0) ? this.banco1.saque(Agencia, numeroConta, valor) : this.banco2.saque(Agencia, numeroConta, valor);
    }

    @Override
    public double saldo(int agencia, int numeroConta) throws RemoteException {
        connCounter++;
        return (connCounter % 2 == 0) ? this.banco1.saldo(agencia, numeroConta) : this.banco2.saldo(agencia, numeroConta);
    }

    @Override
    public double transferir(int agenciaOrigem, int numeroContaOrigem, double valor, int agenciaDestino, int numeroContaDestino) throws RemoteException {
        connCounter++;
        return (connCounter % 2 == 0) ? this.banco1.transferir(agenciaOrigem, numeroContaOrigem, valor, agenciaDestino, numeroContaDestino) : this.banco2.transferir(agenciaOrigem, numeroContaOrigem, valor, agenciaDestino, numeroContaDestino);
    }

    @Override
    public ArrayList<String> extrato(int Agencia, int numeroConta) throws RemoteException {
        connCounter++;
        return (connCounter % 2 == 0) ? this.banco1.extrato(Agencia, numeroConta) : this.banco2.extrato(Agencia, numeroConta);
    }
}

