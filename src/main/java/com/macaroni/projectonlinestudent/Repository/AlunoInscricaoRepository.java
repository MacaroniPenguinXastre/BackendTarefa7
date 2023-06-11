package com.macaroni.projectonlinestudent.Repository;

import com.macaroni.projectonlinestudent.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlunoInscricaoRepository extends JpaRepository<AlunoInscricao, Long> {
    public List<AlunoInscricao>findAlunoInscricaosByAluno_Id(Long id);

    AlunoInscricao findAlunoInscricaoByAlunoAndTreinamento(User user,Treinamento treinamento);

    Optional<AlunoInscricao>findAlunoInscricaoByAluno_IdAndStatusTreinoAndTreinamento_Id(Long id, StatusTreinamento statusTreinamento, Long treinamentoID);
}
