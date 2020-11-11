package io.javabrains.nnpda.services;

import io.javabrains.nnpda.model.db.Role;

public interface RoleService {
    Role findByName(String name);
}
