package com.cet.Repositories.CrudRepositories;

import com.cet.Models.FailedInfoCet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FailedCetCrudRepository extends JpaRepository<FailedInfoCet, Long> {
}
