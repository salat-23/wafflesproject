package com.salat23.wafflesproject.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.salat23.wafflesproject.model.entity.Series;
import lombok.Data;

import javax.persistence.Column;

@Data
public class EpisodeDTO {
    private Long id;
    private Series series;
    @JsonProperty("video_id")
    private String videoId;
}
