package com.macaroni.projectonlinestudent;

import com.macaroni.projectonlinestudent.DTO.TreinamentoDTO;
import com.macaroni.projectonlinestudent.Model.CargoUser;
import com.macaroni.projectonlinestudent.Repository.TreinamentoRepository;
import com.macaroni.projectonlinestudent.Repository.UserRepository;
import com.macaroni.projectonlinestudent.Service.TreinamentoService;
import com.macaroni.projectonlinestudent.Service.UserDetailServiceImpl;
import com.macaroni.projectonlinestudent.config.SecurityConfig;
import com.macaroni.projectonlinestudent.Model.Treinamento;
import com.macaroni.projectonlinestudent.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/adm/treinamentos")
    public ResponseEntity<List<Treinamento>> showAllTreinamentos(@RequestBody User user){
        if(user == null){
            return ResponseEntity.badRequest().build();
        }
        if(!user.getCargo().equals(CargoUser.ADM)){
            return ResponseEntity.status(403).build();
        }
        List<Treinamento>allTreinamentos = treinamentoRepository.findAll();
        return ResponseEntity.ok().body(allTreinamentos);
    }

    @GetMapping("adm/treinamentos/{id}")
    public ResponseEntity<Treinamento>getTreinamento(@PathVariable Long id){
        Optional<Treinamento> treinamento = treinamentoRepository.findById(id);
        if(treinamento.isPresent()){
            return ResponseEntity.ok().body(treinamento.get());
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/adm/treinamentos")
    public ResponseEntity<?> createTreinamento(@RequestBody TreinamentoDTO treinamentoDTO){
        if(treinamentoDTO == null){
            return ResponseEntity.badRequest().build();
        }

        try{
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
        catch (NullPointerException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }


    }

    @PostMapping("/adm/users")
    public ResponseEntity<?> cadastrar(@RequestBody User user) {
        try {
            User registeredUser = userRepository.findUserByEmail(user.getEmail());
            if (registeredUser != null) {
                return ResponseEntity.status(409).build();
            }
            user.setSenha(securityConfig.passwordEncoder().encode(user.getSenha()));
            registeredUser = user;

            userRepository.save(registeredUser);

            return ResponseEntity.ok().build();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

}
