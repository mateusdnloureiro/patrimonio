package com.rest.api.patrimonio;

import com.rest.api.patrimonio.service.NumeroService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NumeroServiceTest {

    @Autowired
    private NumeroService numeroService;


    /**
     * EXERCICIO 1
     */
    @Test
    public void testMaiorNumeroFamilia() {
        Assertions.assertEquals(321, numeroService.getMaiorNumeroFamilia(213));
        Assertions.assertEquals(533, numeroService.getMaiorNumeroFamilia(335));
        Assertions.assertEquals(654321, numeroService.getMaiorNumeroFamilia(123456));
        Assertions.assertEquals(864, numeroService.getMaiorNumeroFamilia(468));

        Assertions.assertEquals(-1, numeroService.getMaiorNumeroFamilia(251489645));
        Assertions.assertEquals(100000000, numeroService.getMaiorNumeroFamilia(100000000));
    }



}
