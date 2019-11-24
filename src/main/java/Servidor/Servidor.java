package Servidor;

import java.rmi.Naming;

public class Servidor {
    public static void main(String[] args) throws Exception{
        BancoImplementacao bi = new BancoImplementacao();
        Naming.rebind("BI", bi);
        System.out.println("==== SERVIDOR INICIADO ====");
    }
}
