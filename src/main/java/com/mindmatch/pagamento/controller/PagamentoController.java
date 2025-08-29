package com.mindmatch.pagamento.controller;


import com.mindmatch.pagamento.dto.PagamentoDTO;
import com.mindmatch.pagamento.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pagamentos")
@CrossOrigin(origins = "*") // allow mobile during development; tighten in production
public class PagamentoController {

    @Autowired
    private PagamentoService service;

    @GetMapping
    public ResponseEntity<List<PagamentoDTO>>getAll(){
        List<PagamentoDTO>dto = service.getAll();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDTO>getById(@PathVariable Long id){
        PagamentoDTO dto = service.getById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PagamentoDTO>create(@RequestBody @Valid PagamentoDTO dto){
        dto = service.createPagamento(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDTO>update( @PathVariable Long id, @RequestBody @Valid PagamentoDTO dto){
        dto = service.updatePagamento(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>delete(@PathVariable Long id){
        service.deletePagamento(id);
        return ResponseEntity.noContent().build();
    }

}
