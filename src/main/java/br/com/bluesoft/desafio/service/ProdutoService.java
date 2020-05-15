package br.com.bluesoft.desafio.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bluesoft.desafio.dto.FornecedorDTO;
import br.com.bluesoft.desafio.dto.FornecedorProdutoDTO;
import br.com.bluesoft.desafio.dto.PedidoDTO;
import br.com.bluesoft.desafio.dto.PrecoDTO;
import br.com.bluesoft.desafio.dto.ProdutoDTO;
import br.com.bluesoft.desafio.dto.ResultPedidoDTO;
import br.com.bluesoft.desafio.dto.ResultProdutoDTO;
import br.com.bluesoft.desafio.exception.FornecedorProdutoNotFoundException;
import br.com.bluesoft.desafio.model.Pedido;
import br.com.bluesoft.desafio.model.Produto;
import br.com.bluesoft.desafio.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private FornecedorService fornecedorService;
	
	@Autowired
	private PedidoService pedidoService;

	@Autowired
	ProdutoRepository produtoRepository;

	public Iterable<Produto> findAll() {

		return produtoRepository.findAll();
	}
	
	public Produto buscarProdutoPorGtin(String gtin) {
		
		Optional<Produto> result = produtoRepository.findById(gtin);
		
		if(result.isPresent()) {
			
		}
		
		return result.get();
	}



	public List<PedidoDTO> criarListaPedidosPorProdutos(List<ProdutoDTO> produtos) throws Exception {

		List<FornecedorProdutoDTO> listaFornecedorProduto = new ArrayList<FornecedorProdutoDTO>();

		for (ProdutoDTO produto : produtos) {

			if (produto.getQuantidade() > 0) {
				listaFornecedorProduto.add(buscarFornecedorComMelhorPrecoPorProduto(produto));
			}

		}

		Collections.sort(listaFornecedorProduto, Comparator.comparing(FornecedorProdutoDTO::getMenorPreco));
		
		List<ResultPedidoDTO> pedidosDTO = listarFornecedoresAgrupados(listaFornecedorProduto);
		
		List<PedidoDTO> listaPedidos = new ArrayList<PedidoDTO>(); 

		for(Pedido pedido : pedidoService.salvarPedidos(pedidosDTO)) {
			listaPedidos.add(pedidoService.converterObjetoParaDTO(pedido)); 
		}

		return listaPedidos;
	}

	public FornecedorProdutoDTO buscarFornecedorComMelhorPrecoPorProduto(ProdutoDTO produto) throws Exception {

		List<FornecedorDTO> fornecedoresProduto = fornecedorService
				.buscarListaFornecedoresPorProduto(produto.getGtin());
		
		validarValoresMinimos(fornecedoresProduto, produto);

		FornecedorProdutoDTO fornecedorComMenorPrecoProduto = buscarFornecedorComMenorPrecoProduto(fornecedoresProduto, produto.getQuantidade());
		
		fornecedorComMenorPrecoProduto.setQuantidade(produto.getQuantidade());
		fornecedorComMenorPrecoProduto.setGtin(produto.getGtin());
		fornecedorComMenorPrecoProduto.setCnpj(fornecedorComMenorPrecoProduto.getCnpj());
		fornecedorComMenorPrecoProduto.setProduto(produto);
		
		return fornecedorComMenorPrecoProduto;
	}
	
	private boolean validarValoresMinimos(List<FornecedorDTO> fornecedoresProduto, ProdutoDTO produto) throws Exception {
		List<PrecoDTO> precos = new ArrayList<PrecoDTO>();
		for(FornecedorDTO fornecedor : fornecedoresProduto) {
			precos.addAll(fornecedor.getPrecos());
		}
		
		for(PrecoDTO preco : precos) {
			
			if(preco.getQuantidade_minima() <= produto.getQuantidade()) {
				return true;
			}
			
		}
		
		Produto prod = buscarProdutoPorGtin(produto.getGtin());
		
		throw new Exception("Nenhum fornecedor encontrado para a quantidade solicitada do produto " + prod.getNome());
		
	}

	public void atribuirNomeProduto(FornecedorProdutoDTO fornecedorComMenorPrecoProduto, String gtin) {
		Optional<Produto> result = produtoRepository.findById(gtin);
		
		fornecedorComMenorPrecoProduto.setNomeProduto(result.get().getNome());
	}

	public FornecedorProdutoDTO buscarFornecedorComMenorPrecoProduto(List<FornecedorDTO> fornecedoresProduto, Integer quantidade) {
		List<FornecedorProdutoDTO> listaProdutoFornecedor = new ArrayList<FornecedorProdutoDTO>();
	
		for(FornecedorDTO fornecedor : fornecedoresProduto) {
		
			listaProdutoFornecedor.add(buildFornecedorComMelhorPreco(fornecedor, quantidade));
			
		}
		
		Collections.sort(listaProdutoFornecedor,Comparator.comparing(FornecedorProdutoDTO:: getMenorPreco));
		
		return listaProdutoFornecedor.get(0);
	}
	
	public FornecedorProdutoDTO buildFornecedorComMelhorPreco(FornecedorDTO fornecedor, Integer quantidade) {
		
		Double menorPreco = buscarMenorPrecoProduto(fornecedor, quantidade);
		
		FornecedorProdutoDTO dto = FornecedorProdutoDTO
				.builder()
				.cnpj(fornecedor.getCnpj())
				.nomeFornecedor(fornecedor.getNome())
				.menorPreco(menorPreco)
				.build();
		
		return dto;
		
	}
	
	public Double buscarMenorPrecoProduto(FornecedorDTO fornecedor, Integer quantidade) {
		List<PrecoDTO> precos = fornecedor.getPrecos();
		
		
		Double menorValor = precos.stream().filter(preco -> preco.getQuantidade_minima() >= quantidade)
				.mapToDouble(preco -> preco.getPreco()).min().getAsDouble();

		return menorValor;
	}

	public FornecedorProdutoDTO buscarProdutoFornecedorComMenorValor(FornecedorDTO fornecedor, Integer quantidade) {

		List<PrecoDTO> precos = fornecedor.getPrecos();

		FornecedorProdutoDTO dto = new FornecedorProdutoDTO();

		Double menorValor = precos.stream().filter(preco -> preco.getQuantidade_minima() >= quantidade)
				.mapToDouble(preco -> preco.getPreco()).min().getAsDouble();

		dto.setNomeFornecedor(fornecedor.getNome());
		dto.setMenorPreco(menorValor);

		return dto;
	}

	public FornecedorDTO agruparFornecedores(List<FornecedorProdutoDTO> listaFornecedores) {
		
		Map<String, List<FornecedorProdutoDTO>> collect = listaFornecedores
				.stream()
				.collect(Collectors.groupingBy(FornecedorProdutoDTO :: getNomeFornecedor))
				;
		
		
		
		FornecedorDTO dto = new FornecedorDTO();
		
		for (Entry<String, List<FornecedorProdutoDTO>> pair : collect.entrySet()) {
			List<PrecoDTO> precos = new ArrayList<PrecoDTO>(); 
		    dto.setNome(pair.getKey());
		    
		    for(FornecedorProdutoDTO item : pair.getValue()) {
		    	PrecoDTO preco = new PrecoDTO();
		    	preco.setPreco(item.getMenorPreco());
		    	precos.add(preco);
		    }
		    
		    dto.setPrecos(precos);    
		    
		}
		
		
		return dto;
	}
	

	public List<ResultPedidoDTO> listarFornecedoresAgrupados(List<FornecedorProdutoDTO> listaFornecedores) {
		
		
		List<ResultPedidoDTO> lista = new ArrayList<ResultPedidoDTO>();
		
		
		Map<String, List<FornecedorProdutoDTO>> collect = listaFornecedores
				.stream()
				.collect(Collectors.groupingBy(FornecedorProdutoDTO :: getNomeFornecedor))
				;
		
		
		for (Entry<String, List<FornecedorProdutoDTO>> pair : collect.entrySet()) {
			ResultPedidoDTO resultPedido = new ResultPedidoDTO();
			
		    resultPedido.setNomeFornecedor(pair.getKey());
		  
		    
		    List<ResultProdutoDTO> listaResultProduto = new ArrayList<ResultProdutoDTO>();
		    for(FornecedorProdutoDTO item : pair.getValue()) {
		    	ResultProdutoDTO resultProduto = new ResultProdutoDTO();
		    	resultProduto.setProduto(item.getProduto());
		    	resultProduto.setPreco(item.getMenorPreco());
		    	resultProduto.setQuantidade(item.getQuantidade());
		    	resultPedido.setCnpj(item.getCnpj());
   	
		    	listaResultProduto.add(resultProduto);
		    }
		    
		    resultPedido.setListaProdutos(listaResultProduto); 
		
		    lista.add(resultPedido);
		}
		
		Collections.sort(lista,Comparator.comparing(ResultPedidoDTO:: getNomeFornecedor));
		
		return lista;
	}
	
}
