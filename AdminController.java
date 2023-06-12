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
    public ResponseEntity<List<Treinamento>> showAllTreinamentos(){
        try {
            List<Treinamento>allTreinamentos = treinamentoRepository.findAll();
            return ResponseEntity.ok().body(allTreinamentos);
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("adm/treinamentos/{id}")
    public ResponseEntity<Treinamento>getTreinamento(@PathVariable Long id){
        Optional<Treinamento> treinamento = treinamentoRepository.findById(id);
        if(treinamento.isPresent()){
            return ResponseEntity.ok().body(treinamento.get());
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/adm/{id}/treinamentos")
    public ResponseEntity<?> createTreinamento(@RequestBody Treinamento treinamento,@PathVariable("id")Long id){
        if(treinamento == null){
            return ResponseEntity.badRequest().build();
        }
        try{
            Treinamento newTreinamento = treinamento;
            Optional<User>user = userRepository.findById(id);

            if(user.isEmpty() ||!userDetailService.isAdm(user.get())){
                return ResponseEntity.status(403).build();
            }
            if(treinamentoService.checkDate(treinamento.getDataInicioTreinamento(), treinamento.getDataFimTreinamento())
                    || treinamentoService.checkDate(treinamento.getDataInicioInscricao(),treinamento.getDataFimInscricao())){
                return ResponseEntity.status(409).build();
            }

            treinamentoRepository.save(newTreinamento);
            return ResponseEntity.ok().build();
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/adm/users")
    public ResponseEntity<?> cadastrar(@RequestBody User user){
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

    @GetMapping("/adm/users/{id}")
    public ResponseEntity<User> userDetail(@PathVariable("id")Long id){
        try {
            Optional<User>user = userRepository.findById(id);
            if (user.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(user.get());

        } catch (NullPointerException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
