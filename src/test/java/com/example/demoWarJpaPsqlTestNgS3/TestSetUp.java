/**
 * 
 */
package com.example.demoWarJpaPsqlTestNgS3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

import com.example.demoWarJpaPsqlTestNgS3.storage.StorageProperties;

import io.findify.s3mock.S3Mock;

/**
 * @author x173117
 *
 */
@EnableConfigurationProperties({ StorageProperties.class })
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = DemoWarJpaPsqlTestNgS3Application.class)
public class TestSetUp extends AbstractTestNGSpringContextTests {

    private S3Mock s3Server;

    @Autowired
    private StorageProperties storageProperties;

    /**
     * 
     */
    public TestSetUp() {

    }

    @BeforeMethod
    public void setup() {
        if (s3Server == null) {

            String port = storageProperties.getEndpoint()
                    .substring(storageProperties.getEndpoint().lastIndexOf(":") + 1);
            s3Server = new S3Mock.Builder().withPort(Integer.parseInt(port)).withInMemoryBackend().build();
            s3Server.start();
        }
    }

    @AfterSuite
    public void cleanup() {
        s3Server.stop();
    }

}
