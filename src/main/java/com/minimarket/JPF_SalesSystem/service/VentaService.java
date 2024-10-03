package com.minimarket.JPF_SalesSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minimarket.JPF_SalesSystem.model.Venta;
import com.minimarket.JPF_SalesSystem.repository.VentaRepository;

@Service
public class VentaService {

	@Autowired
	private VentaRepository ventaRepository;

	public List<Venta> listarVentas() {
		return ventaRepository.findAll();
	}

	public Venta guardarVenta(Venta venta) {
		return ventaRepository.save(venta);
	}

	public Venta obtenerVentaPorId(Integer id) {
		return ventaRepository.findById(id).orElse(null);
	}

	public void eliminarVenta(Integer id) {
		ventaRepository.deleteById(id);
	}
}
