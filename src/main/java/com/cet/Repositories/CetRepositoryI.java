package com.cet.Repositories;

import com.cet.Repositories.CrudRepositories.CetCrudRepository;
import com.cet.Models.Cet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CetRepositoryI implements CetRepository{

    @Autowired
    private CetCrudRepository cetCrudRepository;

    @Override
    public List<Cet> findAll() {
        return cetCrudRepository.findAll();
    }

    @Override
    public Optional<Cet> findOne(Long id) {
        return cetCrudRepository.findById(id);
    }

    @Override
    public Cet save(Cet cet) {
        return cetCrudRepository.save(cet);
    }

    @Override
    public boolean fileAlreadyUploaded(String name) {
        return cetCrudRepository.existsByNombreArchivo(name);
    }
}
