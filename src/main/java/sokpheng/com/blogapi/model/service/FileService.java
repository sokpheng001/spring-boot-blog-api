package sokpheng.com.blogapi.model.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sokpheng.com.blogapi.model.dto.FileResponseDto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    @Value("${file.target}")
    private String uploadFolder;
    @Value("${file.media-url}")
    private String filePreviewUrl;
    @Value("${url.base-url}")
    private String baseUrl;
    public List<FileResponseDto> uploadFiles(List<MultipartFile> files) throws IOException {
        List<FileResponseDto> fileResponseDtos = new ArrayList<>();
        for(MultipartFile multipartFile: files){
            String filename = multipartFile.getOriginalFilename();
            if (filename == null ||
                    (!filename.toLowerCase().endsWith(".jpg") &&
                            !filename.toLowerCase().endsWith(".jpeg") &&
                            !filename.toLowerCase().endsWith(".png"))) {

                throw new IOException("File must be png, jpg, or jpeg");
            }
           fileResponseDtos.add(uploadFile(multipartFile));
        }
        return fileResponseDtos;
    }
    public String getRootDirectory(){
        File currentDir = new File(System.getProperty("user.dir"));
        return currentDir.getAbsolutePath();
    }
    private FileResponseDto uploadFile(MultipartFile multipartFile) throws IOException {
        String newFileName =  UUID.randomUUID()
                + "." + multipartFile.getOriginalFilename().split("\\.")[1];
        try(BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                new FileOutputStream(getRootDirectory() + "/" + uploadFolder +newFileName)
        )) {
            bufferedOutputStream.write(multipartFile.getBytes());
            bufferedOutputStream.flush();
        }catch (IOException exception){
            throw new IOException(exception.getMessage());
        }
        return new FileResponseDto(
                newFileName,
                multipartFile.getSize(),
                baseUrl + filePreviewUrl+"/"+newFileName
        );
    }
}
