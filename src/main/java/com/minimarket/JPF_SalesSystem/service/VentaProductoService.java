package com.minimarket.JPF_SalesSystem.service;

import java.util.List;

import com.minimarket.JPF_SalesSystem.model.VentaProducto;

public interface VentaProductoService {

	public List<VentaProducto> listarVentasProducto();

	public void guardarVentaProducto(VentaProducto ventaProducto);

	public VentaProducto obtenerVentaProductoPorId(Integer id);

	public void eliminarVentaProducto(Integer id);
}
