package org.kosa.myproject.dto;

import lombok.Builder;
import lombok.Getter;
import org.kosa.myproject.entity.Post;

import java.time.LocalDateTime;

/**
 *  게시물 리스트에 필요한 정보만 응답하기 위한 Dto
 */
@Getter
@Builder
public class PostListResponseDto {
        private Long postId;
        private String title;
        private String authorName;
        private LocalDateTime createdAt;
        // Entity -> Dto 변환
    public static PostListResponseDto from(Post post){
        return PostListResponseDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .authorName(post.getMember().getUsername())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
