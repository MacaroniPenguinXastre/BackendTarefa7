package com.macaroni.projectonlinestudent;

import com.macaroni.projectonlinestudent.Repository.PerguntaRepository;
import com.macaroni.projectonlinestudent.Repository.UserRepository;
import com.macaroni.projectonlinestudent.config.SecurityConfig;
import com.macaroni.projectonlinestudent.model.CargoUser;
import com.macaroni.projectonlinestudent.model.Pergunta;
import com.macaroni.projectonlinestudent.model.Quiz;
import com.macaroni.projectonlinestudent.model.User;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GenerateController {
    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private PerguntaRepository perguntaRepository;

    @Autowired
    private UserRepository userRepository;

    User alunoA = new User("Lucas","lucas@email.com","123", CargoUser.ALUNO);
    User alunoB = new User("Batata","batata@email.com","1234",CargoUser.ALUNO);
    User professorA = new User("Xastre","xastre@email.com","2525",CargoUser.MENTOR);
    User professorB = new User("Mano Bincas","bincos@email.com","777",CargoUser.MENTOR);
    User empresaA = new User("MAECROSOFT","mancos@soft.com","xpmelhorjanela",CargoUser.EMPRESA_PARCEIRA);
    User empresaB = new User("Turma do Pagode FC","pagode@email.com","deixaacontecer",CargoUser.EMPRESA_PARCEIRA);

    @GetMapping("/generateUser")
    public ResponseEntity<?> createUser(){

        alunoA.setSenha(securityConfig.passwordEncoder().encode(alunoA.getSenha()));
        alunoB.setSenha(securityConfig.passwordEncoder().encode(alunoB.getSenha()));
        professorA.setSenha(securityConfig.passwordEncoder().encode(alunoB.getSenha()));
        professorB.setSenha(securityConfig.passwordEncoder().encode(professorB.getPassword()));
        empresaA.setSenha(securityConfig.passwordEncoder().encode(empresaA.getSenha()));
        empresaB.setSenha(securityConfig.passwordEncoder().encode(empresaB.getSenha()));

        userRepository.save(alunoA);
        userRepository.save(alunoB);
        userRepository.save(professorA);
        userRepository.save(professorB);
        userRepository.save(empresaA);
        userRepository.save(empresaB);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/generateQuestion")
    public ResponseEntity<?>generateQuestion(){

        Pergunta pergunta = new Pergunta();
        pergunta.setEnunciado("Em que país o Sato nasceu ?");
        pergunta.setAlternativaA("Japão");
        pergunta.setAlternativaB("Austrália");
        pergunta.setAlternativaC("Rio de Janeiro");
        pergunta.setAlternativaD("Lôndres");
        pergunta.setAlternativaCorreta('B');
        pergunta.setMentor(professorA);

        perguntaRepository.save(pergunta);

        Pergunta perguntaB = new Pergunta();
        perguntaB.setEnunciado("Quão baixo é o Veigar ?");
        perguntaB.setAlternativaA("Muito");
        perguntaB.setAlternativaB("Pouco");
        perguntaB.setAlternativaC("Anão");
        perguntaB.setAlternativaD("Nível Sato");
        perguntaB.setAlternativaCorreta('D');
        perguntaB.setMentor(professorB);

        perguntaRepository.save(pergunta);
        perguntaRepository.save(perguntaB);

        return ResponseEntity.ok().build();
    }

}
