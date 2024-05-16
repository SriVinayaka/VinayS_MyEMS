/**
 * 
 */
package com.gl.myems.controller;

/**
 * 
 */

import com.gl.myems.model.*;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gl.myems.repository.EmployeeRepository;

/**
 * 
 */

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	// Get all employees
	@GetMapping
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	// Get employee by ID
	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		Optional<Employee> employee = employeeRepository.findById(id);
		if (employee.isPresent()) {
			return ResponseEntity.ok(employee.get());
		} else {
			// return ResponseEntity.notFound().build();
			return ResponseEntity.ok(null);
		}
	}

	// Create a new employee
	@PostMapping("/createEmployee")
	public Employee createEmployee(@RequestBody Employee employee) {
		Employee e1 = new Employee();
		e1.setId(employeeRepository.count() + 1);
		e1.setFirstName(employee.getFirstName());
		e1.setLastName(employee.getLastName());
		e1.setEmail(employee.getEmail());
		return employeeRepository.saveAndFlush(e1);
	}

	// Update employee details (similar logic for PUT request)
	@PatchMapping("/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
		Optional<Employee> existingEmployee = employeeRepository.findById(id);
		if (existingEmployee.isPresent()) {
			Employee employee = existingEmployee.get();
			// Update specific fields based on employeeDetails
			employee.setId(id);
			employee.setFirstName(employeeDetails.getFirstName());
			employee.setLastName(employeeDetails.getLastName());
			employee.setEmail(employeeDetails.getEmail());
			// Update other fields as needed
			employeeRepository.saveAndFlush(employee);
			return ResponseEntity.ok(employee);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Delete employee by ID
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
		employeeRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
