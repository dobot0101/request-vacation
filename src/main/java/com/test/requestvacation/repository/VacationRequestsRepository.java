package com.test.requestvacation.repository;

import com.test.requestvacation.entity.MemberVacation;
import com.test.requestvacation.entity.VacationRequests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VacationRequestsRepository extends JpaRepository<VacationRequests, Long> {

    @Query(value = "select member_id, sum(use_days) as total_use_days from request_vacations where member_id = :member_id", nativeQuery = true)
    Integer getTotalUsedVacationDays(@Param("member_id") Long memberId);
}
