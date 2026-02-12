package org.example.devices.domain;


import java.time.Instant;
import java.util.UUID;

//public class Device {
//
//
//    private UUID id;
//
//    private String name;
//    private String brand;
//
//    private DeviceState state;
//
//    private Instant creationTime;
//
//    public UUID getId() {
//        return id;
//    }
//
//    public void setId(UUID id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getBrand() {
//        return brand;
//    }
//
//    public void setBrand(String brand) {
//        this.brand = brand;
//    }
//
//    public DeviceState getState() {
//        return state;
//    }
//
//    public void setState(DeviceState state) {
//        this.state = state;
//    }
//
//    public Instant getCreationTime() {
//        return creationTime;
//    }
//
//    public void setCreationTime(Instant creationTime) {
//        this.creationTime = creationTime;
//    }
//}

public record Device (UUID id,
                      String name, String brand, DeviceState state, Instant creationTime) {
}

