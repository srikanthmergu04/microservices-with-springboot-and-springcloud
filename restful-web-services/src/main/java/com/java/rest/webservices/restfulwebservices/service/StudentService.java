package com.java.rest.webservices.restfulwebservices.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.java.rest.webservices.restfulwebservices.exception.StudentNotFoundException;
import com.java.rest.webservices.restfulwebservices.model.Student;
import com.java.rest.webservices.restfulwebservices.repository.StudentRepository;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	public Student saveStudent(Student student) {

		return studentRepository.save(student);

	}

	public Student getStudent(Integer id) {
		Student student = studentRepository.findById(id).orElse(null);

		if (student == null) {
			throw new StudentNotFoundException("id-" + id);
		}

		return student;
	}

	public List<Student> getStudents() {
		return studentRepository.findAll();
	}

	public void deleteStudent(Integer id) {

		if (!studentRepository.existsById(id)) {
			throw new StudentNotFoundException("id-" + id);
		}

		studentRepository.deleteById(id);
	}

	public MappingJacksonValue getFilteredStudents(String field1, String field2) {
		List<Student> students = getStudents();

		SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(field1, field2);

		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("student-filter",
				simpleBeanPropertyFilter);

		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(students);
		mappingJacksonValue.setFilters(filterProvider);
		return mappingJacksonValue;
	}

}
