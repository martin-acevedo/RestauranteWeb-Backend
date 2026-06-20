package cl.ucm.mantenedor.service;

import cl.ucm.mantenedor.dto.in.CategoriaDtoIn;
import cl.ucm.mantenedor.dto.out.CategoriaDtoOut;
import cl.ucm.mantenedor.entities.Categoria;
import cl.ucm.mantenedor.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    private CategoriaDtoOut toDto(Categoria c) {
        return new CategoriaDtoOut(c.getId(), c.getNombre());
    }

    @Override
    public List<CategoriaDtoOut> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public CategoriaDtoOut getById(int id) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("id no encontrado"));
        return toDto(categoria);
    }

    @Override
    public CategoriaDtoOut create(CategoriaDtoIn in) {
        if (repository.findByNombre(in.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una categoría con ese nombre");
        }
        Categoria categoria = new Categoria();
        categoria.setNombre(in.getNombre());
        return toDto(repository.save(categoria));
    }

    @Override
    public CategoriaDtoOut edit(int id, CategoriaDtoIn in) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("id no encontrado"));
        
        repository.findByNombre(in.getNombre()).ifPresent(existing -> {
            if (existing.getId() != id) {
                throw new IllegalArgumentException("Ya existe otra categoría con ese nombre");
            }
        });

        categoria.setNombre(in.getNombre());
        return toDto(repository.save(categoria));
    }

    @Override
    public void delete(int id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("id no encontrado");
        }
        repository.deleteById(id);
    }
}
