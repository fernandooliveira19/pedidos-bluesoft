package br.com.bluesoft.desafio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FornecedorProdutoDTO {
	
	private String nomeFornecedor;
	private String cnpj;
	private String nomeProduto;
	private Double menorPreco;
	private Integer quantidade;
	private String gtin;
	private ProdutoDTO produto;
	
	


}
