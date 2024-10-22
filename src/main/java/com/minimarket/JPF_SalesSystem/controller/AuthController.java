package com.minimarket.JPF_SalesSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.minimarket.JPF_SalesSystem.model.Usuario;
import com.minimarket.JPF_SalesSystem.service.UsuarioService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;;

@Controller
@RequiredArgsConstructor
public class AuthController {

	private final UsuarioService usuarioService;

	@GetMapping("/")
	public String mostrarLogin(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute("usuario") Usuario usuarioFormulario, Model model, HttpSession session) {
		boolean validarUsuario = usuarioService.validarUsuario(usuarioFormulario);

		if (validarUsuario) {

			for (Usuario usuario : usuarioService.listarUsuarios()) {

				if (usuario.getEmail().equals(usuarioFormulario.getEmail())) {
					session.setAttribute("rol", usuario.getRol().getRol());
					session.setAttribute("usuarioId", usuario.getId_usuario());
				}
			}

			session.setAttribute("usuario", usuarioFormulario.getEmail());
			return "redirect:/ventas";
		}
		model.addAttribute("loginInvalido", "No existe el usuario");
		model.addAttribute("usuario", new Usuario());
		return "login";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
