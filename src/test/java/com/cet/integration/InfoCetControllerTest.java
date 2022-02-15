package com.cet.integration;

import com.cet.CetApplication;
import com.cet.ResponseClasses.InfoCetResponseBody;
import com.cet.Models.InfoCet;
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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

@SpringBootTest(classes = CetApplication.class)
@AutoConfigureMockMvc
class InfoCetControllerTest {

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
    void getData() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/info-cets");
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void givenIdShouldReturnThatId() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/info-cets/" + 1L);
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void givenNotValidIdShouldReturn422() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/info-cets/" + 999);
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals(404, result.getResponse().getStatus());
    }

    @Test
    void givenNotValidIdShouldNotDeleteAndReturn422() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete("/info-cets/" + 999);
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals(404, result.getResponse().getStatus());
    }

    @Test
    void givenIdShouldDeleteThatId() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete("/info-cets/" + 200);
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void givenIdShouldUpdateThatId() throws Exception {
        MvcResult getConfirmado = mvc.perform(MockMvcRequestBuilders
                        .get("/info-cets/" + 1L)).andReturn();

        assertEquals(200, getConfirmado.getResponse().getStatus());

        InfoCet confirmado = obm.readValue(getConfirmado.getResponse().getContentAsString(), InfoCet.class);
        InfoCetDto payload = InfoCetDto.builder().id(confirmado.getId())
                .numeroCaso(confirmado.getNumeroCaso())
                .fechaDiagnostico(confirmado.getFechaDiagnostico())
                .bduaAfiliadoId(confirmado.getBduaAfiliadoId())
                .tipoId(confirmado.getTipoId())
                .identificacion(confirmado.getIdentificacion())
                .nombre1(confirmado.getNombre1())
                .nombre2(confirmado.getNombre2())
                .apellido1(confirmado.getApellido1())
                .apellido2(confirmado.getApellido2())
                .fechaNacimiento(confirmado.getFechaNacimiento())
                .sexo(confirmado.getSexo())
                .codEps(confirmado.getCodEps())
                .telefonoFijo(confirmado.getTelefonoFijo())
                .celular(confirmado.getCelular())
                .covidContacto(confirmado.getCovidContacto())
                .cet(confirmado.getCetId())
                .productoFinanciero(false)
                .entidadFinancieraId(null)
                .giroAFamiliar(true)
                .fallecido(false)
                .fechaExpedicion(null)
                .email("prueba@mail.com")
                .direccion("Calle imaginaria #45-54")
                .codigoDepartamento("01")
                .codigoMunicipio("234")

                .cumpleAislamiento(true)
                .autorizaEps(false)
                .parentescoId(8)
                .compartenGastos(true)

                .updatingInfoCet(false)
                .fueConfirmado(false)
                .localiza(true)
                .build();

        MvcResult updating = mvc.perform(MockMvcRequestBuilders
                .put("/info-cets/" + confirmado.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(obm.writeValueAsString(payload))).andReturn();

        InfoCet response = obm.readValue(updating.getResponse().getContentAsString(), InfoCet.class);
        assertEquals(200, updating.getResponse().getStatus());
        assertEquals(confirmado.getBduaAfiliadoId(), response.getIdBduaAfConfirmado());
        assertEquals(confirmado.getTipoId(), response.getTipoidAfConfirmado());
        assertEquals(confirmado.getIdentificacion(), response.getIdentificacionAfConfirmado());
        assertEquals("1900-01-01", response.getFechaExpedicion().toString());
        assertFalse(response.getFueConfirmado());
        assertEquals(1, response.getCovidContacto());
    }

    @Test
    void updateAndLinkContactoWithConfirmado() throws Exception {
        MvcResult getContacto = mvc.perform(MockMvcRequestBuilders
                .get("/info-cets/" + 2L)).andReturn();

        assertEquals(200, getContacto.getResponse().getStatus());

        InfoCet contacto = obm.readValue(getContacto.getResponse().getContentAsString(), InfoCet.class);
        assertEquals(2, contacto.getId());

        InfoCetDto payload = InfoCetDto.builder().id(contacto.getId())
                .numeroCaso(contacto.getNumeroCaso())
                .fechaDiagnostico(contacto.getFechaDiagnostico())
                .bduaAfiliadoId(contacto.getBduaAfiliadoId())
                .tipoId(contacto.getTipoId())
                .identificacion(contacto.getIdentificacion())
                .nombre1(contacto.getNombre1())
                .nombre2(contacto.getNombre2())
                .apellido1(contacto.getApellido1())
                .apellido2(contacto.getApellido2())
                .fechaNacimiento(contacto.getFechaNacimiento())
                .sexo(contacto.getSexo())
                .codEps(contacto.getCodEps())
                .telefonoFijo(contacto.getTelefonoFijo())
                .celular(contacto.getCelular())
                .covidContacto(contacto.getCovidContacto())
                .cet(contacto.getCetId())
                .productoFinanciero(false)
                .entidadFinancieraId(null)
                .giroAFamiliar(true)
                .fallecido(false)
                .fechaExpedicion(LocalDate.of(2017, 8, 19))
                .email("prueba@mail.com")
                .direccion("Calle imaginaria #45-54")
                .codigoDepartamento("01")
                .codigoMunicipio("234")
                //here goes the link values
                .cumpleAislamiento(true)
                .autorizaEps(true)
                .parentescoId(2)
                .compartenGastos(true)
                //reason of no contact
                .fueConfirmado(false)
                .localiza(true)
                .build();

        MvcResult update = mvc.perform(MockMvcRequestBuilders
                .put("/info-cets/" + 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(obm.writeValueAsString(payload))).andReturn();

        MvcResult getContactoAlreadyLinked = mvc.perform(MockMvcRequestBuilders
                .get("/info-cets/" + 2L)).andReturn();

        InfoCet confirmado = obm.readValue(update.getResponse().getContentAsString(), InfoCet.class);
        InfoCet contactoAlreadyLinked = obm.readValue(getContactoAlreadyLinked.getResponse().getContentAsString(), InfoCet.class);

        assertEquals(200, update.getResponse().getStatus());
        assertEquals("2017-08-19", contactoAlreadyLinked.getFechaExpedicion().toString());
        assertTrue(contactoAlreadyLinked.getFueConfirmado());
        assertEquals(2, contactoAlreadyLinked.getCovidContacto());

        assertEquals(confirmado.getBduaAfiliadoId(), contactoAlreadyLinked.getIdBduaAfConfirmado());
        assertEquals(confirmado.getTipoId(), contactoAlreadyLinked.getTipoidAfConfirmado());
        assertEquals(confirmado.getIdentificacion(), contactoAlreadyLinked.getIdentificacionAfConfirmado());
    }

    @Test
    void updateAndLinkContactoWithConfirmadoFinishedMustUnlinkContactoFromConfirmado() throws Exception {
        MvcResult getContacto = mvc.perform(MockMvcRequestBuilders
                .get("/info-cets/" + 2L)).andReturn();

        assertEquals(200, getContacto.getResponse().getStatus());

        InfoCet contacto = obm.readValue(getContacto.getResponse().getContentAsString(), InfoCet.class);
        assertEquals(2, contacto.getId());

        InfoCetDto payload = InfoCetDto.builder().id(contacto.getId())
                .numeroCaso(contacto.getNumeroCaso())
                .fechaDiagnostico(contacto.getFechaDiagnostico())
                .bduaAfiliadoId(contacto.getBduaAfiliadoId())
                .tipoId(contacto.getTipoId())
                .identificacion(contacto.getIdentificacion())
                .nombre1(contacto.getNombre1())
                .nombre2(contacto.getNombre2())
                .apellido1(contacto.getApellido1())
                .apellido2(contacto.getApellido2())
                .fechaNacimiento(contacto.getFechaNacimiento())
                .sexo(contacto.getSexo())
                .codEps(contacto.getCodEps())
                .telefonoFijo(contacto.getTelefonoFijo())
                .celular(contacto.getCelular())
                .covidContacto(contacto.getCovidContacto())
                .cet(contacto.getCetId())
                .productoFinanciero(false)
                .entidadFinancieraId(null)
                .giroAFamiliar(true)
                .fallecido(false)
                .fechaExpedicion(LocalDate.of(2017, 8, 19))
                .email("prueba@mail.com")
                .direccion("Calle imaginaria #45-54")
                .codigoDepartamento("01")
                .codigoMunicipio("234")
                //here goes the link values
                .cumpleAislamiento(true)
                .autorizaEps(true)
                .parentescoId(2)
                .compartenGastos(true)
                //reason of no contact
                .fueConfirmado(false)
                .localiza(true)
                .build();

        mvc.perform(MockMvcRequestBuilders
                .put("/info-cets/" + 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(obm.writeValueAsString(payload))).andReturn();


        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .put("/info-cets/unlink/contacto/" + 2L + "/confirmado/" + 1L)).andReturn();

        assertEquals(200, result.getResponse().getStatus());

        MvcResult findContacto = mvc.perform(MockMvcRequestBuilders
                .get("/info-cets/" + 2L)).andReturn();
        assertEquals(200, findContacto.getResponse().getStatus());

        InfoCet contactoUnlinked = obm.readValue(findContacto.getResponse().getContentAsString(), InfoCet.class);
        assertFalse(contactoUnlinked.getFueConfirmado());
        assertEquals(1, contactoUnlinked.getCovidContacto());
        assertNull(contactoUnlinked.getIdBduaAfConfirmado());
        assertNull(contactoUnlinked.getTipoidAfConfirmado());
        assertNull(contactoUnlinked.getIdentificacionAfConfirmado());
        assertNull(contactoUnlinked.getProductoFinanciero());
        assertNull(contactoUnlinked.getGiroAFamiliar());
        assertNull(contactoUnlinked.getCumpleAislamiento());
        assertNull(contactoUnlinked.getAutorizaEps());
        assertNull(contactoUnlinked.getParentescoId());
    }

    @Test
    void updatingConfirmadoNotAvaliableShouldCreateFailedInfoCet() throws Exception {
        MvcResult getConfirmado = mvc.perform(MockMvcRequestBuilders
                .get("/info-cets/" + 3L)).andReturn();

        assertEquals(200, getConfirmado.getResponse().getStatus());
        InfoCet confirmado = obm.readValue(getConfirmado.getResponse().getContentAsString(), InfoCet.class);

        InfoCetDto payload = InfoCetDto.builder().id(confirmado.getId())
                .numeroCaso(confirmado.getNumeroCaso())
                .fechaDiagnostico(confirmado.getFechaDiagnostico())
                .bduaAfiliadoId(confirmado.getBduaAfiliadoId())
                .tipoId(confirmado.getTipoId())
                .identificacion(confirmado.getIdentificacion())
                .nombre1(confirmado.getNombre1())
                .nombre2(confirmado.getNombre2())
                .apellido1(confirmado.getApellido1())
                .apellido2(confirmado.getApellido2())
                .fechaNacimiento(confirmado.getFechaNacimiento())
                .sexo(confirmado.getSexo())
                .codEps(confirmado.getCodEps())
                .telefonoFijo(confirmado.getTelefonoFijo())
                .celular(confirmado.getCelular())
                .covidContacto(confirmado.getCovidContacto())
                .cet(confirmado.getCetId())
                .productoFinanciero(false)
                .entidadFinancieraId(null)
                .giroAFamiliar(true)
                .fallecido(false)
                .fechaExpedicion(LocalDate.of(2017, 8, 19))
                .email("prueba@mail.com")
                .direccion("Calle imaginaria #45-54")
                .codigoDepartamento("01")
                .codigoMunicipio("234")
                //here goes the link values
                .cumpleAislamiento(true)
                .autorizaEps(true)
                .parentescoId(2)
                .compartenGastos(true)
                //reason of no contact
                .fueConfirmado(false)
                .localiza(false)
                .noEfectividad("Telefono apagado")
                .build();

        mvc.perform(MockMvcRequestBuilders
                .put("/info-cets/" + payload.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(obm.writeValueAsString(payload))).andReturn();

        MvcResult getConfirmadoNoLocalizado = mvc.perform(MockMvcRequestBuilders
                .get("/info-cets/" + 3L)).andReturn();

        InfoCet confirmadoNoLocalizado = obm.readValue(getConfirmadoNoLocalizado.getResponse().getContentAsString(), InfoCet.class);
        assertEquals("Telefono apagado", confirmadoNoLocalizado.getNoEfectividad());
        assertEquals(1, confirmadoNoLocalizado.getFailedInfoCets().size());
    }

    @Test
    void updatedContactoWithConfirmadoShouldReturnConfirmadoWithContactos() throws Exception {
        MvcResult getContacto = mvc.perform(MockMvcRequestBuilders
                .get("/info-cets/" + 2L)).andReturn();

        assertEquals(200, getContacto.getResponse().getStatus());

        InfoCet contacto = obm.readValue(getContacto.getResponse().getContentAsString(), InfoCet.class);
        assertEquals(2, contacto.getId());

        InfoCetDto payload = InfoCetDto.builder().id(contacto.getId())
                .numeroCaso(contacto.getNumeroCaso())
                .fechaDiagnostico(contacto.getFechaDiagnostico())
                .bduaAfiliadoId(contacto.getBduaAfiliadoId())
                .tipoId(contacto.getTipoId())
                .identificacion(contacto.getIdentificacion())
                .nombre1(contacto.getNombre1())
                .nombre2(contacto.getNombre2())
                .apellido1(contacto.getApellido1())
                .apellido2(contacto.getApellido2())
                .fechaNacimiento(contacto.getFechaNacimiento())
                .sexo(contacto.getSexo())
                .codEps(contacto.getCodEps())
                .telefonoFijo(contacto.getTelefonoFijo())
                .celular(contacto.getCelular())
                .covidContacto(contacto.getCovidContacto())
                .cet(contacto.getCetId())
                .productoFinanciero(false)
                .entidadFinancieraId(null)
                .giroAFamiliar(true)
                .fallecido(false)
                .fechaExpedicion(LocalDate.of(2017, 8, 19))
                .email("prueba@mail.com")
                .direccion("Calle imaginaria #45-54")
                .codigoDepartamento("01")
                .codigoMunicipio("234")
                //here goes the link values
                .cumpleAislamiento(true)
                .autorizaEps(true)
                .parentescoId(2)
                .compartenGastos(true)
                //reason of no contact
                .fueConfirmado(false)
                .localiza(true)
                .build();

        mvc.perform(MockMvcRequestBuilders
                .put("/info-cets/" + 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(obm.writeValueAsString(payload))).andReturn();

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get("/info-cets/show/" + 1L)).andReturn();

        assertEquals(200, result.getResponse().getStatus());
        InfoCetResponseBody infoCet = obm.readValue(result.getResponse().getContentAsString(), InfoCetResponseBody.class);

        assertEquals(1, infoCet.getContatos().size());
    }

}