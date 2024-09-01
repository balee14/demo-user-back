package com.terry.demo.domain.test.dto;


import com.terry.demo.core.entity.PfTest;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TestDto {

    private Long testId;

    private String idEmail;

    @QueryProjection
    public TestDto(Long testId, String idEmail) {
        this.testId = testId;
        this.idEmail = idEmail;
    }

    public TestDto(PfTest pfTest) {
        this.testId = pfTest.getTestId();
        this.idEmail = pfTest.getIdEmail();
    }


}

