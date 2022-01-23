package com.salat23.wafflesproject.repository;

import com.salat23.wafflesproject.model.entity.Episode;
import com.salat23.wafflesproject.model.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenresRepository extends JpaRepository<Genre, Long> {

    List<Genre> findAllBySeries_Name(String seriesName);
    void deleteAllBySeries_Name(String seriesName);

}
