package Cliente;

import Servidor.BancoImplementacao;
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
        Cliente cliente = new Cliente();

        try{
            Registry registry = LocateRegistry.getRegistry("localhost", 8080);
            BancoImplementacao Banco = (BancoImplementacao) registry.lookup("BI");

            while(flagFim != 0){
                System.out.println("======= SISTEMA BANCARIO =======");
                System.out.println("1 - Logar");
                System.out.println("0 - Sair");

                if(cliente.login()){
                    cliente.menu();
                }else{

                }


            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void menu(Cliente clente) throws RemoteException{

        Scanner sc = new Scanner(System.in);

        int flagFim = 1;
        int operador;

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

    private boolean login throws RemoteException(){
        System.out.println("======= SISTEMA BANCARIO =======");
        System.out.println("\nDigite o numero da sua conta:\n");

        Scanner sc = new Scanner(System.in);

        Integer conta = sc.nextInt();

        System.out.println("\nDigite sua senha:\n");

        Integer senha = sc.nextInt();

        try{
            return true;
        }catch(RemoteException e){
            return false;
        }

        return false;

    }
}
