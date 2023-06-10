package com.macaroni.projectonlinestudent.Model;

import jakarta.persistence.*;
import org.springframework.lang.Nullable;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
public class AlunoInscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User aluno;

    @ManyToOne
    private Treinamento treinamento;

    @OneToOne
    private Submissao quizIntroducao;

    @OneToOne
    private Submissao caseOne;

    @OneToOne
    private Submissao caseTwo;

    @Enumerated(EnumType.STRING)
    private StatusTreinamento statusTreino;

    private ZonedDateTime dataInscricao;

    public Submissao getQuizIntroducao() {
        return quizIntroducao;
    }

    public void setQuizIntroducao(Submissao quizIntroducao) {
        this.quizIntroducao = quizIntroducao;
    }


    public Submissao getCaseOne() {
        return caseOne;
    }

    public void setCaseOne(Submissao caseOne) {
        this.caseOne = caseOne;
    }


    public Submissao getCaseTwo() {
        return caseTwo;
    }

    public void setCaseTwo(Submissao caseTwo) {
        this.caseTwo = caseTwo;
    }

    public Long getId() {
        return id;
    }

    public User getAluno() {
        return aluno;
    }

    public void setAluno(User aluno) {
        this.aluno = aluno;
    }

    public Treinamento getTreinamento() {
        return treinamento;
    }

    public void setTreinamento(Treinamento treinamento) {
        this.treinamento = treinamento;
    }

    public StatusTreinamento getStatusTreino() {
        return statusTreino;
    }

    public void setStatusTreino(StatusTreinamento statusTreino) {
        this.statusTreino = statusTreino;
    }

    public ZonedDateTime getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(ZonedDateTime dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

}
