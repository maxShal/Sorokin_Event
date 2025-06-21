package com.example.Sorokin_Event.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class LocationDto
{
    @NotBlank
    @Size(min = 1, max = 100)
    private String address;
    @NotNull
    @Min(1)
    private int capacity;
    private String description;

    public LocationDto(String address, int capacity, String description) {
        this.address = address;
        this.capacity = capacity;
        this.description = description;
    }

    public LocationDto() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LocationDto that = (LocationDto) o;
        return capacity == that.capacity && Objects.equals(address, that.address) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, capacity, description);
    }

    @Override
    public String toString() {
        return "LocationDto{" +
                "address='" + address + '\'' +
                ", capacity=" + capacity +
                ", peculiarities='" + description + '\'' +
                '}';
    }
}
