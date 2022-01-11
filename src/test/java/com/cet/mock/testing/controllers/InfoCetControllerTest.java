package com.cet.mock.testing.controllers;

import com.cet.Controllers.InfoCetController;
import com.cet.Models.Cet;
import com.cet.Models.InfoCet;
import com.cet.Services.InfoCetService;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @BeforeEach
    void setUp() {
        Cet cet = Cet.builder().id(1L).nombreArchivo("CET_2020-02-01").fechaProceso(new Date()).build();
        InfoCet infoCet = InfoCet.builder().id(1L)
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
        infoCetList.add(infoCet);
    }

    @Test
    void shouldReturnAllInfoCetRows() throws Exception {
        when(infoCetService.findAll()).thenReturn(infoCetList);
        MvcResult responseMVC = mockMvc.perform(get("/info-cets")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println("esta es la longitud" + responseMVC.getResponse().getContentAsString());
        verify(infoCetService, times(1)).findAll();
    }

}
