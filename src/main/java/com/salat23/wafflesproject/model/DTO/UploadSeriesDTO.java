package com.salat23.wafflesproject.model.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UploadSeriesDTO {
    private String title;
    private String description;
    private List<String> genres;
    private String releaseDate;
    private String director;
    private String studio;
    private String code;
}
