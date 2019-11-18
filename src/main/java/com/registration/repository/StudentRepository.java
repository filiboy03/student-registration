package com.registration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.registration.model.Student;


@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

}
