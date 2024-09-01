package com.terry.demo.user.service;

import com.terry.demo.core.dto.CommonResponseEntity;
import com.terry.demo.core.dto.CommonResponseEntityType;
import com.terry.demo.core.entity.PfTest;
import com.terry.demo.domain.test.dto.TestDto;
import com.terry.demo.domain.test.dto.TestRequest;
import com.terry.demo.domain.test.dto.TestResponse;
import com.terry.demo.domain.test.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class UserTestService {

    private final TestService testService;

    /**
     * user test 목록 조회
     * @param
     * @return
     */
    /*
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAdminTestList(TestListRequest testListRequest, Pageable pageable) {

        TestListResponse testListResponse = new TestListResponse();

        Page<TestsDto> testList = testService.getTestList(testListRequest, pageable);
        testListResponse.setTestList(testList.getContent());
        testListResponse.setAllCount(testList.getTotalElements());

        return new ResponseEntity<>(new CommonResponseEntity<>(CommonResponseEntityType.OK, testListResponse)
            , CommonResponseEntityType.OK.getHttpStatus());

    }
    */

    /**
     * user test 조회
     * @param
     * @return
     */
    @Transactional(readOnly = true)
    public ResponseEntity<?> getUserTestById(Long testId) {
        TestResponse.TestDtoResponse testDtoResponse  = new TestResponse.TestDtoResponse();

        TestDto testDto = testService.getTestById(testId);
        testDtoResponse.setTest(testDto);

        return new ResponseEntity<>(new CommonResponseEntity<>(CommonResponseEntityType.OK, testDtoResponse)
            , CommonResponseEntityType.OK.getHttpStatus());

    }

    /**
     * 등록
     * @param pfTest
     * @return
     */
    public ResponseEntity<?> userTestSave(PfTest pfTest) {

        TestResponse.TestEntityResponse testEntityResponse = new TestResponse.TestEntityResponse();

        PfTest pfTestSave = testService.testSave(pfTest);
        testEntityResponse.setTest(pfTestSave);

        return new ResponseEntity<>(new CommonResponseEntity<>(CommonResponseEntityType.OK, testEntityResponse)
            , CommonResponseEntityType.OK.getHttpStatus());

    }

    /**
     * 등록(파일)
     * @param testRequest
     * @return
     */
    public ResponseEntity<?> userTestFileSave(TestRequest testRequest) throws IOException {

        TestResponse.TestDtoResponse testDtoResponse = new TestResponse.TestDtoResponse();

        PfTest pfTest = testRequest.getPfTest();

        PfTest pfTestSave = testService.testSave(pfTest);
        TestDto testDto = new TestDto(pfTestSave);
        testDtoResponse.setTest(testDto);

        if (!ObjectUtils.isEmpty(pfTest.getMultipartFile())) {

            String fileName = pfTest.getMultipartFile().getOriginalFilename();
            // 초까지 같을 수가 있기 때문에 i를 통한 index 값 추가
            String s3FileName = "document-" + "1" + "-" + "0" + "-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String fileUrl = "document/" + "1" + "/";
            String s3FileKey = fileUrl + s3FileName;
            // s3파일 업로드
//            AwsS3BucketOrObject.uploadObjectFileToS3(s3FileKey, pfTest.getMultipartFile());

        }
        return new ResponseEntity<>(new CommonResponseEntity<>(CommonResponseEntityType.OK, testDtoResponse)
                , CommonResponseEntityType.OK.getHttpStatus());

    }

    /**
     * 수정
     * @param testUpdateRequest
     * @return
     */
    /*
    public ResponseEntity<?> userTestUpdate(TestUpdateRequest testUpdateRequest) {

        TestUpdateResponse testUpdateResponse = new TestUpdateResponse();

        Long testCnt = testService.testUpdate(testUpdateRequest);
        testUpdateResponse.setTestCnt(testCnt);

        return new ResponseEntity<>(new CommonResponseEntity<>(CommonResponseEntityType.OK, testUpdateResponse)
            , CommonResponseEntityType.OK.getHttpStatus());

    }
    */

    /**
     * 수정
     * @param testPwdUpdateRequest
     * @return
     */
    /*
    public ResponseEntity<?> userTestPwdUpdate(TestPwdUpdateRequest testPwdUpdateRequest) {

        TestUpdateResponse testUpdateResponse = new TestUpdateResponse();

        Long testCnt = testService.testPwdUpdate(testPwdUpdateRequest);
        testUpdateResponse.setTestCnt(testCnt);

        return new ResponseEntity<>(new CommonResponseEntity<>(CommonResponseEntityType.OK, testUpdateResponse)
            , CommonResponseEntityType.OK.getHttpStatus());

    }
    */



}

