package com.cet.dtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data @Builder
public class FailedInfoCetDto {

    private Long id;

    @NotEmpty
    private Long infoCetId;

    @NotEmpty
    private String descripcion;

}
