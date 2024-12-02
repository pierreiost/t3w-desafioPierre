package io.t3w.desafio.services;

import io.t3w.desafio.data.dao.PedidoDAO;
import io.t3w.desafio.data.entity.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PedidoDAO pedidoDAO;

    public List<Pedido> buscarTodos() {
        //  return pedidoDAO.findAll();
        return List.of();
    }

    public Pedido salvar(Pedido pedido) {
        //     return pedidoDAO.save(pedido);
        return pedido;
    }

    public void remover(Pedido pedido) {
        //  pedidoDAO.delete(pedido);
    }
}
