package com.macaroni.projectonlinestudent.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Pergunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String enunciado;

    private String alternativaA;

    private String alternativaB;

    private String alternativaC;

    private String alternativaD;

    private char alternativaCorreta;

    @ManyToOne
    private User mentor;

    @ManyToMany
    private List<Quiz> quizAssociados;


    public List<Quiz> getQuizAssociados() {
        return quizAssociados;
    }

    public void setQuizAssociados(List<Quiz> quizAssociados) {
        this.quizAssociados = quizAssociados;
    }

    public User getMentor() {
        return mentor;
    }

    public void setMentor(User mentor) {
        this.mentor = mentor;
    }

    public char getAlternativaCorreta() {
        return alternativaCorreta;
    }


    public void setAlternativaCorreta(char alternativaCorreta) {
        this.alternativaCorreta = alternativaCorreta;
    }


    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getAlternativaA() {
        return alternativaA;
    }

    public void setAlternativaA(String alternativaA) {
        this.alternativaA = alternativaA;
    }

    public String getAlternativaB() {
        return alternativaB;
    }

    public void setAlternativaB(String alternativaB) {
        this.alternativaB = alternativaB;
    }

    public String getAlternativaC() {
        return alternativaC;
    }

    public void setAlternativaC(String alternativaC) {
        this.alternativaC = alternativaC;
    }

    public String getAlternativaD() {
        return alternativaD;
    }

    public void setAlternativaD(String alternativaD) {
        this.alternativaD = alternativaD;
    }

    public Long getId() {
        return id;
    }
}
