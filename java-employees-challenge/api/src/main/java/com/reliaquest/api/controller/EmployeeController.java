package com.reliaquest.api.controller;

import com.reliaquest.api.dtos.Employee;
import com.reliaquest.api.service.impl.EmployeeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.ServiceUnavailableException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController implements IEmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeService;

    private final Logger logger = LoggerFactory.getLogger(EmployeeController.class);


    /**
     * General handler for all service calls.
     * This method centralizes logging, error handling, and response creation.
     */
    public ResponseEntity<?> handleRequest(Supplier<?> serviceCall, String methodName) {
        logger.info("EmployeeController|{}|Entry", methodName);
        try {
            Object response = serviceCall.get();

            if (response == null || (response instanceof Collection && ((Collection<?>) response).isEmpty())) {
                logger.error("EmployeeController|{}|Error: Response is empty", methodName);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Resource not found from server");
            }

            logger.info("EmployeeController|{}|Exit", methodName);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            logger.error("EmployeeController|{}|Error: {}", methodName, e.getMessage());
            throw e;

        } catch (Exception e) {
            // General exception handling
            String errorMessage = e instanceof ServiceUnavailableException
                    ? "The service is currently unavailable. Please try again later."
                    : "Internal server error occurred: " + e.getMessage();
            HttpStatus status = e instanceof ServiceUnavailableException ? HttpStatus.SERVICE_UNAVAILABLE : HttpStatus.INTERNAL_SERVER_ERROR;
            logger.error("EmployeeController|{}|Error: {}", methodName, errorMessage);
            throw new ResponseStatusException(status, errorMessage);
        }
    }

    /**
     * Validates that the input is not null or empty.
     * If the input is invalid, throws a ResponseStatusException with a BAD_REQUEST status.
     */
    private void validateNonNullOrEmpty(String input, String fieldName) {
        if (input == null || input.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, fieldName + " cannot be null or empty");
        }
    }


    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return (ResponseEntity<List<Employee>>) handleRequest(employeeService::getAllEmployeeList, "getAllEmployees");
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(@PathVariable("searchString") String searchString) {
        validateNonNullOrEmpty(searchString, "Search String");
        return (ResponseEntity<List<Employee>>) handleRequest(() -> employeeService.getEmployeeBySearchName(searchString), "getEmployeesByNameSearch");
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") String id) {
        validateNonNullOrEmpty(id, "Employee ID");
        return (ResponseEntity<Employee>) handleRequest(() -> employeeService.getEmployeeById(id), "getEmployeeById");
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        return (ResponseEntity<Integer>) handleRequest(employeeService::getHighestSalaryOfEmployee, "getHighestSalaryOfEmployees");
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        return (ResponseEntity<List<String>>) handleRequest(employeeService::getTopTenHighestEarningEmployeeNames, "getTopTenHighestEarningEmployeeNames");
    }


    @Override
    public ResponseEntity createEmployee(Object employeeInput) {
        if (employeeInput == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee input cannot be null");
        }
        return handleRequest(() -> employeeService.createEmployee((Map<String, Object>) employeeInput), "createEmployee");
    }

    @Override
    public ResponseEntity<Object> deleteEmployeeById(@PathVariable("id") String id) {
        validateNonNullOrEmpty(id, "Employee ID");
        return (ResponseEntity<Object>) handleRequest(() -> employeeService.deleteEmployee(id), "deleteEmployeeById");
    }

}
