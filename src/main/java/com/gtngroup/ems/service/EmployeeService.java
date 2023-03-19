package com.gtngroup.ems.service;

import com.gtngroup.ems.dto.EmployeeDTO;
import com.gtngroup.ems.entity.Employee;
import com.gtngroup.ems.repo.EmployeeRepo;
import com.gtngroup.ems.util.VarList;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private ModelMapper modelMapper;

    public String saveEmployee(EmployeeDTO employeeDTO){
        if (employeeRepo.existsById(employeeDTO.getEmpId())){
            return VarList.RSP_DUPLICATED;
        }
        employeeRepo.save(modelMapper.map(employeeDTO, Employee.class));
        return VarList.RSP_SUCCESS;
    }

    public String updateEmployee(EmployeeDTO employeeDTO){
        if (employeeRepo.existsById(employeeDTO.getEmpId())) {
            employeeRepo.save(modelMapper.map(employeeDTO,Employee.class));
            return VarList.RSP_SUCCESS;
        }
        return VarList.RSP_NO_DATA_FOUND;
    }

    public ArrayList<EmployeeDTO> getAllEmployee(){
        ArrayList<Employee> employees = new ArrayList<>();
        employees = (ArrayList<Employee>) employeeRepo .findAll();
        return modelMapper.map(employees, new TypeToken<ArrayList<EmployeeDTO>>(){}.getType());
    }

    public EmployeeDTO getEmployeeById(int empId){
        if (employeeRepo.existsById(empId)){
            Employee employee=employeeRepo.findById(empId).orElse(null);
            return modelMapper.map(employee,EmployeeDTO.class);
        }
        return null;
    }
}
