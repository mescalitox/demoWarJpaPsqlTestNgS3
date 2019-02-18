package com.example.demoWarJpaPsqlTestNgS3;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.example.demoWarJpaPsqlTestNgS3.storage.StorageDownloadedFileDto;
import com.example.demoWarJpaPsqlTestNgS3.storage.StorageParameterException;
import com.example.demoWarJpaPsqlTestNgS3.storage.StorageService;
import com.example.demoWarJpaPsqlTestNgS3.storage.StorageUploadFileDto;

/**
 * test du service upload
 */
public class StorageAwsS3ServiceTest extends TestSetUp {

    @Autowired
    private StorageService storageService;

    @Test
    public void testUploadS3() {
        StorageUploadFileDto uploadFileDto = new StorageUploadFileDto();
        uploadFileDto.setKey("test/key_cle");
        uploadFileDto.setName("cle.jpg");
        InputStream data = this.getClass().getResourceAsStream("/storage/cle.jpg");
        uploadFileDto.setUploadInputStream(data);
        StorageDownloadedFileDto storageDownloadedFileDto = storageService.uploadFile(uploadFileDto);

        Assert.assertNotNull(storageDownloadedFileDto);
        Assert.assertNotNull(storageDownloadedFileDto.getFile());
    }

    @Test(dependsOnMethods = "testUploadS3")
    public void testDownloadS3() {

        StorageDownloadedFileDto response = storageService.downloadFile("test/key_cle");
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getFile());
    }

    @Test(expectedExceptions = StorageParameterException.class)
    public void testDownloadS3NonExistingFile() {
        String fileKey = "form_icon/cle2.jpg";
        StorageDownloadedFileDto response = storageService.downloadFile(fileKey);
    }
}
