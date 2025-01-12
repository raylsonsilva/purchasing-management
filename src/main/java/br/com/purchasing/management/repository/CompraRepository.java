package br.com.purchasing.management.repository;

import br.com.purchasing.management.entity.Compra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepository extends JpaRepository<Compra, Long> {
    Page<Compra> findAllByOrderByValorTotalAsc(Pageable pageable);
}
