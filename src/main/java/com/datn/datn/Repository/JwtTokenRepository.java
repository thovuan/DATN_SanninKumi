package com.datn.datn.Repository;

import com.datn.datn.Model.User_tokens;
import com.datn.datn.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JwtTokenRepository extends JpaRepository<User_tokens, String> {

    Optional<User_tokens> findByToken(String token);
    List<User_tokens> findByUser(Users user);
}
