package com.minimarket.JPF_SalesSystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_venta_producto")
public class VentaProducto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "id_venta", nullable = false)
	private Venta venta;

	@ManyToOne
	@JoinColumn(name = "id_producto", nullable = false)
	private Producto producto;

	@Column(nullable = false)
	private Integer cantidad;

	@Column(nullable = false)
	private Double precioUnitario;

	@Column(nullable = false)
	private Double totalProducto;

	public VentaProducto(Integer id, Venta venta, Producto producto, Integer cantidad, Double precioUnitario,
			Double totalProducto) {
		super();
		this.id = id;
		this.venta = venta;
		this.producto = producto;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
		this.totalProducto = totalProducto;
	}

	public VentaProducto(Venta venta, Producto producto, Integer cantidad, Double precioUnitario,
			Double totalProducto) {
		super();
		this.venta = venta;
		this.producto = producto;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
		this.totalProducto = totalProducto;
	}

	public VentaProducto() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Venta getVenta() {
		return venta;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public Double getTotalProducto() {
		return totalProducto;
	}

	public void setTotalProducto(Double totalProducto) {
		this.totalProducto = totalProducto;
	}

}