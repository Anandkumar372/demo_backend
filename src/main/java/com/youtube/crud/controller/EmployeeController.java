package com.youtube.crud.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.youtube.crud.dao.EmployeeDao;
import com.youtube.crud.dao.StudentRepository;
import com.youtube.crud.entity.Employee;
import com.youtube.crud.entity.LoginRequest;
import com.youtube.crud.entity.StudentEntity;
import com.youtube.crud.entity.StudentResponse;
import com.youtube.crud.service.EmployeeService;

@RestController
@CrossOrigin("*")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    StudentRepository repo;
    @PostMapping("/save/employee")
    public Employee saveEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        if ("admin".equals(request.getPassword())) {
        	
        	String emailId=request.getUsername();
        	
            // Normally use JWT here
        	
        	StudentEntity  stlist=	repo.findByEmail(emailId);
        	
            return ResponseEntity.ok(stlist);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
    
    @PostMapping(path = "/api/employees",  consumes = {"multipart/form-data"})
    public ResponseEntity<Employee> saveEmployee(
    		@RequestPart("employee") Employee employee,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws Exception {
    	System.out.println("Inside save employee");
        if (file != null && !file.isEmpty()) {
        	 String uploadDir = "D://opt//ewrs_storage/";
        	 File f=new File(uploadDir);
        	 
        	 if(f.exists()) {
        		 String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                 Path filePath = Paths.get(uploadDir + fileName);
                 Files.write(filePath, file.getBytes());
				 System.out.println("Directory already exists");
			 } else {
				 f.mkdirs();
				 String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                 Path filePath = Paths.get(uploadDir + fileName);
                 Files.write(filePath, file.getBytes());
				 System.out.println("Directory created successfully");
			 }
			
        	 
        	
        }

        Employee saved = employeeDao.save(employee);
        return ResponseEntity.ok(saved);
    }

    @PutMapping(path = "/apiUpdate/employees",consumes = {"multipart/form-data"})
    public ResponseEntity<Employee> updateEmployee(
            @RequestPart("employee") Employee employee,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws Exception {
    	   String uploadDir = "D://opt//ewrs_storage/";
   	       File f=new File(uploadDir);
        if (file != null && !file.isEmpty()) {
        	 if(f.exists()) {
        		 String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                 Path filePath = Paths.get(uploadDir + fileName);
                 Files.write(filePath, file.getBytes());
				 System.out.println("Directory already exists");
			 } else {
				 f.mkdirs();
				 String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                 Path filePath = Paths.get(uploadDir + fileName);
                 Files.write(filePath, file.getBytes());
				 System.out.println("Directory created successfully");
			 }
        }

        Employee updated = employeeDao.save(employee);
        return ResponseEntity.ok(updated);
    }
    
    
    
    @GetMapping("/get/employee")
    public List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }
    
    @GetMapping("/sayhello")
    public String sayHello() {
        return "Hello World";
    }

    @GetMapping("/get/employee/{employeeId}")
    public Employee getEmployee(@PathVariable Integer employeeId) {
        return employeeService.getEmployees(employeeId);
    }

    @DeleteMapping("/delete/employee/{employeeId}")
    public void deleteEmployee(@PathVariable Integer employeeId) {
        employeeService.deleteEmployee(employeeId);
    }

    @PutMapping("/update/employee")
    public Employee updateEmployee(@RequestBody Employee employee) {
        return employeeService.updateEmployee(employee);
    }

    private StudentResponse toResponse(StudentEntity s) {
        String url = "/api/students/" + s.getId() + "/photo";
        return new StudentResponse(s.getId(), s.getFirstName(), s.getLastName(), s.getEmail(),
            s.getPhoto() != null ? url : null);
      }
}
