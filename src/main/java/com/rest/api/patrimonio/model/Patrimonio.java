package com.rest.api.patrimonio.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "patrimonio")
public class Patrimonio implements ToDTO<PatrimonioDTO>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @NotNull
    @Column
    private String nome;

    @Setter
    @NotNull
    @ManyToOne
    @JoinColumn(name = "marca_id", foreignKey = @ForeignKey(name = "patrimonio_marca_fk"))
    private Marca marca;

    @Setter
    @Column
    private String descricao;

    @NotNull
    @Generated(GenerationTime.INSERT)
    @Column(name = "numero_tombo")
    private Long numeroTombo;

    @Override
    public PatrimonioDTO toDTO() {
        return mapper.map(this, PatrimonioDTO.class);
    }
}
