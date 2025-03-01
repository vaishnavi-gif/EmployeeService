package com.reliaquest.api.helper;

import com.reliaquest.api.dtos.Employee;
import com.reliaquest.api.service.impl.EmployeeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MockEmployeeData {

    private static final Logger logger = LoggerFactory.getLogger(MockEmployeeData.class);
    // Generate mock data for Employee objects
    public static List<Employee> getMockEmployees() {
        Employee employee1 = new Employee(
                UUID.randomUUID(), // Generate a random UUID
                "John Doe", // Valid name
                80000.00, // Valid positive salary
                "30", // Valid age as string within regex pattern
                "profileImage1.jpg" // Profile image (can be null or a string)
        );

        Employee employee2 = new Employee(
                UUID.randomUUID(), // Generate another random UUID
                "Jane Smith", // Valid name
                95000.00, // Valid positive salary
                "28", // Valid age as string within regex pattern
                "profileImage2.jpg" // Profile image (can be null or a string)
        );

        return Arrays.asList(employee1, employee2);
    }

    // Mock employee data by ID
    public static Employee getMockEmployeeById(String id) {
        return new Employee(
                UUID.randomUUID(), // Generate a random UUID for the mock employee
                "John Doe", // Valid name
                80000.00, // Valid positive salary
                "30", // Valid age
                "profileImage1.jpg" // Profile image
        );
    }

    // Mock input data for creating an employee
    public static Map<String, Object> getMockEmployeeInput() {
        Map<String, Object> input = new HashMap<>();
        input.put("employeeName", "John Doe");
        input.put("employeeSalary", 80000.00); // Positive salary
        input.put("employeeAge", "30"); // Age between 0 and 100 years
        input.put("profileImage", "profileImage1.jpg");
        return input;
    }

    // Utility to generate a random UUID for tests
    public static UUID generateRandomUUID() {
        return UUID.randomUUID();
    }
}
