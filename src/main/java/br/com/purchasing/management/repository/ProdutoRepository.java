package br.com.purchasing.management.repository;

import br.com.purchasing.management.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
