package io.javabrains.nnpda.services;

import io.javabrains.nnpda.model.db.RecoverToken;

public interface RecoverTokenService {
    RecoverToken findById(int id);

    RecoverToken findByToken(String token);
}
