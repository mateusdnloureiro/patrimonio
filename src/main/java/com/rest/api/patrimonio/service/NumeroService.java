package com.rest.api.patrimonio.service;

import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class NumeroService {


    public Integer getMaiorNumeroFamilia(Integer numero) {
        if (numero == null || numero < 0) {
            throw new IllegalArgumentException("Numero deve ser maior que zero");
        }

        final Integer maiorNumeroFamilia = Integer.valueOf(numero.toString().chars()
                .mapToObj(c -> String.valueOf((char) c))
                .sorted(Comparator.reverseOrder())
                .reduce(String::concat)
                .get());

        return maiorNumeroFamilia > 100000000 ? -1 : maiorNumeroFamilia;
    }
}
