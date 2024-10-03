package com.minimarket.JPF_SalesSystem.model;

public class ProductoVentaLocal {

	private String nombreProducto;
	private Double precioUnitario;
	private Integer Cantidad;
	private Double totalPorProducto;
	private Double totalVenta;

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public Double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public Integer getCantidad() {
		return Cantidad;
	}

	public void setCantidad(Integer cantidad) {
		Cantidad = cantidad;
	}

	public Double getTotalPorProducto() {
		return totalPorProducto;
	}

	public void setTotalPorProducto(Double totalPorProducto) {
		this.totalPorProducto = totalPorProducto;
	}

	public Double getTotalVenta() {
		return totalVenta;
	}

	public void setTotalVenta(Double totalVenta) {
		this.totalVenta = totalVenta;
	}

}
