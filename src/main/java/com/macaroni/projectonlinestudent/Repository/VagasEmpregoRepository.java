package com.macaroni.projectonlinestudent.Repository;

import com.macaroni.projectonlinestudent.Model.VagasEmprego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VagasEmpregoRepository extends JpaRepository<VagasEmprego, Long> {
    List<VagasEmprego> findVagasEmpregosByTreinamentoRequisito_Id(Long id);

    Optional<VagasEmprego>findVagasEmpregoByTreinamentoRequisito_Id(Long id);
}
