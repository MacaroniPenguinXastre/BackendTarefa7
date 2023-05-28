package com.macaroni.projectonlinestudent;

import com.macaroni.projectonlinestudent.Repository.QuizRepository;
import com.macaroni.projectonlinestudent.model.CargoUser;
import com.macaroni.projectonlinestudent.model.Quiz;
import com.macaroni.projectonlinestudent.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuizController {
    @Autowired
    private QuizRepository quizRepository;

    //TODO: Permitir criar quiz e associar perguntas a ele

    @GetMapping("/quiz/index/mentor")
    public ResponseEntity<List<Quiz>> indexQuizByUser(@RequestBody User user){
        if(user == null || user.getCargo().equals(CargoUser.ALUNO)){
            return ResponseEntity.status(403).build();
        }
        List<Quiz>indexQuiz = quizRepository.findQuizzesByMentor(user);

        return ResponseEntity.ok(indexQuiz);
    }
}
