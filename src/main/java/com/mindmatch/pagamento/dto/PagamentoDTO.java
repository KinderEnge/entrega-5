package com.mindmatch.pagamento.dto;

import com.mindmatch.pagamento.entities.Pagamento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PagamentoDTO {
    @Schema(description = "Id do pagamento.")
    private Long id;

    @NotNull(message = "Campo requerido")
    @Positive(message = "O valor do pagamento dever ser um número positivo")
    @Schema(description = "Valor do pagamento.")
    private BigDecimal valor;

    @NotNull(message = "Campo requerido")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    @Schema(description = "Nome do efetuador do pagamento.")
    private String nome;

    @NotNull(message = "Campo requerido")
    @Size(min = 16, max = 19, message = "O número deve ter entre 16 e 19")
    @Schema(description = "Numero do cartão utilizado no pagamento.")
    private String numeroDoCartao;

    @NotNull(message = "Campo requerido")
    @Size(min = 5, max = 5, message = "Validade deve ter 5 caracteres")
    @Schema(description = "Validade do cartão utilizado no pagamento.")
    private String validade;

    @NotNull(message = "Campo requerido")
    @Size(min = 3, max = 3, message = "Código de segurança deve ter 3 caracteres")
    @Schema(description = "Código de segurança do cartão utilizado no pagamento.")
    private String codigoDeSeguranca;

    @NotNull(message = "Forma de pagamento Id é obrigatório")
    @Schema(description = "Forma de pagamento utilizada; 1 - Crédito, 2 - Débito.")
    private Long formaDePagamentoId;

    @Schema(description = "Data da transação.")
    private LocalDate transactionDate;
    private String descricao;

    public PagamentoDTO(Pagamento entity) {
        id = entity.getId();
        valor = entity.getValor();
        nome = entity.getNome();
        numeroDoCartao = entity.getNumeroDoCartao();
        validade = entity.getValidade();
        codigoDeSeguranca = entity.getCodigoDeSeguranca();
        formaDePagamentoId = entity.getFormaDePagamentoId();
        transactionDate = entity.getTransactionDate();
        descricao = entity.getDescricao();
    }

}
