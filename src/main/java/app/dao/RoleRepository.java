package app.dao;

import app.model.Role;

import java.util.List;

public interface RoleRepository {
    Role findById(Long id);
    Role findByName(String name);
    List<Role> findAll();
}
