package com.minimarket.JPF_SalesSystem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minimarket.JPF_SalesSystem.model.Categoria;
import com.minimarket.JPF_SalesSystem.repository.CategoriaRepository;
import com.minimarket.JPF_SalesSystem.service.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService{
	
	 @Autowired
	 private CategoriaRepository categoriaRepository;
	 
	 @Override
	 public List<Categoria> listarcategorias(){
		 return categoriaRepository.findAll();
	 }
	 
	 @Override
	 public void guardarCategoria(Categoria categoria) {
		 if(categoria.getNombreCategoria().isEmpty()) {
			 throw new IllegalArgumentException("El Campo no puede estar vacio");
		 }
		 
		 categoriaRepository.save(categoria);
	 }
	 
	 @Override
	 public Categoria obtenerCategoriaPorId(Integer id) {
		 return categoriaRepository.findById(id).orElse(null);
	 }
	 
	 @Override
	 public void eliminarCategoria(Integer id) {
		 categoriaRepository.deleteById(id);
	 }

}
