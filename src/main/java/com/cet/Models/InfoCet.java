package com.cet.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity @Data @AllArgsConstructor @NoArgsConstructor @Builder
public class InfoCet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String numeroCaso;

    @Column(nullable = false)
    private LocalDate fechaDiagnostico;

    @Column(nullable = false)
    private String bduaAfiliadoId;

    @Column(nullable = false)
    private String tipoId;

    @Column(nullable = false)
    private String identificacion;

    @Column(nullable = false)
    private String nombre1;

    private String nombre2;

    @Column(nullable = false)
    private String apellido1;

    private String apellido2;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Column(nullable = false)
    private String sexo;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean fallecido;

    @Column(nullable = false)
    private String codEps;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean productoFinanciero;

    private Integer entidadFinancieraId;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean giroAFamiliar;

    private String telefonoFijo;

    private String celular;

    private Date fechaExpedicion;

    private String email;

    private String direccion;

    private String codigoDepartamento;

    private String codigoMunicipio;

    private String idBduaAfConfirmado;

    private String tipoidAfConfirmado;

    private String identificacionAfConfirmado;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean cumpleAislamiento;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean autorizaEps;

    @Column(nullable = false)
    private Integer covidContacto;

    private Integer parentescoId;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean compartenGastos;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean fueConfirmado;

    private String noEfectividad;

    private Long cetId;

    @OneToMany(mappedBy = "infoCet")
    private List<FailedInfoCet> failedInfoCets;
}
