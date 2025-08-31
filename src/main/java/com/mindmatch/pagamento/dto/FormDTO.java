package com.mindmatch.pagamento.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FormDTO {
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    @Schema(description = "Nome de quem efeutou o pagamento pesquisado pelo formulário.")
    private String nome;//
    @Size(min = 16, max = 19, message = "O número deve ter entre 16 e 19")
    @Schema(description = "Numero do cartão que efeutou o pagamento pesquisado pelo formulário.")
    private String numeroDoCartao;
}
