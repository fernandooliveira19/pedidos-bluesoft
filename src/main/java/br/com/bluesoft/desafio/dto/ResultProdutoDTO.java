package br.com.bluesoft.desafio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultProdutoDTO {
	
	private ProdutoDTO produto;
	private Integer quantidade;
	private Double preco;
	private Double total;

}
