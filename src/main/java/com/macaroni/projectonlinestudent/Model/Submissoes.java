package com.macaroni.projectonlinestudent.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Submissoes {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany
    private List<User> aluno;

    @ManyToMany
    private List<Treinamento> treinamentos;

    @ManyToMany
    private List<Quiz> quizzes;

    private int nota;

    private String resposta;

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
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
