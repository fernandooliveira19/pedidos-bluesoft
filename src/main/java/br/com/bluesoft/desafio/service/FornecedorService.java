package br.com.bluesoft.desafio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.bluesoft.desafio.dto.FornecedorDTO;

@Service
public class FornecedorService {

	@Value("${uri.api.fornecedor.produtos}")
	private String uriApiFornecedorProdutos;
	
	@Autowired
	private RestTemplateService restService;
	
	public List<FornecedorDTO> buscarListaFornecedoresPorProduto(String gtin){
		
		String uri = builderUriApiFornecedor(gtin);
		
		List<FornecedorDTO> result = restService.acessarApiRest(uri);
		
		return result;
		
	}
	
	private String builderUriApiFornecedor(String gtin) {
		
		return new StringBuilder(uriApiFornecedorProdutos).append(gtin).toString();
		
	}
}
