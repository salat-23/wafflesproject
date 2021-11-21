package com.salat23.wafflesproject.model.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
public class SeriesRequest {
    private String name;
    private MultipartFile cover;
}
