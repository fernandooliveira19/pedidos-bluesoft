package br.com.bluesoft.desafio.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultPedidoDTO {
	
	private String nomeFornecedor;
	private String cnpj;
	private List<ResultProdutoDTO> listaProdutos;
//	private List<PrecoDTO> precos;
	
	


}
