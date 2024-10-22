package com.minimarket.JPF_SalesSystem.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.minimarket.JPF_SalesSystem.model.Usuario;
import com.minimarket.JPF_SalesSystem.service.RolService;
import com.minimarket.JPF_SalesSystem.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private RolService rolService;

	@GetMapping
	public String listarUsuarios(Model model, @ModelAttribute("successMessage") String successMessage,@ModelAttribute("errorMessage") String errorMessage) {
		model.addAttribute("usuarios", usuarioService.listarUsuarios());
		model.addAttribute("roles", rolService.listarRoles());
		model.addAttribute("usuario", new Usuario());

		model.addAttribute("successMessage", successMessage);
		model.addAttribute("errorMessage", errorMessage);

		return "usuarios/lista";
	}

	@PostMapping("/guardar")
	public String guardarUsuario(Usuario usuario, Model model, RedirectAttributes redirectAttributes) {
		try {
			Usuario usuarioExistentePorCorreo = usuarioService.buscarPorCorreo(usuario.getEmail());
			if (usuarioExistentePorCorreo != null && !usuarioExistentePorCorreo.getId_usuario().equals(usuario.getId_usuario())) {
				model.addAttribute("errorMessage", "Correo ya registrado!!");
				model.addAttribute("user", usuario);
				model.addAttribute("usuarios", usuarioService.listarUsuarios());
				model.addAttribute("roles", rolService.listarRoles());
				return "usuarios/lista";
			}

			usuario.setFechaCreacion(LocalDateTime.now());
			usuarioService.guardarUsuario(usuario);
			redirectAttributes.addFlashAttribute("successMessage", "Usuario guardado con éxito!");
			return "redirect:/usuarios";
		} catch (Exception e) {
			model.addAttribute("usuario", usuario);
			model.addAttribute("usuarios", usuarioService.listarUsuarios());
			model.addAttribute("roles", rolService.listarRoles());
			model.addAttribute("errorMessage", e.getMessage());
			return "usuarios/lista";
		}
	}

	@GetMapping("/editar/{id}")
	public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
		Usuario usuario = usuarioService.buscarUsuarioPorId(id);
		if (usuario != null) {
			model.addAttribute("usuario", usuario);
		} else {
			model.addAttribute("errorMessage", "Usuario no encontrado");
		}
		model.addAttribute("usuarios", usuarioService.listarUsuarios());
		model.addAttribute("roles", rolService.listarRoles());
		return "usuarios/lista";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminarUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		try {
			usuarioService.eliminarUsuario(id);
			redirectAttributes.addFlashAttribute("successMessage", "Usuario eliminado con éxito!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar el usuario: " + e.getMessage());
		}
		return "redirect:/usuarios";
	}
	@PostMapping("/login/{email}/{password}")
	public ResponseEntity<String> login(@PathVariable String email, @PathVariable String password) {
	    try {
	        usuarioService.login(email, password);
	        return ResponseEntity.ok("Login exitoso");
	    } catch (IllegalArgumentException e) {
	        // Capturar una excepción en caso de email o password incorrectos
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email o password incorrectos");
	    } catch (Exception e) {
	        // Capturar cualquier otro tipo de excepción inesperada
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error en el servidor");
	    }
	}
	
}
