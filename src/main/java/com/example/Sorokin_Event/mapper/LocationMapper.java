package com.example.Sorokin_Event.mapper;

import com.example.Sorokin_Event.dto.LocationDto;
import com.example.Sorokin_Event.entity.LocationEntity;
import com.example.Sorokin_Event.model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LocationMapper {
    Location toModel(LocationDto dto);
    LocationEntity toEntity(LocationDto dto);
    LocationDto toDto(Location location);
    LocationDto toDto(LocationEntity entity);
    LocationEntity toEntity(Location location);
    Location toModel(LocationEntity entity);
}
