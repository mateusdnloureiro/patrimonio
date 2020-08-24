package com.rest.api.patrimonio.controller;

import com.rest.api.patrimonio.model.Usuario;
import com.rest.api.patrimonio.model.UsuarioDTO;
import com.rest.api.patrimonio.service.UsuarioRepository;
import com.rest.api.patrimonio.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @ResponseBody
    public List<UsuarioDTO> listar() {
        try {
            return usuarioRepository
                    .findAll()
                    .stream()
                    .map(Usuario::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Falha ao listar usuarios", e);
            throw e;
        }
    }

    @GetMapping(path = "/{id}")
    @ResponseBody
    public UsuarioDTO find(@PathVariable("id") Long id) {
        try {
            return usuarioRepository.findById(id).map(Usuario::toDTO).orElse(null);
        } catch (Exception e) {
            log.error("Falha ao listar o usuario de id" + id, e);
            throw e;
        }
    }

    @PostMapping(path = "/save")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UsuarioDTO createUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            return usuarioService.saveUsuario(usuarioDTO).toDTO();
        } catch (Exception e) {
            log.error("Erro ao criar usuario", e);
            throw e;
        }
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUsuario(@PathVariable("id") Long id) {
        try {
            usuarioRepository.findById(id)
                    .ifPresent(u -> usuarioRepository.delete(u));
        } catch (Exception e) {
            log.error("Erro ao deletar o usuário", e);
            throw e;
        }
    }


}
