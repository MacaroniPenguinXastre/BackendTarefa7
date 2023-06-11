package com.macaroni.projectonlinestudent;

import com.macaroni.projectonlinestudent.DTO.QuizPerguntaDTO;
import com.macaroni.projectonlinestudent.Model.*;
import com.macaroni.projectonlinestudent.Repository.PerguntaRepository;
import com.macaroni.projectonlinestudent.Repository.QuizRepository;
import com.macaroni.projectonlinestudent.Repository.UserRepository;
import com.macaroni.projectonlinestudent.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class QuizController {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private PerguntaRepository perguntaRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/adm/{id}/quizzes")
    public ResponseEntity<List<Quiz>> indexQuizByUser(@PathVariable("id")Long id){
        try{
            Optional<User>user = Optional.of(userRepository.getReferenceById(id));
            if(!user.get().getCargo().equals(CargoUser.ADM)){
                return ResponseEntity.status(403).build();
            }

            List<Quiz>indexQuiz = quizRepository.findAll();

            return ResponseEntity.ok().body(indexQuiz);
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/adm/users")
    public ResponseEntity<List<User>>indexUsers(){
        Optional<List<User>>users = Optional.of(userRepository.findAll());
        if(users.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(users.get());
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
        List<Treinamento>treinamento = new ArrayList<>(0);
        quiz.setTreinamentosQuiz(treinamento);
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
