package com.cet.mock.testing.services;

import com.cet.Models.Cet;
import com.cet.Models.InfoCet;
import com.cet.Repositories.InfoCetRepository;
import com.cet.Services.InfoCetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class InfoCetServiceTest {

    @Mock
    private InfoCetRepository infoCetRepository;

    @InjectMocks @Autowired
    private InfoCetService infoCetService;

    private List<InfoCet> infoCetList = new ArrayList<>();

    private InfoCet infoCet;

    @BeforeEach
    void setUp() {
        Cet cet =Cet.builder().id(1L).nombreArchivo("CET_2020-02-01").fechaProceso(new Date()).build();
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
                .fallecido(false)
                .codEps("CC435")
                .productoFinanciero(false)
                .entidadFinancieraId(1)
                .giroAFamiliar(false)
                .celular("3102764356")
                .email("example@mail.com")
                .direccion("Calle example 43-23")
                .codigoDepartamento("80")
                .codigoMunicipio("345")
                .cumpleAislamiento(true)
                .autorizaEps(true)
                .parentescoId(8)
                .compartenGastos(true)
                .fueConfirmado(false)
                .noEfectividad(null)
                .cet(cet)
                .build();
    }

    @Test
    void shouldReturnAllInfoCets() {

    }

}
