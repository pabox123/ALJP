package com.aljp.repository;

import com.aljp.model.Measurement;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MeasurementRepository {

    private final Map<Integer, Measurement> measurements = new ConcurrentHashMap<>();
    private final AtomicInteger idSequence = new AtomicInteger(0);

    @PostConstruct
    public void initializeData() {
        save(new Measurement(1, "2026-09-01T08:10:00", 25.4, "°C", 1));
        save(new Measurement(2, "2026-09-01T08:11:00", 2.1, "bar", 2));
    }

    public Measurement save(Measurement measurement) {
        if (measurement.getId() == null) {
            measurement.setId(idSequence.incrementAndGet());
        } else {
            idSequence.updateAndGet(current -> Math.max(current, measurement.getId()));
        }
        measurements.put(measurement.getId(), measurement);
        return measurement;
    }

    public Collection<Measurement> findAll() {
        return new ArrayList<>(measurements.values());
    }

    public List<Measurement> findByAssetId(Integer assetId) {
        return measurements.values()
                .stream()
                .filter(measurement -> measurement.getAssetId().equals(assetId))
                .toList();
    }

    public boolean existsByAssetId(Integer assetId) {
        return measurements.values().stream().anyMatch(measurement -> measurement.getAssetId().equals(assetId));
    }
}
