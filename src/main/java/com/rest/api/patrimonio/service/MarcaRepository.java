package com.rest.api.patrimonio.service;

import com.rest.api.patrimonio.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<Marca, Long> {

    Marca findByNome(String nome);

}
