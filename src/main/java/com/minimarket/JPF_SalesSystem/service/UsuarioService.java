package com.minimarket.JPF_SalesSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minimarket.JPF_SalesSystem.model.Usuario;
import com.minimarket.JPF_SalesSystem.repository.UsuarioRepository;

@Service
public class UsuarioService {
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public  List<Usuario> listarUsuarios(){
		return usuarioRepository.findAll();
	}
	
	public void guardarUsuario(Usuario usuario) {
		usuarioRepository.save(usuario);
	}
	
	public Usuario buscarUsuarioPorId(Long id) {
		return usuarioRepository.findById(id).orElse(null);
	}
	
	public void eliminarUsuario(Long id) {
		usuarioRepository.deleteById(id);
	}
}
