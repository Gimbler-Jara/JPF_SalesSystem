package com.minimarket.JPF_SalesSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.minimarket.JPF_SalesSystem.model.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer>{

	
}
