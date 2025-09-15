package org.kosa.myproject.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 *  Member 엔티티 클래스
 *  커뮤니티 게시판 회원 정보
 */
@Entity
@Table(name ="members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;
    @Column(name="username", nullable = false, unique = true, length = 50)
    private String username;
    @Column(name="email", nullable = false, unique = true, length = 100)
    private String email;
    @CreationTimestamp
    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt;
    // 비즈니스 메서드
    /**
     *   회원정보 업데이트 비즈니스 메서드
     */
    public void updateInfo(String username,String email){
        if(username !=null && !username.trim().isEmpty()){
            this.username = username.trim();
        }
        if(email !=null && !email.trim().isEmpty()){
            this.email = email.trim().toLowerCase();
        }
    }
}















