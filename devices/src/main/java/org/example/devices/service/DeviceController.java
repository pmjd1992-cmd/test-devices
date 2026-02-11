package org.example.devices.service;


import org.example.devices.domain.Device;
import org.example.devices.domain.DeviceService;
import org.example.devices.domain.DeviceState;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    private final DeviceService service;

    public DeviceController(DeviceService service) {
        this.service = service;
    }

    @PostMapping
    public Device create(@RequestBody Device device) {
        return service.create(device);
    }

    @GetMapping("/{id}")
    public Device get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping
    public List<Device> getAll() {
        return service.getAll();
    }

    @GetMapping("/brand/{brand}")
    public List<Device> getByBrand(@PathVariable String brand) {
        return service.getByBrand(brand);
    }

    @GetMapping("/state/{state}")
    public List<Device> getByState(@PathVariable DeviceState state) {
        return service.getByState(state);
    }

    @PutMapping("/{id}")
    public Device update(@PathVariable UUID id, @RequestBody Device device) {
        return service.update(id, device);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}

