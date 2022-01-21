package com.example.DemoTest.core.upload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


public class UploadFile {
//    @Value("${url.avata}")
//    private String urlImage1;
    private String urlImage ="/home/nguyen/Desktop";

    public String upload(Long id,MultipartFile file) throws IOException {
        System.out.println(file);
        String url= String.format("%s/DemoTest/Image/%s",urlImage,id);
        file.transferTo(new File(url));
        return url ;
    }
    
}
