package br.com.jaison.managerproject.dto;

import br.com.jaison.managerproject.entity.Pessoa;
import br.com.jaison.managerproject.entity.Projeto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MembroDto {

	private Long id;

	private Projeto projeto;

	private Pessoa pessoa;

}
