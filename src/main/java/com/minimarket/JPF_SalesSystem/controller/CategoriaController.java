package com.minimarket.JPF_SalesSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.minimarket.JPF_SalesSystem.model.Categoria;
import com.minimarket.JPF_SalesSystem.service.CategoriaService;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;
    
    Boolean mostrarMensajeExito = false;
	Boolean  mostrarMensajeDeBorrado = false;

    @GetMapping
    public String listarCategorias(Model model) {
    	if(mostrarMensajeExito) {
  		  model.addAttribute("successMessage", "Categoria guardado con éxito!");
  		}
  		
  		if(mostrarMensajeDeBorrado) {
  			model.addAttribute("successMessage", "Categoria borrado con éxito!");
  		}
  		
        model.addAttribute("categorias", categoriaService.listarcategorias());
        model.addAttribute("categoria", new Categoria());
        
        mostrarMensajeExito = false;
		mostrarMensajeDeBorrado = false;
		
        return "categorias/listar";
    }

    @PostMapping("/guardar")
    public String guardarCategoria(Categoria categoria) {
    	categoriaService.guardarCategoria(categoria);
    	mostrarMensajeExito = true;
        return "redirect:/categorias";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarCategoria(@PathVariable Integer id, Model model) {
        Categoria categoria = categoriaService.obtenerCategoriaPorId(id);
        model.addAttribute("categoria", categoria);
        model.addAttribute("categorias", categoriaService.listarcategorias());
        return "categorias/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Integer id) {
    	categoriaService.eliminarCategoria(id);
    	mostrarMensajeDeBorrado = true;
        return "redirect:/categorias";
    }
}
