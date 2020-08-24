package com.rest.api.patrimonio.service;

import com.rest.api.patrimonio.model.Marca;
import com.rest.api.patrimonio.model.Patrimonio;
import com.rest.api.patrimonio.model.PatrimonioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.util.StringUtils.isEmpty;

@Service
public class PatrimonioService {

    @Autowired
    private PatrimonioRepository patrimonioRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    public Patrimonio savePatrimonio(PatrimonioDTO dto) {
        if (dto.getMarcaId() == null
                || isEmpty(dto.getNome())) {
            throw new IllegalArgumentException("Existem dados inválidos para criação do patrimonio, confira se a marca e nome estão corretamente informados!");
        }

        final Marca marca = marcaRepository.findById(dto.getMarcaId()).orElse(null);
        if (marca == null) {
            throw new IllegalArgumentException("ID da marca não encontrada no banco de dados.");
        }

        Patrimonio patrimonio = Optional.ofNullable(dto.getId())
                .map(id -> patrimonioRepository.findById(id).orElse(new Patrimonio()))
                .orElse(new Patrimonio());

        patrimonio.setNome(dto.getNome());
        patrimonio.setMarca(marca);
        patrimonio.setDescricao(dto.getDescricao());

        return patrimonioRepository.save(patrimonio);
    }

}
