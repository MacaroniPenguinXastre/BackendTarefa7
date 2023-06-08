package com.macaroni.projectonlinestudent.Repository;

import com.macaroni.projectonlinestudent.Model.Submissao;
import com.macaroni.projectonlinestudent.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissaoRepository extends JpaRepository<Submissao,Long> {
    List<Submissao>findSubmissaosByAluno(User user);
}
