package com.macaroni.projectonlinestudent.Service;

import com.macaroni.projectonlinestudent.Model.Submissao;
import com.macaroni.projectonlinestudent.Model.VagasEmprego;
import com.macaroni.projectonlinestudent.Repository.SubmissaoRepository;
import com.macaroni.projectonlinestudent.Repository.VagasEmpregoRepository;
import com.macaroni.projectonlinestudent.config.Values;
import com.macaroni.projectonlinestudent.Model.Treinamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class TreinamentoService {

    @Autowired
    private Values values;

    @Autowired
    private SubmissaoRepository submissaoRepository;

    @Autowired
    private VagasEmpregoRepository vagasEmpregoRepository;


    public boolean checkDate(LocalDateTime inicio, LocalDateTime fim){
        return inicio == null || fim == null || !inicio.isBefore(fim);
    }

    public boolean isPossibleToChange(Treinamento treinamento){
        return !(this.checkIfHaveSubmissao(treinamento.getId()) && this.checkIfIsAssociadoWithVagasEmprego(treinamento.getId()));
    }

    public boolean checkIfHaveSubmissao(Long id){
        Optional<Submissao> submissao = submissaoRepository.findSubmissaoByTreinamentos_Id(id);
        return submissao.isPresent();
    }
    public boolean checkIfIsAssociadoWithVagasEmprego(Long id){
        Optional<VagasEmprego>vagaEmprego = vagasEmpregoRepository.findVagasEmpregoByTreinamentoRequisito_Id(id);
        return vagaEmprego.isPresent();
    }

    public void updateTreinamento(Treinamento treinamento, Treinamento treinamentoAtualizado) {
        treinamento.setCargaHorariaTotal(treinamentoAtualizado.getCargaHorariaTotal());
        treinamento.setNomeComercial(treinamentoAtualizado.getNomeComercial());
        treinamento.setDescricao(treinamentoAtualizado.getDescricao());
        treinamento.setDataInicioInscricao(treinamentoAtualizado.getDataInicioInscricao());
        treinamento.setDataFimInscricao(treinamentoAtualizado.getDataFimInscricao());
        treinamento.setDataInicioTreinamento(treinamentoAtualizado.getDataInicioTreinamento());
        treinamento.setDataFimTreinamento(treinamentoAtualizado.getDataFimTreinamento());
        treinamento.setQuantidadeMinima(treinamentoAtualizado.getQuantidadeMinima());
        treinamento.setQuantidadeMaxima(treinamentoAtualizado.getQuantidadeMaxima());
        treinamento.setTesteAptidao(treinamentoAtualizado.getTesteAptidao());
        treinamento.setFaseIntrodutorio(treinamentoAtualizado.getFaseIntrodutorio());
        treinamento.setPrimeiroCase(treinamentoAtualizado.getPrimeiroCase());
        treinamento.setFaseAvancada(treinamentoAtualizado.getFaseAvancada());
        treinamento.setSegundoCase(treinamentoAtualizado.getSegundoCase());
    }
}
