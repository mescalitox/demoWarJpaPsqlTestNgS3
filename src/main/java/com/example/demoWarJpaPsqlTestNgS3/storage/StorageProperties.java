package com.example.demoWarJpaPsqlTestNgS3.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    private String proxyHost;
    private String proxyPort;
    private String proxyUser;
    private String proxyPassword;
    private String credentialAccessKey;
    private String credentialSecretKey;
    private String region;
    private String bucketName;
    private String mainDirectory;
    private String endpoint;
    private String subDirectoryTestFile;
    private String maxFileSize;

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public String getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(String proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getProxyUser() {
        return proxyUser;
    }

    public void setProxyUser(String proxyUser) {
        this.proxyUser = proxyUser;
    }

    public String getProxyPassword() {
        return proxyPassword;
    }

    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

    public String getCredentialAccessKey() {
        return credentialAccessKey;
    }

    public void setCredentialAccessKey(String credentialAccessKey) {
        this.credentialAccessKey = credentialAccessKey;
    }

    public String getCredentialSecretKey() {
        return credentialSecretKey;
    }

    public void setCredentialSecretKey(String credentialSecretKey) {
        this.credentialSecretKey = credentialSecretKey;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getMainDirectory() {
        return mainDirectory;
    }

    public void setMainDirectory(String mainDirectory) {
        this.mainDirectory = mainDirectory;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getSubDirectoryTestFile() {
        return subDirectoryTestFile;
    }

    public void setSubDirectoryTestFile(String subDirectoryTestFile) {
        this.subDirectoryTestFile = subDirectoryTestFile;
    }

    public String getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(String maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

}
