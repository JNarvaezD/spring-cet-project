package com.cet.mock.testing.controllers;

import com.cet.Controllers.InfoCetController;
import com.cet.Models.Cet;
import com.cet.Models.InfoCet;
import com.cet.Services.InfoCetService;
import com.cet.dtos.InfoCetDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = InfoCetController.class)
public class InfoCetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InfoCetService infoCetService;

    @Autowired
    private ObjectMapper objectMapper;

    List<InfoCet> infoCetList = new ArrayList<>();
    private Cet cet;

    @BeforeEach
    void setUp() {
        cet = Cet.builder().id(1L).nombreArchivo("CET_2020-02-01").fechaProceso(LocalDate.now()).build();
        InfoCet infoCet = InfoCet.builder().id(1L)
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

        infoCetList.add(infoCet);
    }

    @Test
    void shouldReturnAllInfoCetRows() throws Exception {
        when(infoCetService.findAll()).thenReturn(infoCetList);
        MvcResult responseMVC = mockMvc.perform(get("/info-cets")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        verify(infoCetService, times(1)).findAll();
        assertEquals(responseMVC.getResponse().getStatus(), 200);
    }

    @Test
    void shouldReturnCreatedInfoCet() throws Exception {
        InfoCet infoCetToReturn = InfoCet.builder().id(2L)
                .numeroCaso("2")
                .fechaDiagnostico(LocalDate.now())
                .bduaAfiliadoId("DCSA234567")
                .tipoId("CC")
                .identificacion("99876543")
                .nombre1("Bryan")
                .apellido1("Doe")
                .fechaNacimiento(LocalDate.now())
                .sexo("M")
                .codEps("CC435")
                .telefonoFijo("3434556")
                .celular("4357890976")
                .covidContacto(1)
                .cetId(cet.getId())
                .fueConfirmado(false)
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
                .noEfectividad(null)
                .idBduaAfConfirmado("DCSA234567")
                .tipoidAfConfirmado("CC")
                .identificacionAfConfirmado("99876543")
                .build();

        InfoCetDto infoCetDto = InfoCetDto.builder().id(infoCetToReturn.getId())
                .numeroCaso(infoCetToReturn.getNumeroCaso())
                .fechaDiagnostico(infoCetToReturn.getFechaDiagnostico())
                .bduaAfiliadoId(infoCetToReturn.getBduaAfiliadoId())
                .tipoId(infoCetToReturn.getTipoId())
                .identificacion(infoCetToReturn.getIdentificacion())
                .nombre1(infoCetToReturn.getNombre1()).nombre2(infoCetToReturn.getNombre2())
                .apellido1(infoCetToReturn.getApellido1()).apellido2(infoCetToReturn.getApellido2())
                .fechaNacimiento(infoCetToReturn.getFechaNacimiento())
                .sexo(infoCetToReturn.getSexo())
                .codEps(infoCetToReturn.getCodEps())
                .telefonoFijo(infoCetToReturn.getTelefonoFijo())
                .celular(infoCetToReturn.getCelular())
                .covidContacto(infoCetToReturn.getCovidContacto())
                .cet(infoCetToReturn.getCetId())
                .fueConfirmado(infoCetToReturn.getFueConfirmado())
                .productoFinanciero(infoCetToReturn.getProductoFinanciero())
                .entidadFinancieraId(null)
                .giroAFamiliar(infoCetToReturn.getGiroAFamiliar())
                .email(infoCetToReturn.getEmail())
                .direccion(infoCetToReturn.getDireccion())
                .codigoDepartamento(infoCetToReturn.getCodigoDepartamento())
                .codigoMunicipio(infoCetToReturn.getCodigoMunicipio())
                .cumpleAislamiento(infoCetToReturn.getCumpleAislamiento())
                .autorizaEps(infoCetToReturn.getAutorizaEps())
                .parentescoId(infoCetToReturn.getParentescoId())
                .compartenGastos(infoCetToReturn.getCompartenGastos())
                .fallecido(infoCetToReturn.getFallecido())
                .localiza(true)
                .build();

        when(infoCetService.update(anyLong(), any())).thenReturn(infoCetToReturn);
        MvcResult responseMVC = mockMvc.perform(put("/info-cets/" + infoCetDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(infoCetDto)))
                .andExpect(status().isOk())
                .andReturn();
        verify(infoCetService, times(1)).update(anyLong(), any());
        assertEquals(responseMVC.getResponse().getStatus(), 200);
        assertEquals(responseMVC.getResponse().getContentAsString(), objectMapper.writeValueAsString(infoCetToReturn));
    }

    @Test
    void givenIdShouldDeleteThatId() throws Exception {
        when(infoCetService.delete(anyLong())).thenReturn(true);
        MvcResult responseMVC = mockMvc.perform(delete("/info-cets/" + 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        verify(infoCetService, times(1)).delete(anyLong());
        assertEquals(200, responseMVC.getResponse().getStatus());
    }

    @Test
    void givenIdThatNotExistsShouldReturnFalse() throws Exception {
        when(infoCetService.delete(3L)).thenReturn(false);
        MvcResult responseMVC = mockMvc.perform(delete("/info-cets/" + 3L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404))
                .andReturn();
        assertEquals(404, responseMVC.getResponse().getStatus());
    }

}
