/**
 * 
 */
package com.example.demoWarJpaPsqlTestNgS3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author x173117
 *
 */
@WebAppConfiguration
public class StorageWsTest extends TestSetUp {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeMethod
    public void setupUpload() throws NoSuchFieldException, IllegalAccessException {
        // mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testUpload() {

    }

}
