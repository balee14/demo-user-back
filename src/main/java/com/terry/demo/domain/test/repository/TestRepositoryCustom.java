package com.terry.demo.domain.test.repository;

import com.terry.demo.domain.test.dto.QTestDto;
import com.terry.demo.domain.test.dto.TestDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.terry.demo.core.entity.QPfTest.pfTest;

@Repository
@RequiredArgsConstructor
public class TestRepositoryCustom {

    private final JPAQueryFactory jPAQueryFactory;

    /**
     *
     * @param testId
     * @return
     */
    public TestDto getTestById(Long testId) {
        return jPAQueryFactory
            .select(new QTestDto
                (   pfTest.testId
                ,   pfTest.idEmail)
            )
            .from(pfTest)
            .where(pfTest.testId.eq(testId))
            .fetchOne();
    }



}
