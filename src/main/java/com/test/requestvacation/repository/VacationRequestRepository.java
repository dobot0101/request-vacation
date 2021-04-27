package com.test.requestvacation.repository;

import com.test.requestvacation.entity.VacationRequest;
import com.test.requestvacation.entity.VacationRequestsGroupBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacationRequestRepository extends JpaRepository<VacationRequest, Long> {

    @Query(value = "select member_id, sum(use_day) as total_use_days from vacation_requests where member_id = :member_id", nativeQuery = true)
    VacationRequestsGroupBy getTotalUsedVacationDays(@Param("member_id") Long memberId);

    List<VacationRequest> findAllByMemberId(Long memberId);

    @Query(value = "update vacation_requests set is_cancel where id = :id", nativeQuery = true)
    boolean cancelVacationById(@Param("id") Long vacationId);
}
