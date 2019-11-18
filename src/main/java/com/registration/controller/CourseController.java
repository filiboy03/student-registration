package com.registration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.registration.exception.ResourceNotFoundException;
import com.registration.model.Course;
import com.registration.repository.CourseRepository;

@RestController
public class CourseController {
	
	@Autowired
	private CourseRepository courseRepository;

	//add a new course
	@PostMapping("courses")
	public Course createStudent(@RequestBody Course course) {
		
		return courseRepository.save(course);
		
	}
	
	//get all courses
	@RequestMapping(value="/courses",method=RequestMethod.GET)
	public List<Course> getCourses() {
			
		return courseRepository.findAll();
		
	}
	
	//find one course by id
	@RequestMapping(value="/courses/{id}",method=RequestMethod.GET)
	public Course getCoursebyId(@PathVariable("id") Long courseId) {
				
		return courseRepository.findById(courseId).orElseThrow(()-> new ResourceNotFoundException("Course","courseId",courseId));
		
	}
	
	//remove a course by id
	@DeleteMapping("/courses/{id}")
	public ResponseEntity<?> deleteMember(@PathVariable(value = "id") Long courseId) {
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new ResourceNotFoundException("Course", "courseId", courseId));
	
		courseRepository.delete(course);

		return ResponseEntity.ok().build();
	}
	
	
	
	
}
