package com.olatoye.photoapp.data.models;

import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Embedded;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
//@RequiredArgsConstructor
@AllArgsConstructor
public class Native {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String userName;

    @Column(unique = true)
    private String email;

    @Embedded
    private Cohort cohort;

    @Temporal(TemporalType.DATE)
    private Date dateCreated;
}
