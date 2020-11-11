package io.javabrains.nnpda.services.impl;

import io.javabrains.nnpda.model.db.Role;
import io.javabrains.nnpda.repository.RoleRepository;
import io.javabrains.nnpda.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) {
        Optional<Role> optionalRole = roleRepository.findByName(name);

        return optionalRole.orElse(null);
    }
}
