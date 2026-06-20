package cl.ucm.mantenedor.dto.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlatoDtoOut {
    private int id;
    private String nombre;
    private String descripcion;
    private int precio;
    private boolean disponible;
    private int idCategoria;
    private String nombreCategoria;
}
