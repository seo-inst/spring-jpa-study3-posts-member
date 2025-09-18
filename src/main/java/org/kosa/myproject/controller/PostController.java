package org.kosa.myproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kosa.myproject.dto.PostCreateRequestDto;
import org.kosa.myproject.dto.PostDetailResponseDto;
import org.kosa.myproject.dto.PostListResponseDto;
import org.kosa.myproject.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  Post Rest Controller
 *  게시물 관련 API 엔드 포인트
 */
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;
    /**
     *   게시물 등록
     */
    @PostMapping
    public ResponseEntity<PostDetailResponseDto> createPost(@RequestBody PostCreateRequestDto postCreateRequestDto){
       PostDetailResponseDto postDetailResponseDto =  postService.createPost(postCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(postDetailResponseDto);
    }
    @GetMapping("/demo/n-plus-one")
    public ResponseEntity<String> demonstrateNPlusOne(){
        postService.demonstrateNPlusOneProblem();
        return ResponseEntity.ok("콘솔 로그 확인하세요");
    }
    /**
     *  전체 게시물 조회 ( N + 1 문제 해결 ) => JPQL FETCH JOIN 으로 해결 -> 1 번의 쿼리로 처리
     */
    @GetMapping
    public ResponseEntity<List<PostListResponseDto>> findAllPostList(){
        List<PostListResponseDto>  posts = postService.findAllPostList();
        return ResponseEntity.ok(posts);
    }
}





