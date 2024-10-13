package com.minimarket.JPF_SalesSystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_rol")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Roles {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idRol;
	
	@Column(name = "rol_usuario", nullable = false)
	private String rol;

}
