package com.test.requestvacation.repository;

import java.util.Optional;

import com.test.requestvacation.entity.Member;

public interface MemberRepository {
    Member save(Member member);

    Member findByEmailAndPassword(String email, String password);

    Optional<Member> findById(Long id);

    Optional<Member> findByEmail(String email);
}
