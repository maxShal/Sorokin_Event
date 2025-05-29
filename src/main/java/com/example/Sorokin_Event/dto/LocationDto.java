package com.example.Sorokin_Event.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
@Getter
@Setter
public class LocationDto
{
    @NotBlank
    @Size(min = 1, max = 100)
    private String address;
    @NotBlank
    @Min(1)
    private int capacity;

    private String peculiarities;

    public LocationDto(String address, int capacity, String peculiarities) {
        this.address = address;
        this.capacity = capacity;
        this.peculiarities = peculiarities;
    }

    public LocationDto() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LocationDto that = (LocationDto) o;
        return capacity == that.capacity && Objects.equals(address, that.address) && Objects.equals(peculiarities, that.peculiarities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, capacity, peculiarities);
    }

    @Override
    public String toString() {
        return "LocationDto{" +
                "address='" + address + '\'' +
                ", capacity=" + capacity +
                ", peculiarities='" + peculiarities + '\'' +
                '}';
    }
}
