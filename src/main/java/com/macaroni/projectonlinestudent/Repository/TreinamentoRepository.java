package com.macaroni.projectonlinestudent.Repository;

import com.macaroni.projectonlinestudent.model.Treinamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface TreinamentoRepository extends JpaRepository<Treinamento, Long> {

}
