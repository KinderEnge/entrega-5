package com.mindmatch.pagamento.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")

@Entity
@Table(name = "tb_pagamento")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private BigDecimal valor;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String numeroDoCartao;
    @Column(nullable = false)
    private String validade;
    @Column(nullable = false)
    private String codigoDeSeguranca;
    @Column(nullable = false)
    private Long formaDePagamentoId; //1 - crédito | 2 -débito
    @Column(name = "transaction_date")
    private LocalDate transactionDate;
    @Column(length = 255)
    private String descricao;
}
