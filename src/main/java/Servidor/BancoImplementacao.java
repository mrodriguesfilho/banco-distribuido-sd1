package Servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;

public class BancoImplementacao extends UnicastRemoteObject implements BancoInterface {
/*
*   Implementar propriamente os métodos listados na interface
*   a lógica das operações é feita aqui
* */

    private ConnFactory db = new ConnFactory();
    private Connection conn;


    protected BancoImplementacao() throws RemoteException{
        super();
        try{
            this.conn = db.getConnection();
            this.conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Conta login(Conta loginConta) throws RemoteException {
        return null;
    }
}
