package sokpheng.com.blogapi.controller;

import sokpheng.com.blogapi.utils.ResponseTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class ResponseData<T>{
    public ResponseTemplate<T> get(String status,
                                   String message,
                                   T data){
        return ResponseTemplate
                .<T>builder()
                .sign(UUID.randomUUID().toString())
                .status(status)
                .message(message)
                .date(Date.from(Instant.now()))
                .data(data)
                .build();
    }
}
