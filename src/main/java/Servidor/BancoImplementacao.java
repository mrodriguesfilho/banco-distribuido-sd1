package Servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 *   Implementar propriamente os métodos listados na interface
 *   a lógica das operações é feita aqui
 */

public class BancoImplementacao extends UnicastRemoteObject implements BancoInterface {

    private ConnFactory db = new ConnFactory();
    private Connection conn;

    protected BancoImplementacao() throws RemoteException {
        super();
        try {
            this.conn = db.getConnection();
            this.conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Conta login(Conta dadosLogin){

        try {
            if(this.conn.isClosed()) {
                this.conn = this.db.getConnection();
            }

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT senha, saldo, nomecliente FROM contas WHERE numeroagencia=? AND numeroconta=?");

            PreparedStatement ps = this.conn.prepareStatement(sql.toString());

            ps.setInt(1, dadosLogin.getAgencia());
            ps.setInt(2, dadosLogin.getNumeroConta());

          //  System.out.println("\n"+ps+"\n");

            ResultSet rs = ps.executeQuery();

       //     System.out.println("AGENCIA: "+dadosLogin.getAgencia());
      //      System.out.println("CONTA: "+dadosLogin.getNumeroConta());

            System.out.println("\n"+rs+"\n");

            if(rs.next()) {
                if(!dadosLogin.getSenha().equals(rs.getString("senha").trim())) {
                    return null;
                }

                dadosLogin.setNomeCliente(rs.getString("nomecliente").trim());
                dadosLogin.setSaldo(rs.getDouble("saldo"));
            }

            return dadosLogin;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                this.conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}