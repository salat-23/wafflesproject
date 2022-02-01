package com.salat23.wafflesproject.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.salat23.wafflesproject.model.entity.Series;
import lombok.Data;

@Data
public class NewEpisodeDTO {
    @JsonProperty("title")
    private String title;
    @JsonProperty("url")
    private String url;
}
