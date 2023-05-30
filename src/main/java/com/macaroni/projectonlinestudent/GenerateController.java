package com.macaroni.projectonlinestudent;

import com.macaroni.projectonlinestudent.Repository.UserRepository;
import com.macaroni.projectonlinestudent.config.SecurityConfig;
import com.macaroni.projectonlinestudent.model.CargoUser;
import com.macaroni.projectonlinestudent.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenerateController {
    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/generateUser")
    public ResponseEntity<?> createUser(){
        User alunoA = new User("Lucas","lucas@email.com","123", CargoUser.ALUNO);
        alunoA.setSenha(securityConfig.passwordEncoder().encode(alunoA.getSenha()));
        userRepository.save(alunoA);

        User alunoB = new User("Batata","batata@email.com","1234",CargoUser.ALUNO);
        alunoB.setSenha(securityConfig.passwordEncoder().encode(alunoB.getSenha()));
        userRepository.save(alunoB);

        User professorA = new User("Xastre","xastre@email.com","2525",CargoUser.MENTOR);
        professorA.setSenha(securityConfig.passwordEncoder().encode(alunoB.getSenha()));
        userRepository.save(professorA);

        User professorB = new User("Mano Bincas","bincos@email.com","777",CargoUser.MENTOR);
        professorB.setSenha(securityConfig.passwordEncoder().encode(professorB.getPassword()));
        userRepository.save(professorB);

        User empresaA = new User("MAECROSOFT","mancos@soft.com","xpmelhorjanela",CargoUser.EMPRESA_PARCEIRA);
        empresaA.setSenha(securityConfig.passwordEncoder().encode(empresaA.getSenha()));
        userRepository.save(empresaA);

        User empresaB = new User("Turma do Pagode FC","pagode@email.com","deixaacontecer",CargoUser.EMPRESA_PARCEIRA);
        empresaB.setSenha(securityConfig.passwordEncoder().encode(empresaB.getSenha()));
        userRepository.save(empresaB);

        return ResponseEntity.ok().build();
    }

}
