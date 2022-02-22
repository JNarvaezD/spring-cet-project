package com.cet.ResponseClasses;

import com.cet.Models.InfoCet;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Builder @Data
public class InfoCetResponseBody {

    private Long id;

    private String numeroCaso;

    private String nombreCompleto;

    private LocalDate fechaDiagnostico;

    private String bduaAfiliadoId;

    private String tipoId;

    private String identificacion;

    private String nombre1;

    private String nombre2;

    private String apellido1;

    private String apellido2;

    private LocalDate fechaNacimiento;

    private String sexo;

    private Boolean fallecido;

    private String codEps;

    private Boolean productoFinanciero;

    private Integer entidadFinancieraId;

    private Boolean giroAFamiliar;

    private String telefonoFijo;

    private String celular;

    private LocalDate fechaExpedicion;

    private String email;

    private String direccion;

    private String codigoDepartamento;

    private String codigoMunicipio;

    private String idBduaAfConfirmado;

    private String tipoidAfConfirmado;

    private String identificacionAfConfirmado;

    private Boolean cumpleAislamiento;

    private Boolean autorizaEps;

    private Integer covidContacto;

    private Integer parentescoId;

    private Boolean compartenGastos;

    private Boolean fueConfirmado;

    private String noEfectividad;

    private Long cetId;

    private List<InfoCet> contatos;

    private String mensajesSaldraEnArchivo;

    public void setNombreCompleto(String nombre1, String nombre2, String apellido1, String apellido2) {
        StringBuilder nombreCompleto = new StringBuilder();

        nombreCompleto.append(nombre1);
        if(!Objects.isNull(nombre2)) {
            nombreCompleto.append(" ").append(nombre2);
        }

        nombreCompleto.append(" ").append(apellido1);
        if(!apellido2.isEmpty()) {
            nombreCompleto.append(" ").append(apellido2);
        }
        this.nombreCompleto = nombreCompleto.toString();
    }
}
