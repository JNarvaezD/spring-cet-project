package com.cet.Services;

import com.cet.Models.FailedInfoCet;
import com.cet.Repositories.FailedInfoCetRepository;
import com.cet.dtos.FailedInfoCetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FailedInfoCetService {

    @Autowired
    private FailedInfoCetRepository failedInfoCetRepository;

    public List<FailedInfoCet> findAll() {
        return failedInfoCetRepository.findAll();
    }

    public FailedInfoCet save(FailedInfoCetDto failedInfoCetDto) {
        FailedInfoCet failedInfoCet = FailedInfoCet.builder().infocet(failedInfoCetDto.getInfoCet())
                .descripcion(failedInfoCetDto.getDescripcion())
                .build();
        return failedInfoCetRepository.save(failedInfoCet);
    }

}
