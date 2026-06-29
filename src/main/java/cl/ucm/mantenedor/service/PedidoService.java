package cl.ucm.mantenedor.service;

import cl.ucm.mantenedor.dto.in.DetallePedidoDtoIn;
import cl.ucm.mantenedor.dto.out.DetallePedidoDtoOut;
import cl.ucm.mantenedor.dto.in.PedidoDtoIn;
import cl.ucm.mantenedor.dto.out.PedidoDtoOut;

import java.util.List;

public interface PedidoService {
    List<PedidoDtoOut> getAll();
    List<PedidoDtoOut> getActivos();
    PedidoDtoOut getById(int id);
    PedidoDtoOut create(PedidoDtoIn in);
    PedidoDtoOut agregarDetalle(int pedidoId, DetallePedidoDtoIn in);
    PedidoDtoOut cerrarPedido(int id);
    List<DetallePedidoDtoOut> getDetalles(int pedidoId);
}