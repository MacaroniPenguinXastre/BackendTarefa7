package com.macaroni.projectonlinestudent;

import com.macaroni.projectonlinestudent.DTO.LoginDTO;
import com.macaroni.projectonlinestudent.DTO.TreinamentoDTO;
import com.macaroni.projectonlinestudent.Repository.CursoRepository;
import com.macaroni.projectonlinestudent.Repository.UserRepository;
import com.macaroni.projectonlinestudent.config.SecurityConfig;
import com.macaroni.projectonlinestudent.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        try{
            User logginUser = userRepository.findUserByEmail(loginDTO.email());
            if(logginUser == null || !securityConfig.passwordEncoder().matches(loginDTO.password(),logginUser.getPassword())){
                System.out.println("ERROR: User or password invalid.");
                return ResponseEntity.badRequest().build();
            }

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(logginUser);

        }
        catch(NullPointerException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    //200 se o usuário for salvo com sucesso, 400 se já existir um usuário
    @PostMapping("/public/register")
    public ResponseEntity<?> cadastrar(@RequestBody User user){
        try{
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


    @GetMapping("/adm/{id}/cursos")
    public ResponseEntity<List<Curso>> allCourses(@PathVariable("id")Long id){
        try{
            User user = userRepository.getReferenceById(id);

            if(user == null || !user.getCargo().equals(CargoUser.ADM)){
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
        try {
            if(curso == null){
                throw new NullPointerException();
            }

            List<Treinamento>treinamentos = new ArrayList<>(0);
            curso.setTreinamentosCurso(treinamentos);
            cursoRepository.save(curso);

        }catch (NullPointerException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/cursos/{id}")
    public ResponseEntity<Curso>cursoDetails(@PathVariable("id")Long id){
        Optional<Curso>curso = cursoRepository.findById(id);
        if(curso.isPresent()){
            return ResponseEntity.ok().body(curso.get());
        }
        return ResponseEntity.badRequest().build();
    }
    @DeleteMapping("/cursos/{id}")
    public ResponseEntity<?>eraseCurso(@PathVariable("id")Long id,@RequestBody User user){
        Optional<User>isAdm = Optional.of(user);
        if(isAdm.isEmpty() || !isAdm.get().getCargo().equals(CargoUser.ADM)){
            return ResponseEntity.status(403).build();
        }

        Optional<Curso>curso = cursoRepository.findById(id);
        if(curso.isPresent()){
            if(!curso.get().getTreinamentosCurso().isEmpty()){
                return ResponseEntity.status(409).build();
            }
            cursoRepository.deleteById(curso.get().getId());
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }
}
