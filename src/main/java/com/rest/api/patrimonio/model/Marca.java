package com.rest.api.patrimonio.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "marca")
public class Marca implements ToDTO<MarcaDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome;

    @Override
    public MarcaDTO toDTO() {
        return mapper.map(this, MarcaDTO.class);
    }
}
