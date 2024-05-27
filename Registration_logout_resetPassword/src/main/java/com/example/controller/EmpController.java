package com.example.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.entity.Employee;

@Controller
@RequestMapping("/emp")
public class EmpController {

	@Autowired
	private com.example.repository.EmpRepository empRepository;

	@GetMapping("/get")
	public String showPage(Model model) {
		model.addAttribute("data", empRepository.findAll());

		return "userInfo";
	}

	@PostMapping("/save")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String save(Employee e) {
		empRepository.save(e);

		return "redirect:/emp/get";
	}

	@GetMapping("/delete")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String deleteCountry(@RequestParam("id") Long id) {
		empRepository.deleteById(id);
		return "redirect:/emp/get";
	}

	@GetMapping("/findOne/{id}")
	@ResponseBody
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public Optional<Employee> findOne(@PathVariable("id") Long id) {
		if (id != null) {
			// Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Optional<Employee> entityOptional = empRepository.findById(id);
			return entityOptional;
		} else {
			throw new IllegalArgumentException("ID must not be null or invalid.");
		}
	}

}
