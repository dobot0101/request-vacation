package com.test.requestvacation.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class MemberVacation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "year")
    private Integer year;

    @Column(name = "vacation_days")
//    private Integer vacationDays;
    private BigDecimal vacationDays;

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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

//    public Integer getVacationDays() {
//        return vacationDays;
//    }
//
//    public void setVacationDays(Integer vacationDays) {
//        this.vacationDays = vacationDays;
//    }

    public BigDecimal getVacationDays() {
        return vacationDays;
    }

    public void setVacationDays(BigDecimal vacationDays) {
        this.vacationDays = vacationDays;
    }
}
