package org.kosa.myproject.repository;

import org.kosa.myproject.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    // username 으로 회원 조회 ( 중복 체크용 )
    Optional<Member> findByUsername(String username);
    // email로 회원조회 (  중복 체크용 )
    Optional<Member> findByEmail(String email);
}










