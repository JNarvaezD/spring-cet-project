package com.cet.Models;

import lombok.Data;

import javax.persistence.*;

@Entity @Data
public class FailedInfoCet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "info_cet_id")
    private InfoCet infoCet;

}
