package com.olatoye.photoapp.data.models;

import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Temporal;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.TemporalType;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;


@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private String tagName;

    @ManyToMany(mappedBy = "tags", fetch = LAZY)
    private List<Photo> photos;

    @CreationTimestamp
    private LocalDateTime dateCreated = LocalDateTime.now();
}
