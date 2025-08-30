package com.mindmatch.pagamento.dto;

import com.mindmatch.pagamento.entities.Pagamento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PagamentoDTO {
    private Long id;
    @NotNull(message = "Campo requerido")
    @Positive(message = "O valor do pagamento dever ser um número positivo")
    private BigDecimal valor;
    @NotNull(message = "Campo requerido")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    private String nome;//
    @NotNull(message = "Campo requerido")
    @Size(min = 16, max = 19, message = "O número deve ter entre 16 e 19")
    private String numeroDoCartao;//
    @NotNull(message = "Campo requerido")
    @Size(min = 5, max = 5, message = "Validade deve ter 5 caracteres")
    private String validade;
    @NotNull(message = "Campo requerido")
    @Size(min = 3, max = 3, message = "Código de segurança deve ter 3 caracteres")
    private String codigoDeSeguranca;
    @NotNull(message = "Forma de pagamento Id é obrigatório")
    private Long formaDePagamentoId;

    public PagamentoDTO(Pagamento entity) {
        id = entity.getId();
        valor = entity.getValor();
        nome = entity.getNome();
        numeroDoCartao = entity.getNumeroDoCartao();
        validade = entity.getValidade();
        codigoDeSeguranca = entity.getCodigoDeSeguranca();
        formaDePagamentoId = entity.getFormaDePagamentoId();
    }
}
