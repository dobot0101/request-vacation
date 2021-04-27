package com.test.requestvacation.repository;

import com.test.requestvacation.entity.VacationRequests;
import com.test.requestvacation.entity.VacationRequestsGroupBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VacationRequestsRepository extends JpaRepository<VacationRequests, Long> {

    @Query(value = "select member_id, sum(use_day) as total_use_days from vacation_requests where member_id = :member_id", nativeQuery = true)
    VacationRequestsGroupBy getTotalUsedVacationDays(@Param("member_id") Long memberId);
}
