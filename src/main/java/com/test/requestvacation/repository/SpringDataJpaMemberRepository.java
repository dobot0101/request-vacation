package com.test.requestvacation.repository;

import com.test.requestvacation.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//@Repository
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {


    Member findByEmailAndPassword(String email, String password);


//    @Override
//    Member save(Member member);

//    @Override
//    Optional<Member> findByEmail(String email);
}
