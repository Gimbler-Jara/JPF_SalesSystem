package com.minimarket.JPF_SalesSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.minimarket.JPF_SalesSystem.model.Roles;
import com.minimarket.JPF_SalesSystem.service.RolService;

@Controller
@RequestMapping("/roles")
public class RolController {

	@Autowired
	private RolService rolService;

	@GetMapping
	public String listarRoles(Model model, @ModelAttribute("successMessage") String successMessage,
			@ModelAttribute("errorMessage") String errorMessage) {
		model.addAttribute("roless", rolService.listarRoles());
		model.addAttribute("roles", new Roles());

		model.addAttribute("successMessage", successMessage);
		model.addAttribute("errorMessage", errorMessage);
		return "roles/listar";
	}

	/*
	 * @GetMapping("/nuevo") public String mostrarFormularioNuevoRol(Model model) {
	 * model.addAttribute("roles", new Roles()); return "roles/formulario"; }
	 */

	@PostMapping("/guardar")
	public String guardarRol(Roles rol, Model model, RedirectAttributes redirectAttributes) {
	    String roleName = rol.getRol();
	    if (roleName.length() < 3 || roleName.length() > 10) {
	        model.addAttribute("roles", rol);
	        model.addAttribute("roless", rolService.listarRoles());
	        model.addAttribute("errorMessage", "El nombre del rol debe tener entre 3 y 10 caracteres.");
	        return "roles/listar";
	    }

	    if (!roleName.equals(roleName.toUpperCase()) && !roleName.equals(roleName.toLowerCase())) {
	        model.addAttribute("roles", rol);
	        model.addAttribute("roless", rolService.listarRoles());
	        model.addAttribute("errorMessage", "El nombre del rol debe ser toda mayúscula o toda minúscula");
	        return "roles/listar";
	    }

	    if (rolService.existeRol(roleName)) {
	        model.addAttribute("roles", rol);
	        model.addAttribute("roless", rolService.listarRoles());
	        model.addAttribute("errorMessage", "Ya existe un rol con este nombre.");
	        return "roles/listar";
	    }

	    try {
	        rolService.guardarRol(rol);
	        redirectAttributes.addFlashAttribute("successMessage", "Rol guardado con éxito!");
	        return "redirect:/roles";
	    } catch (Exception e) {
	        model.addAttribute("roles", rol);
	        model.addAttribute("roless", rolService.listarRoles());
	        model.addAttribute("errorMessage", "Error al guardar el rol: " + e.getMessage());
	        return "roles/listar";
	    }
	}

	@GetMapping("/editar/{id}")
	public String mostrarFormularioEditarRol(@PathVariable Integer id, Model model) {
		model.addAttribute("roless", rolService.listarRoles());
		Roles rol = rolService.obtenerRolPorId(id);
		if (rol != null) {
			model.addAttribute("roles", rol);
		} else {
			model.addAttribute("errorMessage", "Rol no encontrado");
		}
		return "roles/listar";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminarRol(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		try {
			rolService.eliminarRol(id);
			redirectAttributes.addFlashAttribute("successMessage", "Rol borrado con éxito!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar el rol: " + e.getMessage());
		}
		return "redirect:/roles";
	}
}
