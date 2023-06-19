package com.macaroni.projectonlinestudent;

import com.macaroni.projectonlinestudent.Model.CargoUser;
import com.macaroni.projectonlinestudent.Model.VagasEmprego;
import com.macaroni.projectonlinestudent.Repository.TreinamentoRepository;
import com.macaroni.projectonlinestudent.Repository.UserRepository;
import com.macaroni.projectonlinestudent.Repository.VagasEmpregoRepository;
import com.macaroni.projectonlinestudent.Service.TreinamentoService;
import com.macaroni.projectonlinestudent.Service.UserDetailServiceImpl;
import com.macaroni.projectonlinestudent.Service.VagasEmpregoService;
import com.macaroni.projectonlinestudent.config.SecurityConfig;
import com.macaroni.projectonlinestudent.Model.Treinamento;
import com.macaroni.projectonlinestudent.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    @Autowired
    private VagasEmpregoService vagasEmpregoService;

    @Autowired
    private VagasEmpregoRepository vagasEmpregoRepository;

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

    @PutMapping("/admin/{id}/vagas/{vagaID}")
    public ResponseEntity<?>updateVaga(@PathVariable("id")Long idUser,@PathVariable("vagaID")Long vagaID,
                                       @RequestBody VagasEmprego vagasEmprego){
        try {
            Optional<VagasEmprego>vaga = vagasEmpregoRepository.findById(vagaID);
            Optional<User>user = userRepository.findById(idUser);

            if(user.isEmpty() || vaga.isEmpty()){
                return ResponseEntity.badRequest().build();
            }

            if(!user.get().getCargo().equals(CargoUser.ADM)){
                return ResponseEntity.status(403).build();
            }

            vagasEmpregoService.updateVagasEmprego(vaga.get(),vagasEmprego);
            vagasEmpregoRepository.saveAndFlush(vaga.get());
            return ResponseEntity.ok().build();
        }
        catch(NullPointerException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/adm/treinamentos/{id}")
    public ResponseEntity<Treinamento>getTreinamento(@PathVariable Long id){
        Optional<Treinamento> treinamento = treinamentoRepository.findById(id);
        if(treinamento.isPresent()){
            return ResponseEntity.ok().body(treinamento.get());
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/admin/{id}/vagas/{vagaID}")
    public ResponseEntity<?>deleteVaga(@PathVariable("id")Long idUser,@PathVariable("vagaID")Long vagaID){
        try {
            Optional<User>admin = userRepository.findById(idUser);

            Optional<VagasEmprego>vagasEmprego = vagasEmpregoRepository.findById(vagaID);
            if(admin.isEmpty() || vagasEmprego.isEmpty()){
                return ResponseEntity.badRequest().build();
            }

            if(!admin.get().getCargo().equals(CargoUser.ADM)){
                return ResponseEntity.status(403).build();
            }

            vagasEmpregoRepository.delete(vagasEmprego.get());
            return ResponseEntity.ok().build();
        }catch (NullPointerException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/adm/{id}/treinamentos")
    public ResponseEntity<?> createTreinamento(@RequestBody Treinamento treinamento,@PathVariable("id")Long id){
        if(treinamento == null){
            return ResponseEntity.badRequest().build();
        }
        try{
            Optional<User>user = userRepository.findById(id);

            if(user.isEmpty() ||!userDetailService.isAdm(user.get())){
                return ResponseEntity.status(403).build();
            }
            if(treinamentoService.checkDate(treinamento.getDataInicioTreinamento(), treinamento.getDataFimTreinamento())
                    || treinamentoService.checkDate(treinamento.getDataInicioInscricao(),treinamento.getDataFimInscricao())){
                return ResponseEntity.status(409).build();
            }

            treinamentoRepository.save(treinamento);
            return ResponseEntity.ok().build();
        }

        catch (NullPointerException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/adm/{idUser}/treinamentos/{id}")
    public ResponseEntity<?> updateTreinamento(@RequestBody Treinamento treinamento,@PathVariable("idUser")Long userID,
                                               @PathVariable("id")Long treinoID){
        try{
            Optional<User>user = userRepository.findById(userID);
            Optional<Treinamento>treinamentoExistente = treinamentoRepository.findById(treinoID);

            if(treinamento == null){
                return ResponseEntity.badRequest().build();
            }

            if(user.isEmpty() || treinamentoExistente.isEmpty()){
                return ResponseEntity.notFound().build();
            }

            if(!treinamentoService.isPossibleToChange(treinamentoExistente.get())){
                return ResponseEntity.status(409).build();
            }
            treinamentoService.updateTreinamento(treinamentoExistente.get(),treinamento);
            treinamentoRepository.saveAndFlush(treinamentoExistente.get());
            return ResponseEntity.ok().build();
        }
        catch(DataIntegrityViolationException e){
            e.printStackTrace();
            return ResponseEntity.status(409).build();
        }

    }

    @DeleteMapping("/adm/{id}/treinamentos/{treinoID}")
    public ResponseEntity<?>deleteTreinamento(@PathVariable("id")Long id,@PathVariable("treinoID")Long treinoID){
        try {
            Optional<User>user = userRepository.findById(id);
            Optional<Treinamento>treinamento = treinamentoRepository.findById(treinoID);

            if(user.isEmpty() || treinamento.isEmpty()){
                return ResponseEntity.badRequest().build();
            }
            treinamentoRepository.delete(treinamento.get());
            return ResponseEntity.ok().build();
        }
        catch (DataIntegrityViolationException e){
            e.printStackTrace();
            return ResponseEntity.status(409).build();
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
            if(user.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(user.get());

        } catch (NullPointerException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
