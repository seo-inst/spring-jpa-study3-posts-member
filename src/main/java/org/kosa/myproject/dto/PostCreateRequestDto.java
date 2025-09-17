package org.kosa.myproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *   게시물 생성 요청 Dto
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateRequestDto {
    private String title;
    private String content;
    private Long memberId; // 작성자 아이디
}








