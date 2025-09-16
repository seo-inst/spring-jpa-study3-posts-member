package org.kosa.myproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kosa.myproject.dto.MemberCreateRequestDto;
import org.kosa.myproject.dto.MemberResponseDto;
import org.kosa.myproject.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *   REST :  분산환경(MSA) 에서 다양한 시스템간의 통신을 위한 아키텍쳐
 *                자원 -> url  ,  행위 -> http request method : get , post , put , delete,  patch
 *   Member REST Controller
 *   회원 API 엔드 포인트
 */
@RestController
@RequestMapping("api/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    /**
     *   회원 가입
     *   POST   /api/members
     */
    @PostMapping
    public ResponseEntity<MemberResponseDto> createMember(@RequestBody MemberCreateRequestDto memberCreateRequestDto){
        log.info("회원 가입 요청 {}", memberCreateRequestDto.toString());
        MemberResponseDto member = memberService.createMember(memberCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }
    /**
     *  아이디로 회원 조회
     *  GET  /api/members/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> findMemberById(@PathVariable  Long id){
        MemberResponseDto memberResponseDto = memberService.findMemberById(id);
        return ResponseEntity.ok(memberResponseDto);
    }
}




















