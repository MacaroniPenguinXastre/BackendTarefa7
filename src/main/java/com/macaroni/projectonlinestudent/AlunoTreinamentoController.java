package com.macaroni.projectonlinestudent;

import com.macaroni.projectonlinestudent.DTO.TreinamentoDTO;
import com.macaroni.projectonlinestudent.Model.*;
import com.macaroni.projectonlinestudent.Repository.AlunoInscricaoRepository;
import com.macaroni.projectonlinestudent.Repository.SubmissaoRepository;
import com.macaroni.projectonlinestudent.Repository.TreinamentoRepository;
import com.macaroni.projectonlinestudent.Repository.UserRepository;
import com.macaroni.projectonlinestudent.config.Values;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class AlunoTreinamentoController {

    @Autowired
    private TreinamentoRepository treinamentoRepository;

    @Autowired
    private Values values;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlunoInscricaoRepository alunoInscricaoRepository;

    @Autowired
    private SubmissaoRepository submissaoRepository;

    @GetMapping("/treinamentos/avaliable")
    public ResponseEntity<List<Treinamento>> listAllAvailableTreinamentos(){
        List<Treinamento>allAvailable = treinamentoRepository.findTreinamentosByDataFimInscricaoBefore(ZonedDateTime.now(values.defaultZone));
        return ResponseEntity.ok().body(allAvailable);
    }

    //Lista todas as inscrições de um aluno
    @GetMapping("aluno/{id}/treinamentos")
    public ResponseEntity<List<AlunoInscricao>> listAllSubscribeTreinamentos(@PathVariable("id")Long userID){
        try {

            List<AlunoInscricao>inscricoes = alunoInscricaoRepository.findAlunoInscricaosByAluno_Id(userID);
            return ResponseEntity.ok().body(inscricoes);
        }
        catch (NullPointerException e){
            return ResponseEntity.badRequest().build();
        }
    }
    //Detalhe da inscrição feita por um aluno.
    @GetMapping("/aluno/treinamentos/{id}")
    public ResponseEntity<AlunoInscricao>inscricaoDetails(@PathVariable("id") Long id){
        Optional<AlunoInscricao> alunoInscricao = alunoInscricaoRepository.findById(id);
        if(alunoInscricao.isPresent()){
            return ResponseEntity.ok().body(alunoInscricao.get());
        }
        return ResponseEntity.notFound().build();
    }
    //Faz inscrição de um aluno
    @PostMapping("aluno/{alunoID}/treinamentos/{treinamentoID}")
    public ResponseEntity<?>subscribeAluno(@PathVariable("alunoID")Long alunoID,@PathVariable("treinamentoID")Long treinoID){
        try{
            Optional<User>aluno = userRepository.findById(alunoID);
            Optional<Treinamento>treinamento = treinamentoRepository.findById(treinoID);

            if(aluno.isEmpty() || treinamento.isEmpty()){
                return ResponseEntity.badRequest().build();
            }
            if(ZonedDateTime.now(values.defaultZone).isAfter(treinamento.get().getDataFimInscricao())){
                return ResponseEntity.status(409).build();
            }

            AlunoInscricao inscricao = alunoInscricaoRepository.findAlunoInscricaoByAlunoAndTreinamento(aluno.get(),treinamento.get());

            if(inscricao == null){
                inscricao = new AlunoInscricao();

                inscricao.setAluno(aluno.get());
                inscricao.setTreinamento(treinamento.get());
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

    @GetMapping("aluno/{id}/submissoes")
    public ResponseEntity<List<Submissao>>listAlunoSubmissoes(@PathVariable("id") Long userID){
        try{
            List<Submissao>allAlunoSubmissao = submissaoRepository.findSubmissaosByAluno_Id(userID);
            return ResponseEntity.ok().body(allAlunoSubmissao);

        }
        catch (NullPointerException e){
            return ResponseEntity.badRequest().build();
        }
    }


}
