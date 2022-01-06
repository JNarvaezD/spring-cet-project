package com.cet.mock.testing.services;

import com.cet.Models.Cet;
import com.cet.Models.InfoCet;
import com.cet.Repositories.InfoCetRepository;
import com.cet.Services.InfoCetService;
import com.cet.dtos.InfoCetDto;
import com.cet.utils.InfoCetUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InfoCetServiceTest {

    @Mock
    private InfoCetRepository infoCetRepository;

    @InjectMocks @Autowired
    private InfoCetService infoCetService;

    private List<InfoCet> infoCetList = new ArrayList<>();

    private InfoCet infoCet;
    private InfoCetDto infoCetDto;

    @BeforeEach
    void setUp() {
        Cet cet = Cet.builder().id(1L).nombreArchivo("CET_2020-02-01").fechaProceso(new Date()).build();
        infoCet = InfoCet.builder().id(1L)
                .numeroCaso("1")
                .fechaDiagnostico(new Date(1900, Calendar.JANUARY, 1))
                .bduaAfiliadoId("ABC234567")
                .tipoId("CC")
                .identificacion("123456789")
                .nombre1("Carlos")
                .nombre2("Alberto")
                .apellido1("Rojas")
                .apellido2("Torres")
                .fechaNacimiento(new Date(1999, Calendar.JANUARY, 1))
                .sexo("M")
                .codEps("CC435")
                .telefonoFijo("6324567")
                .celular("3102764356")
                .covidContacto(1)
                .cet(cet)
                .fueConfirmado(false)
                .build();

        infoCetDto = InfoCetDto.builder().id(infoCet.getId())
                .numeroCaso(infoCet.getNumeroCaso())
                .fechaDiagnostico(infoCet.getFechaDiagnostico())
                .bduaAfiliadoId(infoCet.getBduaAfiliadoId())
                .tipoId(infoCet.getTipoId())
                .identificacion(infoCet.getIdentificacion())
                .nombre1(infoCet.getNombre1()).nombre2(infoCet.getNombre2())
                .apellido1(infoCet.getApellido1()).apellido2(infoCet.getApellido2())
                .fechaNacimiento(infoCet.getFechaNacimiento())
                .sexo(infoCet.getSexo())
                .codEps(infoCet.getCodEps())
                .telefonoFijo(infoCet.getTelefonoFijo())
                .celular(infoCet.getCelular())
                .covidContacto(infoCet.getCovidContacto())
                .cet(infoCet.getCet())
                .fueConfirmado(infoCet.getFueConfirmado())
                .build();

        infoCetList.add(infoCet);
    }

    @Test
    void shouldReturnAllInfoCets() {
        when(infoCetRepository.findAll()).thenReturn(infoCetList);
        List<InfoCet> infoCetListService = infoCetService.findAll();
        verify(infoCetRepository, times(1)).findAll();
        assertEquals(infoCetListService.size(), this.infoCetList.size());
        assertEquals(infoCetListService, infoCetList);
    }

    @Test
    void shouldUpdateRowAndLinkToHimself() {
        InfoCetDto infoCetDtoToUpdate = infoCetDto;
        infoCetDtoToUpdate.setProductoFinanciero(false);
        infoCetDtoToUpdate.setEntidadFinancieraId(null);
        infoCetDtoToUpdate.setGiroAFamiliar(true);
        //infoCetDtoToUpdate.setFechaExpedicion(infoCetDto.getFechaExpedicion());
        infoCetDtoToUpdate.setEmail("example@mail.com");
        infoCetDtoToUpdate.setDireccion("Direccion example");
        infoCetDtoToUpdate.setCodigoDepartamento("12");
        infoCetDtoToUpdate.setCodigoMunicipio("345");
        infoCetDtoToUpdate.setCumpleAislamiento(true);
        infoCetDtoToUpdate.setAutorizaEps(false);
        infoCetDtoToUpdate.setParentescoId(8);
        infoCetDtoToUpdate.setCompartenGastos(true);
        infoCetDtoToUpdate.setFallecido(false);
        infoCetDtoToUpdate.setLocaliza(true);

        InfoCet infoCetUpdatedExpected = infoCet;
        infoCetUpdatedExpected.setProductoFinanciero(false);
        infoCetUpdatedExpected.setEntidadFinancieraId(null);
        infoCetUpdatedExpected.setGiroAFamiliar(true);
        //infoCetUpdatedExpected.setFechaExpedicion(infoCetDto.getFechaExpedicion());
        infoCetUpdatedExpected.setEmail("example@mail.com");
        infoCetUpdatedExpected.setDireccion("Direccion example");
        infoCetUpdatedExpected.setCodigoDepartamento("12");
        infoCetUpdatedExpected.setCodigoMunicipio("345");
        infoCetUpdatedExpected.setCumpleAislamiento(true);
        infoCetUpdatedExpected.setAutorizaEps(false);
        infoCetUpdatedExpected.setParentescoId(8);
        infoCetUpdatedExpected.setCompartenGastos(true);
        infoCetUpdatedExpected.setFallecido(false);

        InfoCetUtils.setCovidContactoAndFueConfirmado(infoCetUpdatedExpected.getCovidContacto(), infoCetUpdatedExpected.getFueConfirmado());
        infoCetUpdatedExpected.setFueConfirmado(InfoCetUtils.getFueConfirmado());
        infoCetUpdatedExpected.setCovidContacto(InfoCetUtils.getCovidContacto());

        when(infoCetRepository.findOne(anyLong())).thenReturn(Optional.of(infoCet));
        InfoCet infoCetUpdated = infoCetService.update(infoCet.getId(), infoCetDto);
        verify(infoCetRepository, times(2)).findOne(anyLong());
        assertEquals(infoCet.getId(), infoCetDtoToUpdate.getId());
        assertEquals(1, infoCetDtoToUpdate.getCovidContacto());
        assertFalse(infoCetUpdatedExpected.getFueConfirmado());
        assertEquals(1, infoCetUpdatedExpected.getCovidContacto());
    }

}
