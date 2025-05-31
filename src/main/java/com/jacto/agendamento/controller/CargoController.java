package com.jacto.agendamento.controller;

import com.jacto.agendamento.controller.dto.CargoDTO;
import com.jacto.agendamento.controller.requests.CargoRequest;
import com.jacto.agendamento.entity.Cargo;
import com.jacto.agendamento.service.CargoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@RestController
@RequestMapping("/api/cargos")
public class CargoController {

    @Autowired
    private CargoService service;

    @GetMapping
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public List<CargoDTO> listarTodos() {
        return service.listarTodos()
                      .stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }

    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<CargoDTO> buscarPorCodigo(@PathVariable Integer codigo) {
        Optional<Cargo> opt = service.buscarPorCodigo(codigo);
        return opt.map(c -> ResponseEntity.ok(convertToDto(c)))
                  .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<CargoDTO> salvar(@Valid @RequestBody CargoRequest request) {
        Cargo entity = convertToEntity(request);
        Cargo salvo = service.salvar(entity);
        return ResponseEntity.ok(convertToDto(salvo));
    }

    @PutMapping("/{codigo}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<CargoDTO> atualizar(@PathVariable Integer codigo, @Valid @RequestBody CargoRequest request) {
        return service.buscarPorCodigo(codigo)
            .map(c -> {
                c.setDescricao(request.getDescricao());
                Cargo atualizado = service.salvar(c);
                return ResponseEntity.ok(convertToDto(atualizado));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{codigo}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<Void> deletar(@PathVariable Integer codigo) {
        if (service.buscarPorCodigo(codigo).isPresent()) {
            service.deletar(codigo);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Converte entidade p/ DTO
    private CargoDTO convertToDto(Cargo entity) {
        return new CargoDTO(entity.getCodigo(), entity.getDescricao());
    }

    // Converte DTO p/ entidade
    private Cargo convertToEntity(CargoRequest request) {
        Cargo entity = new Cargo();
        entity.setCodigo(request.getCodigo());
        entity.setDescricao(request.getDescricao());
        return entity;
    }

}