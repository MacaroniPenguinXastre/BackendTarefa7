package com.macaroni.projectonlinestudent;

import com.macaroni.projectonlinestudent.DTO.QuizRespostasDTO;
import com.macaroni.projectonlinestudent.DTO.TreinamentoDTO;
import com.macaroni.projectonlinestudent.Model.*;
import com.macaroni.projectonlinestudent.Repository.*;
import com.macaroni.projectonlinestudent.config.Values;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @GetMapping("/treinamentos/available")
    public ResponseEntity<List<Treinamento>> listAllAvailableTreinamentos(){
        List<Treinamento>allAvailable = treinamentoRepository.findTreinamentosByDataFimInscricaoAfter(LocalDateTime.now());

        return ResponseEntity.ok().body(allAvailable);
    }

    //Lista todas as inscrições por um aluno que ainda NÃO venceram
    @GetMapping("aluno/{id}/inscricao/available")
    public ResponseEntity<List<AlunoInscricao>> listAllSubscribeTreinamentosAvailable(@PathVariable("id")Long userID){
        try{
            List<AlunoInscricao>inscricoes = alunoInscricaoRepository.findAlunoInscricaosByAluno_Id(userID);
            System.out.println(inscricoes);
            for(int i = 0; i < inscricoes.size();i++){
                if(LocalDateTime.now().isAfter(inscricoes.get(i).getTreinamento().getDataFimTreinamento())){
                    inscricoes.remove(i);
                }
            }
            System.out.println(inscricoes);
            return ResponseEntity.ok().body(inscricoes);
        }

        catch(NullPointerException e){
            return ResponseEntity.badRequest().build();
        }
    }
    //Lista todas as inscrições por um aluno
    @GetMapping("aluno/{id}/inscricao")
    public ResponseEntity<List<AlunoInscricao>> listAllSubscribeTreinamentos(@PathVariable("id")Long userID){
        try{
            List<AlunoInscricao>inscricoes = alunoInscricaoRepository.findAlunoInscricaosByAluno_Id(userID);

            return ResponseEntity.ok().body(inscricoes);
        }
        catch(NullPointerException e){
            return ResponseEntity.badRequest().build();
        }
    }



    @GetMapping("aluno/{alunoID}/treinamento/{treinoID}")
    public ResponseEntity<AlunoInscricao>checkIfIsSubscribed(@PathVariable("alunoID")Long userID,
                                                             @PathVariable("treinoID")Long treinoID){
        Optional<User>aluno = userRepository.findById(userID);
        Optional<Treinamento>treinamento = treinamentoRepository.findById(treinoID);

        if(aluno.isEmpty() || treinamento.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        Optional<AlunoInscricao>inscricao = alunoInscricaoRepository.findAlunoInscricaoByAluno_IdAndTreinamento_Id(userID,treinoID);
        if(inscricao.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(inscricao.get());
    }

    //Detalhe da inscrição feita por um aluno.
    @GetMapping("/aluno/inscricao/{id}")
    public ResponseEntity<AlunoInscricao>inscricaoDetails(@PathVariable("id")Long id){

        Optional<AlunoInscricao> alunoInscricao = alunoInscricaoRepository.findById(id);
        if(alunoInscricao.isPresent()){
            return ResponseEntity.ok().body(alunoInscricao.get());
        }
        return ResponseEntity.notFound().build();
    }

    //Faz inscrição de um aluno em um treinamento
    @PostMapping("aluno/{alunoID}/treinamentos/{treinamentoID}/inscricao")
    public ResponseEntity<?>subscribeAluno(@PathVariable("alunoID")Long alunoID,@PathVariable("treinamentoID")Long treinoID,
                                           @RequestBody Boolean subscribe){
        try{
            Optional<User>aluno = userRepository.findById(alunoID);
            Optional<Treinamento>treinamento = treinamentoRepository.findById(treinoID);

            if(aluno.isEmpty() || treinamento.isEmpty()){

                return ResponseEntity.badRequest().build();
            }

            if(LocalDateTime.now(values.defaultZone).isAfter(treinamento.get().getDataFimInscricao())
            || LocalDateTime.now(values.defaultZone).isBefore(treinamento.get().getDataInicioInscricao())){

                return ResponseEntity.status(409).build();
            }
            AlunoInscricao inscricao = alunoInscricaoRepository.findAlunoInscricaoByAlunoAndTreinamento(aluno.get(),treinamento.get());

            if(inscricao == null){
                inscricao = new AlunoInscricao();
                inscricao.setAluno(aluno.get());
                inscricao.setTreinamento(treinamento.get());
                inscricao.setDataInscricao(LocalDateTime.now(values.defaultZone));
                inscricao.setStatusTreino(StatusTreinamento.INSCRITO);

                Submissao testeAptidao = new Submissao(aluno.get(),treinamento.get(),treinamento.get().getTesteAptidao(), values.notDone);
                Submissao caseOne = new Submissao(aluno.get(),treinamento.get(),treinamento.get().getPrimeiroCase(),values.notDone);
                Submissao caseTwo = new Submissao(aluno.get(),treinamento.get(),treinamento.get().getSegundoCase(),values.notDone);

                submissaoRepository.save(testeAptidao);
                submissaoRepository.save(caseOne);
                submissaoRepository.save(caseTwo);

                inscricao.setQuizIntroducao(testeAptidao);
                inscricao.setCaseOne(caseOne);
                inscricao.setCaseTwo(caseTwo);

                alunoInscricaoRepository.save(inscricao);
                return ResponseEntity.ok().body(inscricao);
            }
            if(subscribe == true){
                inscricao.setStatusTreino(StatusTreinamento.INSCRITO);
            }
            else {
                inscricao.setStatusTreino(StatusTreinamento.CANCELADO);
            }

            alunoInscricaoRepository.save(inscricao);
            return ResponseEntity.ok().body(inscricao);
        }
        catch(NullPointerException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    //Faz submissão de um quiz
    @PostMapping("/inscricao/{inscricaoID}/submissao/{submissaoID}")
    public ResponseEntity<?>corrigirQuiz(@PathVariable("inscricaoID")Long inscricaoID,
                                         @PathVariable("submissaoID")Long submissaoID,
                                         @RequestBody QuizRespostasDTO quizRespostasDTO){
        try{
            Optional<AlunoInscricao>alunoInscricao = alunoInscricaoRepository.findById(inscricaoID);
            Optional<Submissao>submissao = submissaoRepository.findById(submissaoID);

            if(alunoInscricao.isEmpty() || submissao.isEmpty()){
                return ResponseEntity.badRequest().build();
            }
            //Se o aluno já fez o teste OU tentou fazer uma submissão APÓS o fim do treinamento, a submissão não é realizada
            if(LocalDateTime.now(values.defaultZone).isAfter(alunoInscricao.get().getTreinamento().getDataFimTreinamento()) ||
                    submissao.get().getNota() != values.notDone || alunoInscricao.get().getStatusTreino().equals(StatusTreinamento.REPROVADO)){
                return ResponseEntity.status(409).build();
            }

            Quiz quiz = submissao.get().getQuiz();
            submissao.get().setNota(0);
            Map<Long, Character> respostasSubmetidas = quizRespostasDTO.respostas();
            double quantidadeAcertos = 0;
            int totalQuestoes = quiz.getPerguntas().size();

            for(Map.Entry<Long, Character> entry : respostasSubmetidas.entrySet()){
                Long perguntaId = entry.getKey();
                Character alternativaAluno = entry.getValue();

                Optional<Pergunta>pergunta = perguntaRepository.findById(perguntaId);
                //Se a pergunta referenciada existir, verifica se foi digitado a alternativa correta.
                if(pergunta.isPresent()){
                    if(pergunta.get().getAlternativaCorreta().equals(alternativaAluno)){
                        quantidadeAcertos++;
                    }

                    //Adiciona referência a pergunta
                    submissao.get().getRespostas().put(pergunta.get().getId(),alternativaAluno);
                }
            }
                double nota = (quantidadeAcertos/(double)totalQuestoes)*100;

                //Verifica se é teste de aptidão, se for, reprova o aluno caso a nota seja baixa.
                if(submissao.get().equals(alunoInscricao.get().getQuizIntroducao())){
                    if(nota < 70){
                        alunoInscricao.get().setStatusTreino(StatusTreinamento.REPROVADO);
                    }
                }

                submissao.get().setNota((int)nota);
                submissaoRepository.save(submissao.get());
                alunoInscricaoRepository.save(alunoInscricao.get());

                return ResponseEntity.ok().build();
        }

        catch (NullPointerException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    //200 se achou, 204 se não tem nada e 400 se o parâmetro foi passado incorretamente.
    @GetMapping("aluno/{id}/submissoes")
    public ResponseEntity<List<Submissao>>listAlunoSubmissoes(@PathVariable("id")Long alunoID){
        try{
            List<Submissao>submissoesAluno = submissaoRepository.findSubmissaosByAluno_Id(alunoID);
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
