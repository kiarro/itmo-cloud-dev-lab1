package com.itmoclouddev.lab1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@SpringBootApplication
@RequestMapping("/")
@RestController
public class Lab1Application {

	public static void main(String[] args) {
		SpringApplication.run(Lab1Application.class, args);
	}

    @GetMapping("/test-message")
    public String getMsg() {
        return "It's Alive.\nTest message for lab 1 itmo cloud dev.";
    }
    
}