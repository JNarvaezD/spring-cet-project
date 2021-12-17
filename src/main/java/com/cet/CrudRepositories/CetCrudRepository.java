package com.cet.CrudRepositories;

import com.cet.Models.Cet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CetCrudRepository extends JpaRepository<Cet, Long> {
}
