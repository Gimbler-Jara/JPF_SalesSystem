package com.minimarket.JPF_SalesSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minimarket.JPF_SalesSystem.model.Categoria;
import com.minimarket.JPF_SalesSystem.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	 @Autowired
	 private CategoriaRepository categoriaRepository;
	 
	 public List<Categoria> listarcategorias(){
		 return categoriaRepository.findAll();
	 }
	 
	 public void guardarCategoria(Categoria categoria) {
		 categoriaRepository.save(categoria);
	 }
	 
	 public Categoria obtenerCategoriaPorId(Integer id) {
		 return categoriaRepository.findById(id).orElse(null);
	 }
	 
	 public void eliminarCategoria(Integer id) {
		 categoriaRepository.deleteById(id);
	 }

}
