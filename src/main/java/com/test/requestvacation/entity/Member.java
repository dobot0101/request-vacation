package com.test.requestvacation.entity;

import javax.persistence.*;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String name;

//    @Column(name = "remain_vacation_count")
//    @ColumnDefault("15")
//    private Integer remainVacationCount;

//    @PrePersist
//    private void prePersist() {
//        this.remainVacationCount = this.remainVacationCount == null ? 0 : this.remainVacationCount;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Integer getRemainVacationCount() {
//        return remainVacationCount;
//    }
//
//    public void setRemainVacationCount(Integer remainVacationCount) {
//        this.remainVacationCount = remainVacationCount;
//    }
}
