package com.minimarket.JPF_SalesSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.minimarket.JPF_SalesSystem.model.Roles;
import com.minimarket.JPF_SalesSystem.service.RolService;

@Controller
@RequestMapping("/roles")
public class RolController {

	@Autowired
	private RolService rolService;

	@GetMapping
	public String listarRoles(Model model) {
		model.addAttribute("roless", rolService.listarRoles());
		model.addAttribute("roles", new Roles());
		return "roles/listar";
	}

  /*@GetMapping("/nuevo")
	public String mostrarFormularioNuevoRol(Model model) {
		model.addAttribute("roles", new Roles());
		return "roles/formulario";
	}*/

	@PostMapping("/guardar")
	public String guardarRol(Roles rol) {
		rolService.guardarRol(rol);
		return "redirect:/roles";
	}

	@GetMapping("/editar/{id}")
	public String mostrarFormularioEditarRol(@PathVariable Integer id, Model model) {
		model.addAttribute("roless", rolService.listarRoles());
		Roles rol = rolService.obtenerRolPorId(id);
		model.addAttribute("roles", rol);
		return "roles/listar";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminarRol(@PathVariable Integer id) {
		rolService.eliminarRol(id);
		return "redirect:/roles";
	}
}
