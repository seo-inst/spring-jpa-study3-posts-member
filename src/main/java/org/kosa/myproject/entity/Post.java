package org.kosa.myproject.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Post 엔티티 클래스
 *
 * @ManyToOne :  다 대 일 관계 정의
 * Lazy loading : 지연 로딩 ( 실제 사용할 때 정보를 db에서 조회 -> 성능 향상 )
 */
@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    // MySQL TEXT TYPE : 내용의 게시글 본문 , 상품 상세설명 등을 저장할 때 유용
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    /**
     * 게시글 작성자 ( @ManyToOne : 연관관계의 주인 ( 외래키가 있는 엔티티 ) )
     * POST  ->|0---------|  MEMBER
     * <p>
     * FetchType.LAZY : Lazy Loading  -> 실제  member 필드(변수) 에 접근하는 시점에 로딩하도록 설정
     * 성능을 최적화 시키는 목적
     * <p>
     * JoinColumn  name="member_id" : Posts 테이블에 생성될 외래키 컬럼 이름을 member_id 로 명시
     * nullable = false : Null 허용하지 않음. 모든 게시물은 작성자가 있어야 함
     *
     * @ForeignKey(name="fk_post_member") : 생성될  Foreign key( 외래키 ) 제약 조건명을 fk_post_member 로 명시
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(name = "fk_post_member"))
    private Member member;

    /**
     * 연관 관계 메서드 : 게시물의 작성자 설정 메서드
     */
    public void assignAuthor(Member member) {
        this.member = member;
    }

    /**
     * 비즈니스 메서드 : 게시물 수정
     */
    public void updatePost(String title, String content) {
        if (title != null && !title.trim().isEmpty())
            this.title = title;
        if (content != null && !content.trim().isEmpty())
            this.content = content;
    }
}


















