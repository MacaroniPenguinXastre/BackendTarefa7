package com.macaroni.projectonlinestudent;

import com.macaroni.projectonlinestudent.DTO.TreinamentoDTO;
import com.macaroni.projectonlinestudent.Repository.TreinamentoRepository;
import com.macaroni.projectonlinestudent.Repository.UserRepository;
import com.macaroni.projectonlinestudent.Service.TreinamentoService;
import com.macaroni.projectonlinestudent.Service.UserDetailServiceImpl;
import com.macaroni.projectonlinestudent.config.SecurityConfig;
import com.macaroni.projectonlinestudent.Model.Treinamento;
import com.macaroni.projectonlinestudent.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    private TreinamentoRepository treinamentoRepository;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private TreinamentoService treinamentoService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfig securityConfig;

    @GetMapping("/treinamento/index")
    public ResponseEntity<List<Treinamento>> showAllTreinamentos(@RequestBody TreinamentoDTO treinamentoDTO){
        if(treinamentoDTO == null){
            return ResponseEntity.badRequest().build();
        }
        if(!userDetailService.isAdm(treinamentoDTO.user())){
            return ResponseEntity.status(403).build();
        }
        List<Treinamento>allTreinamentos = treinamentoRepository.findAll();
        return ResponseEntity.ok().body(allTreinamentos);
    }

    @PostMapping("/adm/registerUser")
    public ResponseEntity<?> cadastrar(@RequestBody User user){
        if(userRepository.findUserByEmail(user.getEmail()) != null || user.getEmail().isBlank()){
            System.out.println("ERROR: Email already exists");
            return ResponseEntity.badRequest().build();
        }
        user.setSenha(securityConfig.passwordEncoder().encode(user.getSenha()));
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/treinamento/create")
    public ResponseEntity<?> createTreinamento(@RequestBody TreinamentoDTO treinamentoDTO){
        if(treinamentoDTO == null){
            return ResponseEntity.badRequest().build();
        }

        Treinamento newTreinamento = treinamentoDTO.treinamento();

        if(!userDetailService.isAdm(treinamentoDTO.user())){
            return ResponseEntity.status(403).build();
        }

        if(treinamentoService.checkDate(treinamentoDTO.treinamento().getDataInicioTreinamento(), treinamentoDTO.treinamento().getDataFimTreinamento())
        || treinamentoService.checkDate(treinamentoDTO.treinamento().getDataInicioInscricao(), treinamentoDTO.treinamento().getDataFimInscricao())){
            return ResponseEntity.status(409).build();
        }

        newTreinamento = treinamentoService.convertToSPZone(newTreinamento);
        treinamentoRepository.save(newTreinamento);

        return ResponseEntity.ok().build();
    }


}
