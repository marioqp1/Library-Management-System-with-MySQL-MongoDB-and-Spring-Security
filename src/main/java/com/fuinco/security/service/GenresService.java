package com.fuinco.security.service;

import com.fuinco.security.dto.ApiResponse;
import com.fuinco.security.entity.Genres;
import com.fuinco.security.repository.GenresRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenresService {
    private final GenresRepository genresRepository;
    public GenresService(GenresRepository genresRepository) {
        this.genresRepository = genresRepository;
    }
    public ApiResponse<List<Genres>> getAllGenres() {
        ApiResponse<List<Genres>> response  = new ApiResponse<>();
        List<Genres> genres = genresRepository.findAll();
        response.setMessage("Successfully retrieved all genres");
        response.setSuccess(true);
        response.setStatusCode(200);
        response.setEntity(genres);
        return response;
    }
    public ApiResponse<Genres> getGenre(int genreId) {
        ApiResponse<Genres> response  = new ApiResponse<>();
        Optional<Genres> genres = genresRepository.findById(genreId);
        if (genres.isPresent()) {
            response.setMessage("Successfully retrieved genre");
            response.setSuccess(true);
            response.setStatusCode(200);
            response.setEntity(genres.get());
            return response;
        }
        response.setMessage("Genre not found");
        response.setSuccess(false);
        response.setStatusCode(404);
        return response;
    }
    public ApiResponse<Genres> createGenre(Genres genres) {
        ApiResponse<Genres> response  = new ApiResponse<>();
        genresRepository.save(genres);
        response.setMessage("Successfully created genre");
        response.setSuccess(true);
        response.setStatusCode(200);
        response.setEntity(genres);
        return response;
    }
    public ApiResponse<Genres> updateGenre(int genresId ,Genres genres) {
        ApiResponse<Genres> response  = new ApiResponse<>();
        if(genresRepository.findById(genresId).isPresent()) {
            genres.setId(genresId);
            genresRepository.save(genres);
            response.setMessage("Successfully updated genre");
            response.setSuccess(true);
            response.setStatusCode(200);
            response.setEntity(genres);
            return response;
        }
        response.setMessage("Genre not found");
        response.setSuccess(false);
        response.setStatusCode(404);
        return response;
    }
}
