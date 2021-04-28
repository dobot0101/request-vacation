package com.test.requestvacation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class VacationRequestsGroupBy {

    @Id
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "total_use_days")
    private BigDecimal totalUseDays;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public BigDecimal getTotalUseDays() {
        return totalUseDays;
    }

    public void setTotalUseDays(BigDecimal totalUseDays) {
        this.totalUseDays = totalUseDays;
    }
}
