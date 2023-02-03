package com.bolsadeideas.springboot.app.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsadeideas.springboot.app.entity.Empleado;
import com.bolsadeideas.springboot.app.service.IEmpleadoService;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("empleado")
public class EmpleadosController {

	private static final String TITLE_PAGE= "Gestor De Empleados";
	
	@Autowired
	private IEmpleadoService empleadoService;
	
	@GetMapping({"/index","/"})
	public String index(Model model) {
		List<Empleado> empleados = empleadoService.findAll(/*palabraClave*/);
		Double promedio = empleadoService.averageSalary(empleados);
		Empleado empMaxSueldo = empleadoService.maxSalary(empleados);
		Empleado empMinSueldo = empleadoService.minSalary(empleados);
		model.addAttribute("titulo",TITLE_PAGE);
		model.addAttribute("mensual", empleadoService.expenseSalaryMonth(empleados));
		model.addAttribute("anuales", empleadoService.expenseSalaryAnnual(empleados));
		model.addAttribute("promedio", promedio);
		model.addAttribute("maximo", empMaxSueldo);
		model.addAttribute("minimo", empMinSueldo);
		model.addAttribute("titulo", TITLE_PAGE);
		model.addAttribute("tituloSeccion", "Estadisticas");
		
		return "index";
	}
	
	@GetMapping("/tables")
	public String tables(Model model) {
		List<Empleado> empleados = empleadoService.findAll(/*palabraClave*/);
		model.addAttribute("titulo",TITLE_PAGE);
		model.addAttribute("tituloSeccion","Lista de Empleados");
		model.addAttribute("empleados",empleados);
		
		return "tables";
	}
	
	@GetMapping("/detalle/{id}")
	public String detalleCliente(@PathVariable(name = "id") Long id, Model model) {
		Empleado empleado = null;
		if(id>0) {
			empleado = empleadoService.findOne(id);
			if(empleado!=null) {
				model.addAttribute("empleado",empleado);
				model.addAttribute("titulo", TITLE_PAGE);
				return "detalle";
			}
		}
		return "tables";
	}
	
	
	@GetMapping({"/listar","/"})
	public String listar(Model model) {
		List<Empleado> empleados = empleadoService.findAll(/*palabraClave*/);
		model.addAttribute("titulo","Listado de empleados");
		model.addAttribute("empleados",empleados);
		
		return "listar";
	}
	
	@GetMapping("/add")
	public String formulario(Model model) {
		Empleado empleado = new Empleado();
		model.addAttribute("empleado",empleado);
		model.addAttribute("titulo","Formulario Empleado");
		
		
		return "add";
	}
	@PostMapping("/add")
	public String guardar(@Valid Empleado empleado,BindingResult result,SessionStatus status,Model model) {
		if(result.hasErrors()) {
			return "add";
		}
		
		if(empleado != null) {
			empleadoService.save(empleado);
			status.setComplete();
			return "redirect:/tables";
		}
		
		return "add";
	}
	
	@GetMapping("/add/{id}")
	public String editar(@PathVariable(name = "id") Long id, Model model) {
		Empleado empleado = null;
		if(id>0) {
			empleado = empleadoService.findOne(id);
			if(empleado!=null) {
				model.addAttribute("empleado",empleado);
				return "add";
			}
		}
		return "redirect:/tables";
	}
	
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(name = "id") Long id, Model model) {
		Empleado empleado = null;
		if(id>0) {
			empleado = empleadoService.findOne(id);
			if(empleado!=null) {
				model.addAttribute("empleado",empleado);
				model.addAttribute("titulo", empleado.getNombre()+" "+empleado.getApellido());
				return "ver";
			}
		}
		return "listar";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(name = "id") Long id, Model model) {
		Empleado empleado = null;
		if(id>0) {
			empleado = empleadoService.findOne(id);
			if(empleado != null) {
				empleadoService.delete(id);
			}
			return "redirect:/tables";
		}
		
		return "redirect:/tables";
	}
	
	
}
