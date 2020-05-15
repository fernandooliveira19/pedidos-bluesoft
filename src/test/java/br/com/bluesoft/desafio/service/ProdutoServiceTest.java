package br.com.bluesoft.desafio.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.bluesoft.desafio.dto.FornecedorDTO;
import br.com.bluesoft.desafio.dto.FornecedorProdutoDTO;
import br.com.bluesoft.desafio.dto.PrecoDTO;
import br.com.bluesoft.desafio.dto.ProdutoDTO;
import br.com.bluesoft.desafio.dto.ResultPedidoDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProdutoServiceTest {

	@Autowired 
	private ProdutoService produtoService; 
	
	
	@Test
	public void deveRetornarFornecedorComMenorPrecoPorProduto() {
		Integer quantidade = 1;
		List<FornecedorDTO> listaFornecedores = new ArrayList<FornecedorDTO>();
		
		FornecedorDTO fornecedor = new FornecedorDTO();
		fornecedor.setNome("Fornecedor 1");
		List<PrecoDTO> precos = new ArrayList<PrecoDTO>();
		PrecoDTO preco1 = new PrecoDTO(new Double(6.89),1);
		PrecoDTO preco2 = new PrecoDTO(new Double(5.89),10);
		precos.add(preco1);
		precos.add(preco2);
		fornecedor.setPrecos(precos);
		
		
		FornecedorDTO fornecedor2 = new FornecedorDTO();
		fornecedor2.setNome("Fornecedor 2");
		List<PrecoDTO> precos2 = new ArrayList<PrecoDTO>();
		PrecoDTO preco3 = new PrecoDTO(new Double(6.8),1);
		PrecoDTO preco4 = new PrecoDTO(new Double(6.0),10);
		precos2.add(preco3);
		precos2.add(preco4);
		fornecedor2.setPrecos(precos2);
		
		listaFornecedores.add(fornecedor);
		listaFornecedores.add(fornecedor2);
		
		FornecedorProdutoDTO result = produtoService.buscarFornecedorComMenorPrecoProduto(listaFornecedores, quantidade);
		
		Assert.assertEquals("Fornecedor 1", result.getNomeFornecedor());
		Assert.assertEquals(new Double(5.89), result.getMenorPreco());
		
		
	}
	
	@Test
	public void deveRetornarMenorValorProdutoDeCadaFornecedorPelaQuantidadeMinima() {
		Integer quantidade = 1;
		FornecedorDTO fornecedor = new FornecedorDTO();
		fornecedor.setNome("Fornecedor 1");
		List<PrecoDTO> precos = new ArrayList<PrecoDTO>();
		PrecoDTO preco1 = new PrecoDTO(new Double(6.89),1);
		PrecoDTO preco2 = new PrecoDTO(new Double(5.89),10);
		precos.add(preco1);
		precos.add(preco2);
		fornecedor.setPrecos(precos);
		
		Double menorValor = produtoService.buscarMenorPrecoProduto(fornecedor, quantidade);
		
		Assert.assertEquals(new Double(5.89), menorValor);
		
	}
	
	@Test
	public void deveAgruparProdutosMesmoFornecedor() {
		
		List<FornecedorProdutoDTO> listaFornecedores = new ArrayList<FornecedorProdutoDTO>();
		
		FornecedorProdutoDTO fornecedor1 = new FornecedorProdutoDTO();
		fornecedor1.setNomeFornecedor("Fornecedor 1");
		fornecedor1.setMenorPreco(5.89);
		
		
		FornecedorProdutoDTO fornecedor2 = new FornecedorProdutoDTO();
		fornecedor2.setNomeFornecedor("Fornecedor 1");
		fornecedor2.setMenorPreco(3.1);
		
		listaFornecedores.addAll(Arrays.asList(fornecedor1,fornecedor2));
		
		
		FornecedorDTO result = produtoService.agruparFornecedores(listaFornecedores);
		
		Assert.assertEquals("Fornecedor 1", result.getNome());
		
		Assert.assertEquals(new Double(5.89), result.getPrecos().get(0).getPreco());
		Assert.assertEquals(new Double(3.1), result.getPrecos().get(1).getPreco());
		
				
		
	}

	@Test
	public void deveAgruparFornecedoresPorProduto() {
		
		List<FornecedorProdutoDTO> listaFornecedores = new ArrayList<FornecedorProdutoDTO>();
		
		FornecedorProdutoDTO fornecedor1 = new FornecedorProdutoDTO();
		fornecedor1.setNomeFornecedor("Fornecedor 1");
		fornecedor1.setMenorPreco(5.89);
		fornecedor1.setNomeProduto("REFRIGERANTE COCA-COLA 2LT");
		fornecedor1.setQuantidade(1);
		
		
		FornecedorProdutoDTO fornecedor2 = new FornecedorProdutoDTO();
		fornecedor2.setNomeFornecedor("Fornecedor 1");
		fornecedor2.setMenorPreco(3.1);
		fornecedor2.setNomeProduto("SALGADINHO FANDANGOS QUEIJO");
		fornecedor2.setQuantidade(1);
		

		listaFornecedores.addAll(Arrays.asList(fornecedor1,fornecedor2));
		
		
		List<ResultPedidoDTO> result = produtoService.listarFornecedoresAgrupados(listaFornecedores);
		
		Assert.assertEquals("Fornecedor 1", result.get(0).getNomeFornecedor());
		Assert.assertEquals(new Double(5.89), result.get(0).getListaProdutos().get(0).getPreco());
		Assert.assertEquals(new Integer(1), result.get(0).getListaProdutos().get(0).getQuantidade());
		
		Assert.assertEquals(new Double(3.1), result.get(0).getListaProdutos().get(1).getPreco());
		Assert.assertEquals(new Integer(1), result.get(0).getListaProdutos().get(1).getQuantidade());
		
		
		
	}
	
	@Test
	public void deveRetornarMensagemErroQuantidadeSolicitada() {
		
		List<ProdutoDTO> produtos = new ArrayList<ProdutoDTO>();
		ProdutoDTO produto = ProdutoDTO.builder()
				.gtin("7891000100103")
				.quantidade(1)
				.build();
		produtos.add(produto);
		
		
		Throwable exception  = Assertions.catchThrowable(() -> produtoService.criarListaPedidosPorProdutos(produtos));
		
		Assertions.assertThat(exception).isInstanceOf(Exception.class).hasMessage("Nenhum fornecedor encontrado para a quantidade solicitada do produto LEITE CONDENSADO MOCA");
		
		
	}
	
	
}
