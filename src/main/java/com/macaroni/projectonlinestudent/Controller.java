package com.macaroni.projectonlinestudent;

import com.macaroni.projectonlinestudent.DTO.LoginDTO;
import com.macaroni.projectonlinestudent.Repository.UserRepository;
import com.macaroni.projectonlinestudent.config.SecurityConfig;
import com.macaroni.projectonlinestudent.model.CargoUser;
import com.macaroni.projectonlinestudent.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
public class Controller {
    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/public/login")
    public ResponseEntity<User> loginPage(@RequestBody LoginDTO loginDTO){
        User logginUser = userRepository.findUserByEmail(loginDTO.email());

        if(logginUser == null || !securityConfig.passwordEncoder().matches(loginDTO.password(),logginUser.getPassword())){
            System.out.println("ERROR: User or password invalid.");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(logginUser);
    }


    @GetMapping("/user")
    public String logado(){
        return "TÁ LOGADO";
    }

    //200 se o usuário for salvo com sucesso, 400 se já existir um usuário
    @PostMapping("/public/register")
    public ResponseEntity<?> cadastrar(@RequestBody User user){
        if(userRepository.findUserByEmail(user.getEmail()) != null || user.getEmail().isBlank()){
            return ResponseEntity.badRequest().build();
        }

        user.setSenha(securityConfig.passwordEncoder().encode(user.getSenha()));
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

}
