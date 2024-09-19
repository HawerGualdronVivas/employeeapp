package edu.uptc.swii.employeeapp2.service;

import java.util.List;

import edu.uptc.swii.employeeapp2.model.Employee;

public interface IEmployeeDAO {
    List<Employee> findAll();
    Employee findById(String id);
    boolean save(Employee product);
    boolean deleteEmployeeById(String id);
}

