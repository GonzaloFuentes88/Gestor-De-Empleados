package com.bolsadeideas.springboot.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bolsadeideas.springboot.app.entity.Empleado;

public interface EmpleadoDao extends JpaRepository<Empleado, Long> {
	
	//@Query("SELECT p FROM empleados p WHERE p.nombre LIKE %?1%")
	public List<Empleado> findAll(/*String palabraClave*/); 

}
