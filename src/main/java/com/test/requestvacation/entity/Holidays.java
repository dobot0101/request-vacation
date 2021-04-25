package com.test.requestvacation.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Timestamp;

public class Holidays {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "holiday_at")
    private Date holidayAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getHolidayAt() {
        return holidayAt;
    }

    public void setHolidayAt(Date holidayAt) {
        this.holidayAt = holidayAt;
    }
}
