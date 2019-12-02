package Servidor;

import java.io.Serializable;
import java.math.BigDecimal;

public class Conta implements Serializable {
    private String nomeCliente;
    private Integer numeroConta;
    private Integer agencia;
    private String senha;
    private double saldo;

    public Conta(Integer agencia, Integer numeroConta, String senha) {
        this.agencia = agencia;
        this.numeroConta = numeroConta;
        this.senha = senha;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public Integer getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(Integer numeroConta) {
        this.numeroConta = numeroConta;
    }

    public Integer getAgencia() {
        return agencia;
    }

    public void setAgencia(Integer agencia) {
        this.agencia = agencia;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }


}
