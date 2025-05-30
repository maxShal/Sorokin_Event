package com.example.Sorokin_Event.service;

import com.example.Sorokin_Event.dto.LocationDto;
import com.example.Sorokin_Event.entity.LocationEntity;
import com.example.Sorokin_Event.mapper.LocationMapper;
import com.example.Sorokin_Event.model.Location;
import com.example.Sorokin_Event.repository.LocationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private LocationMapper mapper;

    private LocationRepository repository;

    @Autowired
    public LocationService(LocationMapper mapper, LocationRepository repository){
        this.mapper = mapper;
        this.repository = repository;
    }

    public List<Location> findAll()
    {
        return repository.findAll().stream()
                .map(mapper::toModel)
                .toList();
    }

    public Location findById(Long id)
    {
        LocationEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Локация не найдена с ID: " + id));
        return mapper.toModel(mapper.toDto(entity));
    }


    public Location createLocation(Location location)
    {
        return mapper.toModel(repository.save(mapper.toEntity(mapper.toDto(location))));
    }

    public Location updateLocation(Location location)
    {
        if (location.getId() < 0)
        {
            throw new IllegalArgumentException("Неверный ID");
        }
        if(location.getAddress().isEmpty())
        {
            throw new IllegalArgumentException("Неверный адрес");
        }
        LocationEntity entity = repository.findById(location.getId())
                .orElseThrow(()-> new IllegalArgumentException("Локация не найдена с ID: " + location.getId()));
        LocationEntity updated = mapper.toEntity(location);
        entity.setDescription(updated.getDescription());
        entity.setCapacity(updated.getCapacity());
        entity.setAddress(updated.getAddress());
        return mapper.toModel(repository.save(entity));
    }

    public void deleteById(Long id)
    {
        LocationEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Данного элемента c ID: " +id +"не существует"));
        repository.deleteById(id);
    }
}
