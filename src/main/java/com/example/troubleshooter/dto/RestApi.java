package com.example.troubleshooter.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class RestApi {
    private String Message;
    private HttpStatus httpStatus;
}
