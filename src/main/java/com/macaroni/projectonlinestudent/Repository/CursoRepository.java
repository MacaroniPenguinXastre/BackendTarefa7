package com.macaroni.projectonlinestudent.Repository;

import com.macaroni.projectonlinestudent.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso,Long> {
}
