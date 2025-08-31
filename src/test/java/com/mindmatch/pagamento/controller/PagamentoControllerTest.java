package com.mindmatch.pagamento.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindmatch.pagamento.dto.PagamentoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PagamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getPagamentos_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/pagamentos")).andExpect(status().isOk());
    }

    @Test
    void postPagamento_requiresAuth() throws Exception {
    PagamentoDTO dto = new PagamentoDTO(null, BigDecimal.valueOf(10.0), "User Test", "4111111111111111", "12/25", "123", 1L, LocalDate.now(), "Teste");
        mockMvc.perform(post("/pagamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized());

        // with basic auth (manager) should be allowed
        mockMvc.perform(post("/pagamentos")
                .with(httpBasic("manager","!L3tm3iN!"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }
}
