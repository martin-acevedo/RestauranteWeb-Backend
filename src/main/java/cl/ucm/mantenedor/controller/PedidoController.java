package cl.ucm.mantenedor.controller;

import cl.ucm.mantenedor.dto.in.DetallePedidoDtoIn;
import cl.ucm.mantenedor.dto.in.PedidoDtoIn;
import cl.ucm.mantenedor.error.ErrorInfo;
import cl.ucm.mantenedor.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "pedido")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(path = "activos")
    public ResponseEntity<?> getActivos() {
        return ResponseEntity.ok(service.getActivos());
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
    public ResponseEntity<?> save(@RequestBody PedidoDtoIn in) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.create(in));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorInfo(400, e.getMessage()));
        }
    }

    @PostMapping(path = "{id}/detalle")
    public ResponseEntity<?> agregarDetalle(@PathVariable int id, @RequestBody DetallePedidoDtoIn in) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.agregarDetalle(id, in));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorInfo(400, e.getMessage()));
        }
    }

    @PutMapping(path = "{id}/cerrar")
    public ResponseEntity<?> cerrarPedido(@PathVariable int id) {
        try {
            return ResponseEntity.ok(service.cerrarPedido(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorInfo(400, e.getMessage()));
        }
    }

    @GetMapping(path = "{id}/detalle")
    public ResponseEntity<?> getDetalle(@PathVariable int id) {
        try {
            return ResponseEntity.ok(service.getDetalles(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo(404, e.getMessage()));
        }
    }
}