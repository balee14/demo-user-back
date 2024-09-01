package com.terry.demo.domain.test.dto;



import com.terry.demo.core.entity.PfTest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestResponse {

    @Getter
    @Setter
    public static class TestDtoResponse {
        private TestDto test;
    }

    @Getter
    @Setter
    public static class TestEntityResponse {
        private PfTest test;
    }

    @Getter
    @Setter
    public static class TestUpdateResponse {
        private Long testCnt;
    }

    @Getter
    @Setter
    public static class TestDeleteResponse {
        private Long testCnt;
    }

}

