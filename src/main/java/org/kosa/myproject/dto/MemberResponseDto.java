package org.kosa.myproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kosa.myproject.entity.Member;

import java.time.LocalDateTime;

/**
 *   회원 정보 응답 Dto
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponseDto {
    private Long memberId;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    /**
     *   Entity -> Dto 변환
     */
    public static MemberResponseDto from(Member member){
        return MemberResponseDto.builder()
                .memberId(member.getMemberId())
                .username(member.getUsername())
                .email(member.getEmail())
                .createdAt(member.getCreatedAt())
                .build();
    }
}








