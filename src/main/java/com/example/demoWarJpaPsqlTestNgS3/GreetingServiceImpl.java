/**
 * 
 */
package com.example.demoWarJpaPsqlTestNgS3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demoWarJpaPsqlTestNgS3.aop.ExecutionTime;
import com.example.demoWarJpaPsqlTestNgS3.storage.StorageDownloadedFileDto;
import com.example.demoWarJpaPsqlTestNgS3.storage.StorageService;
import com.example.demoWarJpaPsqlTestNgS3.storage.StorageUploadFileDto;

/**
 * @author x173117
 *
 */
@Service
public class GreetingServiceImpl implements GreetingService {

    private static final Logger log = LoggerFactory.getLogger(GreetingServiceImpl.class);

    @Autowired
    private GreetingDao greetingDao;

    @Autowired
    private StorageService storageService;

    @Autowired
    private ModelMapper modelMapper;

    /*
     * (non-Javadoc)
     * 
     * @see com.example.demoWarJerseyJpaPsqlAtm.GreetingService#findAll()
     */
    @Override
    public List<GreetingDto> findAll() {
        List<GreetingEntity> all = greetingDao.findAll();
        // java8...stream
        List<GreetingDto> list = new ArrayList<>();
        for (GreetingEntity eGreeting : all) {
            log.info(eGreeting.toString());
            // converter to dto
            list.add(new GreetingDto(eGreeting.getId(), eGreeting.getContent()));
        }
        log.info("");
        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.example.demoWarJerseyJpaPsqlAtm.GreetingService#save(com.example.
     * demoWarJerseyJpaPsqlAtm.GreetingDto)
     */
    @Override
    @Transactional
    public GreetingDto save(GreetingDto greetingParamDto) {
        if (greetingParamDto.getContent() == null || "".equals(greetingParamDto.getContent())) {
            throw new IllegalArgumentException("test");
        }

        GreetingEntity greeting = new GreetingEntity();
        greeting.setContent(greetingParamDto.getContent());
        GreetingEntity greeting2 = greetingDao.add(greeting);
        GreetingDto dto = modelMapper.map(greeting2, GreetingDto.class);
        // GreetingDto dto = new GreetingDto(greeting2.getId(), greeting2.getContent());
        return dto;
    }

    @Override
    @Transactional
    public GreetingDto save2(GreetingDto greetingParamDto) {
        GreetingEntity greeting = new GreetingEntity();
        greeting.setContent(greetingParamDto.getContent());
        GreetingEntity greeting2 = greetingDao.add(greeting);

        GreetingEntity greetingTest = new GreetingEntity();
        GreetingEntity greetingTest2 = greetingDao.add(greetingTest);

        // GreetingDto dto = modelMapper.map(greeting2, GreetingDto.class);

        GreetingDto dto = new GreetingDto(greeting2.getId(), greeting2.getContent());
        return dto;
    }

    @Override
    @Transactional
    public GreetingDto uploadFileAndSave(MultipartFile file, String name2) throws IOException {

        GreetingEntity greeting = new GreetingEntity();
        greeting.setContent(name2);
        GreetingEntity greetingSaved = greetingDao.add(greeting);

        StorageUploadFileDto uploadFileDto = new StorageUploadFileDto();
        uploadFileDto.setKey(name2);
        uploadFileDto.setName(file.getOriginalFilename());
        uploadFileDto.setUploadInputStream(file.getInputStream());
        StorageDownloadedFileDto downloadedDto = storageService.uploadFile(uploadFileDto);

        GreetingDto dto = new GreetingDto(greetingSaved.getId(), greetingSaved.getContent());
        return dto;

    }

    @Override
    @Transactional
    @ExecutionTime
    public ExpectedReturnDto<List<GreetingDto>> findAllExpected() {
        ExpectedReturnDto<List<GreetingDto>> expectedReturnDto = new ExpectedReturnDto<>();
        expectedReturnDto.setReturn(findAll());
        return expectedReturnDto;
    }

    @Override
    @Transactional
    public ExpectedReturnDto<GreetingDto> save2Expected(GreetingDto greetingParamDto) {
        ExpectedReturnDto<GreetingDto> expectedReturnDto = new ExpectedReturnDto<>();
        expectedReturnDto.setReturn(save2(greetingParamDto));
        return expectedReturnDto;
    }

    @Override
    @Transactional
    public ExpectedReturnDto<GreetingDto> saveExpected(GreetingDto greetingParamDto) {
        ExpectedReturnDto<GreetingDto> expectedReturnDto = new ExpectedReturnDto<>();
        expectedReturnDto.setReturn(save(greetingParamDto));
        return expectedReturnDto;
    }

    @Override
    @Transactional
    public ExpectedReturnDto<GreetingDto> uploadFileAndSaveExpected(MultipartFile file, String name2)
            throws IOException {
        ExpectedReturnDto<GreetingDto> expectedReturnDto = new ExpectedReturnDto<>();
        expectedReturnDto.setReturn(uploadFileAndSave(file, name2));
        return expectedReturnDto;
    }

}
