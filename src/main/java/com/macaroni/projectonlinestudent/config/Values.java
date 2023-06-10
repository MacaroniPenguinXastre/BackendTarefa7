package com.macaroni.projectonlinestudent.config;

import org.springframework.stereotype.Service;

import java.time.ZoneId;
@Service
public class Values {
    public ZoneId defaultZone = ZoneId.of("America/Sao_Paulo");

    public int notDone = -1;
}
