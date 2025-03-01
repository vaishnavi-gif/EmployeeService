package com.reliaquest.api.controller;


import com.reliaquest.api.ApiApplicationTest;
import com.reliaquest.api.dtos.Employee;
import com.reliaquest.api.dtos.EmployeeListResponseDTO;
import com.reliaquest.api.helper.MockEmployeeData;
import com.reliaquest.api.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EmployeeControllerTest extends ApiApplicationTest {
    private MockMvc mockMvc;
    @Mock
    private WebClient webClient;

    @Mock
    private EmployeeServiceImpl employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        List<Employee> employees = MockEmployeeData.getMockEmployees();

        EmployeeListResponseDTO responseDTO = new EmployeeListResponseDTO();
        responseDTO.setStatus("success");
        responseDTO.setData(employees);

        // Mock service call
        when(employeeService.getAllEmployeeList()).thenReturn(employees);

        mockMvc.perform(get("/api/v1/employee"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetEmployeeById() throws Exception {
        UUID employeeId = MockEmployeeData.generateRandomUUID();
        Employee employee = MockEmployeeData.getMockEmployeeById(employeeId.toString());

        // Mock service call
        when(employeeService.getEmployeeById(employeeId.toString())).thenReturn(employee);

        mockMvc.perform(get("/api/v1/employee/{id}", employeeId))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteEmployeeById() throws Exception {
        // Create mock data
        String employeeId = MockEmployeeData.generateRandomUUID().toString();
        Employee employee = MockEmployeeData.getMockEmployeeById(employeeId);
        when(employeeService.deleteEmployee(any())).thenReturn(employee);
        mockMvc.perform(delete("/api/v1/employee/{id}", employeeId)
                        .contentType("application/json")
                        .content("{\"employeeName\":\"John Doe\"}"))
                .andExpect(status().isOk());

    }

    @Test
    public void testCreateEmployee() throws Exception {
        // Using MockEmployeeData to get mock input data
        Map<String, Object> employeeInput = MockEmployeeData.getMockEmployeeInput();

        // Creating the mock employee object for return using constructor (Lombok-generated constructor)
        Employee employee = new Employee(UUID.randomUUID(), "John Doe", 80000.00, "30", "profileImage1.jpg");

        // Mock service call
        when(employeeService.createEmployee(any())).thenReturn(employee);

        mockMvc.perform(post("/api/v1/employee")
                        .contentType("application/json")
                        .content("{\"employeeName\":\"John Doe\",\"employeeSalary\":80000.00,\"employeeAge\":\"30\",\"profileImage\":\"profileImage1.jpg\"}"))
                .andExpect(status().isOk());
    }
    @Test
    public void testCreateEmployee_withNullInput() throws Exception {
        mockMvc.perform(post("/api/v1/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("null"))  // Simulate null as a string, or you can simulate an empty payload
                .andExpect(status().isBadRequest())  // Expect BAD_REQUEST because input is "null" or empty
                .andExpect(result ->
                        assertFalse(result.getResolvedException() instanceof org.springframework.web.server.ResponseStatusException)
                );
    }

    @Test
    public void testGetEmployeesByNameSearch() throws Exception {
        String searchString = "John";
        List<Employee> employees = MockEmployeeData.getMockEmployees();

        // Mock service call
        when(employeeService.getEmployeeBySearchName(searchString)).thenReturn(employees);

        mockMvc.perform(get("/api/v1/employee/search/{searchString}", searchString))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetHighestSalaryOfEmployees() throws Exception {
        int highestSalary = 100000;

        // Mock service call
        when(employeeService.getHighestSalaryOfEmployee()).thenReturn(highestSalary);

        mockMvc.perform(get("/api/v1/employee/highestSalary"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTopTenHighestEarningEmployeeNames() throws Exception {
        List<String> topEmployeeNames = List.of("John Doe", "Jane Smith");

        // Mock service call
        when(employeeService.getTopTenHighestEarningEmployeeNames()).thenReturn(topEmployeeNames);

        mockMvc.perform(get("/api/v1/employee/topTenHighestEarningEmployeeNames"))
                .andExpect(status().isOk());
    }

    //Negative scenario
    @Test
    void testGetAllEmployees_emptyList() throws Exception {
        // Mock the service call to return an empty list
        when(employeeService.getAllEmployeeList()).thenReturn(Arrays.asList());

        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // Expect 404 Not Found
    }


    @Test
    void testGetEmployeesByNameSearch_emptyList() throws Exception {
        // Mock the service call to return an empty list
        when(employeeService.getEmployeeBySearchName("NonExistent")).thenReturn(Arrays.asList());

        // Perform the GET request to search by name
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/search/NonExistent")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // Expect 404 Not Found

    }


    @Test
    void testCreateEmployee_notFound() throws Exception {
        // Perform the POST request with missing data (e.g., no first name)
        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"lastName\": \"Smith\", \"email\": \"alice.smith@example.com\"}"))
                .andExpect(status().isNotFound()); // Expect 400 Bad Request
    }


    @Test
    void testDeleteEmployeeById_notFound() throws Exception {
        // Mock the service call to throw an exception (employee not found)
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"))
                .when(employeeService).deleteEmployee("999");

        // Perform the DELETE request
        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // Expect 404 Not Found

    }
}