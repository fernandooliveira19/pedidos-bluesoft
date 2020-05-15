package br.com.bluesoft.desafio.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bluesoft.desafio.dto.ResultPedidoDTO;
import br.com.bluesoft.desafio.dto.ResultProdutoDTO;
import br.com.bluesoft.desafio.model.ItemPedido;
import br.com.bluesoft.desafio.model.Pedido;
import br.com.bluesoft.desafio.model.Produto;
import br.com.bluesoft.desafio.repository.ItemPedidoRepository;

@Service
public class ItemPedidoService {

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ProdutoService produtoService;

	public List<ItemPedido> salvarItensPedidos(Pedido pedido, ResultPedidoDTO dto) {
		
		List<ItemPedido> itensPedido = new ArrayList<ItemPedido>();
		for(ResultProdutoDTO item : dto.getListaProdutos()) {
	
			ItemPedido itemPedido = criarItemPedido(pedido, item);
			
			itemPedidoRepository.save(itemPedido);
			itensPedido.add(itemPedido);
			
		}
	
		return itensPedido;
	}

	public ItemPedido criarItemPedido(Pedido pedido, ResultProdutoDTO item) {
		Produto produto = produtoService.buscarProdutoPorGtin(item.getProduto().getGtin());
		ItemPedido itemPedido = ItemPedido.builder()
								.pedido(pedido)
								.quantidade(item.getQuantidade())
								.produto(produto)
								.preco(item.getPreco())
								.total(item.getQuantidade() * item.getPreco())
								.build();
		return itemPedido;
	}
}
