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

    private String numeroCaso;

    private Date fechaDiagnostico;

    private String bduaAfiliadoId;

    private String tipoId;

    private String identificacion;

    private String nombre1;

    private String nombre2;

    private String apellido1;

    private String apellido2;

    private Date fecha_nacimiento;

    private String sexo;

    private Boolean fallecido;

    private String codEps;

    private Boolean producto_financiero;

    private Integer entidadFinancieraId;

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

    private Boolean cumple_aislamiento;

    private Boolean autoriza_eps;

    private Integer covid_contacto;

    private Integer parentesco_id;

    private Boolean comparten_gastos;

    private Boolean fue_confirmado;

    private Boolean noEfectividad;

    @ManyToOne
    @JoinColumn(name = "cet_id")
    private Cet cet;

    @OneToMany(mappedBy = "infoCet")
    private List<FailedInfoCet> failedInfoCets;

}
