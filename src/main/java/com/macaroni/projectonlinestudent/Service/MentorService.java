package com.macaroni.projectonlinestudent.Service;

import com.macaroni.projectonlinestudent.Repository.CursoRepository;
import com.macaroni.projectonlinestudent.Repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MentorService {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private CursoRepository cursoRepository;



}
