package cl.ucm.mantenedor.dto.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDtoOut {
    private int id;
    private String estado;
    private int total;
    private int idMesa;
    private LocalDateTime fechaPedido;
}