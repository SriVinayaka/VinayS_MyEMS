/**
 * 
 */
package com.gl.myems.repository;

/**
 * 
 */
/**
 * 
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gl.myems.model.Employee;

/**
 * 
 */

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
