package br.com.bluesoft.desafio.service;

import java.util.ArrayList;
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
	public List<Pedido> salvarPedidos(List<ResultPedidoDTO> pedidosDTO) throws Exception {

		List<Pedido> pedidosCriados = new ArrayList<Pedido>();
		
		for (ResultPedidoDTO dto : pedidosDTO) {

			Pedido pedidoSalvo = salvarPedido(dto);

			List<ItemPedido> itens = itemPedidoService.salvarItensPedidos(pedidoSalvo, dto);
			pedidoSalvo.setItens(itens);
			
			pedidosCriados.add(pedidoSalvo);

		}
		
		return pedidosCriados;

	}

	
	public Pedido salvarPedido(ResultPedidoDTO dto) {

		Pedido pedido = converterParaObjeto(dto);
		
		return pedidoRepository.save(pedido);
	}

	private Pedido converterParaObjeto(ResultPedidoDTO dto) {

		Fornecedor fornecedor = fornecedorService.buscarFornecedor(dto);

		return Pedido.builder().fornecedor(fornecedor).build();

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

			ProdutoDTO produtoDTO = buildProdutoDTO(itemPedido);

			ItemPedidoDTO itemPedidoDTO = buildItemPedidoDTO(itemPedido, produtoDTO);

			itensPedidoDTO.add(itemPedidoDTO);
		}

		FornecedorDTO fornecedorDTO = buildFornecedorDTO(pedido);

		return buildPedidoDTO(pedido, itensPedidoDTO, fornecedorDTO);
	}


	private ProdutoDTO buildProdutoDTO(ItemPedido itemPedido) {
		ProdutoDTO produtoDTO = ProdutoDTO.builder().gtin(itemPedido.getProduto().getGtin())
				.nome(itemPedido.getProduto().getNome()).build();
		return produtoDTO;
	}


	private ItemPedidoDTO buildItemPedidoDTO(ItemPedido itemPedido, ProdutoDTO produtoDTO) {
		ItemPedidoDTO itemPedidoDTO = ItemPedidoDTO.builder().preco(itemPedido.getPreco())
				.quantidade(itemPedido.getQuantidade()).total(itemPedido.getTotal()).produto(produtoDTO).build();
		return itemPedidoDTO;
	}


	private PedidoDTO buildPedidoDTO(Pedido pedido, List<ItemPedidoDTO> itensPedidoDTO, FornecedorDTO fornecedorDTO) {
		PedidoDTO pedidoDTO = PedidoDTO.builder().fornecedor(fornecedorDTO).id(pedido.getId())
				.itens(itensPedidoDTO).build();
		return pedidoDTO;
	}


	private FornecedorDTO buildFornecedorDTO(Pedido pedido) {
		FornecedorDTO fornecedorDTO = FornecedorDTO.builder().cnpj(pedido.getFornecedor().getCnpj())
				.nome(pedido.getFornecedor().getNome()).build();
		return fornecedorDTO;
	}

}
