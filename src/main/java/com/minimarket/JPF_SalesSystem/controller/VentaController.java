package com.minimarket.JPF_SalesSystem.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.minimarket.JPF_SalesSystem.model.Producto;
import com.minimarket.JPF_SalesSystem.model.ProductoVentaLocal;
import com.minimarket.JPF_SalesSystem.model.Usuario;
import com.minimarket.JPF_SalesSystem.model.Venta;
import com.minimarket.JPF_SalesSystem.model.VentaProducto;
import com.minimarket.JPF_SalesSystem.service.ProductoService;
import com.minimarket.JPF_SalesSystem.service.UsuarioService;
import com.minimarket.JPF_SalesSystem.service.VentaProductoService;
import com.minimarket.JPF_SalesSystem.service.VentaService;

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
	private VentaProductoService ventaProductoService;

	List<VentaProducto> listaVentaProductos = new ArrayList<VentaProducto>();
	public List<ProductoVentaLocal> productosVentaLocal = new ArrayList<ProductoVentaLocal>();

	@GetMapping
	public String mostrarFormularioCrear(Model model) {

		productosVentaLocal.clear();
		double precioTotalVenta = 0;

		for (VentaProducto ventaProducto : listaVentaProductos) {
			Producto producto = ventaProducto.getProducto();
			ProductoVentaLocal producVentaLocal = new ProductoVentaLocal();

			for (Producto p : productoService.listarProductos()) {
				if (p.getId_producto() == producto.getId_producto()) {
					producVentaLocal.setNombreProducto(p.getNombre());
				}
			}

			producVentaLocal.setPrecioUnitario(ventaProducto.getPrecioUnitario());
			producVentaLocal.setCantidad(ventaProducto.getCantidad());
			BigDecimal precioTotalPorProducto = BigDecimal.valueOf(ventaProducto.getTotalProducto()).setScale(2, RoundingMode.HALF_UP);
			producVentaLocal.setTotalPorProducto(precioTotalPorProducto.doubleValue());

			precioTotalVenta += producVentaLocal.getTotalPorProducto();
			productosVentaLocal.add(producVentaLocal);
		}

		if (!productosVentaLocal.isEmpty()) {
			BigDecimal precioTotal = BigDecimal.valueOf(precioTotalVenta).setScale(2, RoundingMode.HALF_UP);
		    productosVentaLocal.get(0).setTotalVenta(precioTotal.doubleValue());
		}

		model.addAttribute("productos", productoService.listarProductos());
		model.addAttribute("usuarios", usuarioService.listarUsuarios());
		model.addAttribute("ventaProducto", new VentaProducto());
		model.addAttribute("listaVentaProductos", productosVentaLocal);
		List<Venta> ventas = ventaService.listarVentas();
		model.addAttribute("ventas", ventas);

		return "ventas/formulario";
	}

	@PostMapping("/agregarProducto")
	public String agregarProducto(@ModelAttribute VentaProducto ventaProducto, Model model) {
		Producto producto = productoService.obtenerProductoPorId(ventaProducto.getProducto().getId_producto());
		ventaProducto.setVenta(null);
		ventaProducto.setProducto(producto);
		ventaProducto.setPrecioUnitario(producto.getPrecio());
		
		// Calcula el total del producto como BigDecimal
	    BigDecimal precioUnitario = BigDecimal.valueOf(producto.getPrecio());
	    BigDecimal cantidad = BigDecimal.valueOf(ventaProducto.getCantidad());
	    BigDecimal totalProducto = precioUnitario.multiply(cantidad);

	    // Redondea el total a 2 decimales
	    totalProducto = totalProducto.setScale(2, RoundingMode.HALF_UP);
	    ventaProducto.setTotalProducto(totalProducto.doubleValue()); 

		boolean productoExistente = false;
		for (VentaProducto vp : listaVentaProductos) {
			if (vp.getProducto().getId_producto().equals(ventaProducto.getProducto().getId_producto())) {
				vp.setCantidad(vp.getCantidad() + ventaProducto.getCantidad());
				vp.setTotalProducto(vp.getPrecioUnitario() * vp.getCantidad());
				productoExistente = true;
				break;
			}
		}

		if (!productoExistente) {
			listaVentaProductos.add(ventaProducto);
		}

		return "redirect:/ventas";
	}

	// Guardar venta
	@PostMapping("/guardarVenta")
	public String guardarVenta(@RequestParam("idUsuario") Long idUsuario) {
		Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);

		Venta venta = new Venta();
		venta.setFecha(LocalDateTime.now());
		BigDecimal totalVentas = BigDecimal.ZERO;

	    for (int i = 0; i < listaVentaProductos.size(); i++) {
	        BigDecimal total = BigDecimal.valueOf(listaVentaProductos.get(i).getTotalProducto());
	        totalVentas = totalVentas.add(total);
	    }

	    // Redondear totalVentas a 2 decimales
	    totalVentas = totalVentas.setScale(2, RoundingMode.HALF_UP);
	    venta.setTotalVenta(totalVentas.doubleValue());
		venta.setUsuario(usuario);

		Venta ventaGuardada = ventaService.guardarVenta(venta);

		for (VentaProducto ventaProducto : listaVentaProductos) {
			ventaProducto.setVenta(ventaGuardada);
			ventaProductoService.guardarVentaProducto(ventaProducto);
		}
		
		listaVentaProductos.clear();

		return "redirect:/ventas";
	}

	@PostMapping("/eliminarProducto")
	public String eliminarProducto(@RequestParam("nombreProducto") String nombreProducto) {
		listaVentaProductos.removeIf(ventaProducto -> ventaProducto.getProducto().getNombre().equals(nombreProducto));

		return "redirect:/ventas";
	}

	/*@GetMapping("/verVentas")
	public String verVentas(Model model) {
		List<Venta> ventas = ventaService.listarVentas();
		model.addAttribute("ventas", ventas);
		return "ventas/listar";
	}*/

	@GetMapping("/detallesVenta")
	public String verDetallesVenta(Model model, @RequestParam("idVenta") Integer idVenta) {
		List<VentaProducto> listVentaProductos = ventaProductoService.listarVentasProducto();
	    
	    // Filtrar productos según la venta seleccionada
	    List<VentaProducto> ventaProductos = listVentaProductos.stream()
	            .filter(ventaProducto -> ventaProducto.getVenta().getId_venta().equals(idVenta))
	            .collect(Collectors.toList());

	    model.addAttribute("ventaProductos", ventaProductos);
	    model.addAttribute("mostrarModal", true);
	    
	    //datos para llenar campos del resto de la pagina
	    model.addAttribute("productos", productoService.listarProductos());
		model.addAttribute("usuarios", usuarioService.listarUsuarios());
		model.addAttribute("ventaProducto", new VentaProducto());
		model.addAttribute("listaVentaProductos", productosVentaLocal);
		List<Venta> ventas = ventaService.listarVentas();
		model.addAttribute("ventas", ventas);
	    return "ventas/formulario";
	}
	
	
	@PostMapping("/cerrarModal")
	public String cerrarModal(Model model) {
	    model.addAttribute("mostrarModal", false); 
		return "redirect:/ventas";
	}


}
