package cl.ucm.mantenedor.repository;

import cl.ucm.mantenedor.entities.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Integer> {
    Optional<Mesa> findByNumero(int numero);
}
