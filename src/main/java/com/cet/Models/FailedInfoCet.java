package com.cet.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Data @AllArgsConstructor @Builder @NoArgsConstructor
public class FailedInfoCet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long infoCetId;

    private String descripcion;

}
