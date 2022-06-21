package com.olatoye.photoapp.data.models;


import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;


@Embeddable
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cohort {

    private String cohortName;
    private Integer cohortNumber;
}
