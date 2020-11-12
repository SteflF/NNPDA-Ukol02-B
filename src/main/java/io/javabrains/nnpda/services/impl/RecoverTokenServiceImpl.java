package io.javabrains.nnpda.services.impl;

import io.javabrains.nnpda.model.db.RecoverToken;
import io.javabrains.nnpda.services.RecoverTokenService;
import org.springframework.stereotype.Service;

@Service("recoverTokenService")
public class RecoverTokenServiceImpl implements RecoverTokenService {
    @Override
    public RecoverToken findById(int id) {
        return null;
    }

    @Override
    public RecoverToken findByToken(String token) {
        return null;
    }
}
