package com.macaroni.projectonlinestudent.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class VagasEmprego {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    @ManyToOne
    private User empresa;

    private String atividades;

    @ManyToMany
    private List<Treinamento> treinamentoRequisito;

    private Double faixaSalarial;

    @ManyToMany
    private List<User> candidatos;

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public User getEmpresa() {
        return empresa;
    }

    public void setEmpresa(User empresa) {
        this.empresa = empresa;
    }

    public String getAtividades() {
        return atividades;
    }

    public void setAtividades(String atividades) {
        this.atividades = atividades;
    }

    public List<Treinamento> getTreinamentoRequisito() {
        return treinamentoRequisito;
    }

    public void setTreinamentoRequisito(List<Treinamento> treinamentoRequisito) {
        this.treinamentoRequisito = treinamentoRequisito;
    }

    public Double getFaixaSalarial() {
        return faixaSalarial;
    }

    public void setFaixaSalarial(Double faixaSalarial) {
        this.faixaSalarial = faixaSalarial;
    }

    public List<User> getCandidatos() {
        return candidatos;
    }

    public void setCandidatos(List<User> candidatos) {
        this.candidatos = candidatos;
    }
}
