package com.minimarket.JPF_SalesSystem.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductoVentaLocal {

	private String nombreProducto;
	private Double precioUnitario;
	private Integer Cantidad;
	private Double totalPorProducto;
	private Double totalVenta;
}
