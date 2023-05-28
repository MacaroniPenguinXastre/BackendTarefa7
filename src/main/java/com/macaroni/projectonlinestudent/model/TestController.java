package com.macaroni.projectonlinestudent.model;

import com.macaroni.projectonlinestudent.Repository.UserRepository;
import com.macaroni.projectonlinestudent.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/generateUser")
    public ResponseEntity<?> createUser(){
        User alunoA = new User("Lucas","lucas@email.com","123",CargoUser.ALUNO);
        alunoA.setSenha(securityConfig.passwordEncoder().encode(alunoA.getSenha()));
        userRepository.save(alunoA);

        User alunoB = new User("Batata","batata@email.com","1234",CargoUser.ALUNO);
        alunoB.setSenha(securityConfig.passwordEncoder().encode(alunoB.getSenha()));
        userRepository.save(alunoB);

        User professorA = new User("Xastre","xastre@email.com","2525",CargoUser.MENTOR);
        professorA.setSenha(securityConfig.passwordEncoder().encode(alunoB.getSenha()));
        userRepository.save(professorA);



        return ResponseEntity.ok().build();
    }
}
