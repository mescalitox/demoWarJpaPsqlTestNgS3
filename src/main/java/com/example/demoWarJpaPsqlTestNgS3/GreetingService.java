/**
 * 
 */
package com.example.demoWarJpaPsqlTestNgS3;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author x173117
 *
 */
public interface GreetingService {

    List<GreetingDto> findAll();

    GreetingDto save(GreetingDto greetingParamDto);

    GreetingDto save2(GreetingDto greetingParamDto);

    GreetingDto uploadFileAndSave(MultipartFile file, String name2) throws IOException;

    ExpectedReturnDto<List<GreetingDto>> findAllExpected();

    ExpectedReturnDto<GreetingDto> saveExpected(GreetingDto greetingParamDto);

    ExpectedReturnDto<GreetingDto> save2Expected(GreetingDto greetingParamDto);

    ExpectedReturnDto<GreetingDto> uploadFileAndSaveExpected(MultipartFile file, String name2) throws IOException;

}
