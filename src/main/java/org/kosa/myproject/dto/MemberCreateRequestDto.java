package org.kosa.myproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *  회원 가입 요청 Dto
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberCreateRequestDto {
    private String username;
    private String email;
}








