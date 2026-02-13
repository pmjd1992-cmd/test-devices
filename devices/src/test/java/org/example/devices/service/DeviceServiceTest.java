package org.example.devices.service;

import org.example.devices.db.DeviceEntity;
import org.example.devices.db.DeviceRepo;
import org.example.devices.domain.Device;
import org.example.devices.domain.DeviceService;
import org.example.devices.domain.DeviceState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {

    @Mock
    private DeviceRepo repository;

    @InjectMocks
    private DeviceService service;

    @Test
    void shouldCreateDevice() {
        Device request =
                new Device(null, "iPhone", "Apple", DeviceState.AVAILABLE, null);

        DeviceEntity entity = new DeviceEntity();
        entity.setId(UUID.randomUUID());
        entity.setName("iPhone");
        entity.setBrand("Apple");
        entity.setState(DeviceState.AVAILABLE);
        entity.setCreationTime(Instant.now());

        when(repository.save(any())).thenReturn(entity);

        Device device = service.create(request);

        Assertions.assertEquals(entity.getId(), device.id());
        Assertions.assertEquals(entity.getCreationTime(), device.creationTime());
        Assertions.assertEquals(entity.getName(), device.name());
        Assertions.assertEquals(entity.getBrand(), device.brand());
        Assertions.assertEquals(entity.getState(), device.state());

        verify(repository, times(1)).save(any());
    }

    @Test
    void shouldNotAllowNameChangeWhenInUse() {
        UUID id = UUID.randomUUID();

        DeviceEntity existing = new DeviceEntity();
        existing.setName("Old");
        existing.setBrand("Brand");
        existing.setState(DeviceState.IN_USE);

        when(repository.findById(id)).thenReturn(Optional.of(existing));

        Device request =
                new Device(null, "NewName", "Brand", DeviceState.IN_USE, null);

        assertThatThrownBy(() -> service.update(id, request))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void shouldNotDeleteWhenInUse() {
        UUID id = UUID.randomUUID();

        DeviceEntity device = new DeviceEntity();
        device.setId(id);
        device.setState(DeviceState.IN_USE);

        when(repository.findById(id)).thenReturn(Optional.of(device));

        assertThatThrownBy(() -> service.delete(id))
                .isInstanceOf(RuntimeException.class);

        verify(repository, never()).delete(any());
    }

    @Test
    void shouldDeleteWhenAvailable() {
        UUID id = UUID.randomUUID();

        DeviceEntity device = new DeviceEntity();

        device.setId(id);
        device.setState(DeviceState.AVAILABLE);

        when(repository.findById(id)).thenReturn(Optional.of(device));

        service.delete(id);

        verify(repository, times(1)).delete(device);
    }
}
