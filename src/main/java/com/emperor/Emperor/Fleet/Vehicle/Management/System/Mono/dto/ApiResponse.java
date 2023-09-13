package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

/**
 * ApiResponse
 */
@Data
public class ApiResponse<T> {

    private HttpStatus status;
    private String message;
    private String error;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @Column(columnDefinition = "DATETIME")
    //@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime timestamp;
    private String debugMessage;
    private T data;

    private ApiResponse() {
        timestamp = LocalDateTime.now();
    }

    public ApiResponse(HttpStatus status) {
        this();
        this.status = status;
    }

    public ApiResponse(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.error = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiResponse(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

}
