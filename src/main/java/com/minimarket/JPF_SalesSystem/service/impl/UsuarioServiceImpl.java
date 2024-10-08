package com.minimarket.JPF_SalesSystem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minimarket.JPF_SalesSystem.model.Usuario;
import com.minimarket.JPF_SalesSystem.repository.UsuarioRepository;
import com.minimarket.JPF_SalesSystem.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public  List<Usuario> listarUsuarios(){
		return usuarioRepository.findAll();
	}
	
	@Override
	public void guardarUsuario(Usuario usuario) {
		usuarioRepository.save(usuario);
	}
	
	@Override
	public Usuario buscarUsuarioPorId(Long id) {
		return usuarioRepository.findById(id).orElse(null);
	}
	
	@Override
	public void eliminarUsuario(Long id) {
		usuarioRepository.deleteById(id);
	}
}
