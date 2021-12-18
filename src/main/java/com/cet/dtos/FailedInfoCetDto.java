package com.cet.dtos;

import com.cet.Models.InfoCet;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class FailedInfoCetDto {

    @NotEmpty
    private InfoCet infoCet;

    @NotEmpty
    private String descripcion;

}
