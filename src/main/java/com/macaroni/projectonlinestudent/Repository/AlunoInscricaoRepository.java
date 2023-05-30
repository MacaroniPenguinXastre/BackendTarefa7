package com.macaroni.projectonlinestudent.Repository;

import com.macaroni.projectonlinestudent.model.AlunoInscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoInscricaoRepository extends JpaRepository<AlunoInscricao, Long> {

}
