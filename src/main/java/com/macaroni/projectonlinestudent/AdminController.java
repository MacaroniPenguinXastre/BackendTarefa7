package com.macaroni.projectonlinestudent;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @GetMapping("/admin")
    public String testeAdm(){
        return "O ADM TÁ ON";
    }
}
