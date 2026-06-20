package cl.ucm.mantenedor.service;

import cl.ucm.mantenedor.dto.in.PlatoDtoIn;
import cl.ucm.mantenedor.dto.out.PlatoDtoOut;
import cl.ucm.mantenedor.entities.Categoria;
import cl.ucm.mantenedor.entities.Plato;
import cl.ucm.mantenedor.repository.CategoriaRepository;
import cl.ucm.mantenedor.repository.PlatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatoServiceImpl implements PlatoService {

    @Autowired
    private PlatoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    private PlatoDtoOut toDto(Plato p) {
        return new PlatoDtoOut(
                p.getId(),
                p.getNombre(),
                p.getDescripcion(),
                p.getPrecio(),
                p.isDisponible(),
                p.getCategoria() != null ? p.getCategoria().getId() : 0,
                p.getCategoria() != null ? p.getCategoria().getNombre() : ""
        );
    }

    @Override
    public List<PlatoDtoOut> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<PlatoDtoOut> getDisponibles() {
        return repository.findByDisponible(true)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public PlatoDtoOut getById(int id) {
        Plato plato = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Plato no encontrado"));
        return toDto(plato);
    }

    @Override
    public PlatoDtoOut create(PlatoDtoIn in) {
        Categoria categoria = categoriaRepository.findById(in.getIdCategoria())
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        Plato plato = new Plato();
        plato.setNombre(in.getNombre());
        plato.setDescripcion(in.getDescripcion());
        plato.setPrecio(in.getPrecio());
        plato.setDisponible(in.isDisponible());
        plato.setCategoria(categoria);

        return toDto(repository.save(plato));
    }

    @Override
    public PlatoDtoOut edit(int id, PlatoDtoIn in) {
        Plato plato = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Plato no encontrado"));

        Categoria categoria = categoriaRepository.findById(in.getIdCategoria())
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        plato.setNombre(in.getNombre());
        plato.setDescripcion(in.getDescripcion());
        plato.setPrecio(in.getPrecio());
        plato.setDisponible(in.isDisponible());
        plato.setCategoria(categoria);

        return toDto(repository.save(plato));
    }

    @Override
    public void delete(int id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Plato no encontrado");
        }
        repository.deleteById(id);
    }
}
