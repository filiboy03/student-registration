package com.registration.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.registration.model.StudentDTO;

@Repository
public class StudentJDBCDAO {

	@Autowired
	  private JdbcTemplate jdbcTemplate;
	
	    @Transactional(readOnly=true)
	    public List<StudentDTO> getStudentsbyCourse(String courseName) {
		  
	   return jdbcTemplate.query("Select studentName From student s Inner Join stud_course sc On sc.studentId= s.studentId Inner Join course c On c.courseId = sc.courseId "
		  		+ "where c.courseName=? order by studentName ASC",  new Object[] { courseName }, new StudentDTORowMapper());
		  
	  }
	
	
	
}
