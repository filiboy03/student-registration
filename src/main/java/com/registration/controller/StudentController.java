package com.registration.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.registration.exception.ResourceNotFoundException;
import com.registration.model.Course;
import com.registration.model.Student;
import com.registration.model.StudentDTO;
import com.registration.repository.CourseRepository;
import com.registration.repository.StudentJDBCDAO;
import com.registration.repository.StudentRepository;

@RestController
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private StudentJDBCDAO studentjdbcDao;

	// add a new student
	@PostMapping("students")
	public Student createStudent(@RequestBody Student student) {

		return studentRepository.save(student);
	}

	// get all students
	@RequestMapping(value = "/students", method = RequestMethod.GET)
	public List<Student> getStudents() {

		return studentRepository.findAll();

	}

	// find one student by id
	@RequestMapping(value = "/students/{id}", method = RequestMethod.GET)
	public Student getStudentbyId(@PathVariable("id") Long studentId) {

		return studentRepository.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Student", "studentId", studentId));
	}

	// remove a student by id
	@DeleteMapping("/students/{id}")
	public ResponseEntity<?> deleteMember(@PathVariable(value = "id") Long studentId) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

		studentRepository.delete(student);

		return ResponseEntity.ok().build();
	}

	// register a course
	@PostMapping("/register/{studentid}")
	public ResponseEntity<?> registerCourse(@PathVariable(value = "studentid") Long studentId,
			@RequestParam String courseId) {

		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

		Course selectedCourse = courseRepository.findById(Long.valueOf(courseId))
				.orElseThrow(() -> new ResourceNotFoundException("Course", "courseId", courseId));

		student.getCourse().add(selectedCourse);
		studentRepository.save(student);

		return ResponseEntity.ok().build();
	}

	// retrieve all students enrolled in a selected course

	@RequestMapping(value = "/enrollments", method = RequestMethod.GET)
	public List<String> getAllenrolled(@RequestParam String courseName) {

		List<StudentDTO> studentDTO = studentjdbcDao.getStudentsbyCourse(courseName);

		List<String> studentNames = studentDTO.stream().map(s -> s.getStudentName()).collect(Collectors.toList());

		return studentNames;

	}
}
