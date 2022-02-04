package com.cet.dtos;

import com.cet.Models.InfoCet;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data @Builder
public class FailedInfoCetDto {

    private Long id;

    @NotEmpty
    private InfoCet infoCet;

    @NotEmpty
    private String descripcion;

}
