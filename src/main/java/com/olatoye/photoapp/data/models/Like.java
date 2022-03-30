package com.olatoye.photoapp.data.models;

import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Date;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.FetchType.EAGER;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LIKE_TABLE")
public class Like {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @OneToOne(fetch = EAGER)
    private Photo photo;

    @OneToOne
    private Native liker;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateLiked;
}
