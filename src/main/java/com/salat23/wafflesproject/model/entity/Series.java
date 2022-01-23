package com.salat23.wafflesproject.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "series")
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String cover;
    @Column(name = "latest_update")
    private LocalDate latestUpdate;

    @Column(name = "release_date")
    private String releaseDate;

    private String director;

    private String studio;




}
