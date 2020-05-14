package br.com.bluesoft.desafio.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.bluesoft.desafio.dto.FornecedorDTO;
import br.com.bluesoft.desafio.dto.ItemPedidoDTO;
import br.com.bluesoft.desafio.dto.PedidoDTO;
import br.com.bluesoft.desafio.dto.ProdutoDTO;
import br.com.bluesoft.desafio.dto.ResultPedidoDTO;
import br.com.bluesoft.desafio.model.Fornecedor;
import br.com.bluesoft.desafio.model.ItemPedido;
import br.com.bluesoft.desafio.model.Pedido;
import br.com.bluesoft.desafio.model.Produto;
import br.com.bluesoft.desafio.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private FornecedorService fornecedorService;

	@Autowired
	private ItemPedidoService itemPedidoService;

	@Transactional(propagation = Propagation.REQUIRED)
	public void salvarPedidos(List<ResultPedidoDTO> pedidosDTO) {

		for (ResultPedidoDTO dto : pedidosDTO) {

			Pedido pedidoSalvo = salvarPedido(dto);

			itemPedidoService.salvarItensPedidos(pedidoSalvo, dto);

		}

	}

	
	public Pedido salvarPedido(ResultPedidoDTO dto) {

		Pedido pedido = converterParaObjeto(dto);
		Pedido pedidoSalvo = pedidoRepository.save(pedido);

		return pedidoSalvo;
	}

	private Pedido converterParaObjeto(ResultPedidoDTO dto) {

		Fornecedor fornecedor = fornecedorService.buscarFornecedor(dto.getCnpj());

		Pedido pedido = Pedido.builder().fornecedor(fornecedor).build();

		return pedido;

	}

	public Pedido salvarPedido(Pedido pedido) {

		return pedidoRepository.save(pedido);
	}

	public List<PedidoDTO> buscarPedidos() {

		List<PedidoDTO> pedidosDTO = new ArrayList<PedidoDTO>();
				
		for(Pedido pedido : pedidoRepository.findAll()) {
		
			pedidosDTO.add(converterObjetoParaDTO(pedido));
		}
		
		return pedidosDTO;
	}

	public PedidoDTO converterObjetoParaDTO(Pedido pedido) {

		List<ItemPedidoDTO> itensPedidoDTO = new ArrayList<ItemPedidoDTO>();
		for (ItemPedido itemPedido : pedido.getItens()) {

			ProdutoDTO produtoDTO = ProdutoDTO.builder().gtin(itemPedido.getProduto().getGtin())
					.nome(itemPedido.getProduto().getNome()).build();

			ItemPedidoDTO itemPedidoDTO = ItemPedidoDTO.builder().preco(itemPedido.getPreco())
					.quantidade(itemPedido.getQuantidade()).total(itemPedido.getTotal()).produto(produtoDTO).build();

			itensPedidoDTO.add(itemPedidoDTO);
		}

		FornecedorDTO fornecedorDTO = FornecedorDTO.builder().cnpj(pedido.getFornecedor().getCnpj())
				.nome(pedido.getFornecedor().getNome()).build();

		PedidoDTO pedidoDTO = PedidoDTO.builder().fornecedor(fornecedorDTO).id(pedido.getId())
				.itens(itensPedidoDTO).build();

		return pedidoDTO;
	}

}
