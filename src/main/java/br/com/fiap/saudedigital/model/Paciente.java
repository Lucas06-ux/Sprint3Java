package br.com.fiap.saudedigital.model;

import java.time.LocalDate;

public class Paciente extends Usuario {
    private int codigoPaciente;
    private String cpf;
    private String nmrTelefone1;
    private String nmrTelefone2;
    private String nmrTelefone3;

    public Paciente(String nome, LocalDate dataNascimento, String cpf, String nmrTelefone1,
                    String nmrTelefone2, String nmrTelefone3){
        super(nome, dataNascimento);
        this.cpf = cpf;
        this.nmrTelefone1 = nmrTelefone1;
        this.nmrTelefone2 = nmrTelefone2;
        this.nmrTelefone3 = nmrTelefone3;

    }

    public Paciente(int codigoPaciente, String nome, LocalDate dataNascimento,
                    String cpf, String nmrTelefone1, String nmrTelefone2, String nmrTelefone3) {
        super(nome, dataNascimento);
        this.codigoPaciente = codigoPaciente;
        this.cpf = cpf;
        this.nmrTelefone1 = nmrTelefone1;
        this.nmrTelefone2 = nmrTelefone2;
        this.nmrTelefone3 = nmrTelefone3;

    }

    public int getCodigoPaciente() {
        return codigoPaciente;
    }

    public void setCodigoPaciente(int codigoPaciente) {
        this.codigoPaciente = codigoPaciente;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNmrTelefone1() {
        return nmrTelefone1;
    }

    public void setNmrTelefone1(String nmrTelefone1) {
        this.nmrTelefone1 = nmrTelefone1;
    }

    public String getNmrTelefone2() {
        return nmrTelefone2;
    }

    public void setNmrTelefone2(String nmrTelefone2) {
        this.nmrTelefone2 = nmrTelefone2;
    }

    public String getNmrTelefone3() {
        return nmrTelefone3;
    }

    public void setNmrTelefone3(String nmrTelefone3) {
        this.nmrTelefone3 = nmrTelefone3;
    }


    @Override
    public String toString() {
        return "Dados do paciente: " +
                " Codigo= " + codigoPaciente +
                super.toString() +
                ", Cpf= " + cpf +
                ", Número de telefone principal= " + nmrTelefone1 +
                ", Segundo número de telefone= " + nmrTelefone2 +
                ", Terceiro número de telefone= " + nmrTelefone3;
    }
}
