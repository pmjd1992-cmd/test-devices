package org.example.devices.db;

import org.example.devices.domain.Device;
import org.example.devices.domain.DeviceState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DeviceRepo extends JpaRepository<DeviceEntity, UUID> {
    List<Device> findByBrand(String brand);
    List<Device> findByState(DeviceState state);
}
