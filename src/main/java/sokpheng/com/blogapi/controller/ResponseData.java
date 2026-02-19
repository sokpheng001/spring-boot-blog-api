package sokpheng.com.blogapi.controller;

import org.springframework.http.HttpStatus;
import sokpheng.com.blogapi.model.dto.BlogResponseDto;
import sokpheng.com.blogapi.utils.ResponseTemplate;

import java.time.LocalDate;
import java.util.List;
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
                .date(LocalDate.now())
                .data(data)
                .build();
    }
}
