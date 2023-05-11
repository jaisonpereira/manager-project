package br.com.jaison.managerproject.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jaison.managerproject.entity.Projeto;
import br.com.jaison.managerproject.service.ProjetoService;

@RestController
public class ProjetoController {

	private final ProjetoService serviceProjeto;

	public ProjetoController(ProjetoService serviceProjeto) {
		super();
		this.serviceProjeto = serviceProjeto;
	}

	@GetMapping("/projetos")
	public List<Projeto> listar() {
		return serviceProjeto.listar();
	}

}
