package Servidor;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;

public class BancoImplementacao extends UnicastRemoteObject implements BancoInterface {
/*
*   Implementar propriamente os métodos listados na interface
*   a lógica das operações é feita aqui
* */

    private ConnFactory db = new ConnFactory();
    private Connection conn;


    protected BancoImplementacao() throws Exception{
        super();
        try{
            this.conn = db.getConnection();
            this.conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void criarConta(String nomeCliente, String senhaCliente) {

    }
}
