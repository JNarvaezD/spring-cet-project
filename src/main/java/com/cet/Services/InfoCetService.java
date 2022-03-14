package com.cet.Services;

import com.cet.ResponseClasses.InfoCetResponseBody;
import com.cet.Models.FailedInfoCet;
import com.cet.Models.InfoCet;
import com.cet.Repositories.FailedInfoCetRepository;
import com.cet.Repositories.InfoCetRepository;
import com.cet.dtos.InfoCetDto;
import com.cet.utils.InfoCetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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

    public InfoCetResponseBody show(Long id) {
        Optional<InfoCet> confirmado = findOne(id);
        if(confirmado.isPresent()) {
            InfoCet cabezaHogar = confirmado.get();
            List<InfoCet> familyGroup = infoCetRepository.findFamilyGroup(cabezaHogar.getTipoId(), cabezaHogar.getIdentificacion());
            InfoCetResponseBody response = InfoCetResponseBody.builder().id(cabezaHogar.getId())
                    .numeroCaso(cabezaHogar.getNumeroCaso()).fechaDiagnostico(cabezaHogar.getFechaDiagnostico())
                    .bduaAfiliadoId(cabezaHogar.getBduaAfiliadoId()).tipoId(cabezaHogar.getTipoId())
                    .identificacion(cabezaHogar.getIdentificacion()).nombre1(cabezaHogar.getNombre1())
                    .nombre2(cabezaHogar.getNombre2()).apellido1(cabezaHogar.getApellido1()).apellido2(cabezaHogar.getApellido2())
                    .fechaNacimiento(cabezaHogar.getFechaNacimiento()).sexo(cabezaHogar.getSexo()).fallecido(cabezaHogar.getFallecido())
                    .codEps(cabezaHogar.getCodEps()).productoFinanciero(cabezaHogar.getProductoFinanciero()).entidadFinancieraId(cabezaHogar.getEntidadFinancieraId())
                    .giroAFamiliar(cabezaHogar.getGiroAFamiliar()).telefonoFijo(cabezaHogar.getTelefonoFijo()).celular(cabezaHogar.getCelular())
                    .fechaExpedicion(cabezaHogar.getFechaExpedicion()).email(cabezaHogar.getEmail()).direccion(cabezaHogar.getDireccion())
                    .codigoDepartamento(cabezaHogar.getCodigoDepartamento()).codigoMunicipio(cabezaHogar.getCodigoMunicipio())
                    .idBduaAfConfirmado(cabezaHogar.getIdBduaAfConfirmado()).tipoidAfConfirmado(cabezaHogar.getTipoidAfConfirmado())
                    .identificacionAfConfirmado(cabezaHogar.getIdentificacionAfConfirmado()).cumpleAislamiento(cabezaHogar.getCumpleAislamiento())
                    .autorizaEps(cabezaHogar.getAutorizaEps()).covidContacto(cabezaHogar.getCovidContacto()).parentescoId(cabezaHogar.getParentescoId())
                    .compartenGastos(cabezaHogar.getCompartenGastos()).fueConfirmado(cabezaHogar.getFueConfirmado()).noEfectividad(cabezaHogar.getNoEfectividad())
                    .cetId(cabezaHogar.getCetId()).contatos(familyGroup).build();

            response.setNombreCompleto(cabezaHogar.getNombre1(), cabezaHogar.getNombre2(), cabezaHogar.getApellido1(), cabezaHogar.getApellido2());
            return response;
        }
        throw new NoSuchElementException();
    }

    public InfoCet update(Long idConfirmado, InfoCetDto infoCetDto) {

        if(infoCetDto.getFechaExpedicion() == null) {
            infoCetDto.setFechaExpedicion(LocalDate.of(1900, 1, 1));
        }

        InfoCet infoCet = InfoCet.builder()
                .id(infoCetDto.getId()).numeroCaso(infoCetDto.getNumeroCaso())
                .fechaDiagnostico(infoCetDto.getFechaDiagnostico()).bduaAfiliadoId(infoCetDto.getBduaAfiliadoId())
                .tipoId(infoCetDto.getTipoId()).identificacion(infoCetDto.getIdentificacion())
                .nombre1(infoCetDto.getNombre1()).nombre2(infoCetDto.getNombre2()).apellido1(infoCetDto.getApellido1())
                .apellido2(infoCetDto.getApellido2()).fechaNacimiento(infoCetDto.getFechaNacimiento()).sexo(infoCetDto.getSexo())
                .codEps(infoCetDto.getCodEps()).fallecido(infoCetDto.getFallecido()).productoFinanciero(infoCetDto.getProductoFinanciero())
                .entidadFinancieraId(infoCetDto.getEntidadFinancieraId()).giroAFamiliar(infoCetDto.getGiroAFamiliar())
                .telefonoFijo(infoCetDto.getTelefonoFijo()).celular(infoCetDto.getCelular()).fechaExpedicion(infoCetDto.getFechaExpedicion())
                .email(infoCetDto.getEmail()).direccion(infoCetDto.getDireccion()).codigoDepartamento(infoCetDto.getCodigoDepartamento())
                .codigoMunicipio(infoCetDto.getCodigoMunicipio()).cumpleAislamiento(infoCetDto.getCumpleAislamiento()).autorizaEps(infoCetDto.getAutorizaEps())
                .parentescoId(infoCetDto.getParentescoId()).compartenGastos(infoCetDto.getCompartenGastos()).cetId(infoCetDto.getCet())
                .covidContacto(infoCetDto.getCovidContacto()).fueConfirmado(infoCetDto.getFueConfirmado())
                .noEfectividad(infoCetDto.getNoEfectividad()).build();

        if(infoCetDto.getLocaliza()) {
            infoCet.setNoEfectividad(infoCetDto.getNoEfectividad());
            InfoCetUtils.setCovidContactoAndFueConfirmado(
                    infoCet.getCovidContacto(), infoCet.getFueConfirmado(),
                    idConfirmado, infoCet.getId()
            );

            Optional<InfoCet> cabezaFamiliar = infoCetRepository.findOne(idConfirmado);

            if(cabezaFamiliar.isPresent() && cabezaFamiliar.get().getCovidContacto() == 1) {
                infoCet.setTipoidAfConfirmado(cabezaFamiliar.get().getTipoId());
                infoCet.setIdentificacionAfConfirmado(cabezaFamiliar.get().getIdentificacion());
                infoCet.setIdBduaAfConfirmado(cabezaFamiliar.get().getBduaAfiliadoId());
                infoCet.setFueConfirmado(InfoCetUtils.getFueConfirmado());
                infoCet.setCovidContacto(InfoCetUtils.getCovidContacto());
            }

            infoCetRepository.update(infoCet);
        } else {
            FailedInfoCet failedInfoCet = FailedInfoCet.builder()
                    .infocet(infoCet.getId())
                    .descripcion(infoCetDto.getNoEfectividad())
                    .build();

            failedInfoCetRepository.save(failedInfoCet);
            infoCetRepository.update(infoCet);
        }

        return infoCetRepository.findOne(idConfirmado).get();
    }

    public InfoCet linkOrUnlinkContacto(Long idContacto, Long idConfirmado) {
        Optional<InfoCet> getConfirmado = infoCetRepository.findOne(idConfirmado);
        Optional<InfoCet> getContacto = infoCetRepository.findOne(idContacto);

        if(getConfirmado.isPresent() && getContacto.isPresent()) {
            InfoCet confirmado = getConfirmado.get();
            InfoCet contacto = getContacto.get();

            InfoCetUtils.setCovidContactoAndFueConfirmado(
                    contacto.getCovidContacto(),
                    contacto.getFueConfirmado(),
                    confirmado.getId(),
                    contacto.getId()
            );

            if(contacto.getIdentificacionAfConfirmado().isEmpty()){
                contacto.setCovidContacto(InfoCetUtils.getCovidContacto());
                contacto.setFueConfirmado(InfoCetUtils.getFueConfirmado());
                contacto.setIdBduaAfConfirmado(confirmado.getBduaAfiliadoId());
                contacto.setTipoidAfConfirmado(confirmado.getTipoId());
                contacto.setIdentificacionAfConfirmado(confirmado.getIdentificacion());
            } else {
                contacto.setCovidContacto(InfoCetUtils.getCovidContacto());
                contacto.setFueConfirmado(InfoCetUtils.getFueConfirmado());
                contacto.setIdBduaAfConfirmado(null);
                contacto.setTipoidAfConfirmado(null);
                contacto.setIdentificacionAfConfirmado(null);
                contacto.setProductoFinanciero(null);
                contacto.setGiroAFamiliar(null);
                contacto.setCumpleAislamiento(null);
                contacto.setAutorizaEps(null);
                contacto.setParentescoId(null);
                contacto.setCompartenGastos(null);
            }

            infoCetRepository.save(contacto);
            return confirmado;
        }

        throw new NoSuchElementException();
    }

    public boolean delete(Long id) {
        Optional<InfoCet> infoCet = infoCetRepository.findOne(id);
        if(infoCet.isPresent()) {
            infoCetRepository.delete(id);
            return true;
        }
        return false;
    }

    public List<InfoCet> dataForReporte(Long cetId) {
        List<InfoCet> data = new ArrayList<>();
        infoCetRepository.dataForReporte(cetId).forEach(ax -> {
            data.add(InfoCet.builder().numeroCaso((String) ax[0])
                    .fechaDiagnostico((LocalDate) ax[1])
                    .bduaAfiliadoId((String) ax[2])
                    .tipoId((String) ax[3])
                    .identificacion((String) ax[4])
                    .apellido1((String) ax[5])
                    .apellido2((String) ax[6])
                    .nombre1((String) ax[7])
                    .nombre2((String) ax[8])
                    .fallecido((Boolean) ax[9])
                    .codEps((String) ax[10])
                    .productoFinanciero((Boolean) ax[11])
                    .entidadFinancieraId((Integer) ax[12])
                    .giroAFamiliar((Boolean) ax[13])
                    .telefonoFijo((String) ax[14])
                    .celular((String) ax[15])
                    .fechaExpedicion((LocalDate) ax[16])
                    .email((String) ax[17])
                    .direccion((String) ax[18])
                    .codigoDepartamento((String) ax[19])
                    .codigoMunicipio((String) ax[20])
                    .idBduaAfConfirmado((String) ax[21])
                    .tipoidAfConfirmado((String) ax[22])
                    .identificacionAfConfirmado((String) ax[23])
                    .cumpleAislamiento((Boolean) ax[24])
                    .autorizaEps((Boolean) ax[25])
                    .covidContacto((Integer) ax[26])
                    .parentescoId((Integer) ax[27])
                    .compartenGastos((Boolean) ax[28])
                    .cetId((Long) ax[29])
                    .build());
        });
        return data;
    }

}
