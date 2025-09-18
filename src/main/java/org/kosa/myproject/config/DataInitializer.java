package org.kosa.myproject.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kosa.myproject.dto.MemberCreateRequestDto;
import org.kosa.myproject.dto.MemberResponseDto;
import org.kosa.myproject.dto.PostCreateRequestDto;
import org.kosa.myproject.dto.PostDetailResponseDto;
import org.kosa.myproject.service.MemberService;
import org.kosa.myproject.service.PostService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 애플리케이션 시작 시 테스트용 샘플 데이터 자동 생성
 * CommandLineRunner: Spring Boot 애플리케이션이 완전히 시작된 후 실행
 *
 * 한국 축구 국가대표 선수들을 회원으로 등록하고
 * 각 선수들이 작성한 게시물을 생성
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MemberService memberService;
    private final PostService postService;

    @Override
    public void run(String... args) throws Exception {
        log.info("========== 초기 데이터 생성 시작 ==========");

        try {
            // 이미 데이터가 있는지 확인 (중복 생성 방지)
            MemberResponseDto existingMember = memberService.findMemberById(1L);
            log.info("이미 데이터가 존재합니다. 초기화를 건너뜁니다.");
            return;
        } catch (Exception e) {
            // 데이터가 없으면 생성 진행
            log.info("초기 데이터를 생성합니다...");
        }

        // 1. 회원(축구선수) 생성
        createMembers();

        // 2. 게시물 생성
        createPosts();

        log.info("========== 초기 데이터 생성 완료 ==========");
    }

    /**
     * MemberCreateRequestDto를 직접 사용하여 간결하게 구현
     */
    private void createMembers() {
        log.info("회원 데이터 생성 중...");

        // MemberCreateRequestDto 리스트로 직접 구성
        List<MemberCreateRequestDto> koreanNationalPlayers = List.of(
                MemberCreateRequestDto.builder().username("손흥민").email("heungmin@team.kr").build(),
                MemberCreateRequestDto.builder().username("이강인").email("kangin@team.kr").build(),
                MemberCreateRequestDto.builder().username("김민재").email("minjae@team.kr").build(),
                MemberCreateRequestDto.builder().username("황희찬").email("heechan@team.kr").build(),
                MemberCreateRequestDto.builder().username("이재성").email("jaesung@team.kr").build(),
                MemberCreateRequestDto.builder().username("황인범").email("inbeom@team.kr").build(),
                MemberCreateRequestDto.builder().username("조규성").email("gyusung@team.kr").build(),
                MemberCreateRequestDto.builder().username("백승호").email("seungho@team.kr").build(),
                MemberCreateRequestDto.builder().username("오현규").email("hyungyu@team.kr").build(),
                MemberCreateRequestDto.builder().username("양현준").email("hyunjun@team.kr").build()
        );

        // 각 선수를 회원으로 등록 - 바로 서비스로 전달
        for (MemberCreateRequestDto memberDto : koreanNationalPlayers) {
            try {
                MemberResponseDto savedMember = memberService.createMember(memberDto);
                log.info("회원 등록: {} (ID: {})",
                        savedMember.getUsername(),
                        savedMember.getMemberId());
            } catch (Exception e) {
                log.warn("회원 등록 실패: {} - {}", memberDto.getUsername(), e.getMessage());
            }
        }
    }

    /**
     * 각 회원이 작성한 게시물 생성
     */
    private void createPosts() {
        log.info("게시물 데이터 생성 중...");

        // PostCreateRequestDto 리스트로 직접 구성
        List<PostCreateRequestDto> postsData = List.of(
                // 손흥민 (LA FC)
                PostCreateRequestDto.builder()
                        .title("LA 이적 후 소감")
                        .content("미국에서 새롭게 잘 적응하겠습니다. MLS에서의 새로운 도전이 기대됩니다.")
                        .memberId(1L).build(),
                PostCreateRequestDto.builder()
                        .title("한국 축구의 미래")
                        .content("후배들이 유럽에서 활약하는 모습을 보니 정말 자랑스럽습니다. 한국 축구의 미래는 밝습니다.")
                        .memberId(1L).build(),
                PostCreateRequestDto.builder()
                        .title("팬들과의 소통")
                        .content("SNS를 통해 팬들과 소통하는 시간이 정말 소중합니다. 늘 응원해주셔서 감사합니다.")
                        .memberId(1L).build(),

                // 이강인 (PSG)
                PostCreateRequestDto.builder()
                        .title("파리에서의 성장")
                        .content("PSG에서 세계 최고의 선수들과 함께 훈련하며 매일 성장하고 있습니다.")
                        .memberId(2L).build(),
                PostCreateRequestDto.builder()
                        .title("아시안컵 2023 회고")
                        .content("아시안컵에서 아쉬운 결과였지만, 팀과 함께 더 나은 모습을 보여드리겠습니다.")
                        .memberId(2L).build(),

                // 김민재 (바이에른 뮌헨)
                PostCreateRequestDto.builder()
                        .title("분데스리가 적응기")
                        .content("바이에른에서의 시즌을 성공적으로 마쳤습니다. 더욱 발전하는 모습 보여드리겠습니다.")
                        .memberId(3L).build(),
                PostCreateRequestDto.builder()
                        .title("수비수의 철학")
                        .content("좋은 수비는 팀 전체의 조직력에서 나온다고 생각합니다.")
                        .memberId(3L).build(),

                // 황희찬 (울버햄튼)
                PostCreateRequestDto.builder()
                        .title("프리미어리그에서의 도전")
                        .content("울버햄튼에서 꾸준히 기회를 잡기 위해 노력하고 있습니다.")
                        .memberId(4L).build(),
                PostCreateRequestDto.builder()
                        .title("부상 극복과 재기")
                        .content("힘든 시기를 극복하고 더 강해져서 돌아왔습니다.")
                        .memberId(4L).build(),

                // 이재성 (마인츠)
                PostCreateRequestDto.builder()
                        .title("독일에서의 꾸준함")
                        .content("마인츠에서 6시즌째, 꾸준함이 가장 중요한 무기라고 생각합니다.")
                        .memberId(5L).build(),

                // 황인범 (페이노르트)
                PostCreateRequestDto.builder()
                        .title("네덜란드 리그에서의 새로운 도전")
                        .content("새로운 환경에서 새로운 축구를 배우고 있습니다.")
                        .memberId(6L).build(),

                // 조규성 (미드틸란트)
                PostCreateRequestDto.builder()
                        .title("덴마크에서의 적응")
                        .content("북유럽 축구 스타일에 적응하며 새로운 경험을 쌓고 있습니다.")
                        .memberId(7L).build(),

                // 백승호 (버밍엄 시티)
                PostCreateRequestDto.builder()
                        .title("EPL 챔피언쉽에서의 성장")
                        .content("중앙 미드필더가 주축이 되려고 노력하고 있습니다.")
                        .memberId(8L).build(),

                // 오현규 (KRC 헹크)
                PostCreateRequestDto.builder()
                        .title("벨기에에서의 도전")
                        .content("헹크에서 유럽 무대 경험을 쌓으며 성장하고 있습니다.")
                        .memberId(9L).build(),

                // 양현준 (셀틱)
                PostCreateRequestDto.builder()
                        .title("셀틱에서의 새로운 시작")
                        .content("셀틱에서 새로운 도전을 시작했습니다. 팬들의 응원이 큰 힘이 됩니다.")
                        .memberId(10L).build()
        );

        // 각 게시물 생성 - PostCreateRequestDto를 직접 서비스로 전달
        for (PostCreateRequestDto postDto : postsData) {
            try {
                PostDetailResponseDto savedPost = postService.createPost(postDto);
                log.info("게시물 등록: {} (작성자 ID: {})",
                        postDto.getTitle(),
                        postDto.getMemberId());
            } catch (Exception e) {
                log.warn("게시물 등록 실패: {} - {}", postDto.getTitle(), e.getMessage());
            }
        }

        log.info("총 {}개의 게시물 생성 완료!", postsData.size());
    }
}