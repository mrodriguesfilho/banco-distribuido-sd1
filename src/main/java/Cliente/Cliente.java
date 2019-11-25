package Cliente;

import Servidor.BancoImplementacao;
import Servidor.BancoInterface;
import Servidor.Conta;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Cliente {
    private Conta conta;
    private BancoImplementacao banco;


    public static void main(String[] args) throws Exception{

        int flagFim = 1;
        int operador = -1;
        Scanner sc = new Scanner(System.in);
        Cliente cliente = new Cliente();

        try{

            Registry registry = LocateRegistry.getRegistry("localhost", 8080);
            BancoInterface banco = (BancoInterface) registry.lookup("bi");

            while(flagFim != 0){
                System.out.println("======= SISTEMA BANCARIO =======");
                System.out.println("1 - Logar");
                System.out.println("0 - Sair");
                flagFim = sc.nextInt();

                switch(flagFim){
                    case 1:
                        if(cliente.login()){

                        }else {

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

    private static void menu(Cliente clente) throws RemoteException{

        int flagFim = 1;
        int operador;
        Scanner sc = new Scanner(System.in);

        while(flagFim != 0 ) {
            System.out.println("======= SISTEMA BANCARIO =======");
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
                    System.out.println("============= OPERAÇÃO FINALIZADA ===============");
                    break;
                case 1:

                case 2:

                    System.out.println("\nQual numero da conta?\n");

                default:

            }

        }
    }

    private boolean login(){
        Scanner sc = new Scanner(System.in);
        System.out.println("======= SISTEMA BANCARIO =======");
        System.out.println("\nDigite o numero da sua Agencia:\n");

        Integer agencia = sc.nextInt();

        System.out.println("\nDigite o numero da sua conta:\n");

        Integer numeroConta = sc.nextInt();

        System.out.println("\nDigite sua senha:\n");

        String senha = sc.next();

        this.conta = new Conta(agencia, numeroConta, senha);

        try{
            this.conta = this.banco.login(this.conta);

            if(this.conta != null){
                return true;
            }
            return false;
        }catch(RemoteException e){
            return false;
        }

    }
}
