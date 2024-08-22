package com.fuinco.security.controller;

import com.fuinco.security.dto.ApiResponse;
import com.fuinco.security.entity.Publisher;
import com.fuinco.security.service.PublisherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {
    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping("/create")
    public ApiResponse<Publisher> createPublisher(@RequestBody Publisher publisher) {
        return publisherService.addPublisher(publisher);
    }

    @PutMapping("/update")
    public ApiResponse<Publisher> updatePublisher(@RequestBody Publisher publisher) {
        return publisherService.updatePublisher(publisher);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Publisher> deletePublisher(@PathVariable int id) {
        return publisherService.deletePublisher(id);
    }

    @GetMapping
    public ApiResponse<List<Publisher>> getAllPublishers() {
        return publisherService.getAllPublishers();
    }

    @GetMapping("/{id}")
    public ApiResponse<Publisher> getPublisher(@PathVariable int id) {
        return publisherService.getPublisher(id);
    }
}
