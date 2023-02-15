package com.example.spring03.dto;

import lombok.Data;

@Data
public class MailDto {
    private String email;
    private String title;
    private String message;
}