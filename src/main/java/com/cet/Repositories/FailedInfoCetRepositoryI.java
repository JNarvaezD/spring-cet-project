package com.cet.Repositories;

import com.cet.Repositories.CrudRepositories.FailedInfoCetCrudRepository;
import com.cet.Models.FailedInfoCet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FailedInfoCetRepositoryI implements FailedInfoCetRepository{

    @Autowired
    private FailedInfoCetCrudRepository failedInfoCetCrudRepository;

    @Override
    public List<FailedInfoCet> findAll() {
        return failedInfoCetCrudRepository.findAll();
    }

    @Override
    public FailedInfoCet save(FailedInfoCet failedInfoCet) {
        return failedInfoCetCrudRepository.save(failedInfoCet);
    }

}
