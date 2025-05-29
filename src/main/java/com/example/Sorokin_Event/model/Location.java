package com.example.Sorokin_Event.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class Location {
    private Long id;

    private String address;
    private int capacity;
    private String peculiarities;

    public Location(Long id, String address, int capacity, String peculiarities) {
        this.id = id;
        this.address = address;
        this.capacity = capacity;
        this.peculiarities = peculiarities;
    }

    public Location() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPeculiarities() {
        return peculiarities;
    }

    public void setPeculiarities(String peculiarities) {
        this.peculiarities = peculiarities;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return capacity == location.capacity && Objects.equals(id, location.id) && Objects.equals(address, location.address) && Objects.equals(peculiarities, location.peculiarities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, capacity, peculiarities);
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", capacity=" + capacity +
                ", peculiarities='" + peculiarities + '\'' +
                '}';
    }
}
