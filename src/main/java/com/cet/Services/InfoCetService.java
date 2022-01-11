package com.cet.Services;

import com.cet.Models.FailedInfoCet;
import com.cet.Models.InfoCet;
import com.cet.Repositories.FailedInfoCetRepository;
import com.cet.Repositories.InfoCetRepository;
import com.cet.dtos.FailedInfoCetDto;
import com.cet.dtos.InfoCetDto;
import com.cet.utils.InfoCetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InfoCetService {

    @Autowired
    private InfoCetRepository infoCetRepository;

    @Autowired
    private FailedInfoCetRepository failedInfoCetRepository;

    public List<InfoCet> findAll() {
        return infoCetRepository.findAll();
    }

    public Optional<InfoCet> findOne(Long id) {
        return infoCetRepository.findOne(id);
    }

    public InfoCet update(Long idConfirmado, InfoCetDto infoCetDto) {
        InfoCet infoCet = InfoCet.builder()
                .id(infoCetDto.getId())
                .numeroCaso(infoCetDto.getNumeroCaso())
                .fechaDiagnostico(infoCetDto.getFechaDiagnostico())
                .bduaAfiliadoId(infoCetDto.getBduaAfiliadoId())
                .tipoId(infoCetDto.getTipoId())
                .identificacion(infoCetDto.getIdentificacion())
                .nombre1(infoCetDto.getNombre1())
                .nombre2(infoCetDto.getNombre2())
                .apellido1(infoCetDto.getApellido1())
                .apellido2(infoCetDto.getApellido2())
                .fechaNacimiento(infoCetDto.getFechaNacimiento())
                .sexo(infoCetDto.getSexo())
                .codEps(infoCetDto.getCodEps())
                .fallecido(infoCetDto.getFallecido())
                .productoFinanciero(infoCetDto.getProductoFinanciero())
                .entidadFinancieraId(infoCetDto.getEntidadFinancieraId())
                .giroAFamiliar(infoCetDto.getGiroAFamiliar())
                .telefonoFijo(infoCetDto.getTelefonoFijo())
                .celular(infoCetDto.getCelular())
                .fechaExpedicion(infoCetDto.getFechaExpedicion())
                .email(infoCetDto.getEmail())
                .direccion(infoCetDto.getDireccion())
                .codigoDepartamento(infoCetDto.getCodigoDepartamento())
                .codigoMunicipio(infoCetDto.getCodigoMunicipio())
                .cumpleAislamiento(infoCetDto.getCumpleAislamiento())
                .autorizaEps(infoCetDto.getAutorizaEps())
                .parentescoId(infoCetDto.getParentescoId())
                .compartenGastos(infoCetDto.getCompartenGastos())
                .cet(infoCetDto.getCet())
                .build();

        if(infoCetDto.getLocaliza()) {
            infoCet.setNoEfectividad(infoCetDto.getNoEfectividad());

            Optional<InfoCet> cabezaFamiliar = infoCetRepository.findOne(idConfirmado);

            if(cabezaFamiliar.isPresent() && cabezaFamiliar.get().getCovidContacto() == 1) {
                infoCet.setTipoidAfConfirmado(cabezaFamiliar.get().getTipoidAfConfirmado());
                infoCet.setIdentificacionAfConfirmado(cabezaFamiliar.get().getIdentificacionAfConfirmado());
                infoCet.setIdBduaAfConfirmado(cabezaFamiliar.get().getIdBduaAfConfirmado());
            }

            return infoCetRepository.update(infoCet);
        } else {
            FailedInfoCet failedInfoCet = FailedInfoCet.builder()
                    .infoCet(infoCet)
                    .descripcion(infoCetDto.getNoEfectividad())
                    .build();
            failedInfoCetRepository.save(failedInfoCet);
        }

        return infoCetRepository.findOne(idConfirmado).get();
    }

    public InfoCet vincularContacto(Long idContacto, Long idConfirmado) {
        Optional<InfoCet> confirmado = infoCetRepository.findOne(idContacto);
        Optional<InfoCet> contacto = infoCetRepository.findOne(idConfirmado);

        if(confirmado.isPresent() && contacto.isPresent()) {
            InfoCetUtils.setCovidContactoAndFueConfirmado(
                    contacto.get().getCovidContacto(),
                    contacto.get().getFueConfirmado(),
                    confirmado.get().getId(),
                    contacto.get().getId()
            );

            if(contacto.get().getIdentificacionAfConfirmado().isEmpty()){
                contacto.get().setCovidContacto(InfoCetUtils.getCovidContacto());
                contacto.get().setFueConfirmado(InfoCetUtils.getFueConfirmado());
                contacto.get().setIdBduaAfConfirmado(confirmado.get().getBduaAfiliadoId());
                contacto.get().setTipoidAfConfirmado(confirmado.get().getTipoId());
                contacto.get().setIdentificacionAfConfirmado(confirmado.get().getIdentificacion());
                infoCetRepository.save(contacto.get());
            } else {
                contacto.get().setCovidContacto(InfoCetUtils.getCovidContacto());
                contacto.get().setFueConfirmado(InfoCetUtils.getFueConfirmado());
                contacto.get().setIdBduaAfConfirmado(null);
                contacto.get().setTipoidAfConfirmado(null);
                contacto.get().setIdentificacionAfConfirmado(null);
                infoCetRepository.save(contacto.get());
            }
        }
        return confirmado.get();
    }

    public boolean delete(Long id) {
        Optional<InfoCet> infoCet = infoCetRepository.findOne(id);
        if(infoCet.isPresent()) {
            infoCetRepository.delete(id);
            return true;
        }
        return false;
    }

}
