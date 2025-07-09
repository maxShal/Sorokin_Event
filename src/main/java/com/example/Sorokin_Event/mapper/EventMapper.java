package com.example.Sorokin_Event.mapper;


import com.example.Sorokin_Event.dto.EventDto;
import com.example.Sorokin_Event.dto.EventUpdateDto;
import com.example.Sorokin_Event.dto.EventResponseDto;
import com.example.Sorokin_Event.entity.EventEntity;
import com.example.Sorokin_Event.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EventMapper {

    EventDto toDto(Event event);
    Event toModel(EventDto dto);
    Event toModel(EventEntity entity);
    //EventsEntity toEntity(Events ev);
    EventEntity toEntity(EventDto dto);
    EventResponseDto toRespDto(Event event);
    Event toModel(EventResponseDto dto);
    Event toModel(EventUpdateDto dto);
}
