package cl.ucm.mantenedor.service;

import cl.ucm.mantenedor.dto.in.PlatoDtoIn;
import cl.ucm.mantenedor.dto.out.PlatoDtoOut;

import java.util.List;

public interface PlatoService {
    List<PlatoDtoOut> getAll();
    List<PlatoDtoOut> getDisponibles();
    PlatoDtoOut getById(int id);
    PlatoDtoOut create(PlatoDtoIn in);
    PlatoDtoOut edit(int id, PlatoDtoIn in);
    void delete(int id);
}
