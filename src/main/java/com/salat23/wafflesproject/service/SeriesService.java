package com.salat23.wafflesproject.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.salat23.wafflesproject.model.DTO.EpisodeDTO;
import com.salat23.wafflesproject.model.DTO.NewEpisodeDTO;
import com.salat23.wafflesproject.model.DTO.SeriesDTO;
import com.salat23.wafflesproject.model.DTO.UploadSeriesDTO;
import com.salat23.wafflesproject.model.entity.Episode;
import com.salat23.wafflesproject.model.entity.Genre;
import com.salat23.wafflesproject.model.entity.Series;
import com.salat23.wafflesproject.repository.EpisodesRepository;
import com.salat23.wafflesproject.repository.GenresRepository;
import com.salat23.wafflesproject.repository.SeriesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SeriesService {

    SeriesRepository seriesRepository;
    GenresRepository genresRepository;
    EpisodesRepository episodeRepository;
    private Cloudinary cloudinary;


    public Page<Series> getSeries(int page) {
        Pageable sortedByLatest = PageRequest.of(page, 3, Sort.by("latestUpdate").descending());
        return seriesRepository.findAll(sortedByLatest);
    }

    public Page<Series> getSeriesByGenres(int page, String genresRequest) {
        Pageable sortedByLatest = PageRequest.of(page, 8, Sort.by("latestUpdate").descending());
        /*List<Genre> genres = genresRepository.findAllByGenreTitleIn(genresRequest);*/
        return seriesRepository.findAllByNameContaining(genresRequest, sortedByLatest);
    }

    public Page<Series> fetchSeries(Integer page, Integer pageSize, String sortingField, String sortingDirection) {
        Sort sort = Sort.by(Sort.Direction.valueOf(sortingDirection), sortingField);
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        return seriesRepository.findAll(pageable);
    }

    public String uploadSeries(UploadSeriesDTO request) {

        if (seriesRepository.findByName(request.getTitle()).isPresent()) throw new EntityExistsException();
        Series series = new Series();
        series.setName(request.getTitle());

        series.setDescription(request.getDescription());
        series.setDirector(request.getDirector());
        series.setReleaseDate(request.getReleaseDate());
        series.setStudio(request.getStudio());
        series.setEpisodeCount(0);
        series.setLatestUpdate(LocalDate.now());
        seriesRepository.save(series);
        for (String newGenre : request.getGenres()) {
            Genre genre = new Genre();
            genre.setGenreTitle(newGenre);
            genre.setSeries(series);
            genresRepository.save(genre);
        }
        return "OK";
    }

    public void uploadEpisode(NewEpisodeDTO newEpisode, String name) {
        Series series = seriesRepository.findByName(name).orElseThrow(
                () -> new EntityNotFoundException(String.format("Series %s not found", name)));
        Episode episode = new Episode();
        episode.setSeries(series);
        episode.setTitle(newEpisode.getTitle());
        episode.setUrl(newEpisode.getUrl());
        episode.setNumber(episodeRepository.findAllBySeries_Name(name).size() + 1);
        episodeRepository.save(episode);
    }

    public String updateSeries(String name, UploadSeriesDTO newSeries) {
        Series series = seriesRepository.findByName(name).orElseThrow(
                () -> new EntityNotFoundException(String.format("Series %s not found", name)));
        genresRepository.deleteAllBySeries_Name(name);
        for (String newGenre : newSeries.getGenres()) {
            Genre genre = new Genre();
            genre.setGenreTitle(newGenre);
            genre.setSeries(series);
            genresRepository.save(genre);
        }
        series.setName(newSeries.getTitle());
        series.setDescription(newSeries.getDescription());
        series.setDirector(newSeries.getDirector());
        series.setStudio(newSeries.getStudio());
        series.setReleaseDate(newSeries.getReleaseDate());
        series.setLatestUpdate(LocalDate.now());

        seriesRepository.save(series);
        return "Ok";
    }


    @Transactional
    public String uploadSeriesCover(MultipartFile file, String name) {
        try {
            File coverImage = Files.createTempFile("tempCover", file.getOriginalFilename()).toFile();
            file.transferTo(coverImage);
            Map uploadResult = cloudinary.uploader().upload(coverImage, ObjectUtils.emptyMap());
            String url = (String) uploadResult.get("url");
            System.out.println(url);
            seriesRepository.setSeriesCoverByName(name, url);
        } catch (IOException ex) {
            throw new IllegalArgumentException();
        }
        return "OK";
    }

    public void formSeriesPage(String name, Model model) {
        Optional<Series> optSeries = seriesRepository.findByName(name);
        if (optSeries.isPresent()) model.addAttribute("series", optSeries.get());
        else throw new EntityNotFoundException();
    }

    public SeriesDTO getSeriesInfo(String name) {
        SeriesDTO seriesDTO = new SeriesDTO();
        Optional<Series> optSeries = seriesRepository.findByName(name);
        if (optSeries.isEmpty()) {
            System.out.println(name);
            throw new EntityNotFoundException();
        }
        Series series = optSeries.get();
        seriesDTO.setName(series.getName());
        seriesDTO.setCover(series.getCover());
        List<Episode> episodes = episodeRepository.findAllBySeries_Name(series.getName());
        List<Genre> genresList = genresRepository.findAllBySeries_Name(series.getName());
        List<String> genres = genresList.stream().map(Genre::getGenreTitle).collect(Collectors.toList());
        seriesDTO.setStudio(series.getStudio());
        seriesDTO.setDirector(series.getDirector());
        seriesDTO.setGenres(genres);
        seriesDTO.setEpisodes(episodes);
        seriesDTO.setReleaseDate(series.getReleaseDate());
        seriesDTO.setDescription(series.getDescription());
        return seriesDTO;
    }

}