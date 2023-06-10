package com.macaroni.projectonlinestudent;

import com.macaroni.projectonlinestudent.Model.CargoUser;
import com.macaroni.projectonlinestudent.Model.Submissao;
import com.macaroni.projectonlinestudent.Model.User;
import com.macaroni.projectonlinestudent.Repository.SubmissaoRepository;
import com.macaroni.projectonlinestudent.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class MentorController {
    @Autowired
    private SubmissaoRepository submissaoRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("mentor/aluno/{id}/submissao")
    public ResponseEntity<List<Submissao>>listAllLastTenSub(@PathVariable("id")Long id){
        Optional<List<Submissao>>submissaos = submissaoRepository.findFirst10ByAluno_IdOrderByIdDesc(id);
        if(submissaos.isPresent()){
            return ResponseEntity.ok().body(submissaos.get());
        }
        return ResponseEntity.noContent().build();
    }
    @GetMapping("mentor/aluno")
    public ResponseEntity<List<User>>listAllUsers(){
        Optional<List<User>>listaAluno = userRepository.findUsersByCargo(CargoUser.ALUNO);
        if(listaAluno.isPresent()){
            return ResponseEntity.ok().body(listaAluno.get());
        }
        return ResponseEntity.noContent().build();
    }
}
