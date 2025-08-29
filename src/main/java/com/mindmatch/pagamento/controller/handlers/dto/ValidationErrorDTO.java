package com.mindmatch.pagamento.controller.handlers.dto;

import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationErrorDTO extends CustomErrorDTO {

    private List<FieldMessageDTO> errors = new ArrayList<>();

    public ValidationErrorDTO(Instant timestamp, Integer status,
                              String error, String path) {
        super(timestamp, status, error, path);
    }
    //metodo para add erros a List
    public void addError(String fieldName, String message) {
        //remove erro de campos duplicados
        errors.removeIf(x -> x.getFieldName().equals(fieldName));
        errors.add(new FieldMessageDTO(fieldName, message));
    }
}
