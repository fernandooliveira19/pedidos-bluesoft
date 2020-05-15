package br.com.bluesoft.desafio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.bluesoft.desafio.dto.FornecedorDTO;
import br.com.bluesoft.desafio.dto.ResultPedidoDTO;
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
	
	public List<FornecedorDTO> buscarListaFornecedoresPorProduto(String gtin) throws Exception{
		
		String uri = builderUriApiFornecedor(gtin);
		
		return restService.acessarApiRest(uri);
		
	}
	
	private String builderUriApiFornecedor(String gtin) {
		
		return new StringBuilder(uriApiFornecedorProdutos).append(gtin).toString();
		
	}

	
	public Fornecedor buscarOuCriarFornecedor(Fornecedor fornecedor) {
		
		Optional<Fornecedor> result = fornecedorRepository.findByCnpj(fornecedor.getCnpj());
		
		if(!result.isPresent()) {
			return criarFornecedor(fornecedor);
		}
		return result.get();
	}
	
	public Fornecedor criarFornecedor(Fornecedor fornecedor) {
		
		return fornecedorRepository.save(fornecedor);
	}

	public Fornecedor buscarFornecedor(ResultPedidoDTO dto) {
		
		Fornecedor fornecedor = buildFornecedor(dto);
		
		return buscarOuCriarFornecedor(fornecedor);
		
		
	}
	
	public Fornecedor buildFornecedor(ResultPedidoDTO dto) {
		Fornecedor fornecedor = Fornecedor
				.builder()
				.cnpj(dto.getCnpj())
				.nome(dto.getNomeFornecedor())
				.build();
		return fornecedor;
	}
}
