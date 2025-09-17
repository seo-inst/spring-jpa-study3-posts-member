package org.kosa.myproject.dto;

import lombok.Builder;
import lombok.Getter;
import org.kosa.myproject.entity.Member;
import org.kosa.myproject.entity.Post;

import java.time.LocalDateTime;

/**
 *  게시물 응답 Dto
 */
@Getter
@Builder
public class PostDetailResponseDto {
    private Long postId;
    private String title;
    private String content;
    private Long authorId;
    private String authorName;
    private String authorEmail;
    private LocalDateTime createdAt;

    // Entity -> Dto 변환 메서드
    public static PostDetailResponseDto from(Post post){
        Member author = post.getMember();
        return PostDetailResponseDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .authorId(author.getMemberId())
                .authorName(author.getUsername())
                .authorEmail(author.getEmail())
                .createdAt(post.getCreatedAt())
                .build();
    }
}

















