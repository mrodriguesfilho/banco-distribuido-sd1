package Servidor;

import javax.xml.transform.Result;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.List;

/*
 *   Implementar propriamente os métodos listados na interface
 *   a lógica das operações é feita aqui
 */

public class BancoImplementacao extends UnicastRemoteObject implements BancoInterface {

    private ConnFactory db = new ConnFactory();
    private Connection conn;

    long time = System.currentTimeMillis();
    Timestamp ts = new Timestamp(time);

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

    @Override
    public boolean deposito(int numeroagencia, int numeroconta, double valor) throws RemoteException {
        try {
            if (this.conn.isClosed()) {
                this.conn = this.db.getConnection();
            }

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE contas SET saldo=saldo+? WHERE numeroagencia=? AND numeroconta=?");

            PreparedStatement ps = this.conn.prepareStatement(sql.toString());
            ps.setDouble(1, valor);
            ps.setInt(2, numeroagencia);
            ps.setInt(3, numeroconta);
            ps.executeUpdate();

            sql.setLength(0);
            ps.clearParameters();
            sql.append("INSERT INTO historico (numeroagencia, numeroconta, data, valor, operacao) VALUES (?,?,?,?,?)");
            ps = this.conn.prepareStatement(sql.toString());
            ps.setInt(1, numeroagencia);
            ps.setInt(2, numeroconta);
            ps.setTimestamp(3, ts);
            ps.setDouble(4, valor);
            ps.setString(5, "DEPOSITO");
            ps.executeUpdate();


            return true;

        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    @Override
    public boolean saque(int numeroagencia, int numeroconta, double valor) throws RemoteException {
        try {
            if (this.conn.isClosed()) {
                this.conn = this.db.getConnection();
            }

            StringBuilder sql = new StringBuilder();

            if( saldo(numeroagencia, numeroconta) >= valor ){
                sql.append("UPDATE contas SET saldo=saldo-? WHERE numeroagencia=? AND numeroconta=?");
                PreparedStatement ps = this.conn.prepareStatement(sql.toString());
                ps.setDouble(1, valor);
                ps.setInt(2, numeroagencia);
                ps.setInt(3, numeroconta);
                ps.executeUpdate();

                sql.setLength(0);
                ps.clearParameters();
                sql.append("INSERT INTO historico (numeroagencia, numeroconta, data, valor, operacao) VALUES (?,?,?,?,?)");
                ps = this.conn.prepareStatement(sql.toString());
                ps.setInt(1, numeroagencia);
                ps.setInt(2, numeroconta);
                ps.setTimestamp(3, ts);
                ps.setDouble(4, valor);
                ps.setString(5, "SAQUE");
                ps.executeUpdate();

                return true;
            }else{
                return false;
            }

        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public double saldo(int agencia, int numeroConta) throws RemoteException {
        try {
            if (this.conn.isClosed()) {
                this.conn = this.db.getConnection();
            }

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT saldo FROM contas WHERE numeroagencia=? AND numeroconta=?");
            PreparedStatement ps = this.conn.prepareStatement(sql.toString());
            ps.setInt(1, agencia);
            ps.setInt(2, numeroConta);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getDouble("saldo");
            }else{
                return 0;
            }

        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public double transferir(int agenciaOrigem, int numeroContaOrigem, double valor, int agenciaDestino, int numeroContaDestino) throws RemoteException {
        try {
            if (this.conn.isClosed()) {
                this.conn = this.db.getConnection();
            }

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT saldo FROM contas WHERE numeroagencia=? AND numeroconta=?");
            PreparedStatement ps = this.conn.prepareStatement(sql.toString());
            ps.setInt(1, agenciaDestino);
            ps.setInt(2, numeroContaDestino);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                if( rs.getDouble("saldo") >= valor ){
                    sql.setLength(0);
                    ps.clearParameters();
                    sql.append("UPDATE contas SET saldo=saldo+? WHERE numeroagencia=? AND numeroconta=? ");
                    ps = this.conn.prepareStatement(sql.toString());
                    ps.setDouble(1, valor);
                    ps.setInt(2, agenciaDestino);
                    ps.setInt(3, numeroContaDestino);
                    ps.executeUpdate();

                    sql.setLength(0);
                    ps.clearParameters();
                    sql.append("INSERT INTO historico (numeroagencia, numeroconta, data, valor, operacao) VALUES (?,?,?,?,?)");
                    ps = this.conn.prepareStatement(sql.toString());
                    ps.setInt(1, agenciaDestino);
                    ps.setInt(2, numeroContaDestino);
                    ps.setTimestamp(3, ts);
                    ps.setDouble(4, valor);
                    ps.setString(5, "TRANSFERENCIA +");
                    ps.executeUpdate();

                    sql.setLength(0);
                    ps.clearParameters();
                    sql.append("UPDATE contas SET saldo=saldo-? WHERE numeroagencia=? AND numeroconta=?");
                    ps = this.conn.prepareStatement(sql.toString());
                    ps.setDouble(1, valor);
                    ps.setInt(2, agenciaOrigem);
                    ps.setInt(3, numeroContaOrigem);

                    sql.setLength(0);
                    ps.clearParameters();
                    sql.append("INSERT INTO historico (numeroagencia, numeroconta, data, valor, operacao) VALUES (?,?,?,?,?)");
                    ps = this.conn.prepareStatement(sql.toString());
                    ps.setInt(1, agenciaOrigem);
                    ps.setInt(2, numeroContaOrigem);
                    ps.setTimestamp(3, ts);
                    ps.setDouble(4, valor);
                    ps.setString(5, "TRANSFERENCIA -");
                    ps.executeUpdate();


                    return 1.0;
                }else{
                    return 0;
                }

            }else{
                return -1;
            }

        }catch( Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<String> extrato(int Agencia, int numeroConta) throws RemoteException {

        return null;
    }
}