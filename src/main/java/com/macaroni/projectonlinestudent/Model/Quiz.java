package com.macaroni.projectonlinestudent.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String titulo;

    @ManyToMany
    private List<Pergunta> perguntas;

    @ManyToOne
    private User admCriador;

    @ManyToMany
    private List<Treinamento> treinamentosQuiz;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Treinamento> getTreinamentosQuiz() {
        return treinamentosQuiz;
    }

    public void setTreinamentosQuiz(List<Treinamento> treinamentosQuiz) {
        this.treinamentosQuiz = treinamentosQuiz;
    }

    public User getAdmCriador() {
        return admCriador;
    }

    public void setAdmCriador(User mentor) {
        this.admCriador = mentor;
    }

    public Long getId() {
        return id;
    }

    public List<Pergunta> getPerguntas() {
        return perguntas;
    }

    public void setPerguntas(List<Pergunta> perguntas) {
        this.perguntas = perguntas;
    }
}
