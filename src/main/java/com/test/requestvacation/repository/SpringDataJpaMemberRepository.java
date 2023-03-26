package com.test.requestvacation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.requestvacation.entity.Member;

@Repository
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    Member findByEmailAndPassword(String email, String password);

    // @Override
    // Member save(Member member);

    // @Override
    // Optional<Member> findByEmail(String email);
}
