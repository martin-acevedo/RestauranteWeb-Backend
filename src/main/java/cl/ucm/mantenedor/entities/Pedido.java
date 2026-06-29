package cl.ucm.mantenedor.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private LocalDateTime fechaPedido;

    @Column(length = 20, nullable = false)
    private String estado; // "activo", "cerrado", "cancelado"

    @Column(nullable = false)
    private int total;

    @ManyToOne
    @JoinColumn(name = "mesa_fk")
    private Mesa mesa;
}