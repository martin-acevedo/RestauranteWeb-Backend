package cl.ucm.mantenedor.service;

import cl.ucm.mantenedor.dto.out.DetallePedidoDtoOut;
import cl.ucm.mantenedor.dto.in.DetallePedidoDtoIn;
import cl.ucm.mantenedor.dto.in.PedidoDtoIn;
import cl.ucm.mantenedor.dto.out.PedidoDtoOut;
import cl.ucm.mantenedor.entities.DetallePedido;
import cl.ucm.mantenedor.entities.Mesa;
import cl.ucm.mantenedor.entities.Pedido;
import cl.ucm.mantenedor.entities.Plato;
import cl.ucm.mantenedor.repository.DetallePedidoRepository;
import cl.ucm.mantenedor.repository.MesaRepository;
import cl.ucm.mantenedor.repository.PedidoRepository;
import cl.ucm.mantenedor.repository.PlatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private DetallePedidoRepository detalleRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private PlatoRepository platoRepository;

    private PedidoDtoOut toDto(Pedido p) {
        return new PedidoDtoOut(
                p.getId(),
                p.getEstado(),
                p.getTotal(),
                p.getMesa() != null ? p.getMesa().getId() : 0,
                p.getFechaPedido()
        );
    }

    @Override
    public List<PedidoDtoOut> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<PedidoDtoOut> getActivos() {
        return repository.findByEstado("activo")
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public PedidoDtoOut getById(int id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));
        return toDto(pedido);
    }

    @Override
    public PedidoDtoOut create(PedidoDtoIn in) {
        Mesa mesa = mesaRepository.findById(in.getIdMesa())
                .orElseThrow(() -> new IllegalArgumentException("Mesa no encontrada"));

        if (!mesa.getEstado().equalsIgnoreCase("libre")) {
            throw new IllegalArgumentException("La mesa no está libre");
        }

        Pedido pedido = new Pedido();
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setEstado("activo");
        pedido.setTotal(0);
        pedido.setMesa(mesa);

        mesa.setEstado("ocupada");
        mesaRepository.save(mesa);

        return toDto(repository.save(pedido));
    }

    @Override
    public PedidoDtoOut agregarDetalle(int pedidoId, DetallePedidoDtoIn in) {
        Pedido pedido = repository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));

        if (!pedido.getEstado().equalsIgnoreCase("activo")) {
            throw new IllegalArgumentException("Solo se pueden agregar platos a pedidos activos");
        }

        Plato plato = platoRepository.findById(in.getIdPlato())
                .orElseThrow(() -> new IllegalArgumentException("Plato no encontrado"));

        if (!plato.isDisponible()) {
            throw new IllegalArgumentException("El plato no está disponible");
        }

        if (in.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }

        DetallePedido detalle = new DetallePedido();
        detalle.setPedido(pedido);
        detalle.setPlato(plato);
        detalle.setCantidad(in.getCantidad());
        detalle.setPrecioUnitario(plato.getPrecio());
        detalle.setNotas(in.getNotas());

        detalleRepository.save(detalle);

        int subtotal = in.getCantidad() * plato.getPrecio();
        pedido.setTotal(pedido.getTotal() + subtotal);

        return toDto(repository.save(pedido));
    }

    @Override
    public PedidoDtoOut cerrarPedido(int id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));

        if (!pedido.getEstado().equalsIgnoreCase("activo")) {
            throw new IllegalArgumentException("El pedido no está activo");
        }

        if (pedido.getTotal() <= 0) {
            throw new IllegalArgumentException("No se puede cerrar un pedido sin productos");
        }

        pedido.setEstado("cerrado");

        Mesa mesa = pedido.getMesa();
        mesa.setEstado("libre");
        mesaRepository.save(mesa);

        return toDto(repository.save(pedido));
    }
    private DetallePedidoDtoOut detalleToDto(DetallePedido d) {
        return new DetallePedidoDtoOut(
                d.getId(),
                d.getCantidad(),
                d.getPrecioUnitario(),
                d.getCantidad() * d.getPrecioUnitario(),
                d.getNotas(),
                d.getPlato() != null ? d.getPlato().getId() : 0,
                d.getPlato() != null ? d.getPlato().getNombre() : ""
        );
    }

    @Override
    public List<DetallePedidoDtoOut> getDetalles(int pedidoId) {
        if (!repository.existsById(pedidoId)) {
            throw new IllegalArgumentException("Pedido no encontrado");
        }

        return detalleRepository.findByPedidoId(pedidoId)
                .stream()
                .map(this::detalleToDto)
                .toList();
    }
}