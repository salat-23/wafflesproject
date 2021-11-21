package com.salat23.wafflesproject.mapper;

import com.salat23.wafflesproject.model.DTO.EpisodeDTO;
import com.salat23.wafflesproject.model.entity.Episode;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EpisodeMapper {

    static EpisodeMapper getInstance() {
        return Mappers.getMapper(EpisodeMapper.class);
    }

    Episode toEpisodeEntity(EpisodeDTO episodeDTO);

    EpisodeDTO toEpisodeDTO(Episode episode);

}
