package com.aljp.repository;

import com.aljp.model.Device;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class DeviceRepository {

    private final Map<Integer, Device> devices = new ConcurrentHashMap<>();
    private final AtomicInteger idSequence = new AtomicInteger(0);

    @PostConstruct
    public void initializeData() {
        save(new Device(1, "Sensor Reactor A", "SR00123", "Planta Norte", "sensor de temperatura", "ACTIVE"));
        save(new Device(2, "Sensor Línea B", "SLB9999", "Línea de Producción B", "sensor de presión", "INACTIVE"));
    }

    public Device save(Device device) {
        if (device.getId() == null) {
            device.setId(idSequence.incrementAndGet());
        } else {
            idSequence.updateAndGet(current -> Math.max(current, device.getId()));
        }
        devices.put(device.getId(), device);
        return device;
    }

    public Collection<Device> findAll() {
        List<Device> deviceList = new ArrayList<>(devices.values());
        deviceList.sort(Comparator.comparing(Device::getId));
        return deviceList;
    }

    public Optional<Device> findById(Integer id) {
        return Optional.ofNullable(devices.get(id));
    }

    public Optional<Device> findBySerialNumber(String serialNumber) {
        return devices.values()
                .stream()
                .filter(device -> device.getSerialNumber().equals(serialNumber))
                .findFirst();
    }

    public void deleteById(Integer id) {
        devices.remove(id);
    }
}
