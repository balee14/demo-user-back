package com.zero.demo.service.member;

import com.terry.demo.core.dto.CommonResponseEntity;
import com.terry.demo.core.dto.CommonResponseEntityType;
import com.terry.demo.core.entity.KpMember;
import com.terry.demo.member.dto.*;
import com.terry.demo.member.dto.MemberResponse.MemberDtoResponse;
import com.terry.demo.member.dto.MemberResponse.MemberEntityResponse;
import com.terry.demo.member.dto.MemberResponse.MemberUpdateResponse;
import com.terry.demo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class UserMemberService {

    private final MemberService memberService;

    /**
     * user member 목록 조회
     * @param
     * @return
     */
    @Transactional(readOnly = true)
    public ResponseEntity<?> getUserMemberList(MemberListRequest memberListRequest, Pageable pageable) {

        MemberListResponse memberListResponse = new MemberListResponse();

        Page<MembersDto> memberList = memberService.getMemberList(memberListRequest, pageable);
        memberListResponse.setMemberList(memberList.getContent());
        memberListResponse.setAllCount(memberList.getTotalElements());

        return new ResponseEntity<>(new CommonResponseEntity<>(CommonResponseEntityType.OK, memberListResponse)
            , CommonResponseEntityType.OK.getHttpStatus());

    }

    /**
     * user member 조회
     * @param
     * @return
     */
    @Transactional(readOnly = true)
    public ResponseEntity<?> getUserMemberById(Long memberId) {
        MemberDtoResponse memberDtoResponse  = new MemberDtoResponse();

        MemberDto memberDto = memberService.getMemberById(memberId);
        memberDtoResponse.setMember(memberDto);

        return new ResponseEntity<>(new CommonResponseEntity<>(CommonResponseEntityType.OK, memberDtoResponse)
            , CommonResponseEntityType.OK.getHttpStatus());

    }

    /**
     * 등록
     * @param kpMember
     * @return
     */
    public ResponseEntity<?> userMemberSave(KpMember kpMember) {

        MemberEntityResponse memberEntityResponse = new MemberEntityResponse();

        KpMember kpMemberSave = memberService.memberSave(kpMember);
        memberEntityResponse.setMember(kpMemberSave);

        return new ResponseEntity<>(new CommonResponseEntity<>(CommonResponseEntityType.OK, memberEntityResponse)
            , CommonResponseEntityType.OK.getHttpStatus());

    }

    /**
     * 수정
     * @param memberUpdateRequest
     * @return
     */
    public ResponseEntity<?> userMemberUpdate(MemberUpdateRequest memberUpdateRequest) {

        MemberUpdateResponse memberUpdateResponse = new MemberUpdateResponse();

        Long memberCnt = memberService.memberUpdate(memberUpdateRequest);
        memberUpdateResponse.setMemberCnt(memberCnt);

        return new ResponseEntity<>(new CommonResponseEntity<>(CommonResponseEntityType.OK, memberUpdateResponse)
            , CommonResponseEntityType.OK.getHttpStatus());

    }

    /**
     * 수정
     * @param memberPwdUpdateRequest
     * @return
     */
    public ResponseEntity<?> userMemberPwdUpdate(MemberPwdUpdateRequest memberPwdUpdateRequest) {

        MemberUpdateResponse memberUpdateResponse = new MemberUpdateResponse();

        Long memberCnt = memberService.memberPwdUpdate(memberPwdUpdateRequest);
        memberUpdateResponse.setMemberCnt(memberCnt);

        return new ResponseEntity<>(new CommonResponseEntity<>(CommonResponseEntityType.OK, memberUpdateResponse)
            , CommonResponseEntityType.OK.getHttpStatus());

    }



}

