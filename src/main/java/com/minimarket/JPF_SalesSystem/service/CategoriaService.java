package com.minimarket.JPF_SalesSystem.service;

import java.util.List;

import com.minimarket.JPF_SalesSystem.model.Categoria;

public interface CategoriaService {

	public List<Categoria> listarcategorias();

	public void guardarCategoria(Categoria categoria);

	public Categoria obtenerCategoriaPorId(Integer id);

	public void eliminarCategoria(Integer id);
}
