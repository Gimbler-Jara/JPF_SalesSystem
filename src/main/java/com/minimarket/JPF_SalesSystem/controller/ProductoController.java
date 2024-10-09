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

import com.minimarket.JPF_SalesSystem.model.Producto;
import com.minimarket.JPF_SalesSystem.service.CategoriaService;
import com.minimarket.JPF_SalesSystem.service.ProductoService;
import com.minimarket.JPF_SalesSystem.utility.MessageErrorException;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String listarProductos(Model model, @ModelAttribute("successMessage") String successMessage, @ModelAttribute("errorMessage") String errorMessage) {
        model.addAttribute("productos", productoService.listarProductos());
        model.addAttribute("categorias", categoriaService.listarcategorias());
        model.addAttribute("producto", new Producto());
        model.addAttribute("successMessage", successMessage);
        model.addAttribute("errorMessage", errorMessage);
        return "productos/listar";
    }

    @PostMapping("/guardar")
    public String guardarProducto(Producto producto,Model model, RedirectAttributes redirectAttributes) {
        try {
            productoService.guardarProducto(producto, false);
            redirectAttributes.addFlashAttribute("successMessage", "Producto guardado con éxito!");
            return "redirect:/productos";
        } catch (MessageErrorException e) {
        	model.addAttribute("producto", producto); 
            model.addAttribute("categorias", categoriaService.listarcategorias());
            model.addAttribute("productos", productoService.listarProductos());
            model.addAttribute("errorMessage", e.getMessage()); 
            return "productos/listar";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarProducto(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("producto", productoService.obtenerProductoPorId(id));
            model.addAttribute("categorias", categoriaService.listarcategorias());
            model.addAttribute("productos", productoService.listarProductos());
            return "productos/listar";
        } catch (MessageErrorException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/productos";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productoService.eliminarProducto(id);
            redirectAttributes.addFlashAttribute("successMessage", "Producto borrado con éxito!");
        } catch (MessageErrorException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/productos";
    }
}

