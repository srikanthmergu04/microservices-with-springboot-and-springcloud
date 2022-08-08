package com.java.rest.webservices.restfulwebservices.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.java.rest.webservices.restfulwebservices.model.Student;
import com.java.rest.webservices.restfulwebservices.service.StudentService;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@PostMapping()
	public ResponseEntity<Object> createStudent(@Valid @RequestBody Student student) {
		System.out.println(student.toString());
		Student savedStudent = studentService.saveStudent(student);

		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(savedStudent.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@GetMapping("/{id}")
	public MappingJacksonValue getStudent(@PathVariable Integer id) {
		Student student = studentService.getStudent(id);
		EntityModel<Student> studentEntity = EntityModel.of(student);

		WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getStudents());
		studentEntity.add(linkTo.withRel("all-students"));

		SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name",
				"course");

		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("student-filter",
				simpleBeanPropertyFilter);

		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(studentEntity);
		mappingJacksonValue.setFilters(filterProvider);

		return mappingJacksonValue;
	}

	@GetMapping()
	public MappingJacksonValue getStudents() {

		List<Student> students = studentService.getStudents();

		SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name",
				"course");

		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("student-filter",
				simpleBeanPropertyFilter);

		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(students);
		mappingJacksonValue.setFilters(filterProvider);

		return mappingJacksonValue;
	}

	@DeleteMapping("/{id}")
	public String deleteStudent(@PathVariable Integer id) {
		studentService.deleteStudent(id);
		return "Student Deleted";
	}

	// URI Versioning
	@GetMapping("/V1")
	public MappingJacksonValue getV1Students() {

		MappingJacksonValue mappingJacksonValue = studentService.getFilteredStudents("id", "collegeName");

		return mappingJacksonValue;
	}

	@GetMapping("/V2")
	public MappingJacksonValue getV2Students() {
		MappingJacksonValue mappingJacksonValue = studentService.getFilteredStudents("name", "course");

		return mappingJacksonValue;
	}

	// Request Param Versioning
	@GetMapping(params = "V=1")
	public MappingJacksonValue getParamV1Students() {

		MappingJacksonValue mappingJacksonValue = studentService.getFilteredStudents("id", "collegeName");

		return mappingJacksonValue;
	}

	@GetMapping(params = "V=2")
	public MappingJacksonValue getParamV2Students() {
		MappingJacksonValue mappingJacksonValue = studentService.getFilteredStudents("name", "course");

		return mappingJacksonValue;
	}

	// Header Versioning
	@Hidden
	@GetMapping(headers = "X-API-VERSION=1")
	public MappingJacksonValue getHeaderV1Students() {

		MappingJacksonValue mappingJacksonValue = studentService.getFilteredStudents("id", "collegeName");

		return mappingJacksonValue;
	}

	@Hidden
	@GetMapping(headers = "X-API-VERSION=2")
	public MappingJacksonValue getHeaderV2Students() {
		MappingJacksonValue mappingJacksonValue = studentService.getFilteredStudents("name", "course");

		return mappingJacksonValue;
	}

	// Media Type Versioning
	@Hidden
	@GetMapping(produces = "application/vnd.company.app-v1+json")
	public MappingJacksonValue getMediaTypeV1Students() {

		MappingJacksonValue mappingJacksonValue = studentService.getFilteredStudents("id", "collegeName");

		return mappingJacksonValue;
	}

	@Hidden
	@GetMapping(produces = "application/vnd.company.app-v2+json")
	public MappingJacksonValue getMediaTypeV2Students() {
		MappingJacksonValue mappingJacksonValue = studentService.getFilteredStudents("name", "course");

		return mappingJacksonValue;
	}

}
