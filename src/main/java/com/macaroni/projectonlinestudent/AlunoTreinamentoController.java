package com.macaroni.projectonlinestudent;

import com.macaroni.projectonlinestudent.DTO.TreinamentoDTO;
import com.macaroni.projectonlinestudent.Model.*;
import com.macaroni.projectonlinestudent.Repository.AlunoInscricaoRepository;
import com.macaroni.projectonlinestudent.Repository.TreinamentoRepository;
import com.macaroni.projectonlinestudent.config.Values;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AlunoTreinamentoController {

    @Autowired
    private TreinamentoRepository treinamentoRepository;

    @Autowired
    private Values values;

    @Autowired
    private AlunoInscricaoRepository alunoInscricaoRepository;

    @GetMapping("aluno/treinamento/index")
    public ResponseEntity<List<Treinamento>> listAllAvailableTreinamentos(){
        List<Treinamento>allAvailable = treinamentoRepository.findTreinamentosByDataFimInscricaoBefore(ZonedDateTime.now(values.defaultZone));
        return ResponseEntity.ok().body(allAvailable);
    }

    @GetMapping("aluno/treinamento/inscricoes")
    public ResponseEntity<List<AlunoInscricao>> listAllSubscribeTreinamentos(@RequestBody User user){
        if(user == null){
            return ResponseEntity.badRequest().build();
        }
        List<AlunoInscricao>allInscricoes = alunoInscricaoRepository.findAlunoInscricaosByAluno(user);
        if(allInscricoes.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok().body(allInscricoes);
    }

    @GetMapping("aluno/treinamento/inscricoes/{id}")
    public ResponseEntity<AlunoInscricao>inscricaoDetails(@PathVariable Long id){
        AlunoInscricao alunoInscricao = alunoInscricaoRepository.getReferenceById(id);
        if(alunoInscricao == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(alunoInscricao);
    }

    @PostMapping("aluno/treinamento/inscricoes/subscribe")
    public ResponseEntity<?>subscribeAluno(@RequestBody TreinamentoDTO treinamentoDTO){
        if(treinamentoDTO == null){
            return ResponseEntity.badRequest().build();
        }
        try{
            if(ZonedDateTime.now(values.defaultZone).isAfter(treinamentoDTO.treinamento().getDataFimInscricao())){
                return ResponseEntity.badRequest().build();
            }
            AlunoInscricao inscricao = alunoInscricaoRepository.findAlunoInscricaoByAlunoAndTreinamento(treinamentoDTO.user(), treinamentoDTO.treinamento());
            if(inscricao == null){
                inscricao = new AlunoInscricao();

                inscricao.setAluno(treinamentoDTO.user());
                inscricao.setTreinamento(treinamentoDTO.treinamento());
                inscricao.setDataInscricao(ZonedDateTime.now(values.defaultZone));
                inscricao.setFaseAtual(1);
                inscricao.setStatusTreino(StatusTreinamento.INSCRITO);
                alunoInscricaoRepository.save(inscricao);
                return ResponseEntity.ok().body(inscricao);
            }
            inscricao.setStatusTreino(StatusTreinamento.INSCRITO);
            alunoInscricaoRepository.save(inscricao);
            return ResponseEntity.ok().body(inscricao);
        }

        catch (NullPointerException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }


}
