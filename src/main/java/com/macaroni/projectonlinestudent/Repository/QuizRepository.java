package com.macaroni.projectonlinestudent.Repository;

import com.macaroni.projectonlinestudent.Model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long> {

}
