package org.kosa.myproject.repository;

import org.kosa.myproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
        // List<Post> findAll(); // 만약 @ManyToOne  관계에서 조회시 N + 1 문제 발생 가능성

    /**
     *    N + 1 문제 해결
     *    JPQL Fetch Join 으로 한번의 SQL 로 조회
     *    -> N 번의 쿼리가  단 1번으로 줄어 성능이 크게 향상
     *
     *    JPQL 의 FETCH JOIN 은 데이터베이스에 독립적이고 sql 의 join 을 넘어서
     *    JPA가 연관된 엔티티를 ( 게시물 객체 조회시 회원(작성자) 객체까지 함께 조회해 할당 ) 함께 로드하도록
     *    함 ==>  게시물 엔티티 내에 회원 엔티티까지 저장되어 반환
     */
      @Query("SELECT p FROM Post p JOIN FETCH p.member ")
    List<Post> findAllWithMember();
}
