package com.fitai.fitai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fitai.fitai.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
