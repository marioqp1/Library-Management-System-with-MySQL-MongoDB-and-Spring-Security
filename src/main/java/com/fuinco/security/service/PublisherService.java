package com.fuinco.security.service;

import com.fuinco.security.dto.ApiResponse;
import com.fuinco.security.entity.Publisher;
import com.fuinco.security.repository.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherService {
    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public ApiResponse<Publisher> addPublisher(Publisher publisher) {
        ApiResponse<Publisher> response = new ApiResponse<>();
        try {
            Publisher publisherSaved = publisherRepository.save(publisher);
            response.setEntity(publisherSaved);
            response.setSuccess(true);
            response.setMessage("Publisher successfully added");
            response.setStatusCode(201); // Updated to 201 for resource creation
        } catch (Exception e) {
            response.setSuccess(false);
            response.setStatusCode(500);
            response.setMessage("Error adding publisher: " + e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    public ApiResponse<Publisher> updatePublisher(Publisher publisher) {
        ApiResponse<Publisher> response = new ApiResponse<>();
        if (!publisherRepository.existsById(publisher.getId())) {
            response.setSuccess(false);
            response.setStatusCode(404);
            response.setMessage("Publisher not found for update");
            return response;
        }

        try {
            Publisher updatedPublisher = publisherRepository.save(publisher);
            response.setEntity(updatedPublisher);
            response.setSuccess(true);
            response.setMessage("Publisher successfully updated");
            response.setStatusCode(200);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setStatusCode(500);
            response.setMessage("Error updating publisher: " + e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    public ApiResponse<Publisher> deletePublisher(int id) {
        ApiResponse<Publisher> response = new ApiResponse<>();
        Optional<Publisher> publisher = publisherRepository.findById(id);
        if (publisher.isPresent()) {
            try {
                publisherRepository.delete(publisher.get());
                response.setEntity(publisher.get());
                response.setSuccess(true);
                response.setMessage("Publisher successfully deleted");
                response.setStatusCode(200);
            } catch (Exception e) {
                response.setSuccess(false);
                response.setStatusCode(500);
                response.setMessage("Error deleting publisher: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            response.setSuccess(false);
            response.setStatusCode(404);
            response.setMessage("Publisher not found");
        }
        return response;
    }

    public ApiResponse<List<Publisher>> getAllPublishers() {
        ApiResponse<List<Publisher>> response = new ApiResponse<>();
        List<Publisher> publishers = publisherRepository.findAll();
        response.setEntity(publishers);
        response.setSuccess(true);
        response.setMessage("Retrieved all publishers");
        response.setStatusCode(200);
        return response;
    }

    public ApiResponse<Publisher> getPublisher(int id) {
        ApiResponse<Publisher> response = new ApiResponse<>();
        Optional<Publisher> publisher = publisherRepository.findById(id);
        if (publisher.isPresent()) {
            response.setEntity(publisher.get());
            response.setSuccess(true);
            response.setMessage("Publisher found");
            response.setStatusCode(200);
        } else {
            response.setSuccess(false);
            response.setStatusCode(404);
            response.setMessage("Publisher not found");
        }
        return response;
    }
}
