package com.lucidprogrammers.projects.musicwikiaback.database.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "artist")
    private List<Album> albums;

    @Column(length = 75, nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigInteger monthlyListener = BigInteger.ZERO;

    @Column(length = 50, nullable = false)
    private String status;

    @Column(nullable = false)
    private Date startedDate;

    private Date endDate;

    @Column(nullable = false)
    private Date createdDate = new Date();

}
