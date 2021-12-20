package com.cet.Repositories;

import com.cet.CrudRepositories.FailedCetCrudRepository;
import com.cet.Models.FailedInfoCet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FailedInfoCetRepositoryI implements FailedInfoCetRepository{

    @Autowired
    private FailedCetCrudRepository failedCetCrudRepository;

    @Override
    public List<FailedInfoCet> findAll() {
        return this.failedCetCrudRepository.findAll();
    }

    @Override
    public FailedInfoCet save(FailedInfoCet failedInfoCet) {
        return this.failedCetCrudRepository.save(failedInfoCet);
    }

}
