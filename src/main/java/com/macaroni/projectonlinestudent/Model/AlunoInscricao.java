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

    private int faseAtual;

    @Enumerated(EnumType.STRING)
    private StatusTreinamento statusTreino;

    @OneToMany
    @Nullable
    private List<Submissao> submissoes;

    private ZonedDateTime dataInscricao;

    @Nullable
    public List<Submissao> getSubmissoes() {
        return submissoes;
    }

    public void setSubmissoes(@Nullable List<Submissao> submissoes) {
        this.submissoes = submissoes;
    }

    public Long getId() {
        return id;
    }
    public List<Submissao> getAtividadesAlunos() {
        return submissoes;
    }

    public void setAtividadesAlunos(List<Submissao> submissoes) {
        this.submissoes = submissoes;
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

    public int getFaseAtual() {
        return faseAtual;
    }

    public void setFaseAtual(int faseAtual) {
        this.faseAtual = faseAtual;
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
