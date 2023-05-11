package br.com.jaison.managerproject.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "membros")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Membro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "idprojeto", nullable = false)
	private Projeto projeto;

	@ManyToOne
	@JoinColumn(name = "idpessoa", nullable = false)
	private Pessoa pessoa;

}
