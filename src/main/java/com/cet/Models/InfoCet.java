package com.cet.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private Date fechaDiagnostico;

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
    private Date fecha_nacimiento;

    @Column(nullable = false)
    private String sexo;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean fallecido;

    @Column(nullable = false)
    private String codEps;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean producto_financiero;

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

    private Long idBduaAfConfirmado;

    private String tipoidAfConfirmado;

    private String identificacionAfConfirmado;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean cumple_aislamiento;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean autoriza_eps;

    @Column(nullable = false)
    private Integer covid_contacto;

    private Integer parentesco_id;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean comparten_gastos;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean fue_confirmado;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean noEfectividad;

    @ManyToOne
    @JoinColumn(name = "cet_id")
    private Cet cet;

    @OneToMany(mappedBy = "infoCet")
    private List<FailedInfoCet> failedInfoCets;

}
