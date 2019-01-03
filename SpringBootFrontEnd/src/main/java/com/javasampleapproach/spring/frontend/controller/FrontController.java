package com.javasampleapproach.spring.frontend.controller;

import com.javasampleapproach.spring.frontend.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class FrontController {

    @Autowired
    RestTemplate restTemplate;


    @Value("${target.service.name}")
    private String targetServiceName;


    @GetMapping("/books")
    public List<Book> getAllBooks() {
        System.out.println("Get all Books in FrontEnd");

        String url = "http://" + targetServiceName + "/api/books";

        System.out.println("Request Uri: " + url);

//        return restTemplate.postForObject(url, null, List.class);
        List<Book> resultList = restTemplate.getForObject(url, List.class);

        System.out.println("Response Data: " + resultList.toString());

        return resultList;
    }
}
