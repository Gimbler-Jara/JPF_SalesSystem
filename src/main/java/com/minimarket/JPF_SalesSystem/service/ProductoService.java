package com.minimarket.JPF_SalesSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minimarket.JPF_SalesSystem.model.Producto;
import com.minimarket.JPF_SalesSystem.repository.ProductoRepository;
import com.minimarket.JPF_SalesSystem.utility.MessageErrorException;

@Service
public class ProductoService {

	@Autowired
	private ProductoRepository productoRepository;

	public List<Producto> listarProductos() {
		return productoRepository.findAll();
	}

	public void guardarProducto(Producto producto, Boolean esVenta) {
		
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

		productoRepository.save(producto);
	}

	public Producto obtenerProductoPorId(Long id) {
		return productoRepository.findById(id).orElse(null);
	}

	public void eliminarProducto(Long id) {
		productoRepository.deleteById(id);
	}

}
