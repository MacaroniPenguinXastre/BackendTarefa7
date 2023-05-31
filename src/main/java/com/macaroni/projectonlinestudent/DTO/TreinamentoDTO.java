package com.macaroni.projectonlinestudent.DTO;

import com.macaroni.projectonlinestudent.model.Treinamento;
import com.macaroni.projectonlinestudent.model.User;
import org.springframework.web.bind.annotation.RequestBody;

public record TreinamentoDTO(User user, Treinamento treinamento){}
