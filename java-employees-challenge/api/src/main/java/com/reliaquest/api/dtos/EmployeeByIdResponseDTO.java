package com.reliaquest.api.dtos;

import lombok.Data;

@Data
public class EmployeeByIdResponseDTO {
    private String status;
    private Employee data;
}
