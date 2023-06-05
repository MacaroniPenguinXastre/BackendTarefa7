package com.macaroni.projectonlinestudent.Service;

import com.macaroni.projectonlinestudent.config.Values;
import com.macaroni.projectonlinestudent.Model.Treinamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class TreinamentoService {

    @Autowired
    private Values values;

    public boolean checkDate(ZonedDateTime inicio,ZonedDateTime fim){
        return inicio == null || fim == null || !inicio.isBefore(fim);
    }

    public Treinamento convertToSPZone(Treinamento treinamento){
        if(treinamento == null){
            return null;
        }
        treinamento.setDataInicioInscricao(treinamento.getDataInicioInscricao().withZoneSameInstant(values.defaultZone));
        treinamento.setDataFimInscricao(treinamento.getDataFimInscricao().withZoneSameInstant(values.defaultZone));

        treinamento.setDataInicioTreinamento(treinamento.getDataInicioInscricao().withZoneSameInstant(values.defaultZone));
        treinamento.setDataFimInscricao(treinamento.getDataFimInscricao().withZoneSameInstant(values.defaultZone));

        return treinamento;
    }
}
