package com.cet.dtos;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Data @Builder
public class InfoCetDto {

    private Long id;

    @NotBlank
    private String numeroCaso;

    @NotNull
    private LocalDate fechaDiagnostico;

    @NotBlank
    private String bduaAfiliadoId;

    @NotBlank
    private String tipoId;

    @NotBlank
    private String identificacion;

    @NotBlank
    private String nombre1;

    private String nombre2;

    @NotBlank
    private String apellido1;

    private String apellido2;

    @NotNull
    private LocalDate fechaNacimiento;

    @NotBlank
    private String sexo;

    @NotNull
    private Boolean fallecido;

    @NotBlank
    private String codEps;

    @NotNull
    private Boolean productoFinanciero;

    private Integer entidadFinancieraId;

    @NotNull
    private Boolean giroAFamiliar;

    private String telefonoFijo;

    @NotBlank
    private String celular;

    private Date fechaExpedicion;

    @NotBlank
    private String email;

    @NotBlank
    private String direccion;

    @NotBlank
    private String codigoDepartamento;

    @NotBlank
    private String codigoMunicipio;

    private String idBduaAfConfirmado;

    private String tipoidAfConfirmado;

    private String identificacionAfConfirmado;

    @NotNull
    private Boolean cumpleAislamiento;

    @NotNull
    private Boolean autorizaEps;

    @NotNull
    private Integer covidContacto;

    @NotNull
    private Integer parentescoId;

    @NotNull
    private Boolean compartenGastos;

    @NotNull
    private Boolean fueConfirmado;

    private String noEfectividad;

    @NotNull
    private Long cet;

    private Boolean updatingInfoCet;

    @NotNull
    private Boolean localiza;

}
