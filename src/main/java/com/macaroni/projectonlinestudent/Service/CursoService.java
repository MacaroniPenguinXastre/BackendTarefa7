package com.macaroni.projectonlinestudent.Service;

import com.macaroni.projectonlinestudent.Repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;


}
