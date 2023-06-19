package com.macaroni.projectonlinestudent.Repository;

import com.macaroni.projectonlinestudent.Model.Treinamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface TreinamentoRepository extends JpaRepository<Treinamento, Long> {
    public List<Treinamento> findTreinamentosByDataFimInscricaoAfter(LocalDateTime dateTime);

}
