package com.macaroni.projectonlinestudent.model;

import jakarta.persistence.*;

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
    private List<User> alunos;

    @Lob
    private String materialDidatico;

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

    public int getCargaHorariaTotal() {
        return cargaHorariaCurso;
    }

    public void setCargaHorariaTotal(int cargaHorariaTotal) {
        this.cargaHorariaCurso = cargaHorariaTotal;
    }

    public User getMentor() {
        return mentor;
    }

    public void setMentor(User mentor) {
        this.mentor = mentor;
    }

    public List<User> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<User> alunos) {
        this.alunos = alunos;
    }

    public Long getId() {
        return id;
    }
}
