package com.terry.demo.user.service;

import com.terry.demo.core.config.enums.EnumMapper;
import com.terry.demo.core.config.enums.EnumMapperValue;
import com.terry.demo.core.dto.CommonResponseEntity;
import com.terry.demo.core.dto.CommonResponseEntityType;
import com.terry.demo.core.entity.PfMember;
import com.terry.demo.domain.member.member.dto.MemberDto;
import com.terry.demo.domain.member.member.dto.MemberPwdUpdateRequest;
import com.terry.demo.domain.member.member.dto.MemberResponse;
import com.terry.demo.domain.member.member.dto.MemberResponse.MemberDtoResponse;
import com.terry.demo.domain.member.member.dto.MemberResponse.MemberUpdateResponse;
import com.terry.demo.domain.member.member.dto.MemberUpdateRequest;
import com.terry.demo.domain.member.member.dto.list.MemberListRequest;
import com.terry.demo.domain.member.member.dto.list.MemberListResponse;
import com.terry.demo.domain.member.member.dto.list.MembersDto;
import com.terry.demo.domain.member.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class UserMemberService {

    private final MemberService memberService;
    private final EnumMapper enumMapper;


    /**
     * member 등급 목록
     * @return
     */
    public ResponseEntity<?> getMemberRankingTypes() {
        List<EnumMapperValue> memberRankingList =  enumMapper.get("MemberRankingEnum");
        return new ResponseEntity<>(new CommonResponseEntity<>(CommonResponseEntityType.OK, memberRankingList)
                , CommonResponseEntityType.OK.getHttpStatus());
    }

    /**
     * member 회원 상태
     * @return
     */
    public ResponseEntity<?> getMemberStateTypes() {
        List<EnumMapperValue> memberStateList =  enumMapper.get("MemberStateEnum");
        return new ResponseEntity<>(new CommonResponseEntity<>(CommonResponseEntityType.OK, memberStateList)
                , CommonResponseEntityType.OK.getHttpStatus());
    }


    /**
     * member 회원가입 등록 구분 목록 조회
     * @return
     */
    public ResponseEntity<?> getMemberJoinTypes() {
        List<EnumMapperValue> memberJoinTypeList =  enumMapper.get("MemberJoinTypeEnum");
        return new ResponseEntity<>(new CommonResponseEntity<>(CommonResponseEntityType.OK, memberJoinTypeList)
                , CommonResponseEntityType.OK.getHttpStatus());
    }


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
     * @param pfMember
     * @return
     */
    public ResponseEntity<?> userMemberSave(PfMember pfMember) {

        MemberResponse.MemberEntityResponse memberEntityResponse = new MemberResponse.MemberEntityResponse();

        PfMember pfMemberSave = memberService.memberSave(pfMember);
        memberEntityResponse.setMember(pfMemberSave);

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

