package com.example.tickets.domain.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//This return any error as a simple Json object
public class ErrorDto {
    private String error;
}
