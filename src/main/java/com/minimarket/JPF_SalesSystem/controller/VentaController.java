package com.minimarket.JPF_SalesSystem.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.minimarket.JPF_SalesSystem.model.ProductoVentaLocal;
import com.minimarket.JPF_SalesSystem.model.Usuario;
import com.minimarket.JPF_SalesSystem.model.VentaProducto;
import com.minimarket.JPF_SalesSystem.service.ProductoService;
import com.minimarket.JPF_SalesSystem.service.UsuarioService;
import com.minimarket.JPF_SalesSystem.service.VentaService;
import com.minimarket.JPF_SalesSystem.service.impl.VentaProductoServiceImpl;

@Controller
@RequestMapping("/ventas")
public class VentaController {

	@Autowired
	private VentaService ventaService;

	@Autowired
	private ProductoService productoService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private VentaProductoServiceImpl ventaProductoService;

	List<VentaProducto> listaVentaProductos = new ArrayList<VentaProducto>();

	@GetMapping
	public String mostrarFormularioCrear(Model model, @ModelAttribute("errorMessage") String errorMessage) {
		List<ProductoVentaLocal> productosVentaLocal = ventaProductoService.generarListaProductosVenta(listaVentaProductos);
		List<Usuario> clientes = usuarioService.listarClientes();

		model.addAttribute("productos", productoService.listarProductosVenta());
		model.addAttribute("usuarios", clientes);
		model.addAttribute("ventaProducto", new VentaProducto());
		model.addAttribute("listaVentaProductos", productosVentaLocal);
		model.addAttribute("ventas", ventaService.listarVentas());

		model.addAttribute("errorMessage", errorMessage);

		return "ventas/formulario";
	}

	@PostMapping("/agregarProducto")
	public String agregarProducto(@ModelAttribute VentaProducto ventaProducto, RedirectAttributes redirectAttributes) {
		try {
			ventaProductoService.agregarProductoAVenta(ventaProducto, listaVentaProductos);
			return "redirect:/ventas";
		} catch (IllegalArgumentException e) {
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
			return "redirect:/ventas";
		}
		
	}

	@PostMapping("/guardarVenta")
	public String guardarVenta(@RequestParam("idUsuario") Long idUsuario) {
		Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);
		ventaService.guardarVentaConProductos(usuario, listaVentaProductos);
		listaVentaProductos.clear();

		return "redirect:/ventas";
	}

	@PostMapping("/eliminarProducto")
	public String eliminarProducto(@RequestParam("nombreProducto") String nombreProducto) {
		ventaProductoService.eliminarProductoDeVenta(nombreProducto, listaVentaProductos);
		return "redirect:/ventas";
	}

	@GetMapping("/detallesVenta")
	public String verDetallesVenta(Model model, @RequestParam("idVenta") Integer idVenta) {
		model.addAttribute("ventaProductos", ventaProductoService.obtenerProductosDeVenta(idVenta));
		model.addAttribute("mostrarModal", true);

		model.addAttribute("productos", productoService.listarProductos());
		model.addAttribute("usuarios", usuarioService.listarUsuarios());
		model.addAttribute("ventaProducto", new VentaProducto());
		model.addAttribute("listaVentaProductos", ventaProductoService.generarListaProductosVenta(listaVentaProductos));
		model.addAttribute("ventas", ventaService.listarVentas());

		return "ventas/formulario";
	}

	@PostMapping("/cerrarModal")
	public String cerrarModal(Model model) {
		model.addAttribute("mostrarModal", false);
		return "redirect:/ventas";
	}
}
