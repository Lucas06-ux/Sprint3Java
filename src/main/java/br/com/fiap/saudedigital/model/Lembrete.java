package br.com.fiap.saudedigital.model;

import java.time.LocalDate;

public class Lembrete {
    private int codigoLembrete;
    private String mensagem;
    private LocalDate dataEnvio;

    public Lembrete(int codigoLembrete, String mensagem, LocalDate dataEnvio) {
        this.codigoLembrete = codigoLembrete;
        this.mensagem = mensagem;
        this.dataEnvio = dataEnvio;
    }



    public Lembrete(String mensagem, LocalDate dataEnvio) {
        this.mensagem = mensagem;
        this.dataEnvio = dataEnvio;
    }

    public int getCodigoLembrete() {
        return codigoLembrete;
    }

    public void setCodigoLembrete(int codigoLembrete) {
        this.codigoLembrete = codigoLembrete;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDate getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDate dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    @Override
    public String toString() {
        return " Lembrete " +
                " codigoLembrete= " + codigoLembrete +
                ", mensagem= " + mensagem +
                ", dataEnvio= " + dataEnvio ;
    }
}
