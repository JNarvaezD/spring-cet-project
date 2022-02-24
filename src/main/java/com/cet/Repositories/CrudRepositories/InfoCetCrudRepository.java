package com.cet.Repositories.CrudRepositories;

import com.cet.Models.InfoCet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InfoCetCrudRepository extends JpaRepository<InfoCet, Long> {

    Optional<InfoCet> findByIdentificacionAndCetId(String identificacion, Long cetId);
    List<InfoCet> findAllByTipoidAfConfirmadoAndIdentificacionAfConfirmado(String tipoId, String identificacion);
    @Query(
            value = "SELECT ic.numero_caso, ic.fecha_diagnostico, ic.bdua_afiliado_id, ic.tipo_id, ic.identificacion, apellido1, ic.apellido2," +
            " ic.nombre1, ic.nombre2, ic.fallecido, ic.cod_eps, ic.producto_financiero, ic.entidad_financiera_id, ic.giro_a_familiar, " +
            " ic.telefono_fijo, ic.celular, ic.fecha_expedicion, ic.email, ic.direccion, ic.codigo_departamento, ic.codigo_municipio, " +
            " ic.id_bdua_af_confirmado, ic.tipoid_af_confirmado, ic.identificacion_af_confirmado, ic.cumple_aislamiento, " +
            " ic.autoriza_eps, ic.covid_contacto, ic.parentesco_id, ic.comparten_gastos FROM info_cet ic WHERE ic.cet_id = ?1",
            nativeQuery = true
    )
    List<InfoCet> returnDataForReporte(Long cetId);
}
