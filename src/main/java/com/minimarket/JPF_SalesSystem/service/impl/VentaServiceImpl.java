package com.minimarket.JPF_SalesSystem.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minimarket.JPF_SalesSystem.model.Usuario;
import com.minimarket.JPF_SalesSystem.model.Venta;
import com.minimarket.JPF_SalesSystem.model.VentaProducto;
import com.minimarket.JPF_SalesSystem.repository.VentaRepository;
import com.minimarket.JPF_SalesSystem.service.VentaProductoService;
import com.minimarket.JPF_SalesSystem.service.VentaService;

@Service
public class VentaServiceImpl implements VentaService {

	@Autowired
	private VentaRepository ventaRepository;
	
	@Autowired
    private VentaProductoService ventaProductoService;

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

	@Override
	public Venta guardarVentaConProductos(Usuario usuario, List<VentaProducto> productos, String cliente) {
		
		Venta venta = new Venta();
        venta.setFecha(LocalDateTime.now());

        // Calcular el total de la venta
        BigDecimal totalVentas = productos.stream().map(vp -> BigDecimal.valueOf(vp.getTotalProducto())).reduce(BigDecimal.ZERO, BigDecimal::add);

        totalVentas = totalVentas.setScale(2, RoundingMode.HALF_UP);
        venta.setTotalVenta(totalVentas.doubleValue());

        // Asociar la venta al usuario
        venta.setUsuario(usuario);
        venta.setCliente(cliente);

        // Guardar la venta
        Venta ventaGuardada = ventaRepository.save(venta);

        // Guardar cada producto asociado a la venta
        for (VentaProducto producto : productos) {
            producto.setVenta(ventaGuardada);
            ventaProductoService.guardarVentaProducto(producto);
        }

        return ventaGuardada;
	}
}
