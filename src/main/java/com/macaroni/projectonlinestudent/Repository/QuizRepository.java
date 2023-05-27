package com.macaroni.projectonlinestudent.Repository;

import com.macaroni.projectonlinestudent.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz,Long> {
}
