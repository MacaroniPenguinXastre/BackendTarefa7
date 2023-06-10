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
import java.util.Optional;

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

    @GetMapping("/perguntas/{id}")
    public ResponseEntity<Pergunta>questDetails(@PathVariable("id")Long id){
        Optional<Pergunta>pergunta = perguntaRepository.findById(id);
        if(pergunta.isPresent()){
            return ResponseEntity.ok().body(pergunta.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/perguntas")
    public ResponseEntity<?> createQuest(@RequestBody Pergunta pergunta){
        try {
            if(!pergunta.getAdmCriador().getCargo().equals(CargoUser.ADM)){
                return ResponseEntity.status(403).build();
            }
            return ResponseEntity.ok().build();
        }catch (NullPointerException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/perguntas")
    public ResponseEntity<String> deleteQuest(@RequestBody PerguntaMentorDTO perguntaMentorDTO){
        try {
            if(perguntaMentorDTO.pergunta().getQuizAssociados()!= null ||perguntaMentorDTO.pergunta().getQuizAssociados().size() > 0){
                return ResponseEntity.status(409).body("Question is associated with one or more Quizzes.");
            }
            perguntaRepository.delete(perguntaMentorDTO.pergunta());
            return ResponseEntity.ok().body("Deleted successfully");
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

}
