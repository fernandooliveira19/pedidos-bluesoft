package br.com.bluesoft.desafio.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bluesoft.desafio.dto.FornecedorDTO;
import br.com.bluesoft.desafio.dto.PedidoDTO;
import br.com.bluesoft.desafio.model.Produto;
import br.com.bluesoft.desafio.service.ProdutoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private ProdutoService produtoService;

    @Autowired
    public PedidoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }
    

    @GetMapping
    public Iterable<Produto> findAll() {
        return produtoService.findAll();
    }
    
      
    @PostMapping("/novo")
    public void adicionarPedido(@RequestBody List<PedidoDTO> pedidos) {
    	
    		produtoService.buscarListaFornecedoresPorPedidos(pedidos);
    	
    }
}
