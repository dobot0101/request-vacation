package com.test.requestvacation.repository;

import com.test.requestvacation.entity.Member;
import com.test.requestvacation.entity.MemberVacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Member findByEmailAndPassword(String email, String password);
    Optional<Member> findById(Long id);
    Optional<Member> findByEmail(String email);
}
