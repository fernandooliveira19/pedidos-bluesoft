package br.com.bluesoft.desafio.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bluesoft.desafio.dto.PedidoDTO;
import br.com.bluesoft.desafio.dto.ProdutoDTO;
import br.com.bluesoft.desafio.service.PedidoService;
import br.com.bluesoft.desafio.service.ProdutoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private ProdutoService produtoService;
    
    @Autowired
    private PedidoService pedidoService;

    @Autowired
    public PedidoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }
    

    @GetMapping
    public Iterable<PedidoDTO> findAll() {
    	
    	List<PedidoDTO> listaPedidos = new ArrayList<PedidoDTO>();
    	
    	listaPedidos = pedidoService.buscarPedidos();
    	
    	
        return listaPedidos;
    }
    
      
    @PostMapping("/novo")
    public Iterable<PedidoDTO> adicionarPedido(@RequestBody List<ProdutoDTO> produtos) {
    	
    		List<PedidoDTO> listaPedidos = produtoService.criarListaPedidosPorProdutos(produtos);

    		
            return listaPedidos;
    }
}
