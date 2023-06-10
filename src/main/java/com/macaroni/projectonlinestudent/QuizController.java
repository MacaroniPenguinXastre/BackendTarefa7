package com.macaroni.projectonlinestudent;

import com.macaroni.projectonlinestudent.DTO.QuizPerguntaDTO;
import com.macaroni.projectonlinestudent.Model.Pergunta;
import com.macaroni.projectonlinestudent.Repository.PerguntaRepository;
import com.macaroni.projectonlinestudent.Repository.QuizRepository;
import com.macaroni.projectonlinestudent.Service.QuizService;
import com.macaroni.projectonlinestudent.Model.CargoUser;
import com.macaroni.projectonlinestudent.Model.Quiz;
import com.macaroni.projectonlinestudent.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class QuizController {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private PerguntaRepository perguntaRepository;

    @GetMapping("/quizzes")
    public ResponseEntity<List<Quiz>> indexQuizByUser(@RequestBody User user){
        try{
            if(!user.getCargo().equals(CargoUser.ADM)){
                return ResponseEntity.status(403).build();
            }
            List<Quiz>indexQuiz = quizRepository.findAll();
            return ResponseEntity.ok(indexQuiz);
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/quizzes/{id}")
    public ResponseEntity<Quiz>quizDetails(@PathVariable("id")Long id){
        try {
            Optional<Quiz> quiz = quizRepository.findById(id);
            if(quiz.isPresent()){
                return ResponseEntity.ok().body(quiz.get());
            }
            return ResponseEntity.noContent().build();
        }
        catch(NullPointerException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/quizzes")
    public ResponseEntity<?> createNewQuiz(@RequestBody Quiz quiz){
        if(quiz == null){
            return ResponseEntity.badRequest().build();
        }
        quizRepository.save(quiz);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/quizzes/{quizId}/pergunta/{perguntaID}")
    public ResponseEntity<?> associateQuestion(@PathVariable("quizId")Long quizID,@PathVariable("perguntaID")Long perguntaID){
        try{
            Optional<Quiz>quiz = quizRepository.findById(quizID);
            Optional<Pergunta>pergunta = perguntaRepository.findById(perguntaID);

            if(quiz.isEmpty() || pergunta.isEmpty()){
                return ResponseEntity.badRequest().build();
            }

            //Verifica se a pergunta já existe no Quiz
            if(quiz.get().getPerguntas().contains(pergunta.get())){
                return ResponseEntity.status(409).build();
            }

            quiz.get().getPerguntas().add(pergunta.get());
            quizRepository.saveAndFlush(quiz.get());
            return ResponseEntity.ok().build();
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/quizzes/{quizId}/pergunta/{perguntaID}")
    public ResponseEntity<?> removeQuestionFromQuiz(@PathVariable("quizId")Long quizID,@PathVariable("perguntaID")Long perguntaID){
        try{
            Quiz quiz = quizRepository.getReferenceById(quizID);
            Pergunta pergunta = perguntaRepository.getReferenceById(perguntaID);

            if(quiz.getPerguntas().contains(pergunta)){
                quiz.getPerguntas().remove(pergunta);
                quizRepository.saveAndFlush(quiz);

                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        }

        catch (NullPointerException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }


}
