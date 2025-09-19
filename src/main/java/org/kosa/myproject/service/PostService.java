package org.kosa.myproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kosa.myproject.dto.PostCreateRequestDto;
import org.kosa.myproject.dto.PostDetailResponseDto;
import org.kosa.myproject.dto.PostListResponseDto;
import org.kosa.myproject.entity.Member;
import org.kosa.myproject.entity.Post;
import org.kosa.myproject.repository.MemberRepository;
import org.kosa.myproject.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    /**
     *   게시물 전체 조회 -> N + 1 문제 발생 버전 ( 학습을 위해 )
     *   N + 1 문제는 연관 관계가 있는 엔티티를 조회할 때 발생하는 성능 문제
     *   회원 100명이 작성한 게시물 100 개를 조회할 때
     *   1번의 쿼리로 게시물 리스트를 가져오고
     *   각 게시물에 연결된 회원 정보를 가져오기 위해 100번의 추가 쿼리가 발생되는 상황을 말함
     *   ==> 성능 저하가 초래 ==해결==> JPQL 의 Fetch Join 을 해결함
     */
    public void demonstrateNPlusOneProblem(){
        log.info("====N + 1 문제 발생 시연====");
        // 1 번 쿼리 : Select * FROM posts
        List<Post> posts =  postRepository.findAll(); // JpaRepository 에 기본 제공되는 메서드를 이용한다
        log.info("1번 쿼리 실행:{} 개의 게시물 조회",posts.size());
        for(Post post : posts){
            String authorName = post.getMember().getUsername();
            log.info("추가 쿼리! 게시물 : {} 작성자 : {}",post.getTitle(),authorName);
        }
        log.info("N번의 쿼리 실행=> 성능 저하");
    }
    /**
     *    N + 1 문제 해결
     *    JPQL Fetch Join 으로 한번의 SQL 로 조회
     *    -> N 번의 쿼리가  단 1번으로 줄어 성능이 크게 향상
     */
    public List<PostListResponseDto> findAllPostList(){
        List<Post> posts =  postRepository.findAllWithMember();// JPQL Fetch JOIN 적용된 repository 메서드 호출
        return posts.stream().map(PostListResponseDto::from).collect(Collectors.toUnmodifiableList());
    }
    /**
     *  상세 게시물 조회 - JPQL Fetch JOIN 이용한 메서드 사용
     */
    public PostDetailResponseDto findPostDetail(Long postId){
        log.info("게시물 상세 조회: postId {}",postId);
        Post post = postRepository.findByIdWithMember(postId).orElseThrow(()->new RuntimeException("게시물을 찾을 수 없습니다 postId="+postId));
        return PostDetailResponseDto.from(post);// Dto 로 변환하여 반환
    }
    /**
     *  특정 회원의 게시물 목록 조회 : @ManyToOne 으로 모두 가능
     */
    public List<PostListResponseDto> findPostListByMember(Long memberId){
        if(!memberRepository.existsById(memberId)){
            throw new RuntimeException("회원을 찾을 수 없습니다 memberId"+memberId);
        }
        List<Post> posts=postRepository.findPostListByMemberId(memberId);
        // stream(), map() 으로 Entity List 를 Dto List 로 변환
        return posts.stream().map(PostListResponseDto::from).collect(Collectors.toUnmodifiableList());
    }
}




























