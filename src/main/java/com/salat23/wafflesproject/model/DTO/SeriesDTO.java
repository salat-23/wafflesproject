package com.salat23.wafflesproject.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.salat23.wafflesproject.model.entity.Episode;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SeriesDTO {

    private String name;
    private String description;
    private String cover;
    private String studio;
    private String director;
    private List<String> genres;
    @JsonProperty(value = "release_date")
    private LocalDate releaseDate;

    private List<Episode> episodes;

}
