package br.com.jaison.managerproject.service;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.jaison.managerproject.dto.ProjetoDto;
import br.com.jaison.managerproject.entity.Pessoa;
import br.com.jaison.managerproject.entity.Projeto;
import br.com.jaison.managerproject.enums.StatusEnum;
import br.com.jaison.managerproject.exception.ValidationException;
import br.com.jaison.managerproject.repository.ProjetoRepository;

@Service
public class ProjetoService {

	private final ProjetoRepository repositoryProjeto;

	private final PessoaService servicePessoa;

	public ProjetoService(ProjetoRepository repositoryProjeto, PessoaService servicePessoa) {
		super();
		this.repositoryProjeto = repositoryProjeto;
		this.servicePessoa = servicePessoa;
	}

	private void validate(ProjetoDto entity) throws ValidationException {
		if (!StringUtils.hasText(entity.getNome())) {
			throw new ValidationException("Nome é Obrigatorio");
		}
		if (!StringUtils.hasText(entity.getIdGerente())) {
			throw new ValidationException("Gerente do Projeto é Obrigatorio");
		}
	}

	public void delete(Long idProjeto) throws ValidationException {
		if (Objects.isNull(idProjeto)) {
			throw new ValidationException("id do Projeto é Obrigatorio para exclusao");
		}

		final var entity = repositoryProjeto.findById(idProjeto).orElse(null);
		if (entity != null) {
			if (entity.getStatus().equals(StatusEnum.INICIADO.toString())
					|| entity.getStatus().equals(StatusEnum.EM_ANDAMENTO.toString())
					|| entity.getStatus().equals(StatusEnum.ENCERRADO.toString())) {
				throw new ValidationException(
						"O Projeto nao pode ser excluido pois o mesmo encontrase em " + entity.getStatus());
			}
			repositoryProjeto.deleteById(idProjeto);
		} else {
			throw new ValidationException("Nao encontrado projeto com o id " + idProjeto);
		}
	}

	public Projeto getProjectById(Long id) {
		return repositoryProjeto.findById(id).orElse(null);
	}

	@Transactional
	public ProjetoDto getProjectDtoById(Long id) throws ValidationException {
		if (Objects.isNull(id)) {
			throw new ValidationException("id do Projeto é Obrigatorio para alteracao");
		}
		final var entity = getProjectById(id);
		if (entity != null) {
			return ProjetoDto.builder().dataFim(entity.getDataFim()).dataInicio(entity.getDataInicio())
					.dataPrevisaoFim(entity.getDataPrevisaoFim()).descricao(entity.getDescricao())
					.idGerente(entity.getGerente().getId().toString()).nome(entity.getNome())
					.orcamento(entity.getOrcamento()).risco(entity.getRisco()).status(entity.getStatus()).id(entity.getId())
					.build();
		}
		return null;
	}

	public List<Projeto> listar() {
		return repositoryProjeto.findAll();
	}

	@Transactional
	public Projeto save(ProjetoDto dto) throws ValidationException {
		validate(dto);
		final Pessoa pessoa = servicePessoa.findById(Long.parseLong(dto.getIdGerente()));

		final var projeto = Projeto.builder().dataFim(dto.getDataFim()).dataInicio(dto.getDataInicio())
				.dataPrevisaoFim(dto.getDataPrevisaoFim()).descricao(dto.getDescricao()).gerente(pessoa).nome(dto.getNome())
				.orcamento(dto.getOrcamento()).risco(dto.getRisco()).status(dto.getStatus()).id(dto.getId()).build();
		return repositoryProjeto.save(projeto);
	}

}
