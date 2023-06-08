package com.macaroni.projectonlinestudent;

import com.macaroni.projectonlinestudent.DTO.LoginDTO;
import com.macaroni.projectonlinestudent.Repository.CursoRepository;
import com.macaroni.projectonlinestudent.Repository.UserRepository;
import com.macaroni.projectonlinestudent.config.SecurityConfig;
import com.macaroni.projectonlinestudent.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {
    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping("/public/login")
    public ResponseEntity<User> loginPage(@RequestBody LoginDTO loginDTO){
        User logginUser = userRepository.findUserByEmail(loginDTO.email());

        if(logginUser == null || !securityConfig.passwordEncoder().matches(loginDTO.password(),logginUser.getPassword())){
            System.out.println("ERROR: User or password invalid.");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(logginUser);
    }

    //200 se o usuário for salvo com sucesso, 400 se já existir um usuário
    @PostMapping("/public/register")
    public ResponseEntity<?> cadastrar(@RequestBody User user){
        try {
            if(userRepository.findUserByEmail(user.getEmail()) != null || user.getEmail().isBlank()){
                return ResponseEntity.badRequest().build();
            }

            user.setCargo(CargoUser.ALUNO);
            user.setSenha(securityConfig.passwordEncoder().encode(user.getSenha()));
            userRepository.save(user);
        }
        catch (NullPointerException e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }


    @GetMapping("/cursos")
    public ResponseEntity<List<Curso>> allCourses(@RequestBody User user){
        try{
            if(!user.getCargo().equals(CargoUser.ADM)){
                return ResponseEntity.status(403).build();
            }
            List<Curso>cursoList = cursoRepository.findAll();
            return ResponseEntity.ok().body(cursoList);

        }catch (NullPointerException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/cursos")
    public ResponseEntity<?> createCourses(@RequestBody Curso curso){
        if(curso == null){
            return ResponseEntity.badRequest().build();
        }
        cursoRepository.save(curso);
        return ResponseEntity.ok().build();
    }

}
