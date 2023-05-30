package com.macaroni.projectonlinestudent.DTO;

import com.macaroni.projectonlinestudent.model.Pergunta;
import com.macaroni.projectonlinestudent.model.User;

public record PerguntaMentorDTO(Pergunta pergunta, User user) {
}
