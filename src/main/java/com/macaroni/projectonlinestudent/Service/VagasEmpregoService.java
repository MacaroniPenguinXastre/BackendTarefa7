package com.macaroni.projectonlinestudent.Service;

import com.macaroni.projectonlinestudent.Model.VagasEmprego;
import com.macaroni.projectonlinestudent.Repository.VagasEmpregoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VagasEmpregoService {

    public void updateVagasEmprego(VagasEmprego vagasEmprego, VagasEmprego vagasEmpregoAtualizada) {
        vagasEmprego.setTitulo(vagasEmpregoAtualizada.getTitulo());
        vagasEmprego.setEmpresa(vagasEmpregoAtualizada.getEmpresa());
        vagasEmprego.setAtividades(vagasEmpregoAtualizada.getAtividades());
        vagasEmprego.setTreinamentoRequisito(vagasEmpregoAtualizada.getTreinamentoRequisito());
        vagasEmprego.setFaixaSalarial(vagasEmpregoAtualizada.getFaixaSalarial());
        vagasEmprego.setCandidatos(vagasEmpregoAtualizada.getCandidatos());
    }
}
