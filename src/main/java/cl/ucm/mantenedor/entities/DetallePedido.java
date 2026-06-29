package cl.ucm.mantenedor.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "detalle_pedidos")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int cantidad;

    @Column(nullable = false)
    private int precioUnitario;

    @Column(length = 255)
    private String notas;

    @ManyToOne
    @JoinColumn(name = "pedido_fk")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "plato_fk")
    private Plato plato;
}