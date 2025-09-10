package com.youtube.crud.controller;

import java.net.URI;
import java.util.Comparator;
import java.util.List;

import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.youtube.crud.dao.StudentRepository;
import com.youtube.crud.entity.StudentEntity;
import com.youtube.crud.entity.StudentResponse;
import com.youtube.crud.service.StudentService;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*") // tighten in prod
public class StudentController {
  private final StudentService service;
  private final StudentRepository repo;

  public StudentController(StudentService service, StudentRepository repo) {
    this.service = service;
    this.repo = repo;
  }

  // Create with multipart/form-data
  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<StudentResponse> create(
      @RequestPart("firstName") String firstName,
      @RequestPart("lastName") String lastName,
      @RequestPart("email") String email,
      @RequestPart(value = "photo", required = false) MultipartFile photo
  ) {
	StudentEntity saved = service.create(firstName, lastName, email, photo);
    StudentResponse body = toResponse(saved);
    return ResponseEntity.created(URI.create("/api/students/" + saved.getId())).body(body);
  }

//  @GetMapping
//  public List<StudentResponse> list() {
//    return repo.findAll().stream().map(this::toResponse)
//    	    .toList();
//  }
  @GetMapping
  public List<StudentResponse> list() {
	    return repo.findAll().stream()
	        .map(this::toResponse)
	        .sorted(Comparator.comparingLong(StudentResponse::id).reversed()) // sort by id DESC
	        .toList();
	}
  
  
  @GetMapping("/{id}")
  public StudentResponse get(@PathVariable Long id) {
    return toResponse(service.get(id));
  }

  // Serve image bytes
  @GetMapping("/{id}/photo")
  public ResponseEntity<byte[]> photo(@PathVariable Long id) {
	  StudentEntity s = service.get(id);
    if (s.getPhoto() == null) return ResponseEntity.notFound().build();
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(
            s.getPhotoContentType() != null ? s.getPhotoContentType() : MediaType.IMAGE_JPEG_VALUE))
        .cacheControl(CacheControl.noCache())
        .body(s.getPhoto());
  }

  private StudentResponse toResponse(StudentEntity s) {
    String url = "/api/students/" + s.getId() + "/photo";
    return new StudentResponse(s.getId(), s.getFirstName(), s.getLastName(), s.getEmail(),
        s.getPhoto() != null ? url : null);
  }
}
