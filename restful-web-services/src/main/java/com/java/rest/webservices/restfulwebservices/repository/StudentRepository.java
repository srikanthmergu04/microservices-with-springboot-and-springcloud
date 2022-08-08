package com.java.rest.webservices.restfulwebservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.rest.webservices.restfulwebservices.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

}
