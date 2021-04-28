package com.test.requestvacation.entity;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "vacation_requests")
@Entity
public class VacationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "start_at")
    private Date startAt;

    @Nullable
    @Column(name = "end_at")
    private Date endAt;

    @Column(name = "use_day")
    private BigDecimal useDay;

    @Column(name = "comments")
    private String comments;

    @Column(name = "is_cancel")
    private String isCancel;

    @PrePersist
    private void prePersist() {
        this.isCancel = this.isCancel == null ? "N" : this.isCancel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public Date getEndAt() {
        return endAt;
    }

    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }

    public BigDecimal getUseDay() {
        return useDay;
    }

    public void setUseDay(BigDecimal useDay) {
        this.useDay = useDay;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(String isCancel) {
        this.isCancel = isCancel;
    }
}
