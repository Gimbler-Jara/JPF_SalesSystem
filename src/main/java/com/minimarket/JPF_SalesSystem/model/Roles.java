package com.minimarket.JPF_SalesSystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_rol")
public class Roles {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idRol;
	
	@Column(name = "rol_usuario", nullable = false)
	private String rol;

	public Roles(Integer idRol, String rol) {
		super();
		this.idRol = idRol;
		this.rol = rol;
	}

	public Roles(String rol) {
		super();
		this.rol = rol;
	}
	
	public Roles() {
		super();
	}

	public Integer getIdRol() {
		return idRol;
	}

	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	
	

}
