package com.cet.Repositories;

import com.cet.Models.FailedInfoCet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FailedInfoCetRepositoryI implements FailedInfoCetRepository{

    @Autowired
    private FailedInfoCetRepository failedInfoCetRepository;

    @Override
    public List<FailedInfoCet> findAll() {
        return this.failedInfoCetRepository.findAll();
    }

    @Override
    public FailedInfoCet save(FailedInfoCet failedInfoCet) {
        return this.failedInfoCetRepository.save(failedInfoCet);
    }

}
