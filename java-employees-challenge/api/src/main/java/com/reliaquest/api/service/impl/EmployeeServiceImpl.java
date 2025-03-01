package com.reliaquest.api.service.impl;

import com.reliaquest.api.constants.ApiConstants;
import com.reliaquest.api.dtos.Employee;
import com.reliaquest.api.dtos.EmployeeByIdResponseDTO;
import com.reliaquest.api.dtos.EmployeeListResponseDTO;
import com.reliaquest.api.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    public WebClient webClient;

    private final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Override
    public List<Employee> getAllEmployeeList() {
        logger.atInfo().log("EmployeeService|getAllEmployeeList|Entry");

        Mono<EmployeeListResponseDTO> employeeMono = webClient.get().uri(ApiConstants.REST_API_URI_EMPLOYEE)
                .retrieve().bodyToMono(EmployeeListResponseDTO.class)
                .onErrorResume(e -> {
                    logger.error("Error retrieving employee data list: {}", e.getMessage());
                    return Mono.just(new EmployeeListResponseDTO()); // Fallback to empty list
                });

        logger.debug("EmployeeService|getAllEmployeeList|Exit");
        return employeeMono.block().getData();
    }

    @Override
    public Employee getEmployeeById(String id) {
        logger.debug("EmployeeService|getEmployeeById|Entry");

        Mono<EmployeeByIdResponseDTO> employeeMono = webClient.get()
                .uri(ApiConstants.REST_API_URI_EMPLOYEE + "/" + id).retrieve()
                .bodyToMono(EmployeeByIdResponseDTO.class);

        logger.debug("EmployeeService|getEmployeeById|Exit");

        return employeeMono.block().getData();
    }

    @Override
    public List<Employee> getEmployeeBySearchName(String searchString) {
        logger.debug("EmployeeService|getEmployeeBySearchName|Entry");

        List<Employee> employeeList = getAllEmployeeList();

        logger.debug("EmployeeService|getEmployeeBySearchName|Exit");

        return employeeList.stream().filter(emp -> emp.getEmployeeName().toLowerCase().contains(searchString.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Integer getHighestSalaryOfEmployee() {
        logger.debug("EmployeeService|getHighestSalaryOfEmployee|Entry");

        List<Employee> employeeList = getAllEmployeeList();
        logger.debug("EmployeeService|getHighestSalaryOfEmployee|Exit");

        return employeeList.stream().map(emp -> emp.getEmployeeSalary()).mapToInt(Double::intValue).max().getAsInt();
    }

    @Override
    public List<String> getTopTenHighestEarningEmployeeNames() {
        logger.debug("EmployeeService|getTopTenHighestEarningEmployeeNames|Entry");

        List<Employee> employeeList = getAllEmployeeList();

        logger.debug("EmployeeService|getTopTenHighestEarningEmployeeNames|Exit");

        return employeeList.stream().sorted().map(emp -> emp.getEmployeeName()).limit(10).collect(Collectors.toList());
    }

    @Override
    public Object createEmployee(Map<String, Object> employeeInput) {
        logger.debug("EmployeeService|createEmployee|Entry");

        Object employeeMono = webClient.post().uri(ApiConstants.REST_API_URI_EMPLOYEE)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(employeeInput)).retrieve().bodyToMono(Map.class).block();

        logger.debug("EmployeeService|createEmployee|Exit");

        return employeeMono;
    }

    @Override
    public Object deleteEmployee(String id) {
        logger.debug("EmployeeService|deleteEmployee|Entry");

        // Step 1: Fetch Employee Details using the ID to get the name
        Employee employee = getEmployeeById(id);

        if (employee == null || employee.getEmployeeName() == null) {
            throw new RuntimeException("Employee not found with ID: " + id);
        }

        // Step 3: Send DELETE request with the name in the body
        Object response = webClient.method(HttpMethod.DELETE)
                .uri(ApiConstants.REST_API_URI_EMPLOYEE)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(Map.of("name", employee.getEmployeeName()))) // Sending only the name
                .retrieve()
                .bodyToMono(Object.class)
                .block();

        logger.debug("EmployeeService|deleteEmployee|Exit");

        return response;

    }


}
