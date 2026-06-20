package cl.ucm.mantenedor.service;

import cl.ucm.mantenedor.dto.in.MesaDtoIn;
import cl.ucm.mantenedor.dto.out.MesaDtoOut;
import cl.ucm.mantenedor.entities.Mesa;
import cl.ucm.mantenedor.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesaServiceImpl implements MesaService {

    @Autowired
    private MesaRepository repository;

    private MesaDtoOut toDto(Mesa m) {
        return new MesaDtoOut(m.getId(), m.getNumero(), m.getCapacidad(), m.getEstado());
    }

    @Override
    public List<MesaDtoOut> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public MesaDtoOut getById(int id) {
        Mesa mesa = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mesa no encontrada"));
        return toDto(mesa);
    }

    @Override
    public MesaDtoOut create(MesaDtoIn in) {
        if (repository.findByNumero(in.getNumero()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una mesa con ese número");
        }

        Mesa mesa = new Mesa();
        mesa.setNumero(in.getNumero());
        mesa.setCapacidad(in.getCapacidad());
        mesa.setEstado(in.getEstado() != null ? in.getEstado() : "libre");

        return toDto(repository.save(mesa));
    }

    @Override
    public MesaDtoOut edit(int id, MesaDtoIn in) {
        Mesa mesa = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mesa no encontrada"));

        repository.findByNumero(in.getNumero()).ifPresent(existing -> {
            if (existing.getId() != id) {
                throw new IllegalArgumentException("Ya existe otra mesa con ese número");
            }
        });

        mesa.setNumero(in.getNumero());
        mesa.setCapacidad(in.getCapacidad());
        mesa.setEstado(in.getEstado());

        return toDto(repository.save(mesa));
    }

    @Override
    public void delete(int id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Mesa no encontrada");
        }
        repository.deleteById(id);
    }
}
