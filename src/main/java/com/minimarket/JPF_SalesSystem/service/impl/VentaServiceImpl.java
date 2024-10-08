package com.minimarket.JPF_SalesSystem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minimarket.JPF_SalesSystem.model.Venta;
import com.minimarket.JPF_SalesSystem.repository.VentaRepository;
import com.minimarket.JPF_SalesSystem.service.VentaService;

@Service
public class VentaServiceImpl implements VentaService {

	@Autowired
	private VentaRepository ventaRepository;

	@Override
	public List<Venta> listarVentas() {
		return ventaRepository.findAll();
	}

	@Override
	public Venta guardarVenta(Venta venta) {
		return ventaRepository.save(venta);
	}

	@Override
	public Venta obtenerVentaPorId(Integer id) {
		return ventaRepository.findById(id).orElse(null);
	}

	@Override
	public void eliminarVenta(Integer id) {
		ventaRepository.deleteById(id);
	}
}
