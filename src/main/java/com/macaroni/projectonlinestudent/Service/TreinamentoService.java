package com.macaroni.projectonlinestudent.Service;

import com.macaroni.projectonlinestudent.config.Values;
import com.macaroni.projectonlinestudent.Model.Treinamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Service
public class TreinamentoService {

    @Autowired
    private Values values;

    public boolean checkDate(LocalDateTime inicio, LocalDateTime fim){
        return inicio == null || fim == null || !inicio.isBefore(fim);
    }


}
