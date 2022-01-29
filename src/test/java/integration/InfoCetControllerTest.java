package integration;

import com.cet.CetApplication;
import com.cet.Models.InfoCet;
import com.cet.dtos.InfoCetDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = CetApplication.class)
@AutoConfigureMockMvc
class InfoCetControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper obm;

    @Test
    void getData() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/info-cets");
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void findOne() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/info-cets/" + 1L);
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void findOne422() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/info-cets/" + 999);
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals(404, result.getResponse().getStatus());
    }

    @Test
    void delete() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete("/info-cets/" + 999);
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals(404, result.getResponse().getStatus());
    }

    @Test
    void update() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get("/info-cets/" + 1L)).andReturn();

        assertEquals(200, result.getResponse().getStatus());

        InfoCet infoCet = obm.readValue(result.getResponse().getContentAsString(), InfoCet.class);
        InfoCetDto payload = InfoCetDto.builder().id(infoCet.getId())
                .numeroCaso(infoCet.getNumeroCaso())
                .fechaDiagnostico(infoCet.getFechaDiagnostico())
                .bduaAfiliadoId(infoCet.getBduaAfiliadoId())
                .tipoId(infoCet.getTipoId())
                .identificacion(infoCet.getIdentificacion())
                .nombre1(infoCet.getNombre1())
                .nombre2(infoCet.getNombre2())
                .apellido1(infoCet.getApellido1())
                .apellido2(infoCet.getApellido2())
                .fechaNacimiento(infoCet.getFechaNacimiento())
                .sexo(infoCet.getSexo())
                .codEps(infoCet.getCodEps())
                .telefonoFijo(infoCet.getTelefonoFijo())
                .celular(infoCet.getCelular())
                .covidContacto(infoCet.getCovidContacto())
                .cet(infoCet.getCetId())
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
                .put("/info-cets/" + infoCet.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(obm.writeValueAsString(payload))).andReturn();

        InfoCet response = obm.readValue(updating.getResponse().getContentAsString(), InfoCet.class);
        assertEquals(200, updating.getResponse().getStatus());
        assertEquals(infoCet.getBduaAfiliadoId(), response.getIdBduaAfConfirmado());
        assertEquals(infoCet.getTipoId(), response.getTipoidAfConfirmado());
        assertEquals(infoCet.getIdentificacion(), response.getIdentificacionAfConfirmado());
        assertEquals("1900-01-01", response.getFechaExpedicion().toString());
        assertEquals(false, response.getFueConfirmado());
        assertEquals(1, response.getCovidContacto());
    }

    @Test
    void updateAndLinkContactoWithConfirmado() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get("/info-cets/" + 2L)).andReturn();

        assertEquals(200, result.getResponse().getStatus());

        InfoCet infoCet = obm.readValue(result.getResponse().getContentAsString(), InfoCet.class);
        assertEquals(2, infoCet.getId());

        InfoCetDto payload = InfoCetDto.builder().id(infoCet.getId())
                .numeroCaso(infoCet.getNumeroCaso())
                .fechaDiagnostico(infoCet.getFechaDiagnostico())
                .bduaAfiliadoId(infoCet.getBduaAfiliadoId())
                .tipoId(infoCet.getTipoId())
                .identificacion(infoCet.getIdentificacion())
                .nombre1(infoCet.getNombre1())
                .nombre2(infoCet.getNombre2())
                .apellido1(infoCet.getApellido1())
                .apellido2(infoCet.getApellido2())
                .fechaNacimiento(infoCet.getFechaNacimiento())
                .sexo(infoCet.getSexo())
                .codEps(infoCet.getCodEps())
                .telefonoFijo(infoCet.getTelefonoFijo())
                .celular(infoCet.getCelular())
                .covidContacto(infoCet.getCovidContacto())
                .cet(infoCet.getCetId())
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

        MvcResult getContacto = mvc.perform(MockMvcRequestBuilders
                .get("/info-cets/" + 2L)).andReturn();

        InfoCet confirmado = obm.readValue(update.getResponse().getContentAsString(), InfoCet.class);
        InfoCet contacto = obm.readValue(getContacto.getResponse().getContentAsString(), InfoCet.class);

        assertEquals(200, update.getResponse().getStatus());
        assertEquals("2017-08-19", contacto.getFechaExpedicion().toString());
        assertEquals(true, contacto.getFueConfirmado());
        assertEquals(2, contacto.getCovidContacto());

        assertEquals(confirmado.getBduaAfiliadoId(), contacto.getIdBduaAfConfirmado());
        assertEquals(confirmado.getTipoId(), contacto.getTipoidAfConfirmado());
        assertEquals(confirmado.getIdentificacion(), contacto.getIdentificacionAfConfirmado());
    }

}