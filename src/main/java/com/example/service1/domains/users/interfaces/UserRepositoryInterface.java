package com.example.service1.domains.users.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.service1.domains.users.entities.UserEntity;

public interface UserRepositoryInterface extends JpaRepository<UserEntity, Long> {

    public List<UserEntity> findByEmail(String email);

}
