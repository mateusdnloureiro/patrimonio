package com.rest.api.patrimonio.service;

import com.rest.api.patrimonio.model.Patrimonio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatrimonioRepository extends JpaRepository<Patrimonio, Long> {

    Patrimonio findByNome(String nome);

}
