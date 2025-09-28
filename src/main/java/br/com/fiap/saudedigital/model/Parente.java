package br.com.fiap.saudedigital.model;

public class Parente {
    private String nome;
    private int codigoParente;
    private String dsParentesco;
    private String nmrTelefone1;
    private String nmrTelefone2;
    private String nmrTelefone3;
    private int codigoLembrete;
    private int codigoPaciente;

    public Parente(String nome, int codigoParente, String dsParentesco, String nmrTelefone1,
                   String nmrTelefone2, String nmrTelefone3, int codigoLembrete, int codigoPaciente) {
        this.nome = nome;
        this.codigoParente = codigoParente;
        this.dsParentesco = dsParentesco;
        this.nmrTelefone1 = nmrTelefone1;
        this.nmrTelefone2 = nmrTelefone2;
        this.nmrTelefone3 = nmrTelefone3;
        this.codigoLembrete = codigoLembrete;
        this.codigoPaciente = codigoPaciente;
    }


    public Parente(String nome, String dsParentesco, String nmrTelefone1, String nmrTelefone2, String nmrTelefone3, int codLembrete, int codPaciente) {
        this.nome = nome;
        this.dsParentesco = dsParentesco;
        this.nmrTelefone1  = nmrTelefone1;
        this.nmrTelefone2 = nmrTelefone2;
        this.nmrTelefone3 = nmrTelefone3;
        this.codigoLembrete = codLembrete;
        this.codigoPaciente = codPaciente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCodigoParente() {
        return codigoParente;
    }

    public void setCodigoParente(int codigoParente) {
        this.codigoParente = codigoParente;
    }

    public String getDsParentesco() {
        return dsParentesco;
    }

    public void setDsParentesco(String dsParentesco) {
        this.dsParentesco = dsParentesco;
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

    public int getCodigoLembrete() {
        return codigoLembrete;
    }

    public void setCodigoLembrete(int codigoLembrete) {
        this.codigoLembrete = codigoLembrete;
    }

    public int getCodigoPaciente() {
        return codigoPaciente;
    }

    public void setCodigoPaciente(int codigoPaciente) {
        this.codigoPaciente = codigoPaciente;
    }

    @Override
    public String toString() {
        return "Parente " +
                "nome= " + nome +
                ", codigoParente= " + codigoParente +
                ", dsParentesco= " + dsParentesco +
                ", nmrTelefone1= " + nmrTelefone1 +
                ", nmrTelefone2= " + nmrTelefone2 +
                ", nmrTelefone3= " + nmrTelefone3 +
                ", codigoLembrete= " + codigoLembrete +
                ", codigoPaciente= " + codigoPaciente ;
    }
}
