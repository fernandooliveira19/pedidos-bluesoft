package br.com.bluesoft.desafio.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bluesoft.desafio.dto.FornecedorDTO;
import br.com.bluesoft.desafio.dto.PedidoDTO;
import br.com.bluesoft.desafio.model.Produto;
import br.com.bluesoft.desafio.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private FornecedorService fornecedorService;
	
	@Autowired
	ProdutoRepository produtoRepository;

	public Iterable<Produto> findAll() {
		
		return produtoRepository.findAll();
	}

	public List<FornecedorDTO> buscarListaFornecedoresPorPedidos(List<PedidoDTO> listaPedidos) {
		
		List<FornecedorDTO> listaFornecedores = new ArrayList<FornecedorDTO>();
		for(PedidoDTO pedido : listaPedidos) {
			
			if(pedido.getQuantidade() > 0) {
			 listaFornecedores.addAll(buscarListaFornecedoresPorProduto(pedido.getGtin()));
			}
		}
		
		return listaFornecedores;
	}
	
	public List<FornecedorDTO> buscarListaFornecedoresPorProduto(String gtin){ 
		return fornecedorService.buscarListaFornecedoresPorProduto(gtin); 
	
	}
}
