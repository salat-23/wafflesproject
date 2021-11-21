package com.salat23.wafflesproject.controller;

import com.salat23.wafflesproject.model.DTO.EpisodeDTO;
import com.salat23.wafflesproject.model.DTO.VideoData;
import com.salat23.wafflesproject.service.VideoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EpisodeController {

    private final VideoService videoService;

    public EpisodeController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/video")
    public List<EpisodeDTO> getVideoAll() {
        return videoService.getVideos();
    }

    @GetMapping("/video/{id}")
    public EpisodeDTO getVideo(@PathVariable Long id) {
        return videoService.getVideoData(id);
    }

}