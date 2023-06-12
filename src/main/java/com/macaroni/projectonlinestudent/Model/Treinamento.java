package com.macaroni.projectonlinestudent.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
public class Treinamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cargaHorariaTotal;

    private String nomeComercial;

    private String descricao;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime dataInicioInscricao;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime dataFimInscricao;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime dataInicioTreinamento;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")

    private LocalDateTime dataFimTreinamento;

    private int quantidadeMinima;

    private int quantidadeMaxima;

    @ManyToOne
    private Quiz testeAptidao;

    @ManyToMany
    private List<Curso> faseIntrodutorio;

    @ManyToOne
    private Quiz primeiroCase;

    @ManyToMany
    private List<Curso> faseAvancada;

    @ManyToOne
    private Quiz segundoCase;


    public LocalDateTime getDataInicioInscricao() {
        return dataInicioInscricao;
    }

    public void setDataInicioInscricao(LocalDateTime dataInicioInscricao) {
        this.dataInicioInscricao = dataInicioInscricao;
    }

    public LocalDateTime getDataFimInscricao() {
        return dataFimInscricao;
    }

    public void setDataFimInscricao(LocalDateTime dataFimInscricao) {
        this.dataFimInscricao = dataFimInscricao;
    }

    public LocalDateTime getDataInicioTreinamento() {
        return dataInicioTreinamento;
    }

    public void setDataInicioTreinamento(LocalDateTime dataInicioTreinamento) {
        this.dataInicioTreinamento = dataInicioTreinamento;
    }

    public LocalDateTime getDataFimTreinamento() {
        return dataFimTreinamento;
    }

    public Long getId() {
        return id;
    }

    public int getCargaHorariaTotal() {
        return cargaHorariaTotal;
    }

    public void setCargaHorariaTotal(int cargaHorariaTotal) {
        this.cargaHorariaTotal = cargaHorariaTotal;
    }

    public String getNomeComercial() {
        return nomeComercial;
    }

    public void setNomeComercial(String nomeComercial) {
        this.nomeComercial = nomeComercial;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }



    public void setDataFimTreinamento(LocalDateTime dataFimTreinamento) {
        this.dataFimTreinamento = dataFimTreinamento;
    }

    public int getQuantidadeMinima() {
        return quantidadeMinima;
    }

    public void setQuantidadeMinima(int quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }

    public int getQuantidadeMaxima() {
        return quantidadeMaxima;
    }

    public void setQuantidadeMaxima(int quantidadeMaxima) {
        this.quantidadeMaxima = quantidadeMaxima;
    }

    public Quiz getTesteAptidao() {
        return testeAptidao;
    }

    public void setTesteAptidao(Quiz testeAptidao) {
        this.testeAptidao = testeAptidao;
    }

    public List<Curso> getFaseIntrodutorio() {
        return faseIntrodutorio;
    }

    public void setFaseIntrodutorio(List<Curso> faseIntrodutorio) {
        this.faseIntrodutorio = faseIntrodutorio;
    }

    public Quiz getPrimeiroCase() {
        return primeiroCase;
    }

    public void setPrimeiroCase(Quiz primeiroCase) {
        this.primeiroCase = primeiroCase;
    }

    public List<Curso> getFaseAvancada() {
        return faseAvancada;
    }

    public void setFaseAvancada(List<Curso> faseAvancada) {
        this.faseAvancada = faseAvancada;
    }

    public Quiz getSegundoCase() {
        return segundoCase;
    }

    public void setSegundoCase(Quiz segundoCase) {
        this.segundoCase = segundoCase;
    }
}
