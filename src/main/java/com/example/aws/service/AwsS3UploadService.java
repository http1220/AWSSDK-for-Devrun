package com.example.aws.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.http.ContentStreamProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AwsS3UploadService extends AWSS3Service{



    //파일 업로드를 위한 요청서를 만듭니다.
    private PutObjectRequest getPutObjectRequest(String key , String contentType){

        return PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(contentType)
                .build();
    }


    //작성된 요청서와 S3Client 계정 정보를 통해 S3 버킷에 접근하고 지정된 파일을 업로드합니다.
    public void putS3(MultipartFile multipartFile, String key) throws IOException {

        String originalFilename = multipartFile.getOriginalFilename();
        String fieldName = multipartFile.getName();
        String contentType = multipartFile.getContentType();
        boolean empty = multipartFile.isEmpty();
        long fileSize = multipartFile.getSize();
        byte[] fileBytes = multipartFile.getBytes();

        System.out.println(
                originalFilename + "\n" +
                        fieldName + "\n" +
                        contentType + "\n" +
                        empty + "\n" +
                        fileSize + "\n"
        );

        InputStream file = multipartFile.getInputStream();

        long contentlength = multipartFile.getSize();
        RequestBody rb = RequestBody.fromInputStream(file,contentlength );

        PutObjectRequest uploadRequest = getPutObjectRequest(key , contentType);


        PutObjectResponse result = s3Client.putObject(uploadRequest, rb);

    }

}
