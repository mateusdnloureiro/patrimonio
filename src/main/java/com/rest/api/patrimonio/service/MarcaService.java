package com.rest.api.patrimonio.service;

import com.rest.api.patrimonio.model.Marca;
import com.rest.api.patrimonio.model.MarcaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.util.StringUtils.isEmpty;

@Service
@Transactional
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    public Marca saveMarca(MarcaDTO dto) {
        if (isEmpty(dto.getNome())) {
            throw new IllegalArgumentException("Nome da marca não pode ser nulo ou vazio!");
        }

        validaInclusaoMarcaDuplicada(dto);

        Marca marca = Optional.ofNullable(dto.getId())
                .map(id -> marcaRepository.findById(id).orElse(new Marca()))
                .orElse(new Marca());

        marca.setNome(dto.getNome());
        return marcaRepository.save(marca);
    }

    private void validaInclusaoMarcaDuplicada(MarcaDTO dto) {
        final Marca marca = marcaRepository.findByNome(dto.getNome());
        if (marca != null && !marca.getId().equals(dto.getId())) {
            throw new IllegalArgumentException("Já existe uma Marca com o mesmo nome cadastrado na base de dados!");
        }
    }
}
