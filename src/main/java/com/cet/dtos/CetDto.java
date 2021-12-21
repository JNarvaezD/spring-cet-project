package com.cet.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data @Builder @AllArgsConstructor
public class CetDto {

    private Long id;

    @NotEmpty
    private String nombreArchivo;
    @NotEmpty
    private Date fechaProceso;

}
