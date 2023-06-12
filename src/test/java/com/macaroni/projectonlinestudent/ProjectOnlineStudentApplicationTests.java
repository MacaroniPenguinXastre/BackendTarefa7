package com.macaroni.projectonlinestudent;

import com.macaroni.projectonlinestudent.DTO.LoginDTO;
import com.macaroni.projectonlinestudent.Model.User;
import com.macaroni.projectonlinestudent.Repository.UserRepository;
import com.macaroni.projectonlinestudent.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
class ProjectOnlineStudentApplicationTests {

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testLoginwithEmailAndPassword(LoginDTO loginDTO){
        try{
            User logginUser = userRepository.findUserByEmail(loginDTO.email());
            assertNull(logginUser,"Existe um email?");

            boolean result = securityConfig.passwordEncoder().matches(loginDTO.password(),logginUser.getPassword());
            assertTrue("Senha correta?",result);
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
    }

}
