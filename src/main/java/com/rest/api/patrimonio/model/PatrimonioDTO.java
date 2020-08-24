package com.rest.api.patrimonio.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatrimonioDTO {
    private Long id;
    private String nome;
    private Long marcaId;
    private String descricao;
    private Long numeroTombo;
}
