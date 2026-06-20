package cl.ucm.mantenedor.repository;

import cl.ucm.mantenedor.entities.Plato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, Integer> {
    List<Plato> findByDisponible(boolean disponible);
}
