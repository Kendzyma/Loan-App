package com.loanapp.repository;

import com.loanapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/2/25
 * @email :TiamiyuKehinde5@gmail.com
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
