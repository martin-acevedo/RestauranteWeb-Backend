package cl.ucm.mantenedor.dto.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DetallePedidoDtoIn {
    private int idPlato;
    private int cantidad;
    private String notas;
}