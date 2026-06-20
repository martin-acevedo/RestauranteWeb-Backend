package cl.ucm.mantenedor.service;

import cl.ucm.mantenedor.dto.in.CategoriaDtoIn;
import cl.ucm.mantenedor.dto.out.CategoriaDtoOut;

import java.util.List;

public interface CategoriaService {
    List<CategoriaDtoOut> getAll();
    CategoriaDtoOut getById(int id);
    CategoriaDtoOut create(CategoriaDtoIn in);
    CategoriaDtoOut edit(int id, CategoriaDtoIn in);
    void delete(int id);
}
