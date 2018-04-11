package com.marco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marco.dao.EmployeeDAO;
import com.marco.model.Employee;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/employees")
@Api(tags= {"EmployeeRestController"})
public class EmployeeRestController {

	@Autowired
	public EmployeeDAO employeeDao;

	@GetMapping(consumes= {MediaType.APPLICATION_JSON_UTF8_VALUE}, produces= {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ApiOperation(value="Search a list of employees", notes="Without params", response=Employee.class, responseContainer="List")	
	public ResponseEntity<List<Employee>> findAllEmployees() {
			
		List<Employee> listEmployee = employeeDao.getAllEmployees();
		if(listEmployee == null || listEmployee.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(employeeDao.getAllEmployees(), HttpStatus.OK);		
	}
	
	@GetMapping(path="/{id}", consumes= {MediaType.APPLICATION_JSON_UTF8_VALUE}, produces= {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ApiOperation(value="Search employee by employee number", response=Employee.class)
	public ResponseEntity<Employee> findEmployeeById(@PathVariable("id") String empNo) {		
		
		Employee emp = employeeDao.getEmployee(empNo);
		if(emp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(emp, HttpStatus.OK);		
	}
	
	@PostMapping(consumes= {MediaType.APPLICATION_JSON_UTF8_VALUE}, produces= {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ApiOperation(value="create a new employee", response=Employee.class)
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee empl) {		
		return new ResponseEntity<>(employeeDao.addEmployee(empl), HttpStatus.OK);				
	}
	
	@PutMapping(path="/{id}", consumes= {MediaType.APPLICATION_JSON_UTF8_VALUE}, produces= {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ApiOperation(value="update an employee", response=Employee.class)
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") String empNo,  @RequestBody Employee empl) {	
		
		Employee emp = employeeDao.getEmployee(empNo);
		if(emp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(employeeDao.addEmployee(empl), HttpStatus.OK);				
	}
	
	@ApiOperation(value="delete an employee", response=Employee.class)
	@DeleteMapping(path="/{id}", consumes= {MediaType.APPLICATION_JSON_UTF8_VALUE}, produces= {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<Void> deleteEmployee(@PathVariable("id") String empNo) {
		Employee emp = employeeDao.getEmployee(empNo);
		if(emp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		employeeDao.deleteEmployee(empNo);
		return new ResponseEntity<>(HttpStatus.OK); 
	}
	
	
}
