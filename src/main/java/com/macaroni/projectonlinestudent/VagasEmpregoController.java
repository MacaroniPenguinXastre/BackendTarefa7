package com.macaroni.projectonlinestudent;

import com.macaroni.projectonlinestudent.Model.*;
import com.macaroni.projectonlinestudent.Repository.AlunoInscricaoRepository;
import com.macaroni.projectonlinestudent.Repository.UserRepository;
import com.macaroni.projectonlinestudent.Repository.VagasEmpregoRepository;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class VagasEmpregoController {
    @Autowired
    private VagasEmpregoRepository vagasEmpregoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlunoInscricaoRepository alunoInscricaoRepository;


    @GetMapping("/parceiro/{id}/vagas")
    public ResponseEntity<List<VagasEmprego>>listAllVagas(@PathVariable("id")Long id){
        try {
            Optional<User>user = userRepository.findById(id);
            if(user.isEmpty()){
                return ResponseEntity.badRequest().build();
            }

            if(!user.get().getCargo().equals(CargoUser.EMPRESA_PARCEIRA) && !user.get().getCargo().equals(CargoUser.ADM)){
                return ResponseEntity.status(403).build();
            }
            List<VagasEmprego>vagasEmpregos = vagasEmpregoRepository.findAll();
            if(vagasEmpregos.isEmpty()){
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok().body(vagasEmpregos);
        }catch (NullPointerException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/parceiro/{id}/vagas/{vagaID}")
    public ResponseEntity<?>deleteVaga(@PathVariable("id")Long idUser,@PathVariable("vagaID")Long vagaID){
        try {
            Optional<User>user = userRepository.findById(idUser);
            Optional<VagasEmprego>vagasEmprego = vagasEmpregoRepository.findById(vagaID);
            if(user.isEmpty() || vagasEmprego.isEmpty()){
                return ResponseEntity.badRequest().build();
            }
            //Se a vaga a ser excluída NÃO pertencer ao mentor, é barrado
            if(!vagasEmprego.get().getEmpresa().equals(user.get())){
                return ResponseEntity.status(403).build();
            }

            return ResponseEntity.ok().build();
        }catch (NullPointerException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    //Retorna apenas Empresas Parceiras
    @GetMapping("/users/parceiros/vagas")
    public ResponseEntity<List<User>>showAllPartners(){
        Optional<List<User>>parceiros = userRepository.findUsersByCargo(CargoUser.EMPRESA_PARCEIRA);
        if(parceiros.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(parceiros.get());
    }

    @GetMapping("/vagas/{id}")
    public ResponseEntity<VagasEmprego>vagaEmpregoDetails(@PathVariable("id")Long id) {
        Optional<VagasEmprego>vagasEmprego = vagasEmpregoRepository.findById(id);
        if(vagasEmprego.isPresent()){
            return ResponseEntity.ok().body(vagasEmprego.get());
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/vagas")
    public ResponseEntity<?>createVagaEmprego(@RequestBody VagasEmprego vagasEmprego){
        try {
            if(!vagasEmprego.getEmpresa().getCargo().equals(CargoUser.EMPRESA_PARCEIRA) &&
                    !vagasEmprego.getEmpresa().getCargo().equals(CargoUser.ADM)){
                return ResponseEntity.status(403).build();
            }
            if(vagasEmprego.getTreinamentoRequisito() == null){
                return ResponseEntity.badRequest().build();
            }
            List<User>candidatos = new ArrayList<>(0);
            vagasEmprego.setCandidatos(candidatos);
            vagasEmpregoRepository.save(vagasEmprego);
            return ResponseEntity.ok().build();
        }
        catch (NullPointerException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/aluno/{alunoID}/vagas/{vagaID}")
    public ResponseEntity<?>candidateVagaEmprego(@PathVariable("alunoID")Long alunoID,
                                                 @PathVariable("vagaID")Long vagaID){
        Optional<VagasEmprego>vagasEmprego = vagasEmpregoRepository.findById(vagaID);
        if(vagasEmprego.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        //Verifica se o aluno concluiu o treinamento
        Optional<AlunoInscricao>inscricao = alunoInscricaoRepository.findAlunoInscricaoByAluno_IdAndStatusTreinoAndTreinamento_Id(alunoID, StatusTreinamento.CONCLUIDO,vagasEmprego.get().getId());
        if(inscricao.isEmpty()){
            return ResponseEntity.status(409).build();
        }
        Optional<User>candidato = userRepository.findById(alunoID);
        if(candidato.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        vagasEmprego.get().getCandidatos().add(candidato.get());
        vagasEmpregoRepository.save(vagasEmprego.get());
        return ResponseEntity.ok().build();
    }



}
