package com.fuinco.security.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ApiResponse<T> {
    private Integer statusCode;
    private Boolean success;
    private String message;
    private T entity;
}