package com.lucidprogrammers.projects.musicwikiaback.database.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Entity that holds songs information
 *
 * @author Benjamin Gil Flores
 * @since 1.0.0
 */
@Getter
@Setter
@Entity
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(nullable = false)
    private Float duration;

    @Column(length = 100, nullable = true)
    private String featuring;

    @Column(columnDefinition = "TEXT")
    private String lyric;

    private Boolean single;

    private Boolean explicit;

}
