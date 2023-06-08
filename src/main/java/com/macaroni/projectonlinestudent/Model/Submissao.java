package com.macaroni.projectonlinestudent.Model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Map;

@Entity
public class Submissao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    private List<User> aluno;

    @ManyToMany
    private List<Treinamento> treinamentos;

    @ManyToMany
    private List<Quiz> quizzes;

    private int nota;
    @ElementCollection
    private Map<Pergunta, Character>respostas;


    public Map<Pergunta, Character> getRespostas() {
        return respostas;
    }

    public void setRespostas(Map<Pergunta, Character> respostas) {
        this.respostas = respostas;
    }

    public List<User> getAluno() {
        return aluno;
    }

    public void setAluno(List<User> aluno) {
        this.aluno = aluno;
    }

    public List<Treinamento> getTreinamentos() {
        return treinamentos;
    }

    public void setTreinamentos(List<Treinamento> treinamentos) {
        this.treinamentos = treinamentos;
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
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
