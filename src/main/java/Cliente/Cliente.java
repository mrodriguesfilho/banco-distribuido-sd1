package Cliente;

import Servidor.BancoImplementacao;
import Servidor.BancoInterface;
import Servidor.Conta;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.Scanner;

public class Cliente {
    private Conta conta;
    private BancoInterface banco;

    public static void main(String[] args){

        int flagFim = 1;
        Scanner sc = new Scanner(System.in);
        Cliente cliente = new Cliente();

        cliente.setBanco(null);

        try{

            Registry registry = LocateRegistry.getRegistry("localhost", 27017);
            cliente.setBanco((BancoInterface) registry.lookup("banco"));
            //BancoInterface banco = (BancoInterface) registry.lookup("banco");

            while(flagFim != 0){
                System.out.println("======= SISTEMA BANCARIO =======");
                System.out.println("1 - Logar");
                System.out.println("0 - Sair");
                flagFim = sc.nextInt();

                switch(flagFim){
                    case 1:
                        if(cliente.login()){
                            System.out.println("\n========= BEM VINDO AO =========");
                            cliente.menu(cliente);
                        }else {
                            System.out.println("\nO usuário ou a senha não correspondem a um cliente cadastrado. Tente novamente!\n");
                        }
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean login(){
        Scanner sc = new Scanner(System.in);
        System.out.println("======= SISTEMA BANCARIO =======");
        System.out.println("\nDigite o numero da sua Agencia:\n");

        Integer agencia = 1;//sc.nextInt();

        System.out.println("\nDigite o numero da sua conta:\n");

        Integer numeroConta = 1;//sc.nextInt();

        System.out.println("\nDigite sua senha:\n");

        String senha = "123";//sc.next();

        this.conta = new Conta(agencia, numeroConta, senha);

        try{

            this.conta = this.banco.login(this.conta);

            if(this.conta != null) {
                return true;
            }else{
                return false;
            }

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private static void menu(Cliente cliente) throws RemoteException{

        int flagFim = 1;
        int operador;
        Scanner sc = new Scanner(System.in);

        while(flagFim != 0 ) {
            System.out.println("======= SISTEMA BANCARIO =======");
            System.out.println("======= "+cliente.conta.getNomeCliente()+" =======\n");
            System.out.println("1 - Consultar saldo");
            System.out.println("2 - Consultar histórico de movimentações");
            System.out.println("3 - Realizar transferencia");
            System.out.println("4 - Realizar saque");
            System.out.println("5 - Realizar depositos");
            System.out.println("0 - Finalizar");

            System.out.println("\nO que deseja fazer?");
            operador = sc.nextInt();

            switch(operador) {
                case 0:
                    flagFim = 0;
                    System.out.println("======= OPERAÇÃO FINALIZADA =======");
                    break;
                case 1:

                case 2:

                    System.out.println("\nQual numero da conta?\n");
                case 5:
                    System.out.println("\nQual o valor do deposito?");
                    double valor = sc.nextDouble();
                    cliente.deposito(cliente.conta.getAgencia(), cliente.conta.getNumeroConta(), valor);
                    System.out.println("O novo saldo é de: "+cliente.saldo(cliente.conta.getAgencia(), cliente.conta.getNumeroConta())+"\n");
                default:

            }

        }
    }

    private void deposito(int Agencia, int numeroConta, double valorDeposito) throws RemoteException {

        if(banco.deposito(Agencia, numeroConta, valorDeposito)){
            System.out.println("\nO valor de "+valorDeposito+" foi adicionado à conta!");
        }
    }

    private double  saldo(int Agencia, int numeroConta) throws RemoteException{
        return banco.saldo(Agencia, numeroConta);
    }

    public BancoInterface getBanco() {
        return banco;
    }

    public void setBanco(BancoInterface banco) {
        this.banco = banco;
    }
}
