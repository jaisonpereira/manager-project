package br.com.jaison.managerproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jaison.managerproject.entity.Membro;

@Repository
public interface MembroRepository extends JpaRepository<Membro, Long> {

}
