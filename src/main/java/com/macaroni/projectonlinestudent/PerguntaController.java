package com.macaroni.projectonlinestudent;

import com.macaroni.projectonlinestudent.Repository.PerguntaRepository;
import com.macaroni.projectonlinestudent.model.CargoUser;
import com.macaroni.projectonlinestudent.model.Pergunta;
import com.macaroni.projectonlinestudent.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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


}
