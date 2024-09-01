package com.terry.demo.domain.test.service;

import com.terry.demo.core.entity.PfTest;
import com.terry.demo.domain.test.dto.TestDto;
import com.terry.demo.domain.test.repository.TestRepository;
import com.terry.demo.domain.test.repository.TestRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;
    private final TestRepositoryCustom testRepositoryCustom;

    /**
     * test 목록 조회
     *
     * @param
     * @return
     */
    /*
    @Transactional(readOnly = true)
    public Page<TestsDto> getTestList(TestListRequest testListRequest, Pageable pageable) {
        return testRepositoryCustom.getTestList(testListRequest, pageable);
    }
    */

    /**
     * test 조회
     * @param
     * @return
     */
    @Transactional(readOnly = true)
    public TestDto getTestById(Long testId) {
        return testRepositoryCustom.getTestById(testId);
    }

    /**
     *
     * @param pfTest
     * @return
     */
    public PfTest testSave(PfTest pfTest) {

        return testRepository.save(pfTest);

    }

    /**
     *
     * @param testUpdateRequest
     * @return
     */
    /*
    public Long testUpdate(TestUpdateRequest testUpdateRequest) {

        testUpdateRequest.setModId(PfTestUtil.getIdEmail());
        return testRepositoryCustom.testUpdate(testUpdateRequest);

    }
    */


}

