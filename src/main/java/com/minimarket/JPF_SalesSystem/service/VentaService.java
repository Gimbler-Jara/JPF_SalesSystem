package com.minimarket.JPF_SalesSystem.service;

import java.util.List;

import com.minimarket.JPF_SalesSystem.model.Venta;

public interface VentaService {

	public List<Venta> listarVentas();

	public Venta guardarVenta(Venta venta);

	public Venta obtenerVentaPorId(Integer id);

	public void eliminarVenta(Integer id);

}
