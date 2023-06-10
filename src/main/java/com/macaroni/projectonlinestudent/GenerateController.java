package com.macaroni.projectonlinestudent;

import com.macaroni.projectonlinestudent.Repository.PerguntaRepository;
import com.macaroni.projectonlinestudent.Repository.UserRepository;
import com.macaroni.projectonlinestudent.config.SecurityConfig;
import com.macaroni.projectonlinestudent.Model.CargoUser;
import com.macaroni.projectonlinestudent.Model.Pergunta;
import com.macaroni.projectonlinestudent.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class GenerateController {

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private PerguntaRepository perguntaRepository;

    @Autowired
    private UserRepository userRepository;

    List<User>users = Arrays.asList(new User("ADMIN","admin@email.com","sudo",CargoUser.ADM),
            new User("Lucas","lucas@email.com","123", CargoUser.ALUNO),
            new User("Batata","batata@email.com","1234",CargoUser.ALUNO),
            new User("Xastre","xastre@email.com","2525",CargoUser.MENTOR),
            new User("Mano Bincas","bincos@email.com","777",CargoUser.MENTOR),
            new User("MAECROSOFT","mancos@soft.com","xpmelhorjanela",CargoUser.EMPRESA_PARCEIRA),
            new User("Turma do Pagode FC","pagode@email.com","deixaacontecer",CargoUser.EMPRESA_PARCEIRA)
    );

    @GetMapping("/generateUser")
    public ResponseEntity<?> createUser(){
        for(int i = 0; i < users.size();i++){
            users.get(i).setSenha(securityConfig.passwordEncoder().encode(users.get(i).getSenha()));
        }
        userRepository.saveAll(users);
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
        pergunta.setAdmCriador(userRepository.findUserByEmail("admin@email.com"));

        Pergunta perguntaB = new Pergunta();
        perguntaB.setEnunciado("Quão baixo é o Veigar ?");
        perguntaB.setAlternativaA("Muito");
        perguntaB.setAlternativaB("Pouco");
        perguntaB.setAlternativaC("Anão");
        perguntaB.setAlternativaD("Nível Sato");
        perguntaB.setAlternativaCorreta('D');
        perguntaB.setAdmCriador(userRepository.findUserByEmail("admin@email.com"));

        perguntaRepository.save(pergunta);
        perguntaRepository.save(perguntaB);

        return ResponseEntity.ok().build();
    }

}
