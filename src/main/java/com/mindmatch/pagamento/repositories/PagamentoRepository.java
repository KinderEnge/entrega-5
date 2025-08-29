package com.mindmatch.pagamento.repositories;

import com.mindmatch.pagamento.entities.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
