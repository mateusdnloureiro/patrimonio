package com.rest.api.patrimonio.service;

import com.rest.api.patrimonio.model.Usuario;
import com.rest.api.patrimonio.model.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.util.StringUtils.isEmpty;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario saveUsuario(UsuarioDTO dto) {
        if (isEmpty(dto.getEmail())
                || isEmpty(dto.getNome())
                || isEmpty(dto.getSenha())) {
            throw new IllegalArgumentException("Existem dados inválidos para criação do usuário, confira se o nome, email e senha estão corretamente informados!");
        }

        validaInclusaoEmailDuplicado(dto);

        Usuario usuario = Optional.ofNullable(dto.getId())
                .map(id -> usuarioRepository.findById(id).orElse(new Usuario()))
                .orElse(new Usuario());

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        return usuarioRepository.save(usuario);
    }

    private void validaInclusaoEmailDuplicado(UsuarioDTO dto) {
        final Usuario usuario = usuarioRepository.findByEmail(dto.getEmail());
        if (usuario != null && !usuario.getId().equals(dto.getId())) {
            throw new IllegalArgumentException("Já existe um usuário com o mesmo email cadastrado no banco de dados!");
        }
    }
}
