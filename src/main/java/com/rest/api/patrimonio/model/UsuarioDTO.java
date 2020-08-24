package com.rest.api.patrimonio.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String token;
}
