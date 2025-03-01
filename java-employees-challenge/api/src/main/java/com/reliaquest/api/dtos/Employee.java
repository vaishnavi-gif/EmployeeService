package com.reliaquest.api.dtos;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Comparable<Employee> {

    @NotNull
    private UUID id;

    @NotNull
    private String employeeName;

    @Positive
    private Double employeeSalary; // Prefer using a numeric type for salaries

    @NotNull
    @Pattern(regexp = "^[0-9]{2,3}$", message = "Age must be between 0 and 100 years")
    private String employeeAge; // You can make this more appropriate with a numeric type if needed

    private String profileImage;

    @Override
    public int compareTo(Employee o) {
        return Double.compare(o.employeeSalary, this.employeeSalary); // Compare salaries in descending order
    }

}
