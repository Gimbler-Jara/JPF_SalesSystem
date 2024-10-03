package com.minimarket.JPF_SalesSystem.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_ventas")
public class Venta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_venta;

	@Column(nullable = false)
	private LocalDateTime fecha;

	@Column(nullable = false)
	private Double totalVenta;

	@ManyToOne
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario;

	// Relación muchos a muchos con productos a través de VentaProducto
	@OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
	private List<VentaProducto> ventaProductos = new ArrayList<>();

	public Venta(Integer id_venta, LocalDateTime fecha, Double totalVenta, Usuario usuario,
			List<VentaProducto> ventaProductos) {
		super();
		this.id_venta = id_venta;
		this.fecha = fecha;
		this.totalVenta = totalVenta;
		this.usuario = usuario;
		this.ventaProductos = ventaProductos;
	}

	public Venta(LocalDateTime fecha, Double totalVenta, Usuario usuario, List<VentaProducto> ventaProductos) {
		super();
		this.fecha = fecha;
		this.totalVenta = totalVenta;
		this.usuario = usuario;
		this.ventaProductos = ventaProductos;
	}

	public Venta() {
		super();
	}

	public Integer getId_venta() {
		return id_venta;
	}

	public void setId_venta(Integer id_venta) {
		this.id_venta = id_venta;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public Double getTotalVenta() {
		return totalVenta;
	}

	public void setTotalVenta(Double totalVenta) {
		this.totalVenta = totalVenta;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<VentaProducto> getVentaProductos() {
		return ventaProductos;
	}

	public void setVentaProductos(List<VentaProducto> ventaProductos) {
		this.ventaProductos = ventaProductos;
	}

	// Método para calcular el total de la venta
	public void calcularTotalVenta() {
		this.totalVenta = this.ventaProductos.stream().mapToDouble(VentaProducto::getTotalProducto).sum();

	}

}
