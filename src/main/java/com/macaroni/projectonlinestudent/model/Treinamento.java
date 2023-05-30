package com.macaroni.projectonlinestudent.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Entity
public class Treinamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cargaHorariaTotal;

    private String nomeComercial;

    private String descricao;

    private ZonedDateTime dataInicioInscricao;

    private ZonedDateTime dataFimInscricao;

    private ZonedDateTime dataInicioTreinamento;

    private ZonedDateTime dataFimTreinamento;

    private int quantidadeMinima;

    private int quantidadeMaxima;

    @ManyToOne
    private Quiz testeAptidao;

    @ManyToMany
    private List<Curso> faseIntrodutorio;

    private int cargaHorariaNecessariaPrimeiroCase;

    @ManyToOne
    private Quiz primeiroCase;

    @ManyToMany
    private List<Curso> faseAvancada;

    private int cargaHorariaNecessariaSegundoCase;

    @ManyToOne
    private Quiz segundoCase;


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

    public ZonedDateTime getDataInicioInscricao() {
        return dataInicioInscricao;
    }

    public void setDataInicioInscricao(ZonedDateTime dataInicioInscricao) {
        this.dataInicioInscricao = dataInicioInscricao;
    }

    public ZonedDateTime getDataFimInscricao() {
        return dataFimInscricao;
    }

    public void setDataFimInscricao(ZonedDateTime dataFimInscricao) {
        this.dataFimInscricao = dataFimInscricao;
    }

    public ZonedDateTime getDataInicioTreinamento() {
        return dataInicioTreinamento;
    }

    public void setDataInicioTreinamento(ZonedDateTime dataInicioTreinamento) {
        this.dataInicioTreinamento = dataInicioTreinamento;
    }

    public ZonedDateTime getDataFimTreinamento() {
        return dataFimTreinamento;
    }

    public void setDataFimTreinamento(ZonedDateTime dataFimTreinamento) {
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

    public int getCargaHorariaNecessariaPrimeiroCase() {
        return cargaHorariaNecessariaPrimeiroCase;
    }

    public void setCargaHorariaNecessariaPrimeiroCase(int cargaHorariaNecessariaPrimeiroCase) {
        this.cargaHorariaNecessariaPrimeiroCase = cargaHorariaNecessariaPrimeiroCase;
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

    public int getCargaHorariaNecessariaSegundoCase() {
        return cargaHorariaNecessariaSegundoCase;
    }

    public void setCargaHorariaNecessariaSegundoCase(int cargaHorariaNecessariaSegundoCase) {
        this.cargaHorariaNecessariaSegundoCase = cargaHorariaNecessariaSegundoCase;
    }

    public Quiz getSegundoCase() {
        return segundoCase;
    }

    public void setSegundoCase(Quiz segundoCase) {
        this.segundoCase = segundoCase;
    }
}
