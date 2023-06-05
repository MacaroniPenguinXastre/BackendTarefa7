package com.macaroni.projectonlinestudent;

import com.macaroni.projectonlinestudent.DTO.QuizPerguntaDTO;
import com.macaroni.projectonlinestudent.Repository.QuizRepository;
import com.macaroni.projectonlinestudent.Service.QuizService;
import com.macaroni.projectonlinestudent.Model.CargoUser;
import com.macaroni.projectonlinestudent.Model.Quiz;
import com.macaroni.projectonlinestudent.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuizController {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizService quizService;

    @GetMapping("/quiz/index")
    public ResponseEntity<List<Quiz>> indexQuizByUser(@RequestBody User user){
        if(user == null || user.getCargo().equals(CargoUser.ALUNO)){
            return ResponseEntity.status(403).build();
        }
        List<Quiz>indexQuiz = quizRepository.findAll();
        return ResponseEntity.ok(indexQuiz);
    }

    @PostMapping("/quiz/create")
    public ResponseEntity<?> createNewQuiz(@RequestBody Quiz quiz){
        if(quiz == null){
            return ResponseEntity.badRequest().build();
        }
        quizRepository.save(quiz);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/quiz/question/add")
    public ResponseEntity<?> associateQuestion(@RequestBody QuizPerguntaDTO quizPerguntaDTO){
        if(quizPerguntaDTO.pergunta() == null || quizPerguntaDTO.quiz() == null){
            return ResponseEntity.badRequest().build();
        }

        if(quizService.adicionarPerguntaQuiz(quizPerguntaDTO.quiz(), quizPerguntaDTO.pergunta()).value() == 200){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/quiz/question/delete")
    public ResponseEntity<?> removeQuestionFromQuiz(@RequestBody QuizPerguntaDTO quizPerguntaDTO){
        if(quizPerguntaDTO.pergunta() == null || quizPerguntaDTO.quiz() == null){
            return ResponseEntity.badRequest().build();
        }

        if(quizPerguntaDTO.quiz().getPerguntas().contains(quizPerguntaDTO.pergunta())){
            quizPerguntaDTO.quiz().getPerguntas().remove(quizPerguntaDTO.pergunta());
            quizRepository.saveAndFlush(quizPerguntaDTO.quiz());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
