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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CetApplication.class)
@AutoConfigureMockMvc
class InfoCetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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

        mockMvc.perform(multipart("/cets/upload-file").file(file))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void getData() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/info-cets");
        mockMvc.perform(request).andExpect(status().isOk()).andReturn();
    }

    @Test
    void givenIdShouldReturnThatId() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/info-cets/" + 1L);
        mockMvc.perform(request).andExpect(status().isOk()).andReturn();
    }

    @Test
    void givenNotValidIdShouldReturn404() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/info-cets/" + 999);
        mockMvc.perform(request).andExpect(status().isNotFound()).andReturn();
    }

    @Test
    void givenNotValidIdShouldNotDeleteAndReturn404() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete("/info-cets/" + 999);
        mockMvc.perform(request).andExpect(status().isNotFound()).andReturn();
    }

    @Test
    void givenIdShouldDeleteThatId() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete("/info-cets/" + 200);
        mockMvc.perform(request).andExpect(status().isOk()).andReturn();
    }

    @Test
    void givenIdShouldUpdateThatId() throws Exception {
        MvcResult getConfirmado = mockMvc.perform(MockMvcRequestBuilders.get("/info-cets/" + 1L))
                .andExpect(status().isOk()).andReturn();

        InfoCet confirmado = objectMapper.readValue(getConfirmado.getResponse().getContentAsString(), InfoCet.class);
        InfoCetDto payload = returnInfoCetDto(confirmado);
        payload.setFechaExpedicion(null);

        MvcResult updating = mockMvc.perform(MockMvcRequestBuilders
                .put("/info-cets/" + confirmado.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andReturn();

        InfoCet response = objectMapper.readValue(updating.getResponse().getContentAsString(), InfoCet.class);

        assertEquals(confirmado.getBduaAfiliadoId(), response.getIdBduaAfConfirmado());
        assertEquals(confirmado.getTipoId(), response.getTipoidAfConfirmado());
        assertEquals(confirmado.getIdentificacion(), response.getIdentificacionAfConfirmado());
        assertEquals("1900-01-01", response.getFechaExpedicion().toString());
        assertFalse(response.getFueConfirmado());
        assertEquals(1, response.getCovidContacto());
    }

    @Test
    void updateAndLinkContactoWithConfirmado() throws Exception {
        MvcResult getContacto = mockMvc.perform(MockMvcRequestBuilders.get("/info-cets/" + 2L))
                .andExpect(status().isOk()).andReturn();

        InfoCet contacto = objectMapper.readValue(getContacto.getResponse().getContentAsString(), InfoCet.class);
        assertEquals(2, contacto.getId());

        InfoCetDto payload = returnInfoCetDto(contacto);

        //In this line, the payload of the contact is sent with the ID of the confirmed user so that the contact will be linked with the confirmed user
        MvcResult update = mockMvc.perform(MockMvcRequestBuilders
                .put("/info-cets/" + 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andReturn();

        MvcResult getContactoAlreadyLinked = mockMvc.perform(MockMvcRequestBuilders.get("/info-cets/" + 2L)).andReturn();

        InfoCet confirmado = objectMapper.readValue(update.getResponse().getContentAsString(), InfoCet.class);
        InfoCet contactoAlreadyLinked = objectMapper.readValue(getContactoAlreadyLinked.getResponse().getContentAsString(), InfoCet.class);

        assertEquals("2017-08-19", contactoAlreadyLinked.getFechaExpedicion().toString());
        assertTrue(contactoAlreadyLinked.getFueConfirmado());
        assertEquals(2, contactoAlreadyLinked.getCovidContacto());
        assertEquals(confirmado.getBduaAfiliadoId(), contactoAlreadyLinked.getIdBduaAfConfirmado());
        assertEquals(confirmado.getTipoId(), contactoAlreadyLinked.getTipoidAfConfirmado());
        assertEquals(confirmado.getIdentificacion(), contactoAlreadyLinked.getIdentificacionAfConfirmado());
    }

    @Test
    void updateAndLinkContactoWithConfirmadoHasFinishedNowShouldUnlinkContactoFromConfirmado() throws Exception {
        MvcResult getContacto = mockMvc.perform(MockMvcRequestBuilders.get("/info-cets/" + 2L))
                .andExpect(status().isOk()).andReturn();

        InfoCet contacto = objectMapper.readValue(getContacto.getResponse().getContentAsString(), InfoCet.class);
        assertEquals(2, contacto.getId());

        InfoCetDto payload = returnInfoCetDto(contacto);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/info-cets/" + 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andReturn();


        mockMvc.perform(MockMvcRequestBuilders.put("/info-cets/contacto/" + 2L + "/confirmado/" + 1L))
                .andExpect(status().isOk()).andReturn();

        MvcResult findContacto = mockMvc.perform(MockMvcRequestBuilders.get("/info-cets/" + 2L))
                .andExpect(status().isOk()).andReturn();

        InfoCet contactoUnlinked = objectMapper.readValue(findContacto.getResponse().getContentAsString(), InfoCet.class);

        assertFalse(contactoUnlinked.getFueConfirmado());
        assertEquals(2, contactoUnlinked.getCovidContacto());
        assertNull(contactoUnlinked.getIdBduaAfConfirmado());
        assertNull(contactoUnlinked.getTipoidAfConfirmado());
        assertNull(contactoUnlinked.getIdentificacionAfConfirmado());
        assertNull(contactoUnlinked.getProductoFinanciero());
        assertNull(contactoUnlinked.getGiroAFamiliar());
        assertNull(contactoUnlinked.getCumpleAislamiento());
        assertNull(contactoUnlinked.getAutorizaEps());
        assertNull(contactoUnlinked.getParentescoId());

        MvcResult findConfirmado = mockMvc.perform(MockMvcRequestBuilders.get("/info-cets/" + 1L))
                .andExpect(status().isOk()).andReturn();

        InfoCet confirmado = objectMapper.readValue(findConfirmado.getResponse().getContentAsString(), InfoCet.class);

        assertNotNull(confirmado.getIdBduaAfConfirmado());
        assertNotNull(confirmado.getTipoidAfConfirmado());
        assertNotNull(confirmado.getIdentificacionAfConfirmado());
        assertNotNull(confirmado.getProductoFinanciero());
        assertNotNull(confirmado.getGiroAFamiliar());
        assertNotNull(confirmado.getCumpleAislamiento());
        assertNotNull(confirmado.getAutorizaEps());
        assertNotNull(confirmado.getParentescoId());
    }

    @Test
    void updatingConfirmadoNotAvaliableShouldCreateFailedInfoCet() throws Exception {
        MvcResult getConfirmado = mockMvc.perform(MockMvcRequestBuilders.get("/info-cets/" + 3L))
                .andExpect(status().isOk()).andReturn();

        InfoCet confirmado = objectMapper.readValue(getConfirmado.getResponse().getContentAsString(), InfoCet.class);

        InfoCetDto payload = returnInfoCetDto(confirmado);
        payload.setLocaliza(false);
        payload.setNoEfectividad("Telefono apagado");

        mockMvc.perform(MockMvcRequestBuilders
                .put("/info-cets/" + payload.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andReturn();

        MvcResult getConfirmadoNoLocalizado = mockMvc.perform(MockMvcRequestBuilders.get("/info-cets/" + 3L))
                .andExpect(status().isOk()).andReturn();

        InfoCet confirmadoNoLocalizado = objectMapper.readValue(getConfirmadoNoLocalizado.getResponse().getContentAsString(), InfoCet.class);

        assertEquals("Telefono apagado", confirmadoNoLocalizado.getNoEfectividad());
        assertEquals(1, confirmadoNoLocalizado.getFailedInfoCets().size());
    }

    @Test
    void updatedContactoWithConfirmadoShouldReturnConfirmadoWithContactos() throws Exception {
        MvcResult getContacto = mockMvc.perform(MockMvcRequestBuilders
                .get("/info-cets/" + 2L))
                .andExpect(status().isOk()).andReturn();

        MvcResult getConfirmado = mockMvc.perform(MockMvcRequestBuilders
                .get("/info-cets/" + 1L))
                .andExpect(status().isOk()).andReturn();

        InfoCet contacto = objectMapper.readValue(getContacto.getResponse().getContentAsString(), InfoCet.class);
        InfoCet confirmado = objectMapper.readValue(getConfirmado.getResponse().getContentAsString(), InfoCet.class);
        assertEquals(2, contacto.getId());

        InfoCetDto payloadContacto = returnInfoCetDto(contacto);
        InfoCetDto payloadConfirmado = returnInfoCetDto(confirmado);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/info-cets/" + payloadConfirmado.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payloadContacto)))
                .andExpect(status().isOk()).andReturn();

        mockMvc.perform(MockMvcRequestBuilders
                .put("/info-cets/" + payloadConfirmado.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payloadConfirmado)))
                .andExpect(status().isOk()).andReturn();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/info-cets/family-group/" + 1L))
                .andExpect(status().isOk()).andReturn();

        InfoCetResponseBody infoCet = objectMapper.readValue(result.getResponse().getContentAsString(), InfoCetResponseBody.class);

        assertEquals(2, infoCet.getContatos().size());
    }

    private InfoCetDto returnInfoCetDto(InfoCet payload) {
        return InfoCetDto.builder().id(payload.getId())
                .numeroCaso(payload.getNumeroCaso())
                .fechaDiagnostico(payload.getFechaDiagnostico())
                .bduaAfiliadoId(payload.getBduaAfiliadoId())
                .tipoId(payload.getTipoId())
                .identificacion(payload.getIdentificacion())
                .nombre1(payload.getNombre1())
                .nombre2(payload.getNombre2())
                .apellido1(payload.getApellido1())
                .apellido2(payload.getApellido2())
                .fechaNacimiento(payload.getFechaNacimiento())
                .sexo(payload.getSexo())
                .codEps(payload.getCodEps())
                .telefonoFijo(payload.getTelefonoFijo())
                .celular(payload.getCelular())
                .covidContacto(payload.getCovidContacto())
                .cet(payload.getCetId())
                .productoFinanciero(false)
                .entidadFinancieraId(null)
                .giroAFamiliar(true)
                .fallecido(false)
                .fechaExpedicion(LocalDate.of(2017, 8, 19))
                .email("prueba@mail.com")
                .direccion("Calle imaginaria #45-54")
                .codigoDepartamento("01")
                .codigoMunicipio("234")
                .cumpleAislamiento(true)
                .autorizaEps(true)
                .parentescoId(2)
                .compartenGastos(true)
                .fueConfirmado(false)
                .localiza(true)
                .build();
    }

}