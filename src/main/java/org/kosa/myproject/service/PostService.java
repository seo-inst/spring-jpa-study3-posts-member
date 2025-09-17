package org.kosa.myproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kosa.myproject.dto.PostCreateRequestDto;
import org.kosa.myproject.dto.PostDetailResponseDto;
import org.kosa.myproject.entity.Member;
import org.kosa.myproject.entity.Post;
import org.kosa.myproject.repository.MemberRepository;
import org.kosa.myproject.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    /**
     * 게시물 생성
     */
    @Transactional
    public PostDetailResponseDto createPost(PostCreateRequestDto postCreateRequestDto) {
        // 작성자 조회 , Member Table에 존재하는 회원 작성자인지 확인 , 없으면 예외 발생
        Member author = memberRepository.findById(postCreateRequestDto.getMemberId()).orElseThrow(() -> new RuntimeException("회원을 찾을 수 없어 게시물을 등록할 수 없습니다 MEMBER ID:" + postCreateRequestDto.getMemberId()));
        //Post 생성 : ManyToOne 관계설정 -> .member(author)
        Post post = Post.builder()
                .title(postCreateRequestDto.getTitle())
                .content(postCreateRequestDto.getContent())
                .member(author) // ManyToOne 관계설정
                .build();
        // db 에 저장
        Post savedPost =  postRepository.save(post);// save 가 반환하는 Post Entity 에는 자동 생성된 postId 와  생성일시createdAt가 할당되어 있음
        return PostDetailResponseDto.from(savedPost);// Dto로 변환하여 컨트롤러에 반환한다
    }
}
























