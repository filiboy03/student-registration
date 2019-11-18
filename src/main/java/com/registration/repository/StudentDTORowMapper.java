package com.registration.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.registration.model.StudentDTO;

public class StudentDTORowMapper implements RowMapper<StudentDTO>  {

		

	@Override
	public StudentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		StudentDTO studentDTO = new StudentDTO();
		studentDTO.setStudentName(rs.getString("studentName"));
		
		
		return studentDTO;
	}

}
