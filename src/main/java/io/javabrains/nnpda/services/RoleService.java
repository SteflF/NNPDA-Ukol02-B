package io.javabrains.nnpda.services;

import io.javabrains.nnpda.model.Role;

public interface RoleService {
    Role findByName(String name);
}
