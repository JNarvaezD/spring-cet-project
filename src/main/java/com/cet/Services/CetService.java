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
        return cetRepository.findAll();
    }

    public Optional<Cet> findOne(Long id) {
        return cetRepository.findOne(id);
    }

    public Cet save(CetDto cetDto) {
        Cet cet = Cet.builder().nombreArchivo(cetDto.getNombreArchivo())
                .fechaProceso(cetDto.getFechaProceso())
                .build();
        return cetRepository.save(cet);
    }

    public Cet update(CetDto cetDto) {
        Optional<Cet> findCet = cetRepository.findOne(cetDto.getId());
        if(findCet.isPresent()) {
            Cet cet = Cet.builder().id(cetDto.getId())
                    .nombreArchivo(cetDto.getNombreArchivo())
                    .fechaProceso(cetDto.getFechaProceso())
                    .build();
            return cetRepository.update(cet);
        }
        return null;
    }

    public void delete(Long id) {
        Optional<Cet> cet = cetRepository.findOne(id);
        if(cet.isPresent()) {
            cetRepository.deleteById(id);
        }
    }

    public boolean findByNombreArchivo(String name) {
        return cetRepository.findByNombreArchivo(name);
    }

}
