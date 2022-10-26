package com.humberto.crudapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.humberto.crudapp.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>
{
	Optional<Producto> findByNombre(String nombre);
	boolean existsByNombre(String nombre);
}
