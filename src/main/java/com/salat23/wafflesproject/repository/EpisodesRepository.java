package com.salat23.wafflesproject.repository;

import com.salat23.wafflesproject.model.entity.Episode;
import com.salat23.wafflesproject.model.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EpisodesRepository extends JpaRepository<Episode, Long>, JpaSpecificationExecutor<Episode> {
    List<Episode> findAllBySeries_Name(String seriesName);
    Optional<Episode> findById(Long id);
}
