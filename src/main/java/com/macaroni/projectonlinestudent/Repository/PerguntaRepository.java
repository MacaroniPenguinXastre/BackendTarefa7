package com.macaroni.projectonlinestudent.Repository;

import com.macaroni.projectonlinestudent.Model.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerguntaRepository extends JpaRepository<Pergunta,Long> {
}
