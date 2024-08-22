package com.fuinco.security.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class BookDto {
    private int id;
    @NotBlank
    private String isbn;
    @NotBlank
    private String title;
    private String description;
    @NotBlank
    private int copiesAvailable;
    @NotBlank
    private Date publishDate;
    @NotBlank
    private int genresId;
    @NotBlank
    private int publisherID;
}
