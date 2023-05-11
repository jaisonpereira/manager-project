package br.com.jaison.managerproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.jaison.managerproject.entity.Pessoa;
import br.com.jaison.managerproject.repository.PessoaRepository;

@Service
public class PessoaService {

	private final PessoaRepository repository;

	public PessoaService(PessoaRepository repository) {
		super();
		this.repository = repository;
	}

	public Pessoa findById(Long id) {
		final var pessoa = repository.findById(id);
		if (pessoa.isPresent()) {
			return pessoa.get();
		}
		return null;
	}

	public List<Pessoa> listar() {
		return repository.findAll();
	}

	public Pessoa save(Pessoa pessoa) {
		return repository.save(pessoa);
	}
}
