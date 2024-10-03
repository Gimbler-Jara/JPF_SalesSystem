package com.minimarket.JPF_SalesSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minimarket.JPF_SalesSystem.model.Roles;
import com.minimarket.JPF_SalesSystem.repository.RolesRepository;

@Service
public class RolService {

    @Autowired
    private RolesRepository rolRepository;

    public List<Roles> listarRoles() {
        return rolRepository.findAll();
    }

    public void guardarRol(Roles rol) {
        rolRepository.save(rol);
    }

    public Roles obtenerRolPorId(Integer id) {
        return rolRepository.findById(id).orElse(null);
    }

    public void eliminarRol(Integer id) {
        rolRepository.deleteById(id);
    }
}
