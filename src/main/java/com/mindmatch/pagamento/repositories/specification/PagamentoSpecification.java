package com.mindmatch.pagamento.repositories.specification;

import com.mindmatch.pagamento.dto.FormDTO;
import com.mindmatch.pagamento.entities.Pagamento;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PagamentoSpecification {

    public static Specification<Pagamento> formFiltro(FormDTO formDTO) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(formDTO.getNome() != null) {
                predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("nome")), formDTO.getNome().toLowerCase()));
            }

            if(formDTO.getNumeroDoCartao() != null) {
                predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("numeroDoCartao")), formDTO.getNumeroDoCartao().toLowerCase()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
