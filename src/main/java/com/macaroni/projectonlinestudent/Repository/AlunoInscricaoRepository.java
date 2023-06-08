package com.macaroni.projectonlinestudent.Repository;

import com.macaroni.projectonlinestudent.Model.AlunoInscricao;
import com.macaroni.projectonlinestudent.Model.Treinamento;
import com.macaroni.projectonlinestudent.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlunoInscricaoRepository extends JpaRepository<AlunoInscricao, Long> {
    public List<AlunoInscricao>findAlunoInscricaosByAluno_Id(Long id);

    AlunoInscricao findAlunoInscricaoByAlunoAndTreinamento(User user,Treinamento treinamento);

}
