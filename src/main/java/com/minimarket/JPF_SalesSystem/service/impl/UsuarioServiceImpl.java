package com.minimarket.JPF_SalesSystem.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minimarket.JPF_SalesSystem.model.Usuario;
import com.minimarket.JPF_SalesSystem.repository.UsuarioRepository;
import com.minimarket.JPF_SalesSystem.service.UsuarioService;
import com.minimarket.JPF_SalesSystem.utility.MessageErrorException;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public List<Usuario> listarUsuarios() {
		return usuarioRepository.findAll();
	}

	@Override
	public void guardarUsuario(Usuario usuario) {
		validarUsuario(usuario);
		usuarioRepository.save(usuario);
	}
	
	public void validarUsuario(Usuario usuario) {
	    // Validación de Username
	    if (usuario.getUsername() == null || usuario.getUsername().isEmpty()) {
	        throw new MessageErrorException("El nombre de usuario no puede estar vacío");
	    }

	    // Validación de Email
	    if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
	        throw new MessageErrorException("El correo electrónico no puede estar vacío");
	    }
	    // Verificación básica de formato de email
	    if (!usuario.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
	        throw new MessageErrorException("El formato del correo electrónico es inválido");
	    }

	    // Validación de Password
	    if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
	        throw new MessageErrorException("La contraseña no puede estar vacía");
	    }
	    if (usuario.getPassword().length() < 8) {
	        throw new MessageErrorException("La contraseña debe tener al menos 8 caracteres");
	    }

	    // Validación de Rol
	    if (usuario.getRol() == null || usuario.getRol().getIdRol() == null) {
	        throw new MessageErrorException("Debe seleccionar un rol válido");
	    }
	}

	@Override
	public Usuario buscarUsuarioPorId(Long id) {
		return usuarioRepository.findById(id).orElse(null);
	}

	@Override
	public void eliminarUsuario(Long id) {
		usuarioRepository.deleteById(id);
	}

	@Override
	public Usuario buscarPorCorreo(String correo) {
		return usuarioRepository.findByEmail(correo);
	}

	@Override
	public List<Usuario> listarClientes() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		List<Usuario> clientes = new ArrayList<>();
		for (Usuario usuario : usuarios) {
			if (usuario.getEstado() == true) {
				if ("CLIENTE".equals(usuario.getRol().getRol())) {
					clientes.add(usuario);
				}
			}
		}
		return clientes;
	}
}
