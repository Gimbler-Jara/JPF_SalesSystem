package com.minimarket.JPF_SalesSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	Boolean mostrarMensajeExito = false;
	Boolean  mostrarMensajeDeBorrado = false;

	@GetMapping
	public String listarProductos(Model model) {
		if(mostrarMensajeExito) {
		  model.addAttribute("successMessage", "Producto guardado con éxito!");
		}
		
		if(mostrarMensajeDeBorrado) {
			model.addAttribute("successMessage", "Producto borrado con éxito!");
		}
		model.addAttribute("productos", productoService.listarProductos());
		model.addAttribute("categorias", categoriaService.listarcategorias());
		model.addAttribute("producto", new Producto());
		mostrarMensajeExito = false;
		mostrarMensajeDeBorrado = false;
		
		return "productos/listar";
	}

	/*@GetMapping("/nuevo")
	public String mostrarFormularioNuevoProducto(Model model) {
		model.addAttribute("producto", new Producto());
		model.addAttribute("categorias", categoriaService.listarcategorias());
		return "productos/formulario";
	}*/

	@PostMapping("/guardar")
	public String guardarProducto(Producto producto,RedirectAttributes redirectAttributes, Model model) {
		try {
			productoService.guardarProducto(producto, false);
			mostrarMensajeExito = true;
			return "redirect:/productos";
		} catch (MessageErrorException e) {
			model.addAttribute("error", e.getMessage());
			model.addAttribute("categorias", categoriaService.listarcategorias());
			model.addAttribute("productos", productoService.listarProductos());
			return "productos/listar";
		}
	}

	@GetMapping("/editar/{id}")
	public String mostrarFormularioEditarProducto(@PathVariable Long id, Model model) {
		Producto producto = productoService.obtenerProductoPorId(id);
		model.addAttribute("producto", producto);
		model.addAttribute("categorias", categoriaService.listarcategorias());
		model.addAttribute("productos", productoService.listarProductos());
		return "productos/listar";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminarProducto(@PathVariable Long id) {
		productoService.eliminarProducto(id);
		mostrarMensajeDeBorrado = true;
		return "redirect:/productos";
	}
}
