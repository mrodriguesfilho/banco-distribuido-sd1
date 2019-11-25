package Servidor;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {
    public static void main(String[] args){

        System.out.println("==== CARREGANDO SERVIDOR ====");
        try{
            Registry registry = LocateRegistry.createRegistry(27017);
            BancoImplementacao bi = new BancoImplementacao();
            registry.rebind("banco", bi);
            System.out.println("==== SERVIDOR INICIADO ====");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
