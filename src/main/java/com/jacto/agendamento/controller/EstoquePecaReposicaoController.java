package com.jacto.agendamento.controller;

import com.jacto.agendamento.dto.EstoquePecaReposicaoDTO;
import com.jacto.agendamento.entity.EstoquePecaReposicao;
import com.jacto.agendamento.service.EstoquePecaReposicaoService;
import com.jacto.agendamento.service.PecaReposicaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@RestController
@RequestMapping("/api/estoque-peca-reposicao")
public class EstoquePecaReposicaoController {

    @Autowired
    private EstoquePecaReposicaoService service;

    @Autowired
    private PecaReposicaoService pecaService;

    @GetMapping
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public List<EstoquePecaReposicaoDTO> listarTodos() {
        return service.listarTodos()
                      .stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<EstoquePecaReposicaoDTO> buscarPorId(@PathVariable Long id) {
        Optional<EstoquePecaReposicao> opt = service.buscarPorId(id);
        return opt.map(e -> ResponseEntity.ok(convertToDto(e))).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<EstoquePecaReposicaoDTO> salvar(@Valid @RequestBody EstoquePecaReposicaoDTO dto) {
        EstoquePecaReposicao entity = convertToEntity(dto);
        EstoquePecaReposicao salvo = service.salvar(entity);
        return ResponseEntity.ok(convertToDto(salvo));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<EstoquePecaReposicaoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody EstoquePecaReposicaoDTO dto) {
        return service.buscarPorId(id)
            .map(e -> {
                EstoquePecaReposicao updated = convertToEntity(dto);
                updated.setId(e.getId()); // garante o id original
                EstoquePecaReposicao salvo = service.salvar(updated);
                return ResponseEntity.ok(convertToDto(salvo));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (service.buscarPorId(id).isPresent()) {
            service.deletar(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

        // Converte entidade para DTO
    private EstoquePecaReposicaoDTO convertToDto(EstoquePecaReposicao entity) {
        return new EstoquePecaReposicaoDTO(
            entity.getPecaReposicao().getCodigo(),
            entity.getQuantidade(),
            entity.getValor()
        );
    }

    // Converte DTO para entidade
    private EstoquePecaReposicao convertToEntity(EstoquePecaReposicaoDTO dto) {
        EstoquePecaReposicao entity = new EstoquePecaReposicao();

        // Buscar a PecaReposicao pelo código
        pecaService.buscarPorCodigo(dto.getCodigoPecaReposicao())
            .ifPresentOrElse(
                peca -> entity.setPecaReposicao(peca),
                () -> { throw new IllegalArgumentException("Peça de reposição não encontrada com código: " + dto.getCodigoPecaReposicao()); }
            );

        entity.setQuantidade(dto.getQuantidade());
        entity.setValor(dto.getValor());

        return entity;
    }

}