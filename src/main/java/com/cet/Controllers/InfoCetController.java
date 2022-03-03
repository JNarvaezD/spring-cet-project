package com.cet.Controllers;

import com.cet.ResponseClasses.InfoCetResponseBody;
import com.cet.Models.InfoCet;
import com.cet.Services.InfoCetService;
import com.cet.dtos.InfoCetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController @RequestMapping("/info-cets")
public class InfoCetController {

    @Autowired
    private InfoCetService infoCetService;

    @GetMapping
    public ResponseEntity<List<InfoCet>> findAll() {
        return new ResponseEntity<>(infoCetService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable("id") Long id) {
        Optional<InfoCet> infoCet = infoCetService.findOne(id);
        if(infoCet.isPresent()) {
            return new ResponseEntity<>(infoCet.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Record with id " + id + " has not been found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/family-group/{id}")
    public ResponseEntity<InfoCetResponseBody> show(@PathVariable("id") Long id) {
        return new ResponseEntity<>(infoCetService.show(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InfoCet> update(@PathVariable("id") Long idConfirmado, @RequestBody @Valid InfoCetDto request) {
        return new ResponseEntity<>(infoCetService.update(idConfirmado, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        boolean rowDeleted = infoCetService.delete(id);
        if(rowDeleted) {
            return new ResponseEntity<>("Record has been deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Record with id " + id + " has not been found", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/contacto/{idContacto}/confirmado/{idConfirmado}")
    public ResponseEntity<InfoCet> linkOrUnlinkContactoFromConfirmado(@PathVariable("idContacto") Long idContacto, @PathVariable("idConfirmado") Long idConfirmado) {
        InfoCet confirmado = infoCetService.linkOrUnlinkContacto(idContacto, idConfirmado);
        return new ResponseEntity<>(confirmado, HttpStatus.OK);
    }

    @GetMapping("/generate-cet-file/{cetId}")
    public ResponseEntity<String> generateFileCet(@PathVariable("cetId") Long cetId) {
        return new ResponseEntity<>(infoCetService.dataForReporte(cetId).toString(), HttpStatus.OK);
    }

}
