package br.com.bluesoft.desafio.service;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.bluesoft.desafio.dto.FornecedorDTO;

@Service
public class RestTemplateService {

	public List<FornecedorDTO> acessarApiRest(String uri) throws Exception {

		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.setAcceptCharset(Collections.singletonList(Charset.forName("UTF-8")));

			HttpEntity<String> entity = new HttpEntity<String>(headers);
			ResponseEntity<List<FornecedorDTO>> result = restTemplate.exchange(uri, HttpMethod.GET, entity,
					new ParameterizedTypeReference<List<FornecedorDTO>>() {
					});

			return result.getBody();
		} catch (Exception e) {

			throw new Exception(
					"Erro ao acessar lista de fornecedores. Tente novamente mais tarde");
		}
	}

}
