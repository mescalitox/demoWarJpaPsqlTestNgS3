package com.example.demoWarJpaPsqlTestNgS3.storage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

/**
 * @author dpetrut
 */
@Component
public class S3ServiceProvider {

    @Autowired
    private StorageProperties storageProperties;

    public AmazonS3 getS3Client() {
        AmazonS3ClientBuilder s3Builder = null;

        // proxy
        ClientConfiguration config = null;

        if (StringUtils.isNotBlank(storageProperties.getProxyHost())) {
            config = new ClientConfiguration();
            config.setProxyHost(storageProperties.getProxyHost());
            if (StringUtils.isNotBlank(storageProperties.getProxyPort())) {
                config.setProxyPort(Integer.valueOf(storageProperties.getProxyPort()));
            }
            config.setSocketTimeout(5000);// ms
            config.setProxyUsername(storageProperties.getProxyUser());
            config.setProxyPassword(storageProperties.getProxyPassword());
        }

        // end point configuration
        String endPointValue = storageProperties.getEndpoint();
        boolean isEndPoint = StringUtils.isNotBlank(endPointValue);
        if (isEndPoint && !isEndPointAmazon(endPointValue)) {
            if (config == null) {
                config = new ClientConfiguration();
            }
            config.setSignerOverride("AWSS3V4SignerType");
        }

        AWSCredentials credentials = new BasicAWSCredentials(storageProperties.getCredentialAccessKey(),
                storageProperties.getCredentialSecretKey());

        s3Builder = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials));
        if (config != null) {
            s3Builder.withClientConfiguration(config);
        }

        // region
        String regionName = storageProperties.getRegion();
        if (StringUtils.isNotBlank(regionName) && !isEndPoint) {
            // Region region = RegionUtils.getRegion(regionName);
            s3Builder.setRegion(regionName);
        }

        // end point configuration
        if (isEndPoint) {
            AwsClientBuilder.EndpointConfiguration epConfig = new AwsClientBuilder.EndpointConfiguration(endPointValue,
                    regionName);
            s3Builder.setEndpointConfiguration(epConfig);
            if (!isEndPointAmazon(endPointValue)) {
                s3Builder.setPathStyleAccessEnabled(true);
            }
        }

        return s3Builder.build();
    }

    private boolean isEndPointAmazon(String endPointValue) {
        return endPointValue.indexOf("amazonaws") != -1;
    }
}
