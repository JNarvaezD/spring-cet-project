package com.cet.dtos;
import com.cet.Models.Cet;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data @Builder
public class InfoCetDto {

    private Long id;

    @NotEmpty
    private String numeroCaso;

    @NotEmpty
    private Date fechaDiagnostico;

    @NotEmpty
    private String bduaAfiliadoId;

    @NotEmpty
    private String tipoId;

    @NotEmpty
    private String identificacion;

    @NotEmpty
    private String nombre1;

    private String nombre2;

    @NotEmpty
    private String apellido1;

    private String apellido2;

    //@NotEmpty
    private Date fechaNacimiento;

    @NotEmpty
    private String sexo;

    @NotEmpty
    private Boolean fallecido;

    @NotEmpty
    private String codEps;

    @NotEmpty
    private Boolean productoFinanciero;

    @NotEmpty
    private Integer entidadFinancieraId;

    @NotEmpty
    private Boolean giroAFamiliar;

    private String telefonoFijo;

    @NotEmpty
    private String celular;

    private Date fechaExpedicion;

    @NotEmpty
    private String email;

    @NotEmpty
    private String direccion;

    @NotEmpty
    private String codigoDepartamento;

    @NotEmpty
    private String codigoMunicipio;

    private String idBduaAfConfirmado;

    private String tipoidAfConfirmado;

    private String identificacionAfConfirmado;

    @NotEmpty
    private Boolean cumpleAislamiento;

    @NotEmpty
    private Boolean autorizaEps;

    @NotEmpty
    private Integer covidContacto;

    @NotEmpty
    private Integer parentescoId;

    @NotEmpty
    private Boolean compartenGastos;

    @NotEmpty
    private Boolean fueConfirmado;

    @NotEmpty
    private String noEfectividad;

    @NotEmpty
    private Cet cet;

    private Boolean updatingInfoCet;

    @NotEmpty
    private Boolean localiza;

}
