package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpRepository extends JpaRepository<com.example.entity.Employee, Long> {

}
