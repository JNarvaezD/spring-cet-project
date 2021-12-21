package com.cet.Repositories;

import com.cet.CrudRepositories.InfoCetCrudRepository;
import com.cet.Models.InfoCet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InfoCetRepositoryI implements InfoCetRepository{

    @Autowired
    private InfoCetCrudRepository infoCetCrudRepository;

    @Override
    public List<InfoCet> findAll() {
        return infoCetCrudRepository.findAll();
    }

    @Override
    public InfoCet save(InfoCet infoCet) {
        return this.infoCetCrudRepository.save(infoCet);
    }

    @Override
    public Optional<InfoCet> findOne(Long id) {
        return this.infoCetCrudRepository.findById(id);
    }

    @Override
    public void update(InfoCet infoCet) {
        this.infoCetCrudRepository.save(infoCet);
    }

    @Override
    public void delete(Long id) {
        this.infoCetCrudRepository.deleteById(id);
    }

}
