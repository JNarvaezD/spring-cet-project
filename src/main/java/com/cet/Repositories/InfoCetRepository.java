package com.cet.Repositories;


import com.cet.Models.InfoCet;

import java.util.List;
import java.util.Optional;

public interface InfoCetRepository {

    List<InfoCet> findAll();
    InfoCet save(InfoCet infoCet);
    Optional<InfoCet> findOne(Long id);
    InfoCet update(InfoCet infoCet);
    void delete(Long id);
    Optional<InfoCet> findByIdentificacionAndCetId(String identificacion, Long cetId);
    void saveAll(List<InfoCet> infoCets);

}
