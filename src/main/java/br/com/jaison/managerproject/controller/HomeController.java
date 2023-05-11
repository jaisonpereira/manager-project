package br.com.jaison.managerproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.jaison.managerproject.dto.ErroDto;
import br.com.jaison.managerproject.dto.ProjetoDto;
import br.com.jaison.managerproject.exception.ValidationException;
import br.com.jaison.managerproject.service.PessoaService;
import br.com.jaison.managerproject.service.ProjetoService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/")
@Slf4j
public class HomeController {
	static final String INDEX_PAGE = "index";

	private final ProjetoService serviceProjeto;

	private final PessoaService servicePessoa;

	public HomeController(ProjetoService serviceProjeto, PessoaService servicePessoa) {
		super();
		this.serviceProjeto = serviceProjeto;
		this.servicePessoa = servicePessoa;
	}

	private void reloadPage(Model model) {
		model.addAttribute("projetos", serviceProjeto.listar());
		model.addAttribute("pessoas", servicePessoa.listar());
	}

	private void resetPage(Model model) {
		log.info("reset on page index");
		reloadPage(model);
		model.addAttribute("projetoDto", new ProjetoDto());
	}

	@PostMapping("/projeto/delete/{id}")
	public String delete(@PathVariable Long id, Model model) throws ValidationException {
		try {
			serviceProjeto.delete(id);
			resetPage(model);
		} catch (final ValidationException e) {
			handleException(e, model);
		}
		return INDEX_PAGE;
	}

	public void handleException(Exception ex, Model model) {
		reloadPage(model);
		final ErroDto erro = new ErroDto();
		erro.setMensagem(ex.getMessage());
		erro.setTipo("ERRO");
		model.addAttribute("erro", erro);
		log.error("Validation ERROR: {}", erro);
	}

	@GetMapping()
	public String listar(Model model) {
		resetPage(model);
		return INDEX_PAGE;
	}

	@PostMapping()
	public String save(@ModelAttribute("projetoDto") ProjetoDto projeto, RedirectAttributes redirectAttribute, Model model)
			throws ValidationException {
		try {
			log.info("Projeto recebido {}", projeto);
			serviceProjeto.save(projeto);
			resetPage(model);
		} catch (final ValidationException e) {
			handleException(e, model);
		}
		return INDEX_PAGE;
	}

	@PostMapping("/projeto/update/{id}")
	public String update(@PathVariable Long id, Model model) throws ValidationException {
		try {
			resetPage(model);
			final var dto = serviceProjeto.getProjectDtoById(id);
			model.addAttribute("projetoDto", dto);
		} catch (final ValidationException e) {
			handleException(e, model);
		}
		return INDEX_PAGE;
	}

}
