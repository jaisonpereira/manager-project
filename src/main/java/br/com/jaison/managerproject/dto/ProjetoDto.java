package br.com.jaison.managerproject.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjetoDto {

	private Long id;

	private String nome;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataInicio;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataPrevisaoFim;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataFim;

	private String descricao;

	private String status;

	private BigDecimal orcamento;

	private String risco;

	private String idGerente;

}
