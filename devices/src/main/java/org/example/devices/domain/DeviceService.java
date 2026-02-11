package org.example.devices.domain;

import org.example.devices.db.DeviceRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DeviceService {

    private final DeviceRepo repository;

    public DeviceService(DeviceRepo repository) {
        this.repository = repository;
    }

    public Device create(Device device) {
        return repository.save(device);
    }

    public Device get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Device not found"));
    }

    public List<Device> getAll() {
        return repository.findAll();
    }

    public List<Device> getByBrand(String brand) {
        return repository.findByBrand(brand);
    }

    public List<Device> getByState(DeviceState state) {
        return repository.findByState(state);
    }

    public Device update(UUID id, Device updated) {
        Device existing = get(id);

        if (existing.getState() == DeviceState.IN_USE) {
            if (!existing.getName().equals(updated.getName()) ||
                    !existing.getBrand().equals(updated.getBrand())) {
                throw new RuntimeException("Cannot update name/brand while device is in use");
            }
        }

        existing.setName(updated.getName());
        existing.setBrand(updated.getBrand());
        existing.setState(updated.getState());

        return repository.save(existing);
    }

    public void delete(UUID id) {
        Device device = get(id);

        if (device.getState() == DeviceState.IN_USE) {
            throw new RuntimeException("Cannot delete device in use");
        }

        repository.delete(device);
    }
}
