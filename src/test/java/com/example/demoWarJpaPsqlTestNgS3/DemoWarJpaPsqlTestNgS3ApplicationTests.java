package com.example.demoWarJpaPsqlTestNgS3;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.example.demoWarJpaPsqlTestNgS3.storage.StorageDownloadedFileDto;
import com.example.demoWarJpaPsqlTestNgS3.storage.StorageProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class DemoWarJpaPsqlTestNgS3ApplicationTests extends TestSetUp {

    // @Autowired
    // private WebTestClient webClient;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StorageProperties storageProperties;

    @Test
    public void contextLoads() {

        System.out.println(storageProperties);

    }

    @Test(dependsOnMethods = "exampleTestPost")
    public void exampleTest() {

        String body = restTemplate.getForObject("/rest/greetings", String.class);

        ObjectMapper mapper = new ObjectMapper();

        // JSONObject returnObj = new JSONObject(body);
        List<GreetingDto> list = null;
        try {
            // dto = mapper.readValue(body, GreetingDto.class);
            TypeFactory typeFactory = mapper.getTypeFactory();
            CollectionType collectionType = typeFactory.constructCollectionType(List.class, GreetingDto.class);
            list = mapper.readValue(body, collectionType);
        } catch (IOException e) {
            System.out.println("erreur");
        }

        System.out.println(list.get(0).getContent());
        assertThat(list.get(0).getContent()).isEqualTo("robert");

        // this.webClient.get().uri("/greetings").exchange().expectStatus().isOk().expectBody(String.class)
        // .isEqualTo("Hello World");
    }

    @Test
    public void exampleTestPost() {

        GreetingDto greetingParamDto = new GreetingDto();
        greetingParamDto.setContent("test");

        GreetingDto dto = restTemplate.postForObject("/rest/addGreeting", greetingParamDto, GreetingDto.class);

        GreetingDto[] list = restTemplate.getForObject("/rest/greetings", GreetingDto[].class);

        Assert.assertEquals(list.length, 4);
    }

    @Test
    public void exampleTestPostExpected() {

        GreetingDto greetingParamDto = new GreetingDto();
        greetingParamDto.setContent("test");

        ResponseEntity<String> response = restTemplate.postForEntity("/rest/addGreetingExpected", greetingParamDto,
                String.class);
        Assert.assertEquals(response.getStatusCodeValue(), HttpStatus.OK.value());

        GreetingDto dto = TestUtils.fromJsonToEntityDto(response.getBody(), GreetingDto.class);

        ResponseEntity<String> responseList = restTemplate.getForEntity("/rest/greetingsExpected", String.class);

        List<GreetingDto> list = TestUtils.fromJsonToEntityDtoList(responseList.getBody(), GreetingDto.class);

        Assert.assertEquals(list.size(), 4);
    }

    @Test
    public void exampleUpload() {

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        Resource file = new ClassPathResource("/storage/cle.jpg");
        map.add("file", file);
        map.add("name2", "dossier/my_key");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(
                map, headers);
        StorageDownloadedFileDto result = restTemplate.postForObject("/rest/uploadFile", requestEntity,
                StorageDownloadedFileDto.class);

        Assert.assertNotNull(result);
    }

    @Test(dependsOnMethods = "exampleUpload")
    public void exampleDownload() {

        String nameFile = "dossier/my_key";
        ResponseEntity<byte[]> reponse = restTemplate.getForEntity("/rest/getFile?name=" + nameFile, byte[].class);
        Assert.assertEquals(reponse.getStatusCodeValue(), HttpStatus.OK.value());

        Resource file2 = new ClassPathResource("/storage/cle.jpg");
        try {
            byte[] testResponse = Files.readAllBytes(file2.getFile().toPath());
            assertEquals(reponse.getBody(), testResponse);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
