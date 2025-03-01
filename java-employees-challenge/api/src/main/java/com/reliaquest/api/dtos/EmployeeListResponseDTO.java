package com.reliaquest.api.dtos;

import lombok.Data;
import java.util.List;

@Data
public class EmployeeListResponseDTO {

    private String status;
    private List<Employee> data;

}
