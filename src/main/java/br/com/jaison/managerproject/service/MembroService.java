package br.com.jaison.managerproject.service;

import org.springframework.stereotype.Service;

import br.com.jaison.managerproject.dto.MembroDto;
import br.com.jaison.managerproject.entity.Membro;
import br.com.jaison.managerproject.exception.ValidationException;
import br.com.jaison.managerproject.repository.MembroRepository;

@Service
public class MembroService {
	private final MembroRepository membroRepository;

	public MembroService(MembroRepository membroRepository) {
		super();
		this.membroRepository = membroRepository;
	}

	public Membro buscarPorId(Long id) {
		return membroRepository.findById(id).orElse(null);
	}

	public void deletarPorId(Long id) {
		membroRepository.deleteById(id);
	}

	public Membro salvar(MembroDto dto) throws ValidationException {
		if (Boolean.FALSE.equals(dto.getPessoa().getFuncionario())) {
			throw new ValidationException("Somente funcionario pode se associar ao projeto");
		}
		final Membro entity = new Membro(dto.getId(), dto.getProjeto(), dto.getPessoa());
		return membroRepository.save(entity);
	}
}
