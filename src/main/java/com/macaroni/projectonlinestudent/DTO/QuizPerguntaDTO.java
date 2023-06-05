package com.macaroni.projectonlinestudent.DTO;

import com.macaroni.projectonlinestudent.Model.Pergunta;
import com.macaroni.projectonlinestudent.Model.Quiz;

public record QuizPerguntaDTO(Quiz quiz, Pergunta pergunta) {
}
