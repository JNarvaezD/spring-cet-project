package com.cet.Controllers;

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
    public ResponseEntity<List<InfoCet>> get() {
        return new ResponseEntity<>(infoCetService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable("id") Long id) {
        Optional<InfoCet> infoCet = infoCetService.findOne(id);
        if(infoCet.isPresent()) {
            return new ResponseEntity<>(infoCet.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("El registro con id " + id + " no ha sido encontrado", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InfoCet> update(@PathVariable("id") Long idConfirmado, @RequestBody @Valid InfoCetDto request) {
        System.out.println("el id del confirmado " + idConfirmado);
        System.out.println("el request " + request);
        return new ResponseEntity<>(infoCetService.update(idConfirmado, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        boolean rowDeleted = infoCetService.delete(id);
        if(rowDeleted) {
            return new ResponseEntity<>("Registro eliminado con exito", HttpStatus.OK);
        }
        return new ResponseEntity<>("El registro con id " + id + " no ha sido encontrado", HttpStatus.NOT_FOUND);
    }

}
