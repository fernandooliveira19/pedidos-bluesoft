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
public class FornecedorDTO {
	
	private String nome;
	private String cnpj;

	private List<PrecoDTO> precos;
	
	


}
