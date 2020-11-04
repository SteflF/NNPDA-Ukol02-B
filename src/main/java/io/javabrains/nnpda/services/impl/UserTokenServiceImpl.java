package io.javabrains.nnpda.services.impl;

import io.javabrains.nnpda.model.UserToken;
import io.javabrains.nnpda.services.UserTokenService;
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
