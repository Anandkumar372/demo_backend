package com.youtube.crud.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.youtube.crud.controller.StudentEntity;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
  boolean existsByEmail(String email);
  StudentEntity findByEmail(String email);
}