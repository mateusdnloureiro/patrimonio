package com.rest.api.patrimonio.controller;

import com.rest.api.patrimonio.model.Marca;
import com.rest.api.patrimonio.model.MarcaDTO;
import com.rest.api.patrimonio.service.MarcaRepository;
import com.rest.api.patrimonio.service.MarcaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/marcas")
public class MarcaController {

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private MarcaService marcaService;

    @GetMapping
    @ResponseBody
    public List<MarcaDTO> findAll() {
        try {
            return marcaRepository
                    .findAll()
                    .stream()
                    .map(Marca::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Falha ao listar marcas", e);
            throw e;
        }
    }

    @PostMapping(path = "/save")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public MarcaDTO saveMarca(@RequestBody MarcaDTO marcaDTO) {
        try {
            return marcaService.saveMarca(marcaDTO).toDTO();
        } catch (Exception e) {
            log.error("Erro ao criar a marca", e);
            throw e;
        }
    }

    @GetMapping(path = "/{id}")
    @ResponseBody
    public MarcaDTO findMarca(@PathVariable("id") Long id) {
        try {
            return marcaRepository.findById(id).map(Marca::toDTO).orElse(null);
        } catch (Exception e) {
            log.error("Falha ao listar a marca de id" + id, e);
            throw e;
        }
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMarca(@PathVariable("id") Long id) {
        try {
            marcaRepository.findById(id)
                    .ifPresent(m -> marcaRepository.delete(m));
        } catch (Exception e) {
            log.error("Erro ao deletar a marca", e);
            throw e;
        }
    }

}
