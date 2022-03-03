package com.cet.integration;

import com.cet.CetApplication;
import com.cet.Models.InfoCet;
import com.cet.ResponseClasses.InfoCetResponseBody;
import com.cet.dtos.InfoCetDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

@SpringBootTest(classes = CetApplication.class)
@AutoConfigureMockMvc
class GenerateCetFileTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper obm;

    @Test
    void firstActivityToRun() throws Exception {
        Path getFile = Paths.get("C:\\Users\\susje\\Downloads\\files\\CCF033COVID15012021.TXT");
        String content = Files.readString(getFile, StandardCharsets.ISO_8859_1);
        MockMultipartFile file = new MockMultipartFile(
                "file",
                getFile.toFile().getName(),
                MediaType.TEXT_PLAIN_VALUE,
                content.getBytes()
        );

        MvcResult result = mvc.perform(multipart("/cets/upload-data").file(file)).andReturn();
        assertEquals(201, result.getResponse().getStatus());
    }

    @Test
    void generatingFile() throws Exception {
        MvcResult getContacto = mvc.perform(MockMvcRequestBuilders
                .get("/info-cets/" + 2L)).andReturn();

        MvcResult getConfirmado = mvc.perform(MockMvcRequestBuilders
                .get("/info-cets/" + 1L)).andReturn();

        InfoCet contacto = obm.readValue(getContacto.getResponse().getContentAsString(), InfoCet.class);
        InfoCet confirmado = obm.readValue(getConfirmado.getResponse().getContentAsString(), InfoCet.class);

        InfoCetDto payloadContacto = InfoCetDto.builder().id(contacto.getId()).numeroCaso(contacto.getNumeroCaso())
                .fechaDiagnostico(contacto.getFechaDiagnostico()).bduaAfiliadoId(contacto.getBduaAfiliadoId())
                .tipoId(contacto.getTipoId()).identificacion(contacto.getIdentificacion()).nombre1(contacto.getNombre1())
                .nombre2(contacto.getNombre2()).apellido1(contacto.getApellido1()).apellido2(contacto.getApellido2())
                .fechaNacimiento(contacto.getFechaNacimiento()).sexo(contacto.getSexo()).codEps(contacto.getCodEps())
                .telefonoFijo(contacto.getTelefonoFijo()).celular(contacto.getCelular()).covidContacto(contacto.getCovidContacto())
                .cet(contacto.getCetId()).productoFinanciero(false).entidadFinancieraId(null).giroAFamiliar(true).fallecido(false)
                .fechaExpedicion(LocalDate.of(2017, 8, 19)).email("prueba@mail.com").direccion("Calle imaginaria #45-54")
                .codigoDepartamento("01").codigoMunicipio("234").cumpleAislamiento(true).autorizaEps(true).parentescoId(2)
                .compartenGastos(true).fueConfirmado(false).localiza(true).build();

        InfoCetDto payloadConfirmado = InfoCetDto.builder().id(confirmado.getId()).numeroCaso(confirmado.getNumeroCaso())
                .fechaDiagnostico(confirmado.getFechaDiagnostico()).bduaAfiliadoId(confirmado.getBduaAfiliadoId())
                .tipoId(confirmado.getTipoId()).identificacion(confirmado.getIdentificacion()).nombre1(confirmado.getNombre1())
                .nombre2(confirmado.getNombre2()).apellido1(confirmado.getApellido1()).apellido2(confirmado.getApellido2())
                .fechaNacimiento(confirmado.getFechaNacimiento()).sexo(confirmado.getSexo()).codEps(confirmado.getCodEps())
                .telefonoFijo(confirmado.getTelefonoFijo()).celular(confirmado.getCelular()).covidContacto(confirmado.getCovidContacto())
                .cet(confirmado.getCetId()).productoFinanciero(false).entidadFinancieraId(null).giroAFamiliar(true).fallecido(false)
                .fechaExpedicion(LocalDate.of(2017, 8, 19)).email("prueba@mail.com").direccion("Calle imaginaria #45-54")
                .codigoDepartamento("01").codigoMunicipio("234").cumpleAislamiento(true).autorizaEps(true).parentescoId(2).compartenGastos(true)
                .fueConfirmado(false).localiza(true).build();

        mvc.perform(MockMvcRequestBuilders.put("/info-cets/" + payloadConfirmado.getId())
                .contentType(MediaType.APPLICATION_JSON).content(obm.writeValueAsString(payloadContacto))).andReturn();

        mvc.perform(MockMvcRequestBuilders.put("/info-cets/" + payloadConfirmado.getId())
                .contentType(MediaType.APPLICATION_JSON).content(obm.writeValueAsString(payloadConfirmado))).andReturn();

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/info-cets/show/" + 1L)).andReturn();

        assertEquals(200, result.getResponse().getStatus());
        InfoCetResponseBody infoCet = obm.readValue(result.getResponse().getContentAsString(), InfoCetResponseBody.class);
        assertEquals(2, infoCet.getContatos().size());


        //Here, we link a confirmed user to himself to test that he will be in the file
        MvcResult getConfirmado2 = mvc.perform(MockMvcRequestBuilders
                .get("/info-cets/" + 3L)).andReturn();

        InfoCet confirmado2 = obm.readValue(getConfirmado2.getResponse().getContentAsString(), InfoCet.class);
        InfoCetDto payloadConfirmado2 = InfoCetDto.builder().id(confirmado2.getId()).numeroCaso(confirmado2.getNumeroCaso())
                .fechaDiagnostico(confirmado2.getFechaDiagnostico()).bduaAfiliadoId(confirmado2.getBduaAfiliadoId())
                .tipoId(confirmado2.getTipoId()).identificacion(confirmado2.getIdentificacion()).nombre1(confirmado2.getNombre1())
                .nombre2(confirmado2.getNombre2()).apellido1(confirmado2.getApellido1()).apellido2(confirmado2.getApellido2())
                .fechaNacimiento(confirmado2.getFechaNacimiento()).sexo(confirmado2.getSexo()).codEps(confirmado2.getCodEps())
                .telefonoFijo(confirmado2.getTelefonoFijo()).celular(confirmado2.getCelular()).covidContacto(confirmado2.getCovidContacto())
                .cet(confirmado2.getCetId()).productoFinanciero(false).entidadFinancieraId(null).giroAFamiliar(false).fallecido(false)
                .fechaExpedicion(LocalDate.of(2017, 8, 19)).email("prueba@mail.com").direccion("Calle imaginaria #45-54")
                .codigoDepartamento("01").codigoMunicipio("234").cumpleAislamiento(true).autorizaEps(true).parentescoId(2)
                .compartenGastos(false).fueConfirmado(false).localiza(true).build();

        MvcResult resultConfirmado2 = mvc.perform(MockMvcRequestBuilders.put("/info-cets/" + payloadConfirmado2.getId())
                .contentType(MediaType.APPLICATION_JSON).content(obm.writeValueAsString(payloadConfirmado2))).andReturn();
        InfoCetResponseBody infoCetConfirmado2 = obm.readValue(resultConfirmado2.getResponse().getContentAsString(), InfoCetResponseBody.class);
        assertEquals("1030241699", infoCetConfirmado2.getIdentificacionAfConfirmado());
        assertEquals("TI", infoCetConfirmado2.getTipoidAfConfirmado());

        MvcResult getDataForReporte = mvc.perform(MockMvcRequestBuilders
                .get("/info-cets/generate-cet-file/" + 1L)).andReturn();
    }

}
