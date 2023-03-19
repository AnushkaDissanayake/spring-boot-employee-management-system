package com.gtngroup.ems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeDTO {
    private int empId;
    private String empName;
    private String empAddress;
    private String empContact;
}
