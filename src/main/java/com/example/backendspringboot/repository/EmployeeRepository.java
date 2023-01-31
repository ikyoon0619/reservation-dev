package com.example.backendspringboot.repository;

import com.example.backendspringboot.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByUserIdAndPassword(String userId, String password);
}