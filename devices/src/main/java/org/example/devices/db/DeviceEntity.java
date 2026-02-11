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
}
