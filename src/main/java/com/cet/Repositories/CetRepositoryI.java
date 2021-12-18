package com.cet.Repositories;

import com.cet.CrudRepositories.CetCrudRepository;
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
        return this.cetCrudRepository.findAll();
    }

    @Override
    public Optional<Cet> findOne(Long id) {
        return this.cetCrudRepository.findById(id);
    }

    @Override
    public Cet save(Cet cet) {
        return this.cetCrudRepository.save(cet);
    }

    @Override
    public void update(Cet cet) {
        this.cetCrudRepository.save(cet);
    }

    @Override
    public void deleteById(Long id) {
        this.cetCrudRepository.deleteById(id);
    }
}
