package com.mycompany.atmbancario.models;

import java.io.Serializable;
import java.math.BigDecimal;

public class Conta implements Serializable {
    private int idConta;
    private int idUsuario;
    private String numeroConta;
    private BigDecimal saldo;
    private String tipoConta;

    public Conta() {}

    public Conta(int idConta, int idUsuario, String numeroConta, BigDecimal saldo, String tipoConta) {
        this.idConta = idConta;
        this.idUsuario = idUsuario;
        this.numeroConta = numeroConta;
        this.saldo = saldo;
        this.tipoConta = tipoConta;
    }

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }
}
