package com.macaroni.projectonlinestudent.Model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Map;

@Entity
public class Submissao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User aluno;

    @ManyToOne
    private Treinamento treinamentos;

    @ManyToOne
    private Quiz quiz;

    private int nota;

    @ElementCollection
    private Map<Pergunta, Character>respostas;

    public Submissao(User aluno, Treinamento treinamentos, Quiz quiz,int notaInicial) {
        this.aluno = aluno;
        this.treinamentos = treinamentos;
        this.quiz = quiz;
        this.setNota(notaInicial);
    }

    public Submissao(){

    }

    public Map<Pergunta, Character> getRespostas() {
        return respostas;
    }

    public void setRespostas(Map<Pergunta, Character> respostas) {
        this.respostas = respostas;
    }

    public User getAluno() {
        return aluno;
    }

    public void setAluno(User aluno) {
        this.aluno = aluno;
    }

    public Treinamento getTreinamentos() {
        return treinamentos;
    }

    public void setTreinamentos(Treinamento treinamentos) {
        this.treinamentos = treinamentos;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public Long getId() {
        return id;
    }
}
