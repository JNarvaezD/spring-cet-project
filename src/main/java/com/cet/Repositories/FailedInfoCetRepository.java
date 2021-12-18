package com.cet.Repositories;

import com.cet.Models.FailedInfoCet;

import java.util.List;

public interface FailedInfoCetRepository {

    List<FailedInfoCet> findAll();
    FailedInfoCet save(FailedInfoCet failedInfoCet);

}
