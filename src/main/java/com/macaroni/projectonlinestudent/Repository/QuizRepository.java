package com.macaroni.projectonlinestudent.Repository;

import com.macaroni.projectonlinestudent.model.Quiz;
import com.macaroni.projectonlinestudent.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long> {

    public List<Quiz>findQuizzesByMentor(User user);
}
