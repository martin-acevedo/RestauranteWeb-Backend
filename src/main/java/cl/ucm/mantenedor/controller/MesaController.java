package cl.ucm.mantenedor.controller;

import cl.ucm.mantenedor.dto.in.MesaDtoIn;
import cl.ucm.mantenedor.error.ErrorInfo;
import cl.ucm.mantenedor.service.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "mesa")
public class MesaController {

    @Autowired
    private MesaService service;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<?> find(@PathVariable int id) {
        try {
            return ResponseEntity.ok(service.getById(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo(404, e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody MesaDtoIn in) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.create(in));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorInfo(400, e.getMessage()));
        }
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<?> edit(@PathVariable int id, @RequestBody MesaDtoIn in) {
        try {
            return ResponseEntity.ok(service.edit(id, in));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorInfo(400, e.getMessage()));
        }
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            service.delete(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo(404, e.getMessage()));
        }
    }
}
