package com.example.Sorokin_Event.mapper;

import com.example.Sorokin_Event.entity.EventRegistrationEntity;
import com.example.Sorokin_Event.model.EventRegistration;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RegistrationMapper {

    EventRegistration toModel(EventRegistrationEntity entity);
}
