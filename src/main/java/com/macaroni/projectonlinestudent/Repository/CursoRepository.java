package com.macaroni.projectonlinestudent.Repository;

import com.macaroni.projectonlinestudent.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso,Long> {
}
