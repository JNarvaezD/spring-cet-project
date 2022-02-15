package com.cet.Repositories.CrudRepositories;

import com.cet.Models.InfoCet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InfoCetCrudRepository extends JpaRepository<InfoCet, Long> {

    Optional<InfoCet> findByIdentificacionAndCetId(String identificacion, Long cetId);
    List<InfoCet> findAllByTipoidAfConfirmadoAndIdentificacionAfConfirmado(String tipoId, String identificacion);
}
