/**
 * 
 */
package com.example.demoWarJpaPsqlTestNgS3;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demoWarJpaPsqlTestNgS3.storage.StorageDownloadedFileDto;
import com.example.demoWarJpaPsqlTestNgS3.storage.StorageService;
import com.example.demoWarJpaPsqlTestNgS3.storage.StorageUploadFileDto;

/**
 * @author x173117
 *
 *         ne pas oublier le context
 *         http://localhost:8080/demo2warClassic/greeting
 *
 */
@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private GreetingService greetingService;

    @Autowired
    private StorageService storageService;

    @GetMapping("/greeting")
    public GreetingDto greeting(@PathParam(value = "name") String name) {
        if (name == null) {
            name = "test";
        }
        return new GreetingDto(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping("/greetingExpected")
    public ExpectedReturnDto<GreetingDto> greetingExpected(@PathParam(value = "name") String name) {

        ExpectedReturnDto<GreetingDto> expectedReturnDto = new ExpectedReturnDto<>();
        expectedReturnDto.setReturn(new GreetingDto(counter.incrementAndGet(), String.format(template, name)));
        return expectedReturnDto;
    }

    @GetMapping("/greetings")
    public List<GreetingDto> greetings() {
        return greetingService.findAll();
    }

    @GetMapping("/greetingsExpected")
    public ExpectedReturnDto<?> greetingsExpected() {
        return greetingService.findAllExpected();
    }

    @PostMapping("/addGreeting")
    public GreetingDto addGreeting(@RequestBody GreetingDto greetingParamDto) {
        return greetingService.save(greetingParamDto);
    }

    @PostMapping("/addGreetingExpected")
    public ExpectedReturnDto<?> addGreetingExpected(@RequestBody GreetingDto greetingParamDto) {
        return greetingService.saveExpected(greetingParamDto);
    }

    @PostMapping("/addGreeting2")
    public GreetingDto addGreeting2(@RequestBody GreetingDto greetingParamDto) {
        return greetingService.save2(greetingParamDto);
    }

    @PostMapping("/addGreeting2Expected")
    public ExpectedReturnDto<?> addGreeting2Expected(@RequestBody GreetingDto greetingParamDto) {
        return greetingService.save2Expected(greetingParamDto);
    }

    /**
     * Download file.
     *
     * responseEntity permet de préciser le header, content type
     *
     * @param docId
     *            the doc id
     * @param request
     *            the request
     * @return the response
     */
    @GetMapping("/getFile")
    public ResponseEntity<Resource> getFile(@PathParam("name") String name) {

        StorageDownloadedFileDto dto = storageService.downloadFile(name);

        Resource resource = new ByteArrayResource(dto.getFile());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name);

        return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType(dto.getMime())).body(resource);
    }

    @PostMapping("/uploadFile")
    public StorageDownloadedFileDto uploadFile(@RequestParam("file") MultipartFile file,
            @RequestParam(name = "name2", required = false) String name2) throws IOException {

        StorageUploadFileDto uploadFileDto = new StorageUploadFileDto();
        uploadFileDto.setKey(name2);
        uploadFileDto.setName(file.getOriginalFilename());
        uploadFileDto.setUploadInputStream(file.getInputStream());
        StorageDownloadedFileDto downloadedDto = storageService.uploadFile(uploadFileDto);
        return downloadedDto;
    }

    @PostMapping("/uploadFileGreeting")
    public GreetingDto uploadFileAndAddGreeting(@RequestParam("file") MultipartFile file,
            @RequestParam("name2") String name2) throws IOException {

        return greetingService.uploadFileAndSave(file, name2);
    }

}
