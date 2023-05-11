package br.com.jaison.managerproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.jaison.managerproject.dto.MembroDto;
import br.com.jaison.managerproject.entity.Membro;
import br.com.jaison.managerproject.entity.Pessoa;
import br.com.jaison.managerproject.entity.Projeto;
import br.com.jaison.managerproject.exception.ValidationException;
import br.com.jaison.managerproject.repository.MembroRepository;
import br.com.jaison.managerproject.service.MembroService;
import br.com.jaison.managerproject.service.PessoaService;
import br.com.jaison.managerproject.service.ProjetoService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MembroServiceTest {

	@Autowired
	MembroRepository membroRepository;

	@Autowired
	MembroService membroService;

	@Autowired
	ProjetoService serviceProjeto;

	@Autowired
	PessoaService servicePessoa;

	@Test
	void testBuscarPorId() throws ValidationException {
		final Long id = 1L;
		final MembroDto dto = new MembroDto(id, serviceProjeto.getProjectById(1L), servicePessoa.findById(1L));
		membroService.salvar(dto);
		final Membro membroEncontrado = membroService.buscarPorId(id);
		assertNotNull(membroEncontrado);
		assertEquals(id, membroEncontrado.getId());
	}

	@Test
	void testDeletarPorId() throws ValidationException {
		final Long id = 1L;
		final MembroDto dto = new MembroDto(id, serviceProjeto.getProjectById(1L), servicePessoa.findById(1L));
		membroService.salvar(dto);
		membroService.deletarPorId(id);
		assertFalse(membroRepository.existsById(id));
	}

	@Test
	void testSalvarComPessoaFuncionario() throws ValidationException {
		final MembroDto membroDto = new MembroDto();
		membroDto.setProjeto(serviceProjeto.getProjectById(1L));
		final Pessoa pessoa = new Pessoa();
		pessoa.setFuncionario(true);
		pessoa.setNome("teste pessoa");
		servicePessoa.save(pessoa);
		membroDto.setPessoa(pessoa);
		final Membro membroSalvo = membroService.salvar(membroDto);
		assertNotNull(membroSalvo);
	}

	@Test
	void testSalvarComPessoaNaoFuncionario() {
		final MembroDto membroDto = new MembroDto();
		membroDto.setProjeto(new Projeto());
		final Pessoa pessoa = new Pessoa();
		pessoa.setFuncionario(false);
		membroDto.setPessoa(pessoa);
		assertThrows(ValidationException.class, () -> membroService.salvar(membroDto));
	}

}
