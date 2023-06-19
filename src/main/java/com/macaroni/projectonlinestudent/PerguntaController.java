package com.macaroni.projectonlinestudent;

import com.macaroni.projectonlinestudent.DTO.PerguntaMentorDTO;
import com.macaroni.projectonlinestudent.Repository.PerguntaRepository;
import com.macaroni.projectonlinestudent.Model.CargoUser;
import com.macaroni.projectonlinestudent.Model.Pergunta;
import com.macaroni.projectonlinestudent.Model.User;
import com.macaroni.projectonlinestudent.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class PerguntaController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PerguntaRepository perguntaRepository;

    @GetMapping("/adm/{id}/perguntas")
    public ResponseEntity<List<Pergunta>> showAllQuests(@PathVariable("id")Long id){
        try{
            Optional<User>user = userRepository.findById(id);
            if(user.isEmpty()){
                throw new NullPointerException();
            }

            if(!user.get().getCargo().equals(CargoUser.ADM)){
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
            pergunta.setQuizAssociados(new ArrayList<>(0));
            perguntaRepository.save(pergunta);
            return ResponseEntity.ok().build();
        }catch (NullPointerException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/adm/{id}/perguntas/{idPergunta}")
    public ResponseEntity<String> deleteQuest(@PathVariable("id")Long idAdm,@PathVariable("idPergunta")Long idPergunta){
        try {
            Optional<User>user = userRepository.findById(idAdm);
            Optional<Pergunta>pergunta = perguntaRepository.findById(idPergunta);
            if(user.isEmpty() || pergunta.isEmpty()){
                throw new NullPointerException();
            }
            perguntaRepository.delete(pergunta.get());
            return ResponseEntity.ok().body("Deleted successfully");
        }
        catch (DataIntegrityViolationException | NullPointerException e){
            e.printStackTrace();
            return ResponseEntity.status(409).body("Question is associated with one or more Quizzes.");
        }
    }

}
