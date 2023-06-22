package com.example.aws.controller;


import com.example.aws.service.AwsS3ReadService;
import com.example.aws.service.AwsS3UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpRequest;

@RestController
public class S3Controller {

    @Autowired
    private AwsS3UploadService awsS3UploadService;

    @Autowired
    private AwsS3ReadService awsS3ReadService;

    //S3에 파일을 지정된 버킷의 루트 디렉토리에 업로드합니다.
    @ResponseBody
    @PostMapping("/upload")
    public String fileupload(@RequestParam(name = "file") MultipartFile file , @RequestParam(name = "key") String key) throws IOException {

        awsS3UploadService.putS3(file , key);


        return "success";

    }

    //S3에 저장된 파일의 url을 입력하면, 해당 파일을 불러옵니다.
    @ResponseBody
    @GetMapping("/read")
    public String fileRead(@RequestParam(name = "key") String key){
        String result = awsS3ReadService.findUploadKeyUrl(key);
        return result;
    }
}
