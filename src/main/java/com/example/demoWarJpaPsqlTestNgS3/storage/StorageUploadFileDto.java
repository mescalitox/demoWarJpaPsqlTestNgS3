/**
 * 
 */
package com.example.demoWarJpaPsqlTestNgS3.storage;

import java.io.InputStream;
import java.io.Serializable;

/**
 * @author jaubert
 *
 */
public class StorageUploadFileDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1941586451256668055L;

    private InputStream uploadInputStream;

    private String name;

    private String key;

    /**
     * @return the uploadInputStream
     */
    public InputStream getUploadInputStream() {
        return uploadInputStream;
    }

    /**
     * @param uploadInputStream
     *            the uploadInputStream to set
     */
    public void setUploadInputStream(InputStream uploadInputStream) {
        this.uploadInputStream = uploadInputStream;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key
     *            the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
