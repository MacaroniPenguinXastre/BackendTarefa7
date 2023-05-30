package com.macaroni.projectonlinestudent.DTO;

import com.macaroni.projectonlinestudent.model.Pergunta;
import com.macaroni.projectonlinestudent.model.Quiz;

public record QuizPerguntaDTO(Quiz quiz, Pergunta pergunta) {
}
