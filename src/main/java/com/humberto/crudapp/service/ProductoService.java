package com.humberto.crudapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.humberto.crudapp.entity.Producto;
import com.humberto.crudapp.repository.ProductoRepository;

@Service
@Transactional
public class ProductoService 
{
	@Autowired
	ProductoRepository productoRepository;
	
	public List<Producto> products()
	{
		return productoRepository.findAll();
	}
	
	public Optional<Producto> productsById(int id)
	{
		return productoRepository.findById(id);
	}
	
	public Optional<Producto> productsByName(String name)
	{
		return productoRepository.findByNombre(name);
	}
	
	public void save(Producto product)
	{
		productoRepository.save(product);
	}
	
	public void delete(int id)
	{
		productoRepository.deleteById(id);
	}
	
	public boolean existsById(int id)
	{
		return productoRepository.existsById(id);
	}
	
	public boolean existsByName(String name)
	{
		return productoRepository.existsByNombre(name);
	}
}
