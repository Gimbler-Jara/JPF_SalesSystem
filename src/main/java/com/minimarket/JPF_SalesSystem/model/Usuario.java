package com.minimarket.JPF_SalesSystem.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; 
    
    @Column(name = "estado", nullable = false)
    private Boolean estado; 
    
    private LocalDateTime fechaCreacion;
    
    private LocalDateTime ultimoAcceso;

    @Column(name = "intentos_fallidos", nullable = false)
    private Integer intentosFallidos = 0;

    // Relación ManyToOne con Roles
    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private Roles rol;

  /*  public Usuario() {
		super();
	}

	public Usuario(Long id_usuario, String username, String email, String password, Boolean estado,
			LocalDateTime fechaCreacion, LocalDateTime ultimoAcceso, Integer intentosFallidos, Roles rol) {
		super();
		this.id_usuario = id_usuario;
		this.username = username;
		this.email = email;
		this.password = password;
		this.estado = estado;
		this.fechaCreacion = fechaCreacion;
		this.ultimoAcceso = ultimoAcceso;
		this.intentosFallidos = intentosFallidos;
		this.rol = rol;
	}

	public Usuario(String username, String email, String password, Boolean estado, LocalDateTime fechaCreacion,
			LocalDateTime ultimoAcceso, Integer intentosFallidos, Roles rol) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.estado = estado;
		this.fechaCreacion = fechaCreacion;
		this.ultimoAcceso = ultimoAcceso;
		this.intentosFallidos = intentosFallidos;
		this.rol = rol;
	}

	public Long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Long id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public LocalDateTime getUltimoAcceso() {
		return ultimoAcceso;
	}

	public void setUltimoAcceso(LocalDateTime ultimoAcceso) {
		this.ultimoAcceso = ultimoAcceso;
	}

	public Integer getIntentosFallidos() {
		return intentosFallidos;
	}

	public void setIntentosFallidos(Integer intentosFallidos) {
		this.intentosFallidos = intentosFallidos;
	}

	public Roles getRol() {
		return rol;
	}

	public void setRol(Roles rol) {
		this.rol = rol;
	}*/
    	
}

