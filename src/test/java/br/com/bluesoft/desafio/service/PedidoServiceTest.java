package br.com.bluesoft.desafio.service;

import java.util.Arrays;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.bluesoft.desafio.dto.PedidoDTO;
import br.com.bluesoft.desafio.dto.ProdutoDTO;
import br.com.bluesoft.desafio.dto.ResultPedidoDTO;
import br.com.bluesoft.desafio.dto.ResultProdutoDTO;
import br.com.bluesoft.desafio.exception.FornecedorProdutoNotFoundException;
import br.com.bluesoft.desafio.model.Fornecedor;
import br.com.bluesoft.desafio.model.ItemPedido;
import br.com.bluesoft.desafio.model.Pedido;
import br.com.bluesoft.desafio.model.Produto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PedidoServiceTest {

	@Autowired
	private PedidoService pedidoService;

	@Test
	public void deveCriarPedido() {

		ProdutoDTO produtoDTO = ProdutoDTO.builder().gtin("7894900011517").quantidade(1).build();

		ResultProdutoDTO resultProdutoDTO = ResultProdutoDTO.builder().preco(new Double(5.89)).quantidade(1)
				.produto(produtoDTO).build();

		ResultPedidoDTO resultadoPedidoDTO = ResultPedidoDTO.builder().nomeFornecedor("Fornecedor 1")
				.cnpj("56.918.868/0001-20").listaProdutos(Arrays.asList(resultProdutoDTO)).build();

		Pedido result = pedidoService.salvarPedido(resultadoPedidoDTO);

		Assert.assertNotNull(result.getId());

	}

	@Test
	public void deveConverterObjetoPedidoParaDTO() {

		Produto produto = Produto.builder().gtin("7894900011517").nome("REFRIGERANTE COCA-COLA 2LT").build();
		ItemPedido itemPedido = ItemPedido.builder().preco(new Double(5.99)).quantidade(1).total(new Double(5.99))
				.produto(produto).build();
		Fornecedor fornecedor = Fornecedor.builder().cnpj("56.918.868/0001-20").nome("Fornecedor 1").build();

		Pedido pedido = Pedido.builder().fornecedor(fornecedor).itens(Arrays.asList(itemPedido)).build();

		PedidoDTO result = pedidoService.converterObjetoParaDTO(pedido);

		Assert.assertEquals("Fornecedor 1", result.getFornecedor().getNome());
		Assert.assertEquals(new Double(5.99), result.getItens().get(0).getPreco());
		Assert.assertEquals(new Double(5.99), result.getItens().get(0).getTotal());
		Assert.assertEquals(new Integer(1), result.getItens().get(0).getQuantidade());
		Assert.assertEquals("REFRIGERANTE COCA-COLA 2LT", result.getItens().get(0).getProduto().getNome());
	}
	
	
}
