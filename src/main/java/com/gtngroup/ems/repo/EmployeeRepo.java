package com.gtngroup.ems.repo;

import com.gtngroup.ems.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee,Integer> {
}
