package br.com.bluesoft.desafio.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.bluesoft.desafio.model.Pedido;

@Repository
public interface PedidoRepository extends CrudRepository<Pedido, Integer> {

}
