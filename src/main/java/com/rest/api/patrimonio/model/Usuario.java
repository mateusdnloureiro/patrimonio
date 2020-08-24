package com.rest.api.patrimonio.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario implements ToDTO<UsuarioDTO> {

    public static final ModelMapper mapper = new ModelMapper();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String nome;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    @Column
    private String senha;

    @Override
    public UsuarioDTO toDTO() {
        return mapper.map(this, UsuarioDTO.class);
    }
}
