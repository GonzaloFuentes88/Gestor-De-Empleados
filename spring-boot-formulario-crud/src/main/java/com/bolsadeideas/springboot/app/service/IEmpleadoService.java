package com.bolsadeideas.springboot.app.service;

import java.util.List;

import com.bolsadeideas.springboot.app.entity.Empleado;

public interface IEmpleadoService {

	public List<Empleado> findAll(/*String palabraClave*/);
	
	public Empleado findOne(Long id);
	
	public void save(Empleado empleado);
	
	public void delete(Long id);
	
	public Double averageSalary(List<Empleado> empleados);
	
	public Double expenseSalaryMonth(List<Empleado> empleados);
	
	public Double expenseSalaryAnnual(List<Empleado> empleados);
	
	public Empleado maxSalary(List<Empleado> empleados);
	
	public Empleado minSalary(List<Empleado> empleados);
	
}
