package com.endava.md.internship.parkinglot.repository;

import com.endava.md.internship.parkinglot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
<<<<<<< HEAD
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
=======

>>>>>>> 02a431b (INTP0002PD-5633 Created RegistrationRequestDto and updated pom.xml)
}
