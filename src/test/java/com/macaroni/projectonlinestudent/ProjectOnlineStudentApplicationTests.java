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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
class ProjectOnlineStudentApplicationTests {

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testLoginwithEmailAndPassword(){
        try{

            User logginUser = userRepository.findUserByEmail("admin@email.com");
            assertNotNull(logginUser,"Existe um email?");

            boolean result = securityConfig.passwordEncoder().matches("sudo",logginUser.getPassword());
            assertTrue("Senha correta?",result);
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
    }

}
