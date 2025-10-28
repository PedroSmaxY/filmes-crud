package com.facul.filmes.controllers;

import com.facul.filmes.domain.entities.Employee;
import com.facul.filmes.services.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> findAll() {
        return this.employeeService.findAll();
    }

    @GetMapping("/{id}")
    public Employee findById(@PathVariable Long id) {
        return this.employeeService.findById(id);
    }

    @PostMapping
    public Employee save(@RequestBody Employee employee) {
        return this.employeeService.save(employee);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        this.employeeService.update(id, updatedEmployee);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.employeeService.delete(id);
    }
}
