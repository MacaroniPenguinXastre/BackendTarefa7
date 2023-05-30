package com.macaroni.projectonlinestudent;

import com.macaroni.projectonlinestudent.DTO.PerguntaMentorDTO;
import com.macaroni.projectonlinestudent.Repository.PerguntaRepository;
import com.macaroni.projectonlinestudent.model.CargoUser;
import com.macaroni.projectonlinestudent.model.Pergunta;
import com.macaroni.projectonlinestudent.model.Quiz;
import com.macaroni.projectonlinestudent.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PerguntaController {

    @Autowired
    private PerguntaRepository perguntaRepository;



    //Mostra todas as perguntas de um usuário
    @GetMapping("/pergunta/indexByUser")
    public ResponseEntity<List<Pergunta>> showAllQuestByUser(@RequestBody User user){
        if(user == null || user.getCargo().equals(CargoUser.ALUNO)){
            System.out.println("Usuário não tem autorização");
            return ResponseEntity.status(403).build();
        }

        List<Pergunta>perguntaUserList = perguntaRepository.findPerguntasByMentor(user);
        return ResponseEntity.ok().body(perguntaUserList);
    }
    @PostMapping("/pergunta/create")
    public ResponseEntity<?> createQuest(@RequestBody Pergunta pergunta){
        if(pergunta == null || pergunta.getMentor() == null){
            return ResponseEntity.badRequest().build();
        }
        perguntaRepository.saveAndFlush(pergunta);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/pergunta/delete")
    public ResponseEntity<String> deleteQuest(@RequestBody PerguntaMentorDTO perguntaMentorDTO){
        if(perguntaMentorDTO == null){
            return ResponseEntity.badRequest().body("Null parameters");
        }
        if(perguntaMentorDTO.pergunta().getQuizAssociados().size() > 0){
            return ResponseEntity.status(409).body("Question is associated with one or more Quizzes.");
        }
        perguntaRepository.delete(perguntaMentorDTO.pergunta());
        return ResponseEntity.ok().body("Deleted successfully");
    }

}
