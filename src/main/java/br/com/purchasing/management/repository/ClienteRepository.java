package br.com.purchasing.management.repository;

import br.com.purchasing.management.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, String> {
}
