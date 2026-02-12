package org.example.devices.domain;

import org.example.devices.db.DeviceEntity;
import org.example.devices.db.DeviceRepo;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeviceService {

    private final DeviceRepo repository;

    public DeviceService(DeviceRepo repository) {
        this.repository = repository;
    }

    public Device create(Device device) {
        DeviceEntity deviceEntity = new DeviceEntity();
        deviceEntity.setName(device.name());
        deviceEntity.setBrand(device.brand());
        deviceEntity.setState(device.state());
        deviceEntity.setCreationTime(Instant.now());

        DeviceEntity save = repository.save(deviceEntity);
        return new Device(save.getId(), save.getName(), save.getBrand(), save.getState(), save.getCreationTime());
    }

    public Device get(UUID id) {
        DeviceEntity device_not_found = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Device not found"));
        Device device = new Device(device_not_found.getId(), device_not_found.getName(), device_not_found.getBrand(),
                device_not_found.getState(), device_not_found.getCreationTime());
        return device;
    }

    public List<Device> getAll() {
        List<DeviceEntity> all = repository.findAll();
        List<Device> devices = new ArrayList<>();
        for (DeviceEntity d : all) {
            devices.add(new Device(d.getId(), d.getName(), d.getBrand(), d.getState(), d.getCreationTime()));
        }
        return devices;
    }

    public List<Device> getByBrand(String brand) {
        return repository.findByBrand(brand);
    }

    public List<Device> getByState(DeviceState state) {
        return repository.findByState(state);
    }

    public Device update(UUID id, Device updated) {
        Optional<DeviceEntity> existing = repository.findById(id);
        if (existing.isEmpty()) {
            return null;
        }
        if (existing.get().getState() == DeviceState.IN_USE) {
            if (!existing.get().getName().equals(updated.name()) ||
                    !existing.get().getBrand().equals(updated.brand())) {
                throw new RuntimeException("Cannot update name/brand while device is in use");
            }
        }
        DeviceEntity deviceEntity = existing.get();
        deviceEntity.setName(updated.name());
        deviceEntity.setBrand(updated.brand());
        deviceEntity.setState(updated.state());

        DeviceEntity save = repository.save(deviceEntity);
        return new Device(save.getId(), save.getName(), save.getBrand(), save.getState(), save.getCreationTime());
    }

    public void delete(UUID id) {
        Optional<DeviceEntity> device = repository.findById(id);

        if (device.isPresent() && device.get().getState() == DeviceState.IN_USE) {
            throw new RuntimeException("Cannot delete device in use");
        }

        repository.delete(device.get());
    }
}
