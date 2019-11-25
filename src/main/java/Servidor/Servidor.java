package Servidor;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        System.out.println("==== CARREGANDO SERVIDOR ====");
        try{
            Registry registry = LocateRegistry.createRegistry(8080);
            BancoImplementacao bi = new BancoImplementacao();
            registry.rebind("bi", bi);
        }catch(Exception e){
            e.printStackTrace();
        }

        System.out.println("==== SERVIDOR INICIADO ====");
    }
}
