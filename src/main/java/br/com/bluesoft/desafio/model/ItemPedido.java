package br.com.bluesoft.desafio.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="item_pedido")
public class ItemPedido {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Integer quantidade;
	private Double preco;
	private Double total;
	
	@ManyToOne
	@JoinColumn(name="id_pedido")
	private Pedido pedido;
	
	@OneToOne
	@JoinColumn(name="id_produto")
	private Produto produto;

}
