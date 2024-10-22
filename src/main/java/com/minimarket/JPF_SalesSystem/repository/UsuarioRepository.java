package com.minimarket.JPF_SalesSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.minimarket.JPF_SalesSystem.model.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Usuario findByEmail(String email);
	
	//login
	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM Usuario u "
			+ "WHERE u.email = :email AND u.password = :password ")
	boolean isRegistered(String email, String password);
	
}
