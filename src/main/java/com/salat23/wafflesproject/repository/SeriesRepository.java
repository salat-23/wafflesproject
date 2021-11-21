package com.salat23.wafflesproject.repository;

import com.salat23.wafflesproject.model.entity.Series;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Long>, JpaSpecificationExecutor<Series> {
    Optional<Series> findByNameContains(String title);
    Page<Series> findAllByNameContaining(String name, Pageable pageable);
    Optional<Series> findByName(String title);
    @Modifying
    @Query("update Series s set s.cover = ?2 where s.name = ?1")
    void setSeriesCoverByName(String name, String url);

}