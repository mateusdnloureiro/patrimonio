package com.rest.api.patrimonio.controller;

import com.rest.api.patrimonio.model.Patrimonio;
import com.rest.api.patrimonio.model.PatrimonioDTO;
import com.rest.api.patrimonio.service.PatrimonioRepository;
import com.rest.api.patrimonio.service.PatrimonioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/patrimonios")
public class PatrimonioController {

    @Autowired
    private PatrimonioService patrimonioService;

    @Autowired
    private PatrimonioRepository patrimonioRepository;


    @GetMapping
    @ResponseBody
    public List<PatrimonioDTO> findAll() {
        try {
            return patrimonioRepository
                    .findAll()
                    .stream()
                    .map(Patrimonio::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Falha ao listar os patrimonios", e);
            throw e;
        }
    }

    @GetMapping(path = "/{id}")
    @ResponseBody
    public PatrimonioDTO find(@PathVariable("id") Long id) {
        try {
            return patrimonioRepository.findById(id).map(Patrimonio::toDTO).orElse(null);
        } catch (Exception e) {
            log.error("Falha ao listar o patrimonio de id" + id, e);
            throw e;
        }
    }

    @PostMapping(path = "/save")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public PatrimonioDTO createPatrimonio(@RequestBody PatrimonioDTO dto) {
        try {
            return patrimonioService.savePatrimonio(dto).toDTO();
        } catch (Exception e) {
            log.error("Erro ao criar patrimonio", e);
            throw e;
        }
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePatrimonio(@PathVariable("id") Long id) {
        try {
            patrimonioRepository.findById(id)
                    .ifPresent(u -> patrimonioRepository.delete(u));
        } catch (Exception e) {
            log.error("Erro ao deletar o patrimonio", e);
            throw e;
        }
    }
}
