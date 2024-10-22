package com.minimarket.JPF_SalesSystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_ventas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Venta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_venta;

	@Column(nullable = false)
	private LocalDateTime fecha;

	@Column(nullable = false)
	private Double totalVenta;
	
	@Column(name = "Datos_cliente")
	private String cliente;

	@ManyToOne
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario;

	// Relación muchos a muchos con productos a través de VentaProducto
	@OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
	private List<VentaProducto> ventaProductos = new ArrayList<>();

	// Método para calcular el total de la venta
	public void calcularTotalVenta() {
		this.totalVenta = this.ventaProductos.stream().mapToDouble(VentaProducto::getTotalProducto).sum();

	}

}
