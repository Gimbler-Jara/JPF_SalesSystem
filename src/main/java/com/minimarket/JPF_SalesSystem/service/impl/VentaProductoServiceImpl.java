package com.minimarket.JPF_SalesSystem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minimarket.JPF_SalesSystem.model.Producto;
import com.minimarket.JPF_SalesSystem.model.VentaProducto;
import com.minimarket.JPF_SalesSystem.repository.VentaProductoRepository;
import com.minimarket.JPF_SalesSystem.service.VentaProductoService;

@Service
public class VentaProductoServiceImpl implements VentaProductoService{

	@Autowired
	private VentaProductoRepository ventaProductoRepository;

	@Autowired
	private ProductoServiceImpl productoService;

	@Override
	public List<VentaProducto> listarVentasProducto() {
		return ventaProductoRepository.findAll();
	}

	@Override
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

	@Override
	public VentaProducto obtenerVentaProductoPorId(Integer id) {
		return ventaProductoRepository.findById(id).orElse(null);
	}

	@Override
	public void eliminarVentaProducto(Integer id) {
		ventaProductoRepository.deleteById(id);
	}

}
