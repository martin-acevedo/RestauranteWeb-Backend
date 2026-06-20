package cl.ucm.mantenedor.dto.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MesaDtoOut {
    private int id;
    private int numero;
    private int capacidad;
    private String estado;
}
