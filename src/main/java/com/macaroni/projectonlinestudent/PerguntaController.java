package com.macaroni.projectonlinestudent;

import com.macaroni.projectonlinestudent.DTO.PerguntaMentorDTO;
import com.macaroni.projectonlinestudent.Repository.PerguntaRepository;
import com.macaroni.projectonlinestudent.Model.CargoUser;
import com.macaroni.projectonlinestudent.Model.Pergunta;
import com.macaroni.projectonlinestudent.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PerguntaController {

    @Autowired
    private PerguntaRepository perguntaRepository;

    @GetMapping("/perguntas")
    public ResponseEntity<List<Pergunta>> showAllQuests(@RequestBody User user){
        try{
            if(!user.getCargo().equals(CargoUser.ADM)){
                System.out.println("Usuário não tem autorização");
                return ResponseEntity.status(403).build();
            }

            List<Pergunta>perguntaUserList = perguntaRepository.findAll();
            return ResponseEntity.ok().body(perguntaUserList);
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping("/perguntas")
    public ResponseEntity<?> createQuest(@RequestBody Pergunta pergunta){
        if(pergunta == null || pergunta.getAdmCriador() == null){
            return ResponseEntity.badRequest().build();
        }
        perguntaRepository.saveAndFlush(pergunta);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/perguntas")
    public ResponseEntity<String> deleteQuest(@RequestBody PerguntaMentorDTO perguntaMentorDTO){
        try {
            if(perguntaMentorDTO.pergunta().getQuizAssociados().size() > 0){
                return ResponseEntity.status(409).body("Question is associated with one or more Quizzes.");
            }
            perguntaRepository.delete(perguntaMentorDTO.pergunta());
            return ResponseEntity.ok().body("Deleted successfully");
        }catch (NullPointerException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

}
