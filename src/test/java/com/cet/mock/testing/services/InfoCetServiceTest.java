package com.cet.mock.testing.services;

import com.cet.Models.Cet;
import com.cet.Models.FailedInfoCet;
import com.cet.Models.InfoCet;
import com.cet.Repositories.FailedInfoCetRepository;
import com.cet.Repositories.InfoCetRepository;
import com.cet.Services.InfoCetService;
import com.cet.dtos.InfoCetDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InfoCetServiceTest {

    @Mock
    private InfoCetRepository infoCetRepository;

    @Mock
    private FailedInfoCetRepository failedInfoCetRepository;

    @InjectMocks @Autowired
    private InfoCetService infoCetService;

    private List<InfoCet> infoCetList = new ArrayList<>();

    private InfoCet infoCetEntity;
    private InfoCetDto infoCetDto;

    @BeforeEach
    void setUp() {
        Cet cet = Cet.builder().id(1L).nombreArchivo("CET_2020-02-01").fechaProceso(LocalDate.now()).build();
        infoCetEntity = InfoCet.builder().id(1L)
                .numeroCaso("1")
                .fechaDiagnostico(LocalDate.now())
                .bduaAfiliadoId("ABC234567")
                .tipoId("CC")
                .identificacion("123456789")
                .nombre1("Carlos")
                .nombre2("Alberto")
                .apellido1("Rojas")
                .apellido2("Torres")
                .fechaNacimiento(LocalDate.now())
                .sexo("M")
                .codEps("CC435")
                .telefonoFijo("6324567")
                .celular("3102764356")
                .covidContacto(1)
                .cetId(cet.getId())
                .fueConfirmado(false)
                .build();

        infoCetDto = InfoCetDto.builder().id(infoCetEntity.getId())
                .numeroCaso(infoCetEntity.getNumeroCaso())
                .fechaDiagnostico(infoCetEntity.getFechaDiagnostico())
                .bduaAfiliadoId(infoCetEntity.getBduaAfiliadoId())
                .tipoId(infoCetEntity.getTipoId())
                .identificacion(infoCetEntity.getIdentificacion())
                .nombre1(infoCetEntity.getNombre1()).nombre2(infoCetEntity.getNombre2())
                .apellido1(infoCetEntity.getApellido1()).apellido2(infoCetEntity.getApellido2())
                .fechaNacimiento(infoCetEntity.getFechaNacimiento())
                .sexo(infoCetEntity.getSexo())
                .codEps(infoCetEntity.getCodEps())
                .telefonoFijo(infoCetEntity.getTelefonoFijo())
                .celular(infoCetEntity.getCelular())
                .covidContacto(infoCetEntity.getCovidContacto())
                .cet(infoCetEntity.getCetId())
                .fueConfirmado(infoCetEntity.getFueConfirmado())
                .productoFinanciero(false)
                .entidadFinancieraId(null)
                .giroAFamiliar(true)
                .email("example@mail.com")
                .direccion("Direccion example")
                .codigoDepartamento("12")
                .codigoMunicipio("345")
                .cumpleAislamiento(true)
                .autorizaEps(false)
                .parentescoId(8)
                .compartenGastos(true)
                .fallecido(false)
                .localiza(true)
                .build();

        infoCetList.add(infoCetEntity);
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
        InfoCet infoCetPayload = infoCetEntity;
        infoCetPayload.setProductoFinanciero(false);
        infoCetPayload.setEntidadFinancieraId(null);
        infoCetPayload.setGiroAFamiliar(true);
        infoCetPayload.setEmail("example@mail.com");
        infoCetPayload.setDireccion("Direccion example");
        infoCetPayload.setCodigoDepartamento("12");
        infoCetPayload.setCodigoMunicipio("345");
        infoCetPayload.setCumpleAislamiento(true);
        infoCetPayload.setAutorizaEps(false);
        infoCetPayload.setParentescoId(8);
        infoCetPayload.setCompartenGastos(true);
        infoCetPayload.setFallecido(false);

        when(infoCetRepository.findOne(anyLong())).thenReturn(Optional.of(infoCetEntity));

        InfoCet infoCetUpdated = infoCetPayload;
        infoCetUpdated.setTipoidAfConfirmado(infoCetEntity.getTipoId());
        infoCetUpdated.setIdentificacionAfConfirmado(infoCetEntity.getIdentificacion());
        infoCetUpdated.setIdBduaAfConfirmado(infoCetEntity.getBduaAfiliadoId());

        when(infoCetRepository.update(any())).thenReturn(infoCetUpdated);
        InfoCet responseService = infoCetService.update(infoCetPayload.getId(), infoCetDto);

        verify(infoCetRepository, times(2)).findOne(anyLong());
        verify(infoCetRepository, times(1)).update(any());
        verify(failedInfoCetRepository, times(0)).save(any());

        assertEquals(infoCetEntity.getBduaAfiliadoId(),responseService.getIdBduaAfConfirmado());
        assertEquals(infoCetEntity.getTipoId(),responseService.getTipoidAfConfirmado());
        assertEquals(infoCetEntity.getIdentificacion(),responseService.getIdentificacionAfConfirmado());
    }

    @Test
    void shouldNotCreateInfoCetBecauseNoLocaliza() {
        InfoCetDto infoCetDtoPayload = infoCetDto;
        infoCetDtoPayload.setLocaliza(false);
        infoCetDtoPayload.setNoEfectividad("No contesta el telefono");

        InfoCet infoCetResponseExpected = infoCetEntity;
        infoCetResponseExpected.setNoEfectividad(infoCetDtoPayload.getNoEfectividad());

        FailedInfoCet failedInfoCet = FailedInfoCet.builder()
                .infocet(infoCetEntity.getId())
                .descripcion(infoCetDto.getNoEfectividad())
                .build();

        when(infoCetRepository.findOne(anyLong())).thenReturn(Optional.of(infoCetResponseExpected));
        when(failedInfoCetRepository.save(any())).thenReturn(failedInfoCet);

        InfoCet responseService = infoCetService.update(infoCetDtoPayload.getId(), infoCetDtoPayload);

        verify(infoCetRepository, times(1)).findOne(anyLong());
        verify(infoCetRepository, times(1)).update(any());
        verify(failedInfoCetRepository, times(1)).save(any());

        assertEquals(responseService.getNoEfectividad(), infoCetDtoPayload.getNoEfectividad());
    }

}
