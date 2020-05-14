package br.com.bluesoft.desafio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.bluesoft.desafio.dto.FornecedorDTO;
import br.com.bluesoft.desafio.model.Fornecedor;
import br.com.bluesoft.desafio.repository.FornecedorRepository;

@Service
public class FornecedorService {

	@Value("${uri.api.fornecedor.produtos}")
	private String uriApiFornecedorProdutos;
	
	@Autowired
	private RestTemplateService restService;
	
	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	public List<FornecedorDTO> buscarListaFornecedoresPorProduto(String gtin){
		
		String uri = builderUriApiFornecedor(gtin);
		
		List<FornecedorDTO> result = restService.acessarApiRest(uri);
		
		return result;
		
	}
	
	private String builderUriApiFornecedor(String gtin) {
		
		return new StringBuilder(uriApiFornecedorProdutos).append(gtin).toString();
		
	}

	
	public Fornecedor buscarFornecedorPorCNPJ(String cnpj) {
		
		Optional<Fornecedor> result = fornecedorRepository.findByCnpj(cnpj);
		
		if(result.isPresent()) {
			
		}
		return result.get();
	}

	public Fornecedor buscarFornecedor(String cnpj) {
		
		Fornecedor fornecedor = buscarFornecedorPorCNPJ(cnpj);
		
		Fornecedor fornecedorSalvo = fornecedorRepository.save(fornecedor);
		
		return fornecedorSalvo;
	}
}
