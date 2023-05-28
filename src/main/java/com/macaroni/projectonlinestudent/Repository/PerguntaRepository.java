package com.macaroni.projectonlinestudent.Repository;

import com.macaroni.projectonlinestudent.model.Pergunta;
import com.macaroni.projectonlinestudent.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerguntaRepository extends JpaRepository<Pergunta,Long> {
    public List<Pergunta> findPerguntasByMentor(User user);
}
