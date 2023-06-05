package com.macaroni.projectonlinestudent.Service;

import com.macaroni.projectonlinestudent.Repository.QuizRepository;
import com.macaroni.projectonlinestudent.Model.Pergunta;
import com.macaroni.projectonlinestudent.Model.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    public HttpStatusCode adicionarPerguntaQuiz(Quiz quiz, Pergunta pergunta){
        quiz.getPerguntas().add(pergunta);
        Optional<Quiz> updatedQuiz = Optional.ofNullable(quizRepository.findById(quiz.getId()).orElse(null));

        if(updatedQuiz.isEmpty()){
            return HttpStatusCode.valueOf(400);
        }
        updatedQuiz.get().getPerguntas().addAll(quiz.getPerguntas());
        quizRepository.save(quiz);

        return HttpStatusCode.valueOf(200);
    }

    public HttpStatusCode corrigirSubmissao(){
        return HttpStatusCode.valueOf(200);
    }

}
