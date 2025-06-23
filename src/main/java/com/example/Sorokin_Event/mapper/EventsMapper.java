package com.example.Sorokin_Event.mapper;


import com.example.Sorokin_Event.dto.EventUpdateDto;
import com.example.Sorokin_Event.dto.EventsDto;
import com.example.Sorokin_Event.dto.EventsResponseDto;
import com.example.Sorokin_Event.entity.EventsEntity;
import com.example.Sorokin_Event.model.Events;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EventsMapper {

    EventsDto toDto(Events events);
    Events toModel(EventsDto dto);
    Events toModel(EventsEntity entity);
    //EventsEntity toEntity(Events ev);
    EventsEntity toEntity(EventsDto dto);
    EventsResponseDto toRespDto(Events events);
    Events toModel(EventsResponseDto dto);
    Events toModel(EventUpdateDto dto);
}
