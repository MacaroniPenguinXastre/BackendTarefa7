package com.macaroni.projectonlinestudent.Repository;

import com.macaroni.projectonlinestudent.Model.CargoUser;
import com.macaroni.projectonlinestudent.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByEmail(String email);

    Optional<List<User>>findUsersByCargo(CargoUser cargoUser);
}
