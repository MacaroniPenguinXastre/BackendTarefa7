package com.macaroni.projectonlinestudent.DTO;

import com.macaroni.projectonlinestudent.Model.Treinamento;
import com.macaroni.projectonlinestudent.Model.User;

public record TreinamentoDTO(int cargaHoraria,String nomeComercial,String descricao,int quantidadeMinima,int quantidadeMaxima){}

