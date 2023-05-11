package br.com.jaison.managerproject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jaison.managerproject.dto.MembroDto;
import br.com.jaison.managerproject.entity.Membro;
import br.com.jaison.managerproject.exception.ValidationException;
import br.com.jaison.managerproject.service.MembroService;

@RestController
@RequestMapping(value = "/membro")
public class MembroController {

	private final MembroService membroService;

	public MembroController(MembroService membroService) {
		super();
		this.membroService = membroService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Membro> buscarMembroPorId(@PathVariable Long id) {
		final var membro = membroService.buscarPorId(id);
		if (membro != null) {
			return ResponseEntity.ok(membro);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<Membro> criarMembro(@RequestBody MembroDto membro) throws ValidationException {
		final Membro novoMembro = membroService.salvar(membro);
		return ResponseEntity.status(HttpStatus.CREATED).body(novoMembro);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarMembroPorId(@PathVariable Long id) {
		membroService.deletarPorId(id);
		return ResponseEntity.noContent().build();
	}

}
