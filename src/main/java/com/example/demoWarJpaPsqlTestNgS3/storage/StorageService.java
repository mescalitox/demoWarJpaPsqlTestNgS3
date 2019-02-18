/**
 * 
 */
package com.example.demoWarJpaPsqlTestNgS3.storage;

import java.util.List;

/**
 * @author jaubert
 *
 */
public interface StorageService {

    /**
     * download file
     * 
     * 
     * @param name
     * @return StorageDownloadedFileDto
     * @throws StorageParameterException
     * @throws StorageAccessException
     */
    StorageDownloadedFileDto downloadFile(String name);

    /**
     * upload file
     * 
     * @param uploadFileDto
     * @return {@link StorageDownloadedFileDto}
     * @throws StorageParameterException
     * @throws StorageAccessException
     */
    StorageDownloadedFileDto uploadFile(StorageUploadFileDto uploadFileDto);

    /**
     * remove file
     * 
     * @param name
     * @return
     * @throws StorageParameterException
     * @throws StorageAccessException
     */
    boolean removeFile(String name);

    /**
     * remove file list
     * 
     * @param List<String>
     * @return
     * @throws StorageParameterException
     * @throws StorageAccessException
     */
    boolean removeFileList(List<String> nameList);

    /**
     * move file (copy + delete previous)
     * 
     * @param previousName
     * @param newName
     * @return
     * @throws StorageParameterException
     * @throws StorageAccessException
     */
    boolean moveFile(String previousName, String newName);

    /**
     * copy file (copy)
     * 
     * @param name
     * @param newName
     * @return
     * @throws StorageParameterException
     * @throws StorageAccessException
     */
    boolean copyFile(String name, String newName);

}
