package com.mindmatch.pagamento.repositories;

import com.mindmatch.pagamento.entities.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long>,
        JpaSpecificationExecutor<Pagamento> {

}
