package com.cet.mock.testing;

import com.cet.Models.Cet;
import com.cet.Repositories.CetRepository;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class CetRepositoryTest {

    @Autowired
    private CetRepository cetRepository;

    @Test
    void saveCet() {
        Cet cet =Cet.builder().id(1L).nombreArchivo("CET_2020-02-01").fechaProceso(new Date()).build();
        Cet newCet = cetRepository.save(cet);
        assertThat(cet.getNombreArchivo().equals(newCet.getNombreArchivo()));
    }

}
