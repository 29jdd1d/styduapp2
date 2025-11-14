package com.study.exam.repository;

import com.study.exam.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByOpenid(String openid);
    
    Optional<User> findByIdAndDeletedFalse(Long id);
    
    boolean existsByOpenid(String openid);
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByUsernameAndDeletedFalse(String username);
}
