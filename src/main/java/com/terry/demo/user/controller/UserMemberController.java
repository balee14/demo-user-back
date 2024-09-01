package com.terry.demo.user.controller;

import com.terry.demo.core.entity.PfMember;
import com.terry.demo.domain.member.member.dto.MemberPwdUpdateRequest;
import com.terry.demo.domain.member.member.dto.MemberUpdateRequest;
import com.terry.demo.domain.member.member.dto.list.MemberListRequest;
import com.terry.demo.user.service.UserMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/user/v1")
public class UserMemberController {

    /**
     * 사용자 관리
     */

    private final UserMemberService userMemberService;


    /**
     * member 회원 상태 목록 조회
     *
     * @param
     * @return
     */
    @GetMapping("/member/memberRankingTypes")
    public ResponseEntity<?> getMemberRankingTypes() {
        return userMemberService.getMemberRankingTypes();
    }

    /**
     * member 회원 상태 목록 조회
     *
     * @param
     * @return
     */
    @GetMapping("/member/memberStateTypes")
    public ResponseEntity<?> getMemberStateTypes() {
        return userMemberService.getMemberStateTypes();
    }

    /**
     * member 회원가입 등록 구분 목록 조회
     *
     * @param
     * @return
     */
    @GetMapping("/member/memberJoinTypes")
    public ResponseEntity<?> getMemberRegisterTypes() {
        return userMemberService.getMemberJoinTypes();
    }

    /**
     * 모든 member 목록 조회
     *
     * @param
     * @return
     */
    @GetMapping("/member/members")
    public ResponseEntity<?> getUserMemberList(@ModelAttribute MemberListRequest memberListRequest, Pageable pageable) {
        return userMemberService.getUserMemberList(memberListRequest, pageable);
    }

    /**
     * member 조회
     *
     * @param
     * @return
     */
    @GetMapping("/member/memberId/{memberId}")
    public ResponseEntity<?> getUserMemberById(@PathVariable("memberId") Long memberId) {
        return userMemberService.getUserMemberById(memberId);
    }

    /**
     * member 등록
     *
     * @param pfMember
     * @return
     */
    @PostMapping("/member")
    public ResponseEntity<?> userMemberSave(@RequestBody PfMember pfMember) {
        return userMemberService.userMemberSave(pfMember);
    }

    /**
     * member 업데이트
     *
     * @param memberUpdateRequest
     * @return
     */
    @PutMapping("/member")
    public ResponseEntity<?> userMemberUpdate(@RequestBody MemberUpdateRequest memberUpdateRequest) {
        return userMemberService.userMemberUpdate(memberUpdateRequest);
    }

    /**
     * member 업데이트
     *
     * @param memberPwdUpdateRequest
     * @return
     */
    @PutMapping("/member/pwd")
    public ResponseEntity<?> userMemberPwdUpdate(@RequestBody MemberPwdUpdateRequest memberPwdUpdateRequest) {
        return userMemberService.userMemberPwdUpdate(memberPwdUpdateRequest);
    }


}


