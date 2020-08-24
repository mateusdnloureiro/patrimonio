package com.rest.api.patrimonio.model;

import org.modelmapper.ModelMapper;

public interface ToDTO<T> {

    ModelMapper mapper = new ModelMapper();

    T toDTO();
}
