package com.macaroni.projectonlinestudent.model;

import jakarta.persistence.*;
import org.springframework.lang.Nullable;

import java.util.List;

@Entity
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String titulo;

    private String descricao;

    private int cargaHorariaCurso;

    @ManyToOne
    private User mentor;

    @ManyToMany
    @Nullable
    private List<User> empresaParceira;

    //Lob permite que Strings de grande tamanho consigam ser inseridas.
    @Lob
    private String materialDidatico;

    @ManyToMany
    private List<Treinamento> treinamentosCurso;

    public List<Treinamento> getTreinamentosCurso() {
        return treinamentosCurso;
    }

    public void setTreinamentosCurso(List<Treinamento> treinamentosCurso) {
        this.treinamentosCurso = treinamentosCurso;
    }

    public List<User> getEmpresaParceira() {
        return empresaParceira;
    }

    public void setEmpresaParceira(List<User> empresaParceira) {
        this.empresaParceira = empresaParceira;
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

    public User getMentor() {
        return mentor;
    }

    public void setMentor(User mentor) {
        this.mentor = mentor;
    }

    public int getCargaHorariaCurso() {
        return cargaHorariaCurso;
    }

    public void setCargaHorariaCurso(int cargaHorariaCurso) {
        this.cargaHorariaCurso = cargaHorariaCurso;
    }

    public String getMaterialDidatico() {
        return materialDidatico;
    }

    public void setMaterialDidatico(String materialDidatico) {
        this.materialDidatico = materialDidatico;
    }

    public Long getId() {
        return id;
    }
}
