package com.salat23.wafflesproject.controller;

import com.salat23.wafflesproject.model.DTO.*;
import com.salat23.wafflesproject.model.entity.Series;
import com.salat23.wafflesproject.service.SeriesService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;

@RestController
@AllArgsConstructor
public class SeriesController {

    private SeriesService seriesService;

    @GetMapping("/series/latest")
    public Page<Series> getLatestSeries(@RequestBody Integer index) {
        return seriesService.getSeries(index);
    }

    @PostMapping("/series/genres")
    public Page<Series> getLatestSeriesGenres(@RequestBody GenresPaginationDTO request) {
        return seriesService.getSeriesByGenres(request.getIndex(), request.getGenres());
    }

    @GetMapping("/series")
    public Page<Series> getUsers(@RequestParam Integer page, @RequestParam Integer size,
                                 @RequestParam String sortingField, @RequestParam String sortingDirection) {
        return seriesService.fetchSeries(page, size, sortingField, sortingDirection);
    }

    @PostMapping("/series/upload")
    public String uploadSeries(@RequestBody UploadSeriesDTO request) {
        return seriesService.uploadSeries(request);
    }

    @PostMapping("/series/upload/image")
    public String uploadSeriesImage(@RequestParam("file") MultipartFile file, @RequestParam("name") String name) {
        return seriesService.uploadSeriesCover(file, name);
    }

    @RequestMapping("/series/{name}")
    public ModelAndView watchSeries(@PathVariable String name, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("watch");
        seriesService.formSeriesPage(name, model);
        return modelAndView;
    }

    @RequestMapping("/admin/qwerty/upload_series")
    public ModelAndView getAdminUploadSeries() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("uploadSeries");
        return modelAndView;
    }

    @RequestMapping("/admin/qwerty/edit/{name}")
    public ModelAndView getAdminEditSeries(@PathVariable String name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("edit");
        return modelAndView;
    }


    @GetMapping({"/series/{name}/get", "/admin/qwerty/edit/{name}/get"})
    public SeriesDTO getEpisodes(@PathVariable String name) {
        return seriesService.getSeriesInfo(name);
    }

}
