package cl.ucm.mantenedor.service;

import cl.ucm.mantenedor.dto.in.MesaDtoIn;
import cl.ucm.mantenedor.dto.out.MesaDtoOut;

import java.util.List;

public interface MesaService {
    List<MesaDtoOut> getAll();
    MesaDtoOut getById(int id);
    MesaDtoOut create(MesaDtoIn in);
    MesaDtoOut edit(int id, MesaDtoIn in);
    void delete(int id);
}
