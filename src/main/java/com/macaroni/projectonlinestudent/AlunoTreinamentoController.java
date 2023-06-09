package com.macaroni.projectonlinestudent;

import com.macaroni.projectonlinestudent.DTO.QuizRespostasDTO;
import com.macaroni.projectonlinestudent.DTO.TreinamentoDTO;
import com.macaroni.projectonlinestudent.Model.*;
import com.macaroni.projectonlinestudent.Repository.*;
import com.macaroni.projectonlinestudent.config.Values;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class AlunoTreinamentoController {

    @Autowired
    private QuizRepository quizRepository;

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

    @Autowired
    private PerguntaRepository perguntaRepository;

    @GetMapping("/treinamentos/avaliable")
    public ResponseEntity<List<Treinamento>> listAllAvailableTreinamentos(){
        List<Treinamento>allAvailable = treinamentoRepository.findTreinamentosByDataFimInscricaoBefore(ZonedDateTime.now(values.defaultZone));
        return ResponseEntity.ok().body(allAvailable);
    }

    //Lista todas as inscrições de um aluno
    @GetMapping("aluno/{id}/treinamentos")
    public ResponseEntity<List<AlunoInscricao>> listAllSubscribeTreinamentos(@PathVariable("id")Long userID){
        try{
            List<AlunoInscricao>inscricoes = alunoInscricaoRepository.findAlunoInscricaosByAluno_Id(userID);
            return ResponseEntity.ok().body(inscricoes);
        }
        catch(NullPointerException e){
            return ResponseEntity.badRequest().build();
        }
    }

    //Detalhe da inscrição feita por um aluno.
    @GetMapping("/aluno/treinamentos/{id}")
    public ResponseEntity<AlunoInscricao>inscricaoDetails(@PathVariable("id")Long id){

        Optional<AlunoInscricao> alunoInscricao = alunoInscricaoRepository.findById(id);
        if(alunoInscricao.isPresent()){
            return ResponseEntity.ok().body(alunoInscricao.get());
        }
        return ResponseEntity.notFound().build();
    }

    //Faz inscrição de um aluno em um treinamento
    @PostMapping("aluno/{alunoID}/treinamentos/{treinamentoID}")
    public ResponseEntity<?>subscribeAluno(@PathVariable("alunoID")Long alunoID,@PathVariable("treinamentoID")Long treinoID){
        try{
            Optional<User>aluno = userRepository.findById(alunoID);
            Optional<Treinamento>treinamento = treinamentoRepository.findById(treinoID);

            if(aluno.isEmpty() || treinamento.isEmpty()){
                return ResponseEntity.badRequest().build();
            }
            if(ZonedDateTime.now(values.defaultZone).isAfter(treinamento.get().getDataFimInscricao())
            || ZonedDateTime.now(values.defaultZone).isBefore(treinamento.get().getDataInicioInscricao())){
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
        catch(NullPointerException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    //Faz submissão de um quiz
    @PostMapping("aluno/{alunoID}/treinamentos/{treinamentoID}/quizzes/{quizID}/submissao")
    public ResponseEntity<?>corrigirQuiz(@PathVariable("alunoID")Long alunoID,
                                         @PathVariable("treinamentoID")Long treinamentoID,
                                         @PathVariable("quizID")Long quizID,
                                         @RequestBody QuizRespostasDTO quizRespostasDTO){
        try{
            User aluno = userRepository.getReferenceById(alunoID);
            Treinamento treinamento = treinamentoRepository.getReferenceById(treinamentoID);
            Quiz quiz = quizRepository.getReferenceById(quizID);

            Submissao submissao = new Submissao();
            submissao.setNota(0);

            Map<Long, Character> respostasSubmetidas = quizRespostasDTO.respostas();
            if(ZonedDateTime.now(values.defaultZone).isAfter(treinamento.getDataFimTreinamento())){
                return ResponseEntity.status(409).build();
            }

            for(Map.Entry<Long, Character> entry : respostasSubmetidas.entrySet()) {
                Long perguntaId = entry.getKey();
                Character alternativaAluno = entry.getValue();
                Optional<Pergunta>pergunta = perguntaRepository.findById(perguntaId);

                //Se a pergunta referenciada existir, verifica se foi digitado a alternativa correta.
                if(pergunta.isPresent()){
                    if(pergunta.get().getAlternativaCorreta().equals(alternativaAluno)){
                        submissao.setNota(submissao.getNota() + 1);
                    }

                    //Adiciona referência a pergunta
                    submissao.getRespostas().put(pergunta.get(),alternativaAluno);
                }
            }
                submissao.setAluno(aluno);
                submissao.setTreinamentos(treinamento);
                submissao.setQuiz(quiz);
                submissaoRepository.save(submissao);
                return ResponseEntity.ok().build();
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }

    //200 se achou, 204 se não tem nada e 400 se o parâmetro foi mal passado.
    @GetMapping("aluno/{id}/submissoes")
    public ResponseEntity<List<Submissao>>listAlunoSubmissoes(@PathVariable("id")Long alunoID){
        try{
            List<Submissao>submissoesAluno = submissaoRepository.findSubmissaosByAluno(alunoID);
            if(submissoesAluno.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(submissoesAluno);
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

}
