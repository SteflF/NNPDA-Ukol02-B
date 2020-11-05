package io.javabrains.nnpda.services.impl;

import io.javabrains.nnpda.model.Role;
import io.javabrains.nnpda.repository.RoleRepository;
import io.javabrains.nnpda.services.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) {
        Optional<Role> optionalRole = roleRepository.findByName(name);

        return optionalRole.orElse(null);
    }
}
