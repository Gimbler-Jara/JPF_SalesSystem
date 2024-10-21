package com.minimarket.JPF_SalesSystem.service;

import java.util.List;

import com.minimarket.JPF_SalesSystem.model.Producto;

public interface ProductoService {

	public List<Producto> listarProductos();
	
	public List<Producto> listarProductosVenta();

	public void guardarProducto(Producto producto, Boolean esVenta);

	public Producto obtenerProductoPorId(Long id);

	public void eliminarProducto(Long id);
}
