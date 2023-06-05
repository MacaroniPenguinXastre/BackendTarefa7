package com.macaroni.projectonlinestudent.Repository;

import com.macaroni.projectonlinestudent.Model.Submissoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtividadesAlunoRepository extends JpaRepository<Submissoes,Long> {
}
