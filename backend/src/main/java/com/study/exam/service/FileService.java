package com.study.exam.service;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final COSClient cosClient;

    @Value("${tencent.cos.bucket-name}")
    private String bucketName;

    public String uploadFile(MultipartFile file, String folder) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null ? 
                originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
        String fileName = folder + "/" + UUID.randomUUID() + extension;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        PutObjectRequest putObjectRequest = new PutObjectRequest(
                bucketName, 
                fileName, 
                file.getInputStream(), 
                metadata
        );

        PutObjectResult result = cosClient.putObject(putObjectRequest);
        
        // Return the URL of the uploaded file
        return "https://" + bucketName + ".cos." + cosClient.getClientConfig().getRegion().getRegionName() 
                + ".myqcloud.com/" + fileName;
    }

    public void deleteFile(String fileUrl) {
        try {
            String key = extractKeyFromUrl(fileUrl);
            cosClient.deleteObject(bucketName, key);
            log.info("File deleted successfully: {}", key);
        } catch (Exception e) {
            log.error("Failed to delete file: {}", fileUrl, e);
        }
    }

    private String extractKeyFromUrl(String fileUrl) {
        // Extract the key from the full URL
        int lastSlash = fileUrl.lastIndexOf("/");
        int secondLastSlash = fileUrl.lastIndexOf("/", lastSlash - 1);
        return fileUrl.substring(secondLastSlash + 1);
    }
}
