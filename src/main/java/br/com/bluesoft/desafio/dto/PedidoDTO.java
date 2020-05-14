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
public class PedidoDTO {
	
	private Integer id;
	
	private String gtin;
	
	private FornecedorDTO fornecedor;
	
	private List<ItemPedidoDTO> itens;
	
	public PedidoDTO(FornecedorDTO fornecedor) {
		this.fornecedor = fornecedor;
	}

}
