package com.facul.filmes.services;

import com.facul.filmes.domain.entities.Employee;
import com.facul.filmes.repositories.EmployeeRepository;
import com.facul.filmes.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee findById(Long id) {
        return this.employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    public List<Employee> findAll() {
        return this.employeeRepository.findAll();
    }

    @Transactional
    public Employee save(Employee employee) {
        return this.employeeRepository.save(employee);
    }

    @Transactional
    public void update(Long id, Employee updatedEmployee) {
        Employee existingEmployee = findById(id);
        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setRole(updatedEmployee.getRole());
        this.employeeRepository.save(existingEmployee);
    }

    @Transactional
    public void delete(Long id) {
        this.findById(id);
        this.employeeRepository.deleteById(id);
    }
}
