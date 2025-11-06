package com.youtube.crud.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;



@RestController
@CrossOrigin(origins = "*") // tighten in prod
public class TestController {

	  @GetMapping("/hello")
	  public String get(@PathVariable Long id) {
	    return "Hello World!";
	  }
	
}
