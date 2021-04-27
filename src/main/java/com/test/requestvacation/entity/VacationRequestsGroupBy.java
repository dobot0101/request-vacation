package com.test.requestvacation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class VacationRequestsGroupBy {

    @Id
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "total_use_days")
    private Integer totalUseDays;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getTotalUseDays() {
        return totalUseDays;
    }

    public void setTotalUseDays(Integer totalUseDays) {
        this.totalUseDays = totalUseDays;
    }
}
