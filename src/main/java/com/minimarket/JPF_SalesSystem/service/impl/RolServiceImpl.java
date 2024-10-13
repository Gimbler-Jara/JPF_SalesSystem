package com.minimarket.JPF_SalesSystem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minimarket.JPF_SalesSystem.model.Roles;
import com.minimarket.JPF_SalesSystem.repository.RolesRepository;
import com.minimarket.JPF_SalesSystem.service.RolService;

@Service
public class RolServiceImpl implements RolService{

    @Autowired
    private RolesRepository rolRepository;

    @Override
    public List<Roles> listarRoles() {
        return rolRepository.findAll();
    }

    @Override
    public void guardarRol(Roles rol) {
        rolRepository.save(rol);
    }

    @Override
    public Roles obtenerRolPorId(Integer id) {
        return rolRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarRol(Integer id) {
        rolRepository.deleteById(id);
    }
    @Override
    public boolean existeRol(String roleName) {
        List<Roles> roles = listarRoles();
        for (Roles rol : roles) {
            if (rol.getRol().equals(roleName)) {
                return true;
            }
        }
        return false;
    }
}
