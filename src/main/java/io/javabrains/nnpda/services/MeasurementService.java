package io.javabrains.nnpda.services;

import io.javabrains.nnpda.model.db.Measurement;

public interface MeasurementService {
    Measurement getLatest(int id);
}
