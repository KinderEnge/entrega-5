package com.mindmatch.pagamento.controller;


import com.mindmatch.pagamento.dto.FormDTO;
import com.mindmatch.pagamento.dto.PagamentoDTO;
import com.mindmatch.pagamento.service.PagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pagamentos")
@CrossOrigin(origins = "*") // allow mobile during development; tighten in production
@Tag(name = "Pagamento", description = "Controller para pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService service;

    @Operation(
            description = "Listar pagamentos",
            summary = "Retorna uma lista com todos os pagamentos registrados.",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200")
            }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PagamentoDTO>>getAll(){
        List<PagamentoDTO>dto = service.getAll();
        return ResponseEntity.ok(dto);
    }

    @Operation(
            description = "Retorna um pagamento a partir do seu identidficador (id)",
            summary = "Consulta um pagamento por id",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(description = "Not Found", responseCode = "404"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401")
            }
    )
    @GetMapping(value = "/{id:[0-9]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagamentoDTO>getById(@PathVariable Long id){
        PagamentoDTO dto = service.getById(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            description = "Consulta todos os pagementos que correspondem ao furmulário",
            summary = "Consulta todos os pagamentos de acordo com os campos do formulário",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401")
            }
    )
    @PostMapping(value = "/forms", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PagamentoDTO>> getByForms(@RequestBody @Valid FormDTO formDTO) {
        List<PagamentoDTO> result = service.getByForm(formDTO);
        return ResponseEntity.ok(result); 
    }

    @Operation(
            description = "Cria um novo pagamento",
            summary = "Salva um novo pagamento.",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201"),
                    @ApiResponse(description = "Unprocessable Entity", responseCode = "422"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401")
            }
    )
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagamentoDTO>create(@RequestBody @Valid PagamentoDTO dto){
        dto = service.createPagamento(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @Operation(
            description = "Atualiza um pagamento apartir do identificador (id)",
            summary = "Atualiza o pagamento pelo id.",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(description = "Unprocessable Entity", responseCode = "422"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            }
    )
    @PutMapping(value = "/{id:[0-9]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagamentoDTO>update( @PathVariable Long id, @RequestBody @Valid PagamentoDTO dto){
        dto = service.updatePagamento(id, dto);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            description = "Apaga um pagamento apartir do identificador (id)",
            summary = "Deleta o pagamento pelo id.",
            responses = {
                    @ApiResponse(description = "No Contert", responseCode = "204"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            }
    )
    @DeleteMapping(value = "/{id:[0-9]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void>delete(@PathVariable Long id){
        service.deletePagamento(id);
        return ResponseEntity.noContent().build();
    }

}
//