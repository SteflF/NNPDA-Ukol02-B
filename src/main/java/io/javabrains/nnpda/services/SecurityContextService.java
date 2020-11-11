package io.javabrains.nnpda.services;

import io.javabrains.nnpda.model.db.User;

public interface SecurityContextService {
    User GetAuthenticatedUser();

    User AuthenticateUser(String username, String password);
}
