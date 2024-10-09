package com.minimarket.JPF_SalesSystem.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minimarket.JPF_SalesSystem.model.Producto;
import com.minimarket.JPF_SalesSystem.model.ProductoVentaLocal;
import com.minimarket.JPF_SalesSystem.model.VentaProducto;
import com.minimarket.JPF_SalesSystem.repository.VentaProductoRepository;
import com.minimarket.JPF_SalesSystem.service.ProductoService;
import com.minimarket.JPF_SalesSystem.service.VentaProductoService;

@Service
public class VentaProductoServiceImpl implements VentaProductoService{

	@Autowired
	private VentaProductoRepository ventaProductoRepository;

	@Autowired
	private ProductoService productoService;

	@Override
	public List<VentaProducto> listarVentasProducto() {
		return ventaProductoRepository.findAll();
	}

	@Override
	public void guardarVentaProducto(VentaProducto ventaProducto) {
		// Actualizar el stock del producto
		Producto producto = productoService.obtenerProductoPorId(ventaProducto.getProducto().getId_producto());
		producto.setStockActual(producto.getStockActual() - ventaProducto.getCantidad());
		productoService.guardarProducto(producto, true);

		// Guardar el producto vendido
		ventaProductoRepository.save(ventaProducto);
	}

	// Lógica para generar la lista de productos en venta
	public List<ProductoVentaLocal> generarListaProductosVenta(List<VentaProducto> listaVentaProductos) {
		List<ProductoVentaLocal> productosVentaLocal = new ArrayList<>();
		double precioTotalVenta = 0;

		for (VentaProducto ventaProducto : listaVentaProductos) {
			Producto producto = ventaProducto.getProducto();
			ProductoVentaLocal producVentaLocal = new ProductoVentaLocal();

			// Obtener el nombre del producto
			producVentaLocal.setNombreProducto(producto.getNombre());
			producVentaLocal.setPrecioUnitario(ventaProducto.getPrecioUnitario());
			producVentaLocal.setCantidad(ventaProducto.getCantidad());

			// Calcular el precio total por producto
			BigDecimal precioTotalPorProducto = BigDecimal.valueOf(ventaProducto.getTotalProducto()).setScale(2, RoundingMode.HALF_UP);
			producVentaLocal.setTotalPorProducto(precioTotalPorProducto.doubleValue());

			precioTotalVenta += producVentaLocal.getTotalPorProducto();
			productosVentaLocal.add(producVentaLocal);
		}

		// Calcular y establecer el total de la venta
		if (!productosVentaLocal.isEmpty()) {
			BigDecimal precioTotal = BigDecimal.valueOf(precioTotalVenta).setScale(2, RoundingMode.HALF_UP);
			productosVentaLocal.get(0).setTotalVenta(precioTotal.doubleValue());
		}

		return productosVentaLocal;
	}

	// Agregar producto a la venta
	public void agregarProductoAVenta(VentaProducto ventaProducto, List<VentaProducto> listaVentaProductos) {
		Producto producto = productoService.obtenerProductoPorId(ventaProducto.getProducto().getId_producto());
		ventaProducto.setProducto(producto);
		ventaProducto.setPrecioUnitario(producto.getPrecio());

		// Calcular el total del producto
		BigDecimal precioUnitario = BigDecimal.valueOf(producto.getPrecio());
		BigDecimal cantidad = BigDecimal.valueOf(ventaProducto.getCantidad());
		BigDecimal totalProducto = precioUnitario.multiply(cantidad).setScale(2, RoundingMode.HALF_UP);
		ventaProducto.setTotalProducto(totalProducto.doubleValue());

		// Verificar si el producto ya está en la lista y actualizar la cantidad
		boolean productoExistente = false;
		for (VentaProducto vp : listaVentaProductos) {
			if (vp.getProducto().getId_producto().equals(ventaProducto.getProducto().getId_producto())) {
				vp.setCantidad(vp.getCantidad() + ventaProducto.getCantidad());
				vp.setTotalProducto(vp.getPrecioUnitario() * vp.getCantidad());
				productoExistente = true;
				break;
			}
		}

		if (!productoExistente) {
			listaVentaProductos.add(ventaProducto);
		}
	}

	// Eliminar producto de la venta
	public void eliminarProductoDeVenta(String nombreProducto, List<VentaProducto> listaVentaProductos) {
		listaVentaProductos.removeIf(ventaProducto -> ventaProducto.getProducto().getNombre().equals(nombreProducto));
	}

	// Obtener productos de una venta específica
	public List<VentaProducto> obtenerProductosDeVenta(Integer idVenta) {
		return ventaProductoRepository.findAll().stream()
				.filter(ventaProducto -> ventaProducto.getVenta().getId_venta().equals(idVenta))
				.collect(Collectors.toList());
	}

	@Override
	public VentaProducto obtenerVentaProductoPorId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminarVentaProducto(Integer id) {
		// TODO Auto-generated method stub
		
	}
}
