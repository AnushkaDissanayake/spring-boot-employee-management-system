package com.gtngroup.ems.controller;


import com.gtngroup.ems.dto.EmployeeDTO;
import com.gtngroup.ems.dto.ResponseDTO;
import com.gtngroup.ems.service.EmployeeService;
import com.gtngroup.ems.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ResponseDTO responseDTO;
    @PostMapping(value = "/saveEmp")
    public ResponseEntity saveEmployee(@RequestBody EmployeeDTO employeeDTO){
        try{
           String response= employeeService.saveEmployee(employeeDTO);
           if (response.equals("00")){
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(employeeDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
           } else if (response.equals("06")) {
               responseDTO.setCode(VarList.RSP_DUPLICATED);
               responseDTO.setMessage("Employee already registered");
               responseDTO.setContent(employeeDTO);
               return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
           }else {
               responseDTO.setCode(VarList.RSP_FAIL);
               responseDTO.setMessage("Error");
               responseDTO.setContent(null);
               return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
           }
        }catch (Exception e){
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @PutMapping(value = "/updateEmp")
    public ResponseEntity updateEmployee(@RequestBody EmployeeDTO employeeDTO){
        try{
            String response= employeeService.updateEmployee(employeeDTO);
            if (response.equals("00")){
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(employeeDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            }
            responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
            responseDTO.setMessage("Failed Update");
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO,HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllEmp")
    public ResponseEntity getAllEmployees(){

        try{
            ArrayList<EmployeeDTO> employeeDTOList= employeeService.getAllEmployee();
            responseDTO.setCode(VarList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(employeeDTOList);
            return new ResponseEntity<>(responseDTO,HttpStatus.ACCEPTED);


        }catch (Exception e){
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchEmp/{empId}")
    public ResponseEntity getEmployeeById(@PathVariable int empId){
        try{
            EmployeeDTO employeeDTO= employeeService.getEmployeeById(empId);
            if (employeeDTO!=null){
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(employeeDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            }
            responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
            responseDTO.setMessage("Not Found Employee");
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO,HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
