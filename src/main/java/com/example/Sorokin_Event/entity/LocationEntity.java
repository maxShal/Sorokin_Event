package com.example.Sorokin_Event.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
public class LocationEntity
{
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String address;
    private int capacity;
    private String peculiarities;

    public LocationEntity(String address, int capacity, String peculiarities) {
        this.address = address;
        this.capacity = capacity;
        this.peculiarities = peculiarities;
    }

    public LocationEntity(){}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LocationEntity that = (LocationEntity) o;
        return capacity == that.capacity && Objects.equals(id, that.id) && Objects.equals(address, that.address) && Objects.equals(peculiarities, that.peculiarities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, capacity, peculiarities);
    }

    @Override
    public String
    toString() {
        return "LocationEntity{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", capacity=" + capacity +
                ", peculiarities='" + peculiarities + '\'' +
                '}';
    }
}
