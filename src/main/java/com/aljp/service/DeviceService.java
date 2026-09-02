package com.aljp.service;

import com.aljp.model.Device;
import com.aljp.repository.DeviceRepository;
import com.aljp.repository.MeasurementRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final MeasurementRepository measurementRepository;

    public DeviceService(DeviceRepository deviceRepository, MeasurementRepository measurementRepository) {
        this.deviceRepository = deviceRepository;
        this.measurementRepository = measurementRepository;
    }

    public Device registerDevice(Device device) {
        validateDevice(device);
        deviceRepository.findBySerialNumber(device.getSerialNumber())
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Ya existe un dispositivo con ese serialNumber");
                });
        return deviceRepository.save(device);
    }

    public Device updateDeviceEstate(Integer id, String estate) {
        if (id == null) {
            throw new IllegalArgumentException("El id del dispositivo es obligatorio");
        }

        String normalizedEstate = normalizeEstate(estate);

        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el dispositivo con id " + id));

        device.setEstate(normalizedEstate);
        return deviceRepository.save(device);
    }

    public void deleteDevice(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El id del dispositivo es obligatorio");
        }
        if (measurementRepository.existsByAssetId(id)) {
            throw new IllegalStateException("No se puede eliminar el dispositivo porque tiene mediciones asociadas");
        }
        deviceRepository.deleteById(id);
    }

    public Collection<Device> listDevices() {
        return deviceRepository.findAll();
    }

    private void validateDevice(Device device) {
        if (device == null) {
            throw new IllegalArgumentException("El dispositivo no puede ser nulo");
        }

        if (device.getName() == null || device.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del dispositivo no puede estar vacío");
        }

        if (device.getSerialNumber() == null || device.getSerialNumber().length() < 5) {
            throw new IllegalArgumentException("El serialNumber debe tener al menos 5 caracteres");
        }

        if (device.getSerialNumber().length() > 20) {
            throw new IllegalArgumentException("El serialNumber no puede tener más de 20 caracteres");
        }

        device.setEstate(normalizeEstate(device.getEstate()));
    }

    private String normalizeEstate(String estate) {
        if (estate == null) {
            throw new IllegalArgumentException("El Estate es obligatorio");
        }
        String normalized = estate.trim().toUpperCase();
        if (!"ACTIVE".equals(normalized) && !"INACTIVE".equals(normalized)) {
            throw new IllegalArgumentException("El Estate solo puede ser ACTIVE o INACTIVE");
        }
        return normalized;
    }
}
