package com.macaroni.projectonlinestudent.Service;

import com.macaroni.projectonlinestudent.Repository.QuizRepository;
import com.macaroni.projectonlinestudent.model.Pergunta;
import com.macaroni.projectonlinestudent.model.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    public HttpStatusCode adicionarPerguntaQuiz(Quiz quiz, Pergunta pergunta){
        quiz.getPerguntas().add(pergunta);
        quizRepository.saveAndFlush(quiz);

        return HttpStatusCode.valueOf(200);
    }

    public HttpStatusCode deletarPerguntaQuiz(Quiz quiz,Pergunta pergunta){
        quiz.getPerguntas().remove(pergunta);

        quizRepository.saveAndFlush(quiz);
        return HttpStatusCode.valueOf(200);
    }

}
