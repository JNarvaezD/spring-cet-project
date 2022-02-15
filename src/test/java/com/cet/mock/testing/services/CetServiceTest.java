package com.cet.mock.testing.services;

import com.cet.Models.Cet;
import com.cet.Repositories.CetRepository;

import com.cet.Services.CetService;
import com.cet.dtos.CetDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CetServiceTest {

    @Mock
    private CetRepository cetRepository;

    @InjectMocks @Autowired
    private CetService cetService;

    List<Cet> cets = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Cet cet =Cet.builder().id(1L).nombreArchivo("CET_2020-02-01").fechaProceso(LocalDate.now()).build();
        Cet cet2 =Cet.builder().id(2L).nombreArchivo("CET_2020-02-02").fechaProceso(LocalDate.now()).build();
        cets.add(cet);
        cets.add(cet2);
    }

    @Test
    void shouldReturnAllCets() {
        when(cetRepository.findAll()).thenReturn(this.cets);
        List<Cet> cetsService = this.cetService.findAll();
        verify(this.cetRepository, times(1)).findAll();
        assertEquals(cetsService.size(), this.cets.size());
        assertEquals(cetsService, this.cets);
    }

    @Test
    void givenIdshouldReturnThatCet() {
        when(cetRepository.findOne(1L)).thenReturn(Optional.of(this.cets.get(0)));
        Optional<Cet> cet = this.cetService.findOne(1L);
        verify(this.cetRepository, times(1)).findOne(1L);
        assertEquals(cet.get().getId(), this.cets.get(0).getId());
        assertEquals(cet.get(), this.cets.get(0));
    }

    @Test
    void shouldReturnCreatedCet() {
        CetDto cetDto = CetDto.builder().id(3L).nombreArchivo("CET_2020-12-26")
                .fechaProceso(LocalDate.now()).build();
        Cet cet = Cet.builder().id(3L).nombreArchivo(cetDto.getNombreArchivo())
                .fechaProceso(cetDto.getFechaProceso()).build();

        when(cetRepository.save(any())).thenReturn(cet);
        Cet cetService = this.cetService.save(cetDto);

        verify(this.cetRepository, times(1)).save(any());
        assertEquals(cet.getId(), cetService.getId());
        assertEquals(cet, cetService);
    }

}
