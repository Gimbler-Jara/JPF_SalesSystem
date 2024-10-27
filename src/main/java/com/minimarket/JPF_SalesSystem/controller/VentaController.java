package com.minimarket.JPF_SalesSystem.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.minimarket.JPF_SalesSystem.model.Venta;
import com.minimarket.JPF_SalesSystem.model.VentaProducto;
import com.minimarket.JPF_SalesSystem.service.ProductoService;
import com.minimarket.JPF_SalesSystem.service.UsuarioService;
import com.minimarket.JPF_SalesSystem.service.VentaService;
import com.minimarket.JPF_SalesSystem.service.impl.PdfService;
import com.minimarket.JPF_SalesSystem.service.impl.VentaProductoServiceImpl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ventas")
public class VentaController {

	private final VentaService ventaService;

	private final ProductoService productoService;

	private final UsuarioService usuarioService;

	private final VentaProductoServiceImpl ventaProductoService;
	
	private final PdfService pdfService;

	List<VentaProducto> listaVentaProductos = new ArrayList<VentaProducto>();
	Integer _idVenta;

	@GetMapping
	public String mostrarFormularioCrear(Model model, @ModelAttribute("errorMessage") String errorMessage, HttpSession session) {
		Long usuarioId = (Long) session.getAttribute("usuarioId");
		Usuario usuario = usuarioService.buscarUsuarioPorId(usuarioId);
		List<ProductoVentaLocal> productosVentaLocal = ventaProductoService.generarListaProductosVenta(listaVentaProductos);
		List<Usuario> clientes = usuarioService.listarClientes();

		model.addAttribute("productos", productoService.listarProductosVenta());
		model.addAttribute("vendedor", usuario.getUsername());
		model.addAttribute("ventaProducto", new VentaProducto());
		model.addAttribute("listaVentaProductos", productosVentaLocal);
		model.addAttribute("ventas", ventaService.listarVentas());
		model.addAttribute("venta", new Venta());

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
	public String guardarVenta(@RequestParam("cliente") String cliente, HttpSession session, Model model) {
		Long usuarioId = (Long) session.getAttribute("usuarioId");
		Usuario usuario = usuarioService.buscarUsuarioPorId(usuarioId);
		ventaService.guardarVentaConProductos(usuario, listaVentaProductos, cliente);
		listaVentaProductos.clear();

		return "redirect:/ventas";
	}

	@PostMapping("/eliminarProducto")
	public String eliminarProducto(@RequestParam("nombreProducto") String nombreProducto ) {
		ventaProductoService.eliminarProductoDeVenta(nombreProducto, listaVentaProductos);
		return "redirect:/ventas";
	}

	@GetMapping("/detallesVenta")
	public String verDetallesVenta(Model model, @RequestParam("idVenta") Integer idVenta) {
		_idVenta= idVenta;
		model.addAttribute("ventaProductos", ventaProductoService.obtenerProductosDeVenta(idVenta));
		model.addAttribute("mostrarModal", true);

		model.addAttribute("venta", new Venta());
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
	
	@GetMapping("/generar_pdf")
	public ResponseEntity<InputStreamResource>generarPDf(HttpSession sesion) throws IOException {
		Long usuarioId = (Long) sesion.getAttribute("usuarioId");
		Usuario usuarioEncontrado = usuarioService.buscarUsuarioPorId(usuarioId);

		
		Venta ventaEncontrada = ventaService.obtenerVentaPorId(_idVenta);
		System.err.println(ventaEncontrada.getId_venta());
		Map<String, Object> datosPdf = new HashMap<String, Object>();
		datosPdf.put("factura", ventaProductoService.obtenerProductosDeVenta(_idVenta));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		datosPdf.put("fechaVenta", ventaEncontrada.getFecha().format(formatter));
		datosPdf.put("totalVenta", ventaEncontrada.getTotalVenta());
		datosPdf.put("cliente", ventaEncontrada.getCliente());
		datosPdf.put("vendedor", ventaEncontrada.getUsuario().getUsername());
		
		ByteArrayInputStream pdfBytes = pdfService.generarPdf("template_pdf", datosPdf);
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.add("Content-Disposition", "inline; filename=productos.pdf");
    	
    	return ResponseEntity.ok()
    			.headers(headers)
    			.contentType(MediaType.APPLICATION_PDF)
    			.body(new InputStreamResource(pdfBytes));

	}
}
