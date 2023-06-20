package com.macaroni.projectonlinestudent.Repository;

import com.macaroni.projectonlinestudent.Model.Submissao;
import com.macaroni.projectonlinestudent.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissaoRepository extends JpaRepository<Submissao,Long> {
    List<Submissao>findSubmissaosByAluno_Id(Long id);

    Optional<List<Submissao>>findFirst10ByAluno_IdOrderByIdDesc(Long id);

    Optional<Submissao>findSubmissaoByTreinamentos_Id(Long id);



}
