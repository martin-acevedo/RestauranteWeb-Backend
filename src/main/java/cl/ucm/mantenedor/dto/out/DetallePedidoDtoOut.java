package cl.ucm.mantenedor.dto.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DetallePedidoDtoOut {
    private int id;
    private int cantidad;
    private int precioUnitario;
    private int subtotal;
    private String notas;
    private int idPlato;
    private String nombrePlato;
}