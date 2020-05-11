package br.com.bluesoft.desafio.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bluesoft.desafio.model.Produto;
import br.com.bluesoft.desafio.service.ProdutoService;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }
  
    @GetMapping
    public Iterable<Produto> findAll() {
        return produtoService.findAll();
    }
    
  
}
