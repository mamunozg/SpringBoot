package com.marco.dao;

import java.util.List;

import com.marco.model.Employee;

public interface EmployeeDAO {

	public Employee getEmployee(String empNo);

	public Employee addEmployee(Employee emp);

	public Employee updateEmployee(Employee emp);

	public void deleteEmployee(String empNoM);

	public List<Employee> getAllEmployees();
}
