package com.mindmatch.pagamento.service;

import com.mindmatch.pagamento.dto.FormDTO;
import com.mindmatch.pagamento.dto.PagamentoDTO;
import com.mindmatch.pagamento.entities.Pagamento;
import com.mindmatch.pagamento.repositories.PagamentoRepository;
import com.mindmatch.pagamento.repositories.specification.PagamentoSpecification;
import com.mindmatch.pagamento.service.exceptions.DatabaseException;
import com.mindmatch.pagamento.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository repository;


    @Transactional(readOnly=true)
    public List<PagamentoDTO> getAll(){
        List<Pagamento>pagamentos = repository.findAll();
        return pagamentos.stream().map(PagamentoDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly=true)
    public PagamentoDTO getById(Long id){
        Pagamento entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado. ID: "+ id));
        return new PagamentoDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<PagamentoDTO> getByForm(FormDTO formDTO) {
        if(formDTO.getNome() == null && formDTO.getNumeroDoCartao() == null) {
            throw new DatabaseException("Ao menos um dos campos deve ser preenchido");
        }

        Specification<Pagamento> specification = PagamentoSpecification.formFiltro(formDTO);

        return repository.findAll(specification)
                .stream().map(PagamentoDTO::new)
                .toList();
    }

    @Transactional
    public PagamentoDTO createPagamento(PagamentoDTO dto){
        Pagamento entity = new Pagamento();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new PagamentoDTO(entity);
    }

    @Transactional
    public PagamentoDTO updatePagamento(Long id, PagamentoDTO dto){
        try{
            Pagamento entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new PagamentoDTO(entity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Recuso não encontrado. ID: "+id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deletePagamento(Long id){
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado. Id: "+id);
        }
        try{
            repository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    private void copyDtoToEntity(PagamentoDTO dto, Pagamento entity) {
        entity.setValor(dto.getValor());
        entity.setNome(dto.getNome());
    entity.setNumeroDoCartao(dto.getNumeroDoCartao());
    entity.setValidade(dto.getValidade());
    entity.setCodigoDeSeguranca(dto.getCodigoDeSeguranca());
    entity.setFormaDePagamentoId(dto.getFormaDePagamentoId());
    entity.setTransactionDate(dto.getTransactionDate());
    entity.setDescricao(dto.getDescricao());
    }
}
