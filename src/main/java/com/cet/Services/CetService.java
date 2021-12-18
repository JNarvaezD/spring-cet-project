package com.cet.Services;

import com.cet.Models.Cet;
import com.cet.Repositories.CetRepository;
import com.cet.dtos.CetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CetService {

    @Autowired
    private CetRepository cetRepository;

    public List<Cet> findAll() {
        return this.cetRepository.findAll();
    }

    public Optional<Cet> findOne(Long id) {
        return this.cetRepository.findOne(id);
    }

    public Cet save(CetDto cetDto) {
        Cet cet = Cet.builder().nombreArchivo(cetDto.getNombreArchivo())
                .fechaProceso(cetDto.getFechaProceso())
                .build();
        return this.cetRepository.save(cet);
    }

    public void update(CetDto cetDto) {
        Cet cet = Cet.builder().id(cetDto.getId())
                .nombreArchivo(cetDto.getNombreArchivo())
                .fechaProceso(cetDto.getFechaProceso())
                .build();
        this.cetRepository.update(cet);
    }

    public void delete(Long id) {
        Optional<Cet> cet = this.cetRepository.findOne(id);
        if(cet.isPresent()) {
            this.cetRepository.deleteById(id);
        }
    }


}
