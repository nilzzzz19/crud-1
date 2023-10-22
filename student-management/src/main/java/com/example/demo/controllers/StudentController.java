package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentDAO;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class StudentController {

	@Autowired
	private final StudentDAO studentDAO;

	public StudentController(StudentDAO studentDAO) {
		this.studentDAO = studentDAO;
	}

	@GetMapping("/students")
	public List<Student> getAllStudent() {
		return studentDAO.findAll();
	}

	@GetMapping("/students/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable("id") long id) {
		Student student = studentDAO.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No record for Student is" + id));
		return ResponseEntity.ok().body(student);
	}

	@PostMapping("/students")
	public Student createStudent(@RequestBody Student student) {
		return studentDAO.save(student);
	}

	@PutMapping("/students/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable(value = "id") Long id, @RequestBody Student studentDto)
			throws ResourceNotFoundException {
		Student student = studentDAO.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found for this id :: " + id));

		student.setEmail(studentDto.getEmail());
		student.setLastName(studentDto.getLastName());
		student.setFirstName(studentDto.getFirstName());
		student.setGender(studentDto.getGender());
		student.setAge(studentDto.getAge());
		student.setGrade(studentDto.getGrade());
		student.setQualification(studentDto.getQualification());
		student.setId(id);

		final Student updateStudent = studentDAO.save(student);
		return ResponseEntity.ok(updateStudent);
	}
	
	@DeleteMapping("/students/{id}")
	public ResponseEntity<Boolean> deleteStudent(@PathVariable(value = "id")
	                     Long id) throws ResourceNotFoundException {
	        Student student = studentDAO.findById(id)
	                                    .orElseThrow(() -> new ResourceNotFoundException("Student not found for this id :: " + id));

	        studentDAO.delete(student);

	        return ResponseEntity.ok(true);
	    }

}
