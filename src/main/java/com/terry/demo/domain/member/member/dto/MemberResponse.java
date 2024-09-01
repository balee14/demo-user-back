package com.terry.demo.domain.member.member.dto;



import com.terry.demo.core.entity.PfMember;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponse {

    @Getter
    @Setter
    public static class MemberDtoResponse {
        private MemberDto member;
    }

    @Getter
    @Setter
    public static class MemberEntityResponse {
        private PfMember member;
    }

    @Getter
    @Setter
    public static class MemberUpdateResponse {
        private Long memberCnt;
    }

    @Getter
    @Setter
    public static class MemberDeleteResponse {
        private Long memberCnt;
    }

}

