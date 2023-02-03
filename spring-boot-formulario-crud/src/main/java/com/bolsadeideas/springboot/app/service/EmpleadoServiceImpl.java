package com.bolsadeideas.springboot.app.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.app.dao.EmpleadoDao;
import com.bolsadeideas.springboot.app.entity.Empleado;

@Service
public class EmpleadoServiceImpl implements IEmpleadoService {

	@Autowired
	private EmpleadoDao empleadoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Empleado> findAll(/*String palabraClave*/) {
		/*if(palabraClave !=null) {
			return empleadoDao.findAll(palabraClave);
		}*/
		return empleadoDao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Empleado findOne(Long id) {
		return empleadoDao.findById(id).orElse(null);
	}
	
	@Override
	public void save(Empleado empleado) {
		empleadoDao.save(empleado);
	}
	
	@Override
	public void delete(Long id) {
		empleadoDao.deleteById(id);
	}
	
	@Override
	public Double averageSalary(List<Empleado> empleados) {
		Double average;
		
		average = empleados.stream().map(Empleado::getSueldo).reduce((a,b)->a+b).orElse(0D);
		
		return average/empleados.size();
	}
	
	@Override
	public Empleado maxSalary(List<Empleado> empleados) { 
		
		return empleados.stream().max(Comparator.comparing(Empleado::getSueldo)).orElse(null);
	}
	@Override
	public Empleado minSalary(List<Empleado> empleados) {
		
		return empleados.stream().min(Comparator.comparing(Empleado::getSueldo)).orElse(null);
	}
	
	@Override
	public Double expenseSalaryMonth(List<Empleado> empleados) {
		return empleados.stream().mapToDouble(Empleado::getSueldo).sum();
	}
	@Override
	public Double expenseSalaryAnnual(List<Empleado> empleados) {
		return empleados.stream().mapToDouble(Empleado::getSueldo).sum() *12;
	}
}
