package com.minimarket.JPF_SalesSystem.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.minimarket.JPF_SalesSystem.model.Roles;
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

    // Listar Usuarios
    @GetMapping
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        model.addAttribute("usuarios", usuarios);
        List<Roles> roles = rolService.listarRoles();
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", roles);
        return "usuarios/lista";
    }

    // Mostrar formulario para crear un nuevo usuario
   /* @GetMapping("/nuevo")
    public String mostrarFormularioNuevoUsuario(Model model) {
    	List<Roles> roles = rolService.listarRoles();
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", roles);
        return "usuarios/formulario"; 
    }*/

    // Guardar usuario
    @PostMapping("/guardar")
    public String guardarUsuario(Usuario usuario, RedirectAttributes redirectAttributes) {
    	 usuario.setFechaCreacion(LocalDateTime.now() );
    	 usuarioService.guardarUsuario(usuario);
         redirectAttributes.addFlashAttribute("mensaje", "Usuario creado exitosamente");
        return "redirect:/usuarios";
    }

    // Mostrar formulario para editar un usuario
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
            model.addAttribute("usuario", usuarioService.buscarUsuarioPorId(id));
            model.addAttribute("roles", rolService.listarRoles());
            List<Usuario> usuarios = usuarioService.listarUsuarios();
            model.addAttribute("usuarios", usuarios);
            return "usuarios/lista";
    }

 // Eliminar usuario
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    	usuarioService.eliminarUsuario(id);
        redirectAttributes.addFlashAttribute("mensaje", "Usuario eliminado exitosamente");
        return "redirect:/usuarios";
    }
}
