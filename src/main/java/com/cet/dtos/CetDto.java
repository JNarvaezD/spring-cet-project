package com.cet.dtos;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class CetDto {

    private Long id;

    @NotEmpty
    private String nombreArchivo;
    @NotEmpty
    private Date fechaProceso;

}
