package br.com.jaison.managerproject.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.com.jaison.managerproject.entity.Membro;
import br.com.jaison.managerproject.entity.Pessoa;
import br.com.jaison.managerproject.entity.Projeto;
import br.com.jaison.managerproject.enums.RiscoEnum;
import br.com.jaison.managerproject.enums.StatusEnum;
import br.com.jaison.managerproject.repository.MembroRepository;
import br.com.jaison.managerproject.repository.PessoaRepository;
import br.com.jaison.managerproject.repository.ProjetoRepository;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class FirstLoadData implements CommandLineRunner {

	private final ProjetoRepository repositoryProjeto;
	private final MembroRepository repositoryMembro;
	private final PessoaRepository repositoryPessoa;

	public FirstLoadData(ProjetoRepository repositoryProjeto, MembroRepository repositoryMembro,
			PessoaRepository repositoryPessoa) {
		super();
		this.repositoryProjeto = repositoryProjeto;
		this.repositoryMembro = repositoryMembro;
		this.repositoryPessoa = repositoryPessoa;
	}

	private void createFirstData() {
		final var pessoa = createPessoa();
		final var projeto = createProject(pessoa);
		createMembro(pessoa, projeto);
	}

	private Membro createMembro(Pessoa pessoa, Projeto projeto) {
		if (!Objects.isNull(projeto) && !Objects.isNull(pessoa) && repositoryMembro.findAll().isEmpty()) {
			final Membro entity = new Membro();
			entity.setPessoa(pessoa);
			entity.setProjeto(projeto);
			log.info("Membro has been saved name of project : {} Pessoa : {}", projeto.getNome(), pessoa.getNome());
			return repositoryMembro.save(entity);
		}
		return null;
	}

	private Pessoa createPessoa() {
		if (repositoryPessoa.findAll().isEmpty()) {
			final Pessoa entity = new Pessoa();
			entity.setNome("Jaison Pereira");
			entity.setCpf("88190213407");
			entity.setDataNascimento(LocalDate.now().minusYears(30));
			entity.setFuncionario(true);
			log.info("Pessoa has been saved name of project : {}", entity.getNome());
			return repositoryPessoa.save(entity);

		}
		return null;
	}

	private Projeto createProject(Pessoa pessoa) {
		if (!Objects.isNull(pessoa) && repositoryProjeto.findAll().isEmpty()) {
			final Projeto entity = new Projeto();
			entity.setDataFim(LocalDate.now().plusDays(10));
			entity.setDataInicio(LocalDate.now().plusDays(10));
			entity.setDataPrevisaoFim(LocalDate.now().plusDays(8));
			entity.setDescricao("Project loaded by first load data");
			entity.setGerente(pessoa);
			entity.setNome("Project First Data");
			entity.setOrcamento(BigDecimal.ONE);
			entity.setRisco(RiscoEnum.BAIXO_RISCO.toString());
			entity.setStatus(StatusEnum.EM_ANDAMENTO.toString());
			log.info("Project has been saved name of project : {}", entity.getNome());
			return repositoryProjeto.save(entity);
		}
		return null;
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Starting load first data");
		createFirstData();
	}
}
