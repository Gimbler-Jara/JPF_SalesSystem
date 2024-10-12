package com.minimarket.JPF_SalesSystem.service;

import java.util.List;

import com.minimarket.JPF_SalesSystem.model.Roles;

public interface RolService {

	public List<Roles> listarRoles();

	public void guardarRol(Roles rol);

	public Roles obtenerRolPorId(Integer id);

	public void eliminarRol(Integer id);

	public boolean existeRol(String roleName);

}
