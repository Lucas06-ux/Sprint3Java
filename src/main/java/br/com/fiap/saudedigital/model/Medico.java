package br.com.fiap.saudedigital.model;

import java.time.LocalDate;

public class Medico extends Usuario{
    private int codigoMedico;
    private String crm;
    private String especialidade;
    private double salario;



    public Medico(String nome, String crm, LocalDate dataNascimento, String especialidade, double salario) {
        super(nome, dataNascimento);
        this.crm = crm;
        this.especialidade = especialidade;
        this.salario = salario;
    }

    public Medico(String nome, LocalDate dataNascimento, int codigoMedico, String crm, String especialidade, double salario) {
        super(nome, dataNascimento);
        this.codigoMedico = codigoMedico;
        this.crm = crm;
        this.especialidade = especialidade;
        this.salario = salario;
    }



    public int getCodigoMedico() {
        return codigoMedico;
    }

    public void setCodigoMedico(int codigoMedico) {
        this.codigoMedico = codigoMedico;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return "Medico" +
                " codigo do medico= " + codigoMedico +
                super.toString() +
                ", crm= " + crm +
                ", especialidade= " + especialidade +
                ", salario = " + salario;
    }
}
