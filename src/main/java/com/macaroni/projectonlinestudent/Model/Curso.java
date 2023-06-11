package com.macaroni.projectonlinestudent.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.Nationalized;

import java.util.List;

@Entity
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String titulo;

    private String descricao;

    @ManyToOne
    private User admCriador;

    //Lob permite que Strings de grande tamanho consigam ser inseridas.

    @Column(columnDefinition = "text")
    @Lob
    private String materialDidatico;

    @ManyToMany
    private List<Treinamento> treinamentosCurso;


    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public User getAdmCriador() {
        return admCriador;
    }

    public void setAdmCriador(User admCriador) {
        this.admCriador = admCriador;
    }

    public String getMaterialDidatico() {
        return materialDidatico;
    }

    public void setMaterialDidatico(String materialDidatico) {
        this.materialDidatico = materialDidatico;
    }

    public List<Treinamento> getTreinamentosCurso() {
        return treinamentosCurso;
    }

    public void setTreinamentosCurso(List<Treinamento> treinamentosCurso) {
        this.treinamentosCurso = treinamentosCurso;
    }
}
