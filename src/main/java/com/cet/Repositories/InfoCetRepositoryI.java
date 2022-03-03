package com.cet.Repositories;

import com.cet.Repositories.CrudRepositories.InfoCetCrudRepository;
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
        return infoCetCrudRepository.save(infoCet);
    }

    @Override
    public Optional<InfoCet> findOne(Long id) {
        return infoCetCrudRepository.findById(id);
    }

    @Override
    public InfoCet update(InfoCet infoCet) {
        return infoCetCrudRepository.save(infoCet);
    }

    @Override
    public void delete(Long id) {
        infoCetCrudRepository.deleteById(id);
    }

    @Override
    public Optional<InfoCet> findByIdentificacionAndCetId(String identificacion, Long cetId) {
        return infoCetCrudRepository.findByIdentificacionAndCetId(identificacion, cetId);
    }

    @Override
    public void saveAll(List<InfoCet> infoCets) {
        infoCetCrudRepository.saveAll(infoCets);
    }

    @Override
    public List<InfoCet> findContactos(String tipoId, String identificacion) {
        return infoCetCrudRepository.findAllByTipoidAfConfirmadoAndIdentificacionAfConfirmado(tipoId, identificacion);
    }

    @Override
    public List<InfoCet> dataForReporte(Long cetId) {
        return infoCetCrudRepository.returnDataForReporte(cetId);
    }

}
