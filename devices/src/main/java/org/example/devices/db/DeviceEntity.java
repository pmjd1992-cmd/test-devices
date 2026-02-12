package org.example.devices.db;

import jakarta.persistence.*;
import org.example.devices.domain.DeviceState;

import java.time.Instant;
import java.util.UUID;

@Entity
public class DeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String brand;

    @Enumerated(EnumType.STRING)
    private DeviceState state;

    @Column(updatable = false, nullable = false)
    private Instant creationTime;

    @PrePersist
    void onCreate() {
        this.creationTime = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public DeviceState getState() {
        return state;
    }

    public void setState(DeviceState state) {
        this.state = state;
    }

    public Instant getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Instant creationTime) {
        this.creationTime = creationTime;
    }
}
