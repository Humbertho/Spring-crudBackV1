package com.humberto.crudapp.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.humberto.crudapp.dto.Mensaje;
import com.humberto.crudapp.dto.ProductoDto;
import com.humberto.crudapp.entity.Producto;
import com.humberto.crudapp.service.ProductoService;

@RestController
@RequestMapping("/producto")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController 
{
	@Autowired
	ProductoService productoService;
	
	@GetMapping("/products")
	public ResponseEntity<List<Producto>> products()
	{
		List<Producto> products = productoService.products();
		return new ResponseEntity<List<Producto>>(products, HttpStatus.OK);
	}
	
	@GetMapping("/product/{id}")
	public ResponseEntity<Producto> productById(@PathVariable("id") int id)
	{
		if(!productoService.existsById(id))
		{
			return new ResponseEntity(new Mensaje("No existe el producto con el id: " + id), HttpStatus.NOT_FOUND);
		}
		
		Producto producto = productoService.productsById(id).get();
		
		return new ResponseEntity<Producto>(producto, HttpStatus.OK);
	}
	
	@GetMapping("/productName/{name}")
    public ResponseEntity<Producto> productByName(@PathVariable("name") String name)
    {
        if(!productoService.existsByName(name))
        {
            return new ResponseEntity(new Mensaje("No existe el producto con el nombre: " + name), HttpStatus.NOT_FOUND);
        }
        
        Producto producto = productoService.productsByName(name).get();
        
        return new ResponseEntity<Producto>(producto, HttpStatus.OK);
    }
	
	@PostMapping("/create")
	public ResponseEntity<?> createProduct(@RequestBody ProductoDto productoDto)
	{
	    if(StringUtils.isBlank(productoDto.getNombre()))
	    {
	        return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
	    }
	    if(productoDto.getPrecio() < 0)
	    {
	        return new ResponseEntity(new Mensaje("El precio debe ser mayor a cero"), HttpStatus.BAD_REQUEST);
	    }
	    if(productoService.existsByName(productoDto.getNombre()))
        {
            return new ResponseEntity(new Mensaje("Ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        }
	    
	    Producto product = new Producto(productoDto.getNombre(), productoDto.getPrecio());
	    
	    productoService.save(product);
	    
	    return new ResponseEntity(new Mensaje("Producto creado"), HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") int id, @RequestBody ProductoDto productoDto)
    {
	    if(!productoService.existsById(id))
        {
            return new ResponseEntity(new Mensaje("No existe el producto con el id: " + id), HttpStatus.NOT_FOUND);
        }
	    
	    if(productoService.existsByName(productoDto.getNombre()) && productoService.productsByName(productoDto.getNombre()).get().getId() != id)
        {
            return new ResponseEntity(new Mensaje("Ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        }
	    
        if(StringUtils.isBlank(productoDto.getNombre()))
        {
            return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if(productoDto.getPrecio() < 0 || productoDto.getPrecio() == null)
        {
            return new ResponseEntity(new Mensaje("El precio debe ser mayor a cero"), HttpStatus.BAD_REQUEST);
        }        
        
        Producto product = productoService.productsById(id).get();
        product.setNombre(productoDto.getNombre());
        product.setPrecio(productoDto.getPrecio());
        
        productoService.save(product);
        
        return new ResponseEntity(new Mensaje("Producto actualizado"), HttpStatus.OK);
    }
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") int id)
	{
	    if(!productoService.existsById(id))
        {
            return new ResponseEntity(new Mensaje("No existe el producto con el id: " + id), HttpStatus.NOT_FOUND);
        }
	    
	    productoService.delete(id);
	    
	    return new ResponseEntity(new Mensaje("Producto eliminado"), HttpStatus.OK);
	}
}
