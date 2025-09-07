package com.youtube.crud.controller;

import org.springframework.web.multipart.MultipartFile;



import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity @Table(name = "students")
public class StudentEntity {
	 public StudentEntity(){}
	 public StudentEntity(String firstName, String lastName, String email, MultipartFile photo) {
		   
	 }
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;

	  @Column(nullable = false)
	  private String firstName;

	  @Column(nullable = false)
	  private String lastName;

	  @Column(nullable = false, unique = true)
	  private String email;
	  @Column(name="image_name",  nullable = false)
	  private String imageName;
	  

	  // Store image bytes directly in DB (MEDIUMBLOB by default for byte[])
	  @Lob
	  @Basic(fetch = FetchType.LAZY)
	  private byte[] photo;

	  private String photoContentType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getPhotoContentType() {
		return photoContentType;
	}

	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
}
