package com.minimarket.JPF_SalesSystem.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_productos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
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

}