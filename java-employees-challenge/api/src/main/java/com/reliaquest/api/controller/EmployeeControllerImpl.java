package com.reliaquest.api.controller;


import com.reliaquest.api.common.Constants;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.service.employee.EmployeeService;
import com.reliaquest.api.service.employee.IEmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeControllerImpl implements IEmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeControllerImpl.class);

    @Override
    @GetMapping()
    public ResponseEntity<List<Employee>> getAllEmployees() {
        logger.info("Started: EmployeeControllerImpl");
        List<Employee> allEmployees = employeeService.getAllEmployees();
        return ResponseEntity.ok(allEmployees);
    }

    @Override
    @GetMapping("/search/{searchString}")
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(@PathVariable String searchString) {
        logger.info("Started: EmployeeControllerImpl");
        List<Employee> searchedEmp = employeeService.getEmployeesByNameSearch(searchString);
        return ResponseEntity.ok(searchedEmp);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        logger.info("Started: EmployeeControllerImpl");
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @Override
    @GetMapping("/highestSalary")
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        logger.info("Started: EmployeeControllerImpl");
        return ResponseEntity.ok(employeeService.getHighestSalaryOfEmployees());
    }

    @Override
    @GetMapping("/topTenHighestEarningEmployeeNames")
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        logger.info("Started: EmployeeControllerImpl");
        List<String> top10Emp = employeeService.getTopTenHighestEarningEmployeeNames();
        return ResponseEntity.ok(top10Emp);
    }


    @Override
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity createEmployee(@RequestBody Object employeeInput) {
        logger.info("Started: EmployeeControllerImpl");
        Employee employee = employeeService.createEmployee((Map<String, Object>) employeeInput);
        return ResponseEntity.ok(employee);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable String id) {
        logger.info("Started: EmployeeControllerImpl");
        return ResponseEntity.ok(employeeService.deleteEmployeeById(id));
    }
}
