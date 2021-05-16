package com.test.requestvacation.repository;

import com.test.requestvacation.DTO.MemberVacationUsageGroupBy;
import com.test.requestvacation.entity.MemberVacationUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberVacationUsageRepository extends JpaRepository<MemberVacationUsage, Long> {

    @Query(value = "select member_id, sum(use_day) as total_use_days from member_vacation_usage where member_id = :member_id group by member_id", nativeQuery = true)
    MemberVacationUsageGroupBy getTotalUsedVacationDays(@Param("member_id") Long memberId);

//    @Query(value="select * from vacation_requests where member_id = :member_id and is_cancel <> 'Y'")
    List<MemberVacationUsage> findAllByMemberId(Long memberId);

    List<MemberVacationUsage> findAllByMemberIdAndIsCancel(Long memberId, String isCancel);

//    @Query(value = "update vacation_requests set is_cancel where id = :id", nativeQuery = true)
//    boolean cancelVacationById(@Param("id") Long vacationId);
}
