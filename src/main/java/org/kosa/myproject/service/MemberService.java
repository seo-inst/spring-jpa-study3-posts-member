package org.kosa.myproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kosa.myproject.dto.MemberCreateRequestDto;
import org.kosa.myproject.dto.MemberResponseDto;
import org.kosa.myproject.entity.Member;
import org.kosa.myproject.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)  // 클래스 차원에서 읽기 전용 트랜잭션 모드
@Slf4j
@RequiredArgsConstructor // final field 에 대한 생성자를 정의해준다
public class MemberService {
    private final MemberRepository memberRepository;
    /**
     *   회원가입
     */
    @Transactional // insert, delete, update 에 대한 트랜잭션
    public MemberResponseDto createMember(MemberCreateRequestDto memberCreateRequestDto){
        log.info("회원 가입 시작 {}",  memberCreateRequestDto.toString());
        // 중복 체크
        if(memberRepository.findByUsername(memberCreateRequestDto.getUsername()).isPresent()){
            throw new IllegalArgumentException("이미 사용 중인 회원명입니다:"+memberCreateRequestDto.getUsername());
        }
        if(memberRepository.findByEmail(memberCreateRequestDto.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 사용중인 이메일입니다:"+memberCreateRequestDto.getEmail());
        }
        // Entity 생성 및 저장
        Member member = Member.builder()
                .username(memberCreateRequestDto.getUsername())
                .email(memberCreateRequestDto.getEmail())
                .build();
       Member savedMember =  memberRepository.save(member);
       // Entity 를 Dto 로 변환해 반환
    return MemberResponseDto.from(savedMember);
    }
    /**
     *   회원 조회
     *   1.  MemberRepository 의 findById(id) 를 이용해 회원정보를 조회한다
     *       MemberRepository 의 find 메서드는 Optional 로 반환되므로
     *       orElseThrow()  를 이용해 RuntimeException 을 발생시켜 throws 한다 ( 별도 throws 명시 필요 x )
     *   2.  return 은 Entity 를 Dto로 변환해서 한다
     */
    public MemberResponseDto  findMemberById(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(()->
            new RuntimeException("회원을 찾을 수 없습니다 ID:"+memberId)
        );
        return MemberResponseDto.from(member);
    }
}

















