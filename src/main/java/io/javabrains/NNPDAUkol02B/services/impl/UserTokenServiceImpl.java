package io.javabrains.NNPDAUkol02B.services.impl;

import io.javabrains.NNPDAUkol02B.model.UserToken;
import io.javabrains.NNPDAUkol02B.services.UserTokenService;
import org.springframework.stereotype.Service;

@Service("userTokenService")
public class UserTokenServiceImpl implements UserTokenService {
    @Override
    public UserToken findById(int id) {
        return null;
    }

    @Override
    public UserToken findByName(String token) {
        return null;
    }
}
