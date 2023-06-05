package com.macaroni.projectonlinestudent.DTO;

import com.macaroni.projectonlinestudent.Model.Pergunta;
import com.macaroni.projectonlinestudent.Model.User;

public record PerguntaMentorDTO(Pergunta pergunta, User user) {
}
