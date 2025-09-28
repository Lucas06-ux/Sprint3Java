package br.com.fiap.saudedigital.model;

import java.time.LocalDate;

public class Usuario {
    private String nome;
    private LocalDate dataNascimento;

    public Usuario(String nome, LocalDate dataNascimento){
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public String toString() {
        return
                " Nome= " + nome +
                ", Data de nascimento= " + dataNascimento
                ;
    }
}
