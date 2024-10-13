package com.minimarket.JPF_SalesSystem.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minimarket.JPF_SalesSystem.model.Producto;
import com.minimarket.JPF_SalesSystem.repository.ProductoRepository;
import com.minimarket.JPF_SalesSystem.service.ProductoService;
import com.minimarket.JPF_SalesSystem.utility.MessageErrorException;

@Service
public class ProductoServiceImpl implements ProductoService {

	@Autowired
	private ProductoRepository productoRepository;

	@Override
	public List<Producto> listarProductos() {
		List<Producto> productos = new ArrayList<Producto>(); 
		for (Producto producto : productoRepository.findAll()) {
			if (producto.getStockActual()>0) {
				productos.add(producto);
			}
		}
		return productos;
	}

	@Override
	public void guardarProducto(Producto producto, Boolean esVenta) {

		validarProducto(producto, esVenta);
		productoRepository.save(producto);
	}

	private void validarProducto(Producto producto, Boolean esVenta) {
		if (!esVenta) {
			if (producto.getStockMinimo() < 0) {
				throw new MessageErrorException("El stock mínimo no puede ser menor a 0.");
			}

			if (producto.getStockActual() < producto.getStockMinimo()) {
				throw new MessageErrorException("El stock actual no puede ser menor al stock mínimo.");
			}

			if (producto.getStockActual() > producto.getStockMaximo()) {
				throw new MessageErrorException("El stock actual no puede ser mayor al stock máximo.");
			}

			if (producto.getStockMaximo() < producto.getStockMinimo()) {
				throw new MessageErrorException("El stock máximo no puede ser menor al stock mínimo.");
			}
		}
	}

	@Override
	public Producto obtenerProductoPorId(Long id) {
		return productoRepository.findById(id)
				.orElseThrow(() -> new MessageErrorException("El producto con ID " + id + " no fue encontrado."));
	}

	@Override
	public void eliminarProducto(Long id) {
		if (!productoRepository.existsById(id)) {
			throw new MessageErrorException("El producto con ID " + id + " no fue encontrado.");
		}
		productoRepository.deleteById(id);
	}

}
