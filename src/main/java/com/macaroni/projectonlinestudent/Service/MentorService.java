package com.macaroni.projectonlinestudent.Service;

import com.macaroni.projectonlinestudent.Repository.CursoRepository;
import com.macaroni.projectonlinestudent.Repository.QuizRepository;
import com.macaroni.projectonlinestudent.model.Curso;
import com.macaroni.projectonlinestudent.model.Pergunta;
import com.macaroni.projectonlinestudent.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class MentorService {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private CursoRepository cursoRepository;



}
