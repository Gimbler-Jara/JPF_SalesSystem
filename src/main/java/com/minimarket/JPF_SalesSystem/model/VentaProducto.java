package com.minimarket.JPF_SalesSystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_venta_producto")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
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

}