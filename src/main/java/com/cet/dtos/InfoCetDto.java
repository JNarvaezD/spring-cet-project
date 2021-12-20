package com.cet.dtos;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

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


    @NotEmpty
    private String apellido1;

    @NotEmpty
    private Date fecha_nacimiento;

    @NotEmpty
    private String sexo;

    @NotEmpty
    private Boolean fallecido;

    @NotEmpty
    private String codEps;

    @NotEmpty
    private Boolean producto_financiero;

    @NotEmpty
    private Integer entidadFinancieraId;

    @NotEmpty
    private Boolean giroAFamiliar;

    @NotEmpty
    private String telefonoFijo;

    @NotEmpty
    private String celular;

    @NotEmpty
    private Date fechaExpedicion;

    @NotEmpty
    private String email;

    @NotEmpty
    private String direccion;

    @NotEmpty
    private String codigoDepartamento;

    @NotEmpty
    private String codigoMunicipio;

    @NotEmpty
    private Long idBduaAfConfirmado;

    @NotEmpty
    private String tipoidAfConfirmado;

    @NotEmpty
    private String identificacionAfConfirmado;

    @NotEmpty
    private Boolean cumple_aislamiento;

    @NotEmpty
    private Boolean autoriza_eps;

    @NotEmpty
    private Integer covid_contacto;

    @NotEmpty
    private Integer parentesco_id;

    @NotEmpty
    private Boolean comparten_gastos;

    @NotEmpty
    private Boolean fue_confirmado;

    @NotEmpty
    private Boolean noEfectividad;

    @NotEmpty
    private Integer cet_id;

}
