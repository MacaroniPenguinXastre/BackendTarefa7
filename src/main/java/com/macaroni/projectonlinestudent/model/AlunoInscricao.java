package com.macaroni.projectonlinestudent.model;

import jakarta.persistence.*;
import org.springframework.lang.Nullable;

import java.time.ZonedDateTime;
import java.util.Date;

@Entity
public class AlunoInscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User aluno;

    @ManyToOne
    private Treinamento treinamento;

    private int cargaHorariaAtual;

    private int faseAtual;

    @Enumerated(EnumType.STRING)
    private StatusTreinamento statusTreino;

    @ManyToOne
    private Quiz quizIntro;
    private String respostasIntro;
    private int maiorAcertosIntro;

    @ManyToOne
    private Quiz quizCaseOne;
    private String respostasCaseOne;
    private int maiorAcertosCaseOne;

    @ManyToOne
    private Quiz quizCaseTwo;
    private String respostasCaseTwo;
    private int maiorAcertosCaseTwo;

    private ZonedDateTime dataInscricao;
    @Nullable
    private String observacao;


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

    public int getCargaHorariaAtual() {
        return cargaHorariaAtual;
    }

    public void setCargaHorariaAtual(int cargaHorariaAtual) {
        this.cargaHorariaAtual = cargaHorariaAtual;
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

    public Quiz getQuizIntro() {
        return quizIntro;
    }

    public void setQuizIntro(Quiz quizIntro) {
        this.quizIntro = quizIntro;
    }

    public String getRespostasIntro() {
        return respostasIntro;
    }

    public void setRespostasIntro(String respostasIntro) {
        this.respostasIntro = respostasIntro;
    }

    public int getMaiorAcertosIntro() {
        return maiorAcertosIntro;
    }

    public void setMaiorAcertosIntro(int maiorAcertosIntro) {
        this.maiorAcertosIntro = maiorAcertosIntro;
    }

    public Quiz getQuizCaseOne() {
        return quizCaseOne;
    }

    public void setQuizCaseOne(Quiz quizCaseOne) {
        this.quizCaseOne = quizCaseOne;
    }

    public String getRespostasCaseOne() {
        return respostasCaseOne;
    }

    public void setRespostasCaseOne(String respostasCaseOne) {
        this.respostasCaseOne = respostasCaseOne;
    }

    public int getMaiorAcertosCaseOne() {
        return maiorAcertosCaseOne;
    }

    public void setMaiorAcertosCaseOne(int maiorAcertosCaseOne) {
        this.maiorAcertosCaseOne = maiorAcertosCaseOne;
    }

    public Quiz getQuizCaseTwo() {
        return quizCaseTwo;
    }

    public void setQuizCaseTwo(Quiz quizCaseTwo) {
        this.quizCaseTwo = quizCaseTwo;
    }

    public String getRespostasCaseTwo() {
        return respostasCaseTwo;
    }

    public void setRespostasCaseTwo(String respostasCaseTwo) {
        this.respostasCaseTwo = respostasCaseTwo;
    }

    public int getMaiorAcertosCaseTwo() {
        return maiorAcertosCaseTwo;
    }

    public void setMaiorAcertosCaseTwo(int maiorAcertosCaseTwo) {
        this.maiorAcertosCaseTwo = maiorAcertosCaseTwo;
    }

    public ZonedDateTime getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(ZonedDateTime dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    @Nullable
    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(@Nullable String observacao) {
        this.observacao = observacao;
    }
}
