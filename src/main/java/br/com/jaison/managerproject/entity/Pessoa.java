package br.com.jaison.managerproject.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pessoa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome", length = 100, nullable = false)
	private String nome;

	@Column(name = "datanascimento")
	private LocalDate dataNascimento;

	@Column(name = "cpf", length = 14)
	private String cpf;

	@Column(name = "funcionario")
	private Boolean funcionario;

	public Pessoa(Long id, String nome, Boolean funcionario) {
		super();
		this.id = id;
		this.nome = nome;
		this.funcionario = funcionario;
	}

}
