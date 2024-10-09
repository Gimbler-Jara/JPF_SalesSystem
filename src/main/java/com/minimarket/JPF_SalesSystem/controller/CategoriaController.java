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

import com.minimarket.JPF_SalesSystem.model.Categoria;
import com.minimarket.JPF_SalesSystem.service.CategoriaService;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;
    
    @GetMapping
    public String listarCategorias(Model model, @ModelAttribute("successMessage") String successMessage, @ModelAttribute("errorMessage") String errorMessage) { 		
        model.addAttribute("categorias", categoriaService.listarcategorias());
        model.addAttribute("categoria", new Categoria());
        
        model.addAttribute("successMessage", successMessage);
        model.addAttribute("errorMessage", errorMessage);
		
        return "categorias/listar";
    }

    @PostMapping("/guardar")
    public String guardarCategoria(Categoria categoria, Model model, RedirectAttributes redirectAttributes) {
        try {
            categoriaService.guardarCategoria(categoria);
            redirectAttributes.addFlashAttribute("successMessage", "Categoría guardada con éxito!");
            return "redirect:/categorias";
        } catch (Exception e) {
            model.addAttribute("categoria", categoria);
            model.addAttribute("categorias", categoriaService.listarcategorias());
            model.addAttribute("errorMessage", "Error al guardar la categoría: " + e.getMessage());
            return "categorias/listar"; 
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarCategoria(@PathVariable Integer id, Model model) {
        Categoria categoria = categoriaService.obtenerCategoriaPorId(id);
        if (categoria != null) {
            model.addAttribute("categoria", categoria);
        } else {
            model.addAttribute("errorMessage", "Categoría no encontrada");
        }
        model.addAttribute("categorias", categoriaService.listarcategorias());
        return "categorias/listar";
    }

   /* @GetMapping("/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            categoriaService.eliminarCategoria(id);
            redirectAttributes.addFlashAttribute("successMessage", "Categoría eliminada con éxito!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar la categoría: " + e.getMessage());
        }
        return "redirect:/categorias";
    }*/
}
