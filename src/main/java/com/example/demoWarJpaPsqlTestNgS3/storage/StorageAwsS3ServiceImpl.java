/**
 *
 */
package com.example.demoWarJpaPsqlTestNgS3.storage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CopyObjectResult;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.MultiObjectDeleteException;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

/**
 * @author jaubert
 */
@Service
public class StorageAwsS3ServiceImpl implements StorageService {

    /**
     * The Constant log.
     */
    private static final Logger log = LoggerFactory.getLogger(StorageAwsS3ServiceImpl.class);

    @Autowired
    private S3ServiceProvider s3ServiceProvider;

    @Autowired
    private StorageProperties storageProperties;

    /*
     * (non-Javadoc)
     * 
     * @see com.rn.rtm.storage.StorageService#downloadFile(java.lang.String)
     */
    @Override
    public StorageDownloadedFileDto downloadFile(String key) {
        if (StringUtils.isBlank(key)) {
            throw new StorageParameterException("key");
        }
        try {
            AmazonS3 s3client = s3ServiceProvider.getS3Client();
            if (!s3client.doesObjectExist(storageProperties.getBucketName(), getMainDirectory() + key)) {
                log.error("key : " + key);
                throw new StorageParameterException("file doesnt exist");
            }
            StorageDownloadedFileDto downloadedFileDto = new StorageDownloadedFileDto();
            S3Object s3Object = s3client.getObject(storageProperties.getBucketName(), getMainDirectory() + key);
            downloadedFileDto.setMime(s3Object.getObjectMetadata().getContentType());
            S3ObjectInputStream objectContent = s3Object.getObjectContent();
            byte[] bytes = IOUtils.toByteArray(objectContent);
            downloadedFileDto.setFile(bytes);
            objectContent.close();
            return downloadedFileDto;
        } catch (AmazonServiceException ase) {
            log.error("key : " + key);
            throw new StorageAccessException(ase);
        } catch (AmazonClientException ace) {
            log.error("key : " + key);
            throw new StorageAccessException(ace);
        } catch (IOException e) {
            log.error("key : " + key);
            throw new StorageAccessException(e);
        }

    }

