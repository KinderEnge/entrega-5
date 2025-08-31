package com.mindmatch.pagamento.service;

import com.mindmatch.pagamento.dto.PagamentoDTO;
import com.mindmatch.pagamento.entities.Pagamento;
import com.mindmatch.pagamento.repositories.PagamentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagamentoServiceTest {

    @Mock
    private PagamentoRepository repository;

    @InjectMocks
    private PagamentoService service;

    @Test
    void createPagamento_shouldCopyTransactionDateAndDescricao() {
        PagamentoDTO dto = new PagamentoDTO(null,
                BigDecimal.valueOf(123.45),
                "Teste",
                "4111111111111111",
                "12/25",
                "123",
                1L,
                LocalDate.of(2025,8,21),
                "Compra: teste" // descricao
        );

        when(repository.save(any(Pagamento.class))).thenAnswer(invocation -> {
            Pagamento p = invocation.getArgument(0);
            p.setId(99L);
            return p;
        });

        PagamentoDTO result = service.createPagamento(dto);

        assertNotNull(result);
        assertEquals(99L, result.getId());
        assertEquals(dto.getTransactionDate(), result.getTransactionDate());
        assertEquals(dto.getDescricao(), result.getDescricao());
    }
}
