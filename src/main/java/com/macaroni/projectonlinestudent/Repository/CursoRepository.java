package com.macaroni.projectonlinestudent.Repository;

import com.macaroni.projectonlinestudent.model.Curso;
import com.macaroni.projectonlinestudent.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso,Long> {

    public List<Curso> findCursosByMentor(User user);
}
