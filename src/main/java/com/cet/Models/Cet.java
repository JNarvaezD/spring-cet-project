package com.cet.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity @NoArgsConstructor @Data @AllArgsConstructor
public class Cet {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    private Date date;

    @OneToMany(mappedBy = "cet")
    private List<InfoCet> infoCets;

}
