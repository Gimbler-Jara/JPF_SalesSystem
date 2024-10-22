package com.minimarket.JPF_SalesSystem.service;

import java.util.List;

import com.minimarket.JPF_SalesSystem.model.Usuario;

public interface UsuarioService {

	public List<Usuario> listarUsuarios();

	public void guardarUsuario(Usuario usuario);

	public Usuario buscarUsuarioPorId(Long id);

	public void eliminarUsuario(Long id);
	
	public Usuario buscarPorCorreo(String correo);
	
	public List<Usuario> listarClientes();
	
	public void login(String email, String password);
}
