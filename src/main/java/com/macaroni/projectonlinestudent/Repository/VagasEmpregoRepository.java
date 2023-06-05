package com.macaroni.projectonlinestudent.Repository;

import com.macaroni.projectonlinestudent.Model.VagasEmprego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VagasEmpregoRepository extends JpaRepository<VagasEmprego, Long> {

}
