package com.minimarket.JPF_SalesSystem.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_productos")
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_producto;

	@Column(nullable = false)
	private String nombre;

	@Column(name = "precio", nullable = false)
	private Double precio;

	@Column(name = "stockMinimo", nullable = false)
	private Integer stockMinimo;

	@Column(name = "stockMaximo", nullable = false)
	private Integer stockMaximo;

	@Column(name = "stockActual", nullable = false)
	private Integer stockActual;

	@ManyToOne
	@JoinColumn(name = "id_categoria", nullable = false)
	private Categoria categoria;

	// Relación bidireccional con VentaProducto
	@OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
	private List<VentaProducto> ventaProductos = new ArrayList<>();

	public Producto(Long id_producto, String nombre, Double precio, Integer stockMinimo, Integer stockMaximo,
			Integer stockActual, Categoria categoria, List<VentaProducto> ventaProductos) {
		super();
		this.id_producto = id_producto;
		this.nombre = nombre;
		this.precio = precio;
		this.stockMinimo = stockMinimo;
		this.stockMaximo = stockMaximo;
		this.stockActual = stockActual;
		this.categoria = categoria;
		this.ventaProductos = ventaProductos;
	}

	public Producto(String nombre, Double precio, Integer stockMinimo, Integer stockMaximo, Integer stockActual,
			Categoria categoria, List<VentaProducto> ventaProductos) {
		super();
		this.nombre = nombre;
		this.precio = precio;
		this.stockMinimo = stockMinimo;
		this.stockMaximo = stockMaximo;
		this.stockActual = stockActual;
		this.categoria = categoria;
		this.ventaProductos = ventaProductos;
	}

	public Producto() {
		super();
	}

	public Long getId_producto() {
		return id_producto;
	}

	public void setId_producto(Long id_producto) {
		this.id_producto = id_producto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Integer getStockMinimo() {
		return stockMinimo;
	}

	public void setStockMinimo(Integer stockMinimo) {
		this.stockMinimo = stockMinimo;
	}

	public Integer getStockMaximo() {
		return stockMaximo;
	}

	public void setStockMaximo(Integer stockMaximo) {
		this.stockMaximo = stockMaximo;
	}

	public Integer getStockActual() {
		return stockActual;
	}

	public void setStockActual(Integer stockActual) {
		this.stockActual = stockActual;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<VentaProducto> getVentaProductos() {
		return ventaProductos;
	}

	public void setVentaProductos(List<VentaProducto> ventaProductos) {
		this.ventaProductos = ventaProductos;
	}

	

}