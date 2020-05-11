package br.com.bluesoft.desafio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrecoDTO {
	
	private Double preco;
	private Integer quantidade_minima;
	

}
