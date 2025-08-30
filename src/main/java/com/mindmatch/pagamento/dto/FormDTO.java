package com.mindmatch.pagamento.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FormDTO {
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    private String nome;//
    @Size(min = 16, max = 19, message = "O número deve ter entre 16 e 19")
    private String numeroDoCartao;
}
