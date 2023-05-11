package br.com.jaison.managerproject;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.jaison.managerproject.dto.ProjetoDto;
import br.com.jaison.managerproject.entity.Pessoa;
import br.com.jaison.managerproject.entity.Projeto;
import br.com.jaison.managerproject.enums.StatusEnum;
import br.com.jaison.managerproject.exception.ValidationException;
import br.com.jaison.managerproject.repository.ProjetoRepository;
import br.com.jaison.managerproject.service.PessoaService;
import br.com.jaison.managerproject.service.ProjetoService;

@SpringBootTest
class ProjetoServiceTest {

	@Mock
	ProjetoRepository repositoryProjeto;

	@Mock
	PessoaService servicePessoa;

	@InjectMocks
	ProjetoService projetoService;

	@Test
	@DisplayName("Deve listar projetos com sucesso")
	void deveListarProjetosComSucesso() throws ValidationException {

		final Projeto dto = new Projeto();
		dto.setNome("projeto");
		dto.setGerente(getPessoa());
		final List<Projeto> projetos = new ArrayList<>();
		projetos.add(dto);
		when(repositoryProjeto.findAll()).thenReturn(projetos);
		final var entities = projetoService.listar();
		assertTrue(!entities.isEmpty());
	}

	@Test
	@DisplayName("Deve retornar erro ao tentar salvar um projeto sem gerente")
	void deveRetornarErroAoTentarSalvarProjetoSemGerente() {
		final ProjetoDto dto = getProjetoDto();
		dto.setNome("projeto");
		dto.setIdGerente(null);

		final Exception exception = assertThrows(ValidationException.class, () -> {
			projetoService.save(dto);
		});

		final String expectedMessage = "Gerente do Projeto é Obrigatorio";
		final String actualMessage = exception.getMessage();

		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	@DisplayName("Deve retornar erro ao tentar salvar um projeto sem nome")
	void deveRetornarErroAoTentarSalvarProjetoSemNome() {
		final ProjetoDto dto = getProjetoDto();
		dto.setNome(null);

		final Exception exception = assertThrows(ValidationException.class, () -> {
			projetoService.save(dto);
		});

		final String expectedMessage = "Nome é Obrigatorio";
		final String actualMessage = exception.getMessage();

		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	@DisplayName("Deve salvar um projeto com sucesso")
	void deveSalvarProjetoComSucesso() throws ValidationException {
		final ProjetoDto dto = getProjetoDto();
		dto.setNome("Projeto teste");
		dto.setIdGerente("1");
		final Pessoa pessoa = Mockito.spy(getPessoa());

		final Projeto projeto = Mockito.spy(new Projeto(dto.getNome(), getPessoa()));
		when(servicePessoa.findById(any(Long.class))).thenReturn(pessoa);
		Mockito.doReturn(projeto).when(repositoryProjeto).save(any(Projeto.class));
		final var saved = projetoService.save(dto);
		assertNotNull(saved);

	}

	Pessoa getPessoa() {
		return new Pessoa();
	}

	ProjetoDto getProjetoDto() {
		return new ProjetoDto();
	}

	@Before
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("Deve lançar ValidationException quando o idProjeto for nulo")
	void testDeleteWhenIdProjetoIsNull() {
		final Long idProjeto = null;
		final ValidationException exception = Assertions.assertThrows(ValidationException.class,
				() -> projetoService.delete(idProjeto));
		Assertions.assertEquals("id do Projeto é Obrigatorio para exclusao", exception.getMessage());
	}

	@Test
	@DisplayName("Deve lançar ValidationException quando o projeto com o idProjeto estiver em andamento")
	void testDeleteWhenProjetoIsEmAndamento() {
		final Long idProjeto = 1L;
		final Projeto projeto = Projeto.builder().id(idProjeto).nome("Projeto 1").status(StatusEnum.EM_ANDAMENTO.toString())
				.build();
		when(repositoryProjeto.findById(idProjeto)).thenReturn(Optional.of(projeto));
		final ValidationException exception = Assertions.assertThrows(ValidationException.class,
				() -> projetoService.delete(idProjeto));
		Assertions.assertEquals("O Projeto nao pode ser excluido pois o mesmo encontrase em " + projeto.getStatus(),
				exception.getMessage());
	}

	@Test
	@DisplayName("Deve lançar ValidationException quando o projeto com o idProjeto estiver encerrado")
	void testDeleteWhenProjetoIsEncerrado() {
		final Long idProjeto = 1L;
		final Projeto projeto = Projeto.builder().id(idProjeto).nome("Projeto 1").status(StatusEnum.ENCERRADO.toString())
				.build();
		when(repositoryProjeto.findById(idProjeto)).thenReturn(Optional.of(projeto));
		final ValidationException exception = Assertions.assertThrows(ValidationException.class,
				() -> projetoService.delete(idProjeto));
		Assertions.assertEquals("O Projeto nao pode ser excluido pois o mesmo encontrase em " + projeto.getStatus(),
				exception.getMessage());
	}

	@Test
	@DisplayName("Deve lançar ValidationException quando o projeto com o idProjeto estiver iniciado")
	void testDeleteWhenProjetoIsIniciado() {
		final Long idProjeto = 1L;
		final Projeto projeto = Projeto.builder().id(idProjeto).nome("Projeto 1").status(StatusEnum.INICIADO.toString())
				.build();
		when(repositoryProjeto.findById(idProjeto)).thenReturn(Optional.of(projeto));
		final ValidationException exception = Assertions.assertThrows(ValidationException.class,
				() -> projetoService.delete(idProjeto));
		Assertions.assertEquals("O Projeto nao pode ser excluido pois o mesmo encontrase em " + projeto.getStatus(),
				exception.getMessage());
	}

	@Test
	@DisplayName("Deve lançar ValidationException quando o projeto com o idProjeto não existir")
	void testDeleteWhenProjetoNotFound() {
		final Long idProjeto = 1L;
		when(repositoryProjeto.findById(idProjeto)).thenReturn(Optional.empty());
		final ValidationException exception = Assertions.assertThrows(ValidationException.class,
				() -> projetoService.delete(idProjeto));
		Assertions.assertEquals("Nao encontrado projeto com o id " + idProjeto, exception.getMessage());
	}

}
