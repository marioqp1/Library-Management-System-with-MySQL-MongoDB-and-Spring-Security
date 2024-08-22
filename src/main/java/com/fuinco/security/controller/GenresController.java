package com.fuinco.security.controller;

import com.fuinco.security.dto.ApiResponse;
import com.fuinco.security.entity.Genres;
import com.fuinco.security.service.GenresService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenresController {
    private final GenresService genresService;

    public GenresController(GenresService genresService) {
        this.genresService = genresService;
    }

    @GetMapping
    public ApiResponse<List<Genres>> getAllGenres() {
        return genresService.getAllGenres();
    }

    @GetMapping("/{id}")
    public ApiResponse<Genres> getGenre(@PathVariable int id) {
        return genresService.getGenre(id);
    }

    @PostMapping("/create")
    public ApiResponse<Genres> createGenre(@RequestBody Genres genres) {
        return genresService.createGenre(genres);
    }

    @PutMapping("/update/{id}")
    public ApiResponse<Genres> updateGenre(@PathVariable int id, @RequestBody Genres genres) {
        return genresService.updateGenre(id, genres);
    }
}
