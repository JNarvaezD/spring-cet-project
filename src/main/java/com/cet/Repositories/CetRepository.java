package com.cet.Repositories;

import com.cet.Models.Cet;

import java.util.List;
import java.util.Optional;

public interface CetRepository {

    List<Cet> findAll();
    Optional<Cet> findOne(Long id);
    Cet save(Cet cet);
    boolean fileAlreadyUploaded(String name);

}
