package com.cet.mock.testing.services;

import com.cet.Models.FailedInfoCet;
import com.cet.Models.InfoCet;
import com.cet.Repositories.FailedInfoCetRepository;
import com.cet.Services.FailedInfoCetService;
import com.cet.dtos.FailedInfoCetDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FailedCetServiceTest {

    @Mock
    private FailedInfoCetRepository failedInfoCetRepository;

    @InjectMocks @Autowired
    private FailedInfoCetService failedInfoCetService;

    List<FailedInfoCet> failedInfoCetList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        InfoCet infoCet = InfoCet.builder().id(1L).build();
        FailedInfoCet failedInfoCet = FailedInfoCet.builder().id(1L).infoCet(infoCet).descripcion("Did not connect").build();
        failedInfoCetList.add(failedInfoCet);
    }

    @Test
    void shouldReturnAllFailedInfoCet(){
        when(this.failedInfoCetRepository.findAll()).thenReturn(this.failedInfoCetList);
        List<FailedInfoCet> failedInfoCetsService = this.failedInfoCetService.findAll();
        verify(this.failedInfoCetRepository, times(1)).findAll();
        assertEquals(failedInfoCetsService, failedInfoCetList);
        assertEquals(failedInfoCetsService.size(), failedInfoCetList.size());
    }

    @Test
    void shouldReturnSavedFailedInfoCet(){
        InfoCet infoCet = InfoCet.builder().id(1L).build();
        FailedInfoCetDto failedInfoCetDto = FailedInfoCetDto.builder().id(2L).infoCet(infoCet).descripcion("Wrong number").build();
        FailedInfoCet failedInfoCet = FailedInfoCet.builder().id(failedInfoCetDto.getId()).infoCet(failedInfoCetDto.getInfoCet()).descripcion(failedInfoCetDto.getDescripcion()).build();

        when(this.failedInfoCetRepository.save(any())).thenReturn(failedInfoCet);
        FailedInfoCet failedInfoCetsServiceReturned = this.failedInfoCetService.save(failedInfoCetDto);

        verify(this.failedInfoCetRepository, times(1)).save(any());
        assertEquals(failedInfoCetsServiceReturned, failedInfoCet);
        assertEquals(failedInfoCetsServiceReturned.getId(), failedInfoCet.getId());
    }

}
