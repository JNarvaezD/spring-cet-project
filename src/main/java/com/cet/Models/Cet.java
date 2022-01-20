package com.cet.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity @NoArgsConstructor @Data @Builder @AllArgsConstructor
public class Cet {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_archivo")
    private String nombreArchivo;

    @Column(name = "fecha_proceso")
    private LocalDate fechaProceso;

    @OneToMany(mappedBy = "cet")
    private List<InfoCet> infoCets;

}
