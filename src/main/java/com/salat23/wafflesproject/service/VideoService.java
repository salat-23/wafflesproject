package com.salat23.wafflesproject.service;

import com.salat23.wafflesproject.mapper.EpisodeMapper;
import com.salat23.wafflesproject.model.DTO.EpisodeDTO;
import com.salat23.wafflesproject.model.DTO.VideoData;
import com.salat23.wafflesproject.model.entity.Episode;
import com.salat23.wafflesproject.repository.EpisodesRepository;
import com.sun.istack.logging.Logger;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class VideoService {

    EpisodesRepository episodeRepository;

    public VideoService(EpisodesRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }

    public List<EpisodeDTO> getVideos() {
        List<Episode> videos = episodeRepository.findAll();
        List<EpisodeDTO> episodeDTOS = new ArrayList<>();
        for(Episode episode : videos) {
            episodeDTOS.add(EpisodeMapper.getInstance().toEpisodeDTO(episode));
        }
        return episodeDTOS;
    }

    public EpisodeDTO getVideoData(Long id) {
        Episode episode = episodeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return EpisodeMapper.getInstance().toEpisodeDTO(episode);
    }


}
