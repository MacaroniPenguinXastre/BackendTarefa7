package com.macaroni.projectonlinestudent.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Treinamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cargaHorariaTotal;

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



    public Long getId() {
        return id;
    }
}
