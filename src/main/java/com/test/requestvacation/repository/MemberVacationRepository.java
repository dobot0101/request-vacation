package com.test.requestvacation.repository;

import com.test.requestvacation.entity.MemberVacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberVacationRepository extends JpaRepository<MemberVacation, Long> {
    Integer findByMemberId(Long memberId);
}
