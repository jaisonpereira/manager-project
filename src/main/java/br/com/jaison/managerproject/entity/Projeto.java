package br.com.jaison.managerproject.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projeto")
@Builder
public class Projeto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome", length = 200, nullable = false)
	private String nome;

	@Column(name = "data_inicio")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataInicio;

	@Column(name = "data_previsao_fim")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataPrevisaoFim;

	@Column(name = "data_fim")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataFim;

	@Column(name = "descricao", length = 5000)
	private String descricao;

	@Column(name = "status", length = 45)
	private String status;

	@Column(name = "orcamento")
	private BigDecimal orcamento;

	@Column(name = "risco", length = 45)
	private String risco;

	@ManyToOne
	@JoinColumn(name = "idgerente", nullable = false)
	private Pessoa gerente;

	public Projeto(String nome, Pessoa gerente) {
		super();
		this.nome = nome;
		this.gerente = gerente;
	}

}