    /**
     * get main directory
     *
     * @return name
     */
    private String getMainDirectory() {
        String name = "";
        if (StringUtils.isNotBlank(storageProperties.getMainDirectory())) {
            name = storageProperties.getMainDirectory() + "/";
        }
        return name;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.rn.rtm.storage.StorageService#uploadFile(com.rn.rtm.storage.dto.
     * UploadFileDto)
     */
    @Override
    public StorageDownloadedFileDto uploadFile(StorageUploadFileDto uploadFileDto) {

        if (uploadFileDto == null) {
            throw new StorageParameterException("uploadFileParameters");
        }
        if (StringUtils.isBlank(uploadFileDto.getKey())) {
            throw new StorageParameterException("key");
        }
        // file null, file detail null or filename initial null
        if (uploadFileDto.getUploadInputStream() == null || StringUtils.isBlank(uploadFileDto.getName())) {
            throw new StorageParameterException("file");
        }

        StorageDownloadedFileDto downloadedFileDto = null;
        ObjectMetadata objectMetadata = new ObjectMetadata();
        byte[] bytes;
        try {
            bytes = IOUtils.toByteArray(uploadFileDto.getUploadInputStream());
            int fileLength = bytes.length;

            if (storageProperties.getMaxFileSize() != null
                    && fileLength > Integer.valueOf(storageProperties.getMaxFileSize()) * 1024) {
                throw new StorageParameterException(" exceeded filesize");
            }

            String mime = URLConnection.guessContentTypeFromName(uploadFileDto.getName());
            objectMetadata.setContentType(mime);
            objectMetadata.setContentLength(fileLength);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

            AmazonS3 s3client = s3ServiceProvider.getS3Client();
            if (!s3client.doesBucketExistV2(storageProperties.getBucketName())) {
                s3client.createBucket(storageProperties.getBucketName());
            }
            PutObjectResult result = s3client.putObject(storageProperties.getBucketName(),
                    getMainDirectory() + uploadFileDto.getKey(), byteArrayInputStream, objectMetadata);
            downloadedFileDto = new StorageDownloadedFileDto();
            downloadedFileDto.setFile(bytes);
            downloadedFileDto.setMime(mime);
        } catch (AmazonServiceException ase) {
            throw new StorageAccessException(ase);
        } catch (AmazonClientException ace) {
            throw new StorageAccessException(ace);
        } catch (IOException e) {
            throw new StorageParameterException("file");
        }

        return downloadedFileDto;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.rn.rtm.storage.StorageService#removeFile(java.lang.String)
     */
    @Override
    public boolean removeFile(String key) {
        if (StringUtils.isBlank(key)) {
            throw new StorageParameterException("key");
        }

        boolean removeOk = false;
        try {
            AmazonS3 s3client = s3ServiceProvider.getS3Client();
            s3client.deleteObject(storageProperties.getBucketName(), getMainDirectory() + key);
            removeOk = true;
        } catch (AmazonServiceException ase) {
            log.error("key : " + key);
            throw new StorageAccessException(ase);
        } catch (AmazonClientException ace) {
            log.error("key : " + key);
            throw new StorageAccessException(ace);
        }
        return removeOk;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.rn.rtm.storage.StorageService#removeFileList(java.util.List)
     */
    @Override
    public boolean removeFileList(List<String> nameList) {
        if (nameList == null || nameList.size() == 0) {
            throw new StorageParameterException("key list");
        }

        boolean removeOk = false;
        try {
            AmazonS3 s3client = s3ServiceProvider.getS3Client();
            DeleteObjectsRequest request = new DeleteObjectsRequest(storageProperties.getBucketName());
            List<KeyVersion> keys = new ArrayList<KeyVersion>();
            for (String name : nameList) {
                keys.add(new KeyVersion(getMainDirectory() + name));
            }
            request.setKeys(keys);

            s3client.deleteObjects(request);
            removeOk = true;
        } catch (MultiObjectDeleteException mde) {
            log.error("keys : " + nameList.toString());
            throw new StorageAccessException(mde);
        } catch (AmazonServiceException ase) {
            log.error("keys : " + nameList.toString());
            throw new StorageAccessException(ase);
        } catch (AmazonClientException ace) {
            log.error("keys : " + nameList.toString());
            throw new StorageAccessException(ace);
        }
        return removeOk;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.rn.rtm.storage.StorageService#moveFile(java.lang.String,
     * java.lang.String)
     */
    @Override
    public boolean moveFile(String previousName, String newName) {

        if (StringUtils.isBlank(previousName)) {
            throw new StorageParameterException("previousName");
        }
        if (StringUtils.isBlank(newName)) {
            throw new StorageParameterException("newName");
        }

        boolean moveOk = false;
        try {
            AmazonS3 s3client = s3ServiceProvider.getS3Client();
            CopyObjectResult result = s3client.copyObject(storageProperties.getBucketName(),
                    getMainDirectory() + previousName, storageProperties.getBucketName(), getMainDirectory() + newName);
            s3client.deleteObject(storageProperties.getBucketName(), getMainDirectory() + previousName);
            moveOk = true;
        } catch (AmazonServiceException ase) {
            log.error("previous key: " + previousName + ". new key: " + newName);
            throw new StorageAccessException(ase);
        } catch (AmazonClientException ace) {
            log.error("previous key: " + previousName + ". new key: " + newName);
            throw new StorageAccessException(ace);
        }

        return moveOk;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.rn.rtm.storage.StorageService#copyFile(java.lang.String,
     * java.lang.String)
     */
    @Override
    public boolean copyFile(String name, String newName) {
        if (StringUtils.isBlank(name)) {
            throw new StorageParameterException("name");
        }
        if (StringUtils.isBlank(newName)) {
            throw new StorageParameterException("newName");
        }
        boolean copyOk = false;
        try {
            AmazonS3 s3client = s3ServiceProvider.getS3Client();
            CopyObjectResult result = s3client.copyObject(storageProperties.getBucketName(), getMainDirectory() + name,
                    storageProperties.getBucketName(), getMainDirectory() + newName);
            copyOk = true;
        } catch (AmazonServiceException ase) {
            log.error("name key: " + name + ". new key: " + newName);
            throw new StorageAccessException(ase);
        } catch (AmazonClientException ace) {
            log.error("name key: " + name + ". new key: " + newName);
            throw new StorageAccessException(ace);
        }

        return copyOk;
    }

}
