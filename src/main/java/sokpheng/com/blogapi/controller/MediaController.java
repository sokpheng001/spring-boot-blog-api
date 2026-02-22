package sokpheng.com.blogapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sokpheng.com.blogapi.model.dto.FileResponseDto;
import sokpheng.com.blogapi.model.dto.UserResponseDto;
import sokpheng.com.blogapi.model.service.FileService;
import sokpheng.com.blogapi.utils.ResponseTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/v100/medias")
@RequiredArgsConstructor
public class MediaController {
    private final FileService fileService;
    @PostMapping("")
    @Operation(summary = "Upload files to server")
    public ResponseTemplate<List<FileResponseDto>> uploadFiles(@RequestParam List<MultipartFile> files){
        try{
            return new ResponseData<List<FileResponseDto>>()
                    .get(String.valueOf(HttpStatus.CREATED.value()),
                            "File uploaded successfully",
                            fileService.uploadFiles(files));
        }catch (Exception exception){
            return new ResponseData<List<FileResponseDto>>()
                    .get(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                            exception.getMessage(),
                            null);
        }
    }
}
