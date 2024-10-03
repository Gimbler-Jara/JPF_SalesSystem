package com.minimarket.JPF_SalesSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minimarket.JPF_SalesSystem.model.Producto;
import com.minimarket.JPF_SalesSystem.model.VentaProducto;
import com.minimarket.JPF_SalesSystem.repository.VentaProductoRepository;

@Service
public class VentaProductoService {

	@Autowired
	private VentaProductoRepository ventaProductoRepository;

	@Autowired
	private ProductoService productoService;

	public List<VentaProducto> listarVentasProducto() {
		return ventaProductoRepository.findAll();
	}

	public void guardarVentaProducto(VentaProducto ventaProducto) {

		List<Producto> productos = productoService.listarProductos();

		for (Producto producto : productos) {
			if (producto.getId_producto() == ventaProducto.getProducto().getId_producto()) {
				producto.setStockActual(producto.getStockActual() - ventaProducto.getCantidad());
				productoService.guardarProducto(producto, true);
			}
		}

		ventaProductoRepository.save(ventaProducto);
	}

	public VentaProducto obtenerVentaProductoPorId(Integer id) {
		return ventaProductoRepository.findById(id).orElse(null);
	}

	public void eliminarVentaProducto(Integer id) {
		ventaProductoRepository.deleteById(id);
	}

}
