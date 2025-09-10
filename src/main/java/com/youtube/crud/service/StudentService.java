package com.youtube.crud.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.youtube.crud.dao.StudentRepository;
import com.youtube.crud.entity.StudentEntity;

@Service
public class StudentService {
  private final StudentRepository repo;

  public StudentService(StudentRepository repo) { this.repo = repo; }

  @Transactional
  public StudentEntity create(String firstName, String lastName, String email, MultipartFile photo) {
    if (repo.existsByEmail(email)) {
      throw new IllegalArgumentException("Email already exists");
    }

    try {
    	StudentEntity s = new StudentEntity();
          s.setFirstName(firstName);
          s.setLastName(lastName);
          s.setEmail(email);	
          

      if (photo != null && !photo.isEmpty()) {
    	  
    	  String uploadDir = "D://opt//ewrs_storage/";
     	 File f=new File(uploadDir);
     	 
     	 if(f.exists()) {
     		 String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
              Path filePath = Paths.get(uploadDir + fileName);
              Files.write(filePath, photo.getBytes());
				 System.out.println("Directory already exists");
				 s.setImageName(fileName);
			 } else {
				 f.mkdirs();
			 String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
              Path filePath = Paths.get(uploadDir + fileName);
              Files.write(filePath, photo.getBytes());
				 System.out.println("Directory created successfully");
				 s.setImageName(fileName);
			 }
    	  
    	  s.setPhotoContentType(photo.getContentType());
        //s.setPhoto(photo.getBytes());
        
      }
      return repo.save(s);
    } catch (Exception e) {
      throw new RuntimeException("Failed to create student", e);
    }
  }

  public StudentEntity get(Long id) {
	  
	  StudentEntity  st=  repo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
	  
	  try {
		  
		  String filePath = "D://opt//ewrs_storage/"+st.getImageName();
		  
		  System.out.println("File Path : "+filePath);
		  File file = new File(filePath);
		  byte[] data= Files.readAllBytes(file.toPath());
		  st.setPhoto(data);
      } catch (Exception e) {
          e.printStackTrace();
      }
	  
    return st;
  }
}
