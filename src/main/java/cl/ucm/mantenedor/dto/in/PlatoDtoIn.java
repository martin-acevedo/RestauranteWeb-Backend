package cl.ucm.mantenedor.dto.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlatoDtoIn {
    private String nombre;
    private String descripcion;
    private int precio;
    private boolean disponible;
    private int idCategoria;
}
